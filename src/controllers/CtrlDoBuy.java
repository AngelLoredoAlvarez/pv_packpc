package controllers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import model.buys.DAOBuys;
import model.buys.DAODetailsBuys;
import model.buys.JBBuys;
import model.buys.JBDetailsBuys;
import model.cashiers.DAOCashiers;
import model.cashiers.JBCashiers;
import model.inventories.DAOInventoriesProductService;
import model.inventories.DAOProductProviders;
import model.inventories.JBInventoriesProductService;
import model.inventories.ValidateData;
import model.providers.JBProviders;
import model.till.DAOTill;
import views.JDActionInventory;
import views.JDDoBuy;
import views.JDConsultInventoriesServices;
import views.JDConsultProviders;

public class CtrlDoBuy {
	//Attributes
	private static JDDoBuy jdDoBuy;
	private CtrlConsultProviders ctrlConsultProviders;
	private String regex = "^$|^[0-9]+$", barcode = "", typeMessage = "", message = "";
	private int id_cashier = 0, selectedRow = 0;
	private boolean root = false, callSaveBuy = false;;
	private ArrayList<String> cashierPrivileges;
	private CtrlBuys ctrlBuys;
	private CtrlConsultInventoriesServices ctrlConsultInventoriesServices;
	private CtrlActionInventory ctrlActionInventory;
	private static JBInventoriesProductService jbInventoriesProductService;
	private static DecimalFormat dfInteger = new DecimalFormat("#,###,##0");
	private static DecimalFormat dfDecimals = new DecimalFormat("#,###,##0.00");
	private DAOInventoriesProductService daoInventoriesProductService;
	private DAOProductProviders daoProductProviders;
	private JBInventoriesProductService jbProduct;
	private static double total = 0.0;
	private DAOBuys daoBuys;
	private DAODetailsBuys daoDetailsBuys;
	private ArrayList<Double> alActualPriceProduct;
	private DAOTill daoTill;
	private JBBuys jbBuy;
	private DAOCashiers daoCashiers;
	
	//Method that SETS the VIEW
	public void setJDDoBuy(JDDoBuy jdDoBuy) {
		CtrlDoBuy.jdDoBuy = jdDoBuy;
	}
	
	//Method that SETS the COmmunication JDDoBuy - JDBuys
	public void setCtrlBuys(CtrlBuys ctrlBuys) {
		this.ctrlBuys = ctrlBuys;
	}
	//Method that SETS the Communication JDBuys - JDConsultProviders
	public void setCtrlConsultProviders(CtrlConsultProviders ctrlConsultProviders) {
		this.ctrlConsultProviders = ctrlConsultProviders;
	}
	//Method that SETS the Communication JDBuys - JDConsultInventories
	public void setCtrlConsultInventories(CtrlConsultInventoriesServices ctrlConsultInventories) {
		this.ctrlConsultInventoriesServices = ctrlConsultInventories;
	}
	//Method that SETS the Communication JDBuys - JDActionInventory
	public void setCtrlActionInventory(CtrlActionInventory ctrlActionInventory) {
		this.ctrlActionInventory = ctrlActionInventory;
	}
	
	//Method that SETS the DAOInventoriesProducts
	public void setDAOInventoriesProducts(DAOInventoriesProductService daoInventoriesProducts) {
		this.daoInventoriesProductService = daoInventoriesProducts;
	}
	//Method that SETS the DAOProductProviders
	public void setDAOProductProviders(DAOProductProviders daoProductProviders) {
		this.daoProductProviders = daoProductProviders;
	}
	//Method that SETS the DAOBuys
	public void setDAOBuys(DAOBuys daoBuys) {
		this.daoBuys = daoBuys;
	}
	//Method that SETS the DAODetailsBuys
	public void setDAODetailsBuys(DAODetailsBuys daoDetailsBuys) {
		this.daoDetailsBuys = daoDetailsBuys;
	}
	//Method that SETS the DAOTill
	public void setDAOTill(DAOTill daoTill) {
		this.daoTill = daoTill;
	}
	//Method that SETS the DAOCashiers
	public void setDAOCashiers(DAOCashiers daoCashiers) {
		this.daoCashiers = daoCashiers;
	}
	
	//Method that SETS the Cashier Privileges
	public void setDataForCashier(boolean root, int id_cashier, ArrayList<String> cashierPrivileges) {
		this.root = root;
		this.id_cashier = id_cashier;
		this.cashierPrivileges = cashierPrivileges;
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//Initializations
		alActualPriceProduct = new ArrayList<Double>();
		//This has to be at the END
		jdDoBuy.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JButtons
		jdDoBuy.jbtnSearchProvider.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JDConsultProviders jdConsultProviders = new JDConsultProviders(jdDoBuy, "JDBuys");
						ctrlConsultProviders.setJDConsultProviders(jdConsultProviders);
						ctrlConsultProviders.setROOT(root);
						ctrlConsultProviders.setCashierPrivileges(cashierPrivileges);
						ctrlConsultProviders.prepareView();
					}
				});
			}
		});
		jdDoBuy.jbtnSearchProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JDConsultInventoriesServices jdConsultInventories = new JDConsultInventoriesServices(jdDoBuy, "JDBuys");
						ctrlConsultInventoriesServices.setJDInventories(jdConsultInventories);
						ctrlConsultInventoriesServices.setIDCashier(id_cashier);
						ctrlConsultInventoriesServices.setROOT(root);
						ctrlConsultInventoriesServices.setCashierPrivileges(cashierPrivileges);
						ctrlConsultInventoriesServices.setIDProvider(Integer.parseInt(jdDoBuy.jlblNoProvider.getText()));
						ctrlConsultInventoriesServices.prepareView();
					}
				});
			}
		});
		jdDoBuy.jbtnRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						total = total - Double.parseDouble(jdDoBuy.dtmProducts.getValueAt(selectedRow, 4).toString().replace(",", ""));
						jdDoBuy.jlblTotalToPay.setText(String.valueOf(dfDecimals.format(total)));
						jdDoBuy.dtmProducts.removeRow(selectedRow);
						
						int quantityRows = jdDoBuy.jtProducts.getRowCount();
						if(quantityRows == 0) {
							jdDoBuy.jbtnRemove.setEnabled(false);
							jdDoBuy.jbtnRegisterBuy.setEnabled(false);
						} else {
							quantityRows -= 1;
							jdDoBuy.jtProducts.setRowSelectionInterval(quantityRows, quantityRows);
						}
						
						jdDoBuy.jtxtfBarCode.requestFocus();
					}
				});
			}
		});
		jdDoBuy.jbtnRegisterBuy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				checkBuy();
			}
		});
		jdDoBuy.jbtnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				total = 0.0;
				jdDoBuy.dispose();
			}
		});
		
		//JTextField
		((AbstractDocument)jdDoBuy.jtxtfBarCode.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regex, string);
				if(matches) {
					super.insertString(fb, offset, string, attr);
					if(fb.getDocument().getLength() + string.length() == 14) {
						addToList();
					}
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regex, text);
			    if(matches) {
			    	fb.replace(offset, length, text, attrs);
			    	if(fb.getDocument().getLength() + text.length() == 14) {
			    		addToList();
					}
			    }
			}
		});
		
		//JTable
		jdDoBuy.jtProducts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				if(!lse.getValueIsAdjusting()) {
					selectedRow = jdDoBuy.jtProducts.getSelectedRow();
					if(selectedRow >= 0) {
						SwingWorker<Void, Void> getPSData = new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								barcode = jdDoBuy.dtmProducts.getValueAt(selectedRow, 1).toString();
								jbInventoriesProductService = daoInventoriesProductService.getProductByBarCode(barcode);
								
								if(jbInventoriesProductService.getUnitmeasurement().equals("Pza")) {
									TableColumnModel tcm = jdDoBuy.jtProducts.getColumnModel();
									TableColumn tc = tcm.getColumn(3);
									tc.setCellEditor(new SpinnerEditorI());
								} else if(jbInventoriesProductService.getUnitmeasurement().equals("Granel")) {
									TableColumnModel tcm = jdDoBuy.jtProducts.getColumnModel();
									TableColumn tc = tcm.getColumn(3);
									tc.setCellEditor(new SpinnerEditorD());
								}
								return null;
							}

							@Override
							protected void done() {
								jdDoBuy.jtxtfBarCode.requestFocus();
							}
						};
						
						//Executes the Worker
						getPSData.execute();
					}
				}
			}
		});
	}
	
	//Method that SETS the Info from the Selected Provider
	public void setProviderData(JBProviders jbProvider) {
		removePreviousRows();
		jdDoBuy.jlblNoProvider.setText(String.valueOf(jbProvider.getIDProvider()));
		jdDoBuy.jlblProvider.setText(jbProvider.getName());
		jdDoBuy.jtxtfBarCode.setEnabled(true);
		jdDoBuy.jtxtfBarCode.requestFocus();
		jdDoBuy.jbtnSearchProduct.setEnabled(true);
	}
	
	//Method that ADDS the Product directly to the List
	private void addToList() {
		SwingWorker<String, Void> addToList = new SwingWorker<String, Void>() {
			@Override
			protected String doInBackground() throws Exception {
				String result = "";
				//First, check if the Product Exist
				barcode = jdDoBuy.jtxtfBarCode.getText();
				boolean existProduct = false;
				ValidateData exist = new ValidateData();
				existProduct = exist.checkProductBarCode(barcode, daoInventoriesProductService);
				
				//If it does
				if(existProduct) {
					//Get the Product Info
					jbProduct = daoInventoriesProductService.getProductByBarCode(barcode);
					//Sets the ID's
					int id_product = jbProduct.getIDProductService();
					int id_provider = Integer.parseInt(jdDoBuy.jlblNoProvider.getText());
					//Then, check y the Product is providers by the Selected Provider
					boolean checking = daoProductProviders.checkIfProductIsProviderByProvider(id_product, id_provider);
					//Sets the Result
					if(checking)
						result = "allOK";
					else
						result = "noMatch";
				}
				//If it doesn't
				else {
					result = "notExist";
				}
				
				return result;
			}

			@Override
			protected void done() {
				try {
					String resultChecking = get();
					if(resultChecking.equals("allOK")) {
						callJDActionInventory(jbProduct);
					} else if(resultChecking.equals("noMatch")) {
						typeMessage = "noMatch";
						message = "El Producto no es Provisto por el Proveedor seleccionado";
						jdDoBuy.setJOPMessages(typeMessage, message);
					} else if(resultChecking.equals("notExist")) {
						typeMessage = "notExist";
						message = "El Producto Ingresado no esta Registrado";
						jdDoBuy.setJOPMessages(typeMessage, message);
					} 
					clearBarCodeField();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the WORKER
		addToList.execute();
	}
	
	//Method that calls the JDActionInventory to get the Quantity to add to the List
	public void callJDActionInventory(JBInventoriesProductService jbInventoriesProductService) {
		//Temporary save the Actual Price from the Selected Product
		alActualPriceProduct.add(jbInventoriesProductService.getBuyprice());
		//Call the JDActionInventory
		String icon = "/img/jframes/add.png";
		String title = "Cantidad de Producto a Comprar";
		String unit_measurement = jbInventoriesProductService.getUnitmeasurement();
		String father = "JDBuys";
		JDActionInventory jdActionInventory = new JDActionInventory(jdDoBuy, icon, title, father, unit_measurement);
		ctrlActionInventory.setJDActionInventory(jdActionInventory);
		ctrlActionInventory.setDataForBuy(jbInventoriesProductService);
		ctrlActionInventory.prepareView();
	}
	
	private boolean alreadyAdded() {
		boolean alreadyAdded = false;
		
		for(int rr = 0; rr < jdDoBuy.dtmProducts.getRowCount(); rr++) {
			String description = jdDoBuy.dtmProducts.getValueAt(rr, 2).toString();
			if(description.equals(jbInventoriesProductService.getDescription())) {
				alreadyAdded = true;
				jdDoBuy.jtProducts.setRowSelectionInterval(rr, rr);
				break;
			}
		}
		
		return alreadyAdded;
	}
	
	//Method that ADDS the Row on the JTable Products
	public void addProductToList(JBInventoriesProductService jbInventoriesProducts, double quantity, double importe) {
		CtrlDoBuy.jbInventoriesProductService = jbInventoriesProducts;
		if(alreadyAdded()) {
			double actualQuantity = Double.parseDouble(jdDoBuy.dtmProducts.getValueAt(selectedRow, 3).toString());
			double actualImport = Double.parseDouble(jdDoBuy.dtmProducts.getValueAt(selectedRow, 4).toString());
			double newQuantity = actualQuantity + quantity;
			double newImport = actualImport + importe;
			
			if(jbInventoriesProducts.getUnitmeasurement().equals("Pza"))
				jdDoBuy.dtmProducts.setValueAt(dfInteger.format(newQuantity), selectedRow, 3);
			else if(jbInventoriesProducts.getUnitmeasurement().equals("Granel"))
				jdDoBuy.dtmProducts.setValueAt(dfDecimals.format(newQuantity), selectedRow, 3);
			
			jdDoBuy.dtmProducts.setValueAt(dfDecimals.format(newImport), selectedRow, 4);
		} else {
			if(jbInventoriesProducts.getUnitmeasurement().equals("Pza")) {
				jdDoBuy.dtmProducts.addRow(new Object[] {
					jbInventoriesProducts.getIDProductService(),
					jbInventoriesProducts.getBarcode(),
					jbInventoriesProducts.getDescription(),
					dfInteger.format(quantity),
					dfDecimals.format(importe)
				});
				TableColumnModel tcm = jdDoBuy.jtProducts.getColumnModel();
				TableColumn tc = tcm.getColumn(3);
				tc.setCellEditor(new SpinnerEditorI());
			} else if(jbInventoriesProducts.getUnitmeasurement().equals("Granel")) {
				jdDoBuy.dtmProducts.addRow(new Object[] {
					jbInventoriesProducts.getIDProductService(),
					jbInventoriesProducts.getBarcode(),
					jbInventoriesProducts.getDescription(),
					quantity,
					dfDecimals.format(importe)
				});
				TableColumnModel tcm = jdDoBuy.jtProducts.getColumnModel();
				TableColumn tc = tcm.getColumn(3);
				tc.setCellEditor(new SpinnerEditorD());
			}
			jdDoBuy.jbtnRemove.setEnabled(true);
			jdDoBuy.jbtnRegisterBuy.setEnabled(true);
			int lastRow = jdDoBuy.jtProducts.getRowCount() - 1;
			jdDoBuy.jtProducts.setRowSelectionInterval(lastRow, lastRow);
			
		}
		
		jdDoBuy.jtxtfBarCode.requestFocus();
		total = total + importe;
		jdDoBuy.jlblTotalToPay.setText(dfDecimals.format(total));
		clearBarCodeField();
	}
	
	//Class that SETS the Integer Model
	public static class SpinnerEditorI extends DefaultCellEditor {
		private static final long serialVersionUID = 1L;
		private JSpinner spinner;
        private JSpinner.DefaultEditor editor;
        private JTextField textField;
        private boolean valueSet;
        private int valueBE = 0, slrBE = 0;

        public SpinnerEditorI() {
            super(new JTextField());
            SpinnerModel spmQuantity = new SpinnerNumberModel(1, 1, 1000, 1);
            spinner = new JSpinner(spmQuantity);
            JSpinner.NumberEditor jspneSpinner = new JSpinner.NumberEditor(spinner, "0");
            spinner.setEditor(jspneSpinner);
            editor = ((JSpinner.DefaultEditor)spinner.getEditor());
            textField = editor.getTextField();
            
            spinner.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent ce) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							//Update GUI
							slrBE = jdDoBuy.jtProducts.getSelectedRow();
							int quantity = Integer.parseInt(spinner.getValue().toString());
			                double price = jbInventoriesProductService.getBuyprice();
			                double totalPrice = quantity * price;
			                jdDoBuy.dtmProducts.setValueAt(dfDecimals.format(totalPrice), slrBE, 4);
			                
			                double ip = 0.0;
			                for(int gi = 0; gi < jdDoBuy.dtmProducts.getRowCount(); gi++) { 
			                	ip += Double.parseDouble(jdDoBuy.dtmProducts.getValueAt(gi, 4).toString().replace(",", ""));
			                }
			                
			                total = ip;
			                
			                jdDoBuy.jtxtfBarCode.requestFocus();
			                jdDoBuy.jlblTotalToPay.setText(dfDecimals.format(ip));
						}
		        	});
				}
            });
            
            textField.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent fe) {
                    SwingUtilities.invokeLater( new Runnable() {
                        public void run() {
                            if (valueSet) {
                                textField.setCaretPosition(1);
                            }
                        }
                    });
                }
            });
            
            textField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae ) {
                    stopCellEditing();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (!valueSet) {
                spinner.setValue(Integer.parseInt(value.toString()));
            }
            
            SwingUtilities.invokeLater( new Runnable() {
                public void run() {
                    textField.requestFocus();
                }
            });
            
            return spinner;
        }

        public boolean isCellEditable(EventObject eo) {
            if (eo instanceof KeyEvent) {
                KeyEvent ke = (KeyEvent)eo;
                System.err.println("key event: "+ke.getKeyChar());
                textField.setText(String.valueOf(ke.getKeyChar()));
                valueSet = true;
            } else {
                valueSet = false;
            }
            
            return true;
        }

        public Object getCellEditorValue() {
            return spinner.getValue();
        }

        public boolean stopCellEditing() {
        	valueBE = Integer.parseInt(textField.getText());
            try {
                editor.commitEdit();
                spinner.commitEdit();
                jdDoBuy.jtxtfBarCode.requestFocus();
            } catch (java.text.ParseException e) {
            	JOptionPane.showMessageDialog(jdDoBuy, "El Valor ingresado exede el Límite o es Invalido");
                textField.setText(String.valueOf(valueBE));
            }
            
            return super.stopCellEditing();
        }
	}
	
	//Class that SETS the Decimal Model
	public static class SpinnerEditorD extends DefaultCellEditor {
		private static final long serialVersionUID = 1L;
		private JSpinner spinner;
        private JSpinner.DefaultEditor editor;
        private JTextField textField;
        private boolean valueSet;
        private int slrBE = 0;
        private double valueBE = 0;

        public SpinnerEditorD() {
            super(new JTextField());
            SpinnerModel spmQuantity = new SpinnerNumberModel(1.0, 1.0, 1000.0, .1);
            spinner = new JSpinner(spmQuantity);
            JSpinner.NumberEditor jspneSpinner = new JSpinner.NumberEditor(spinner, "0.0");
            spinner.setEditor(jspneSpinner);
            editor = ((JSpinner.DefaultEditor)spinner.getEditor());
            textField = editor.getTextField();
            
            spinner.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent ce) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							//Update GUI
							slrBE = jdDoBuy.jtProducts.getSelectedRow();
							double quantity = Double.parseDouble(spinner.getValue().toString());
				            double price = jbInventoriesProductService.getBuyprice();
				            double totalPrice = quantity * price;
				            jdDoBuy.dtmProducts.setValueAt(dfDecimals.format(totalPrice), slrBE, 4);
				                
				            double ip = 0.0;
				            for(int gi = 0; gi < jdDoBuy.dtmProducts.getRowCount(); gi++) { 
				             	ip += Double.parseDouble(jdDoBuy.dtmProducts.getValueAt(gi, 4).toString().replace(",", ""));
				            }
				            
				            total = ip;
				            
				            jdDoBuy.jtxtfBarCode.requestFocus();
				            jdDoBuy.jlblTotalToPay.setText(dfDecimals.format(ip));
						}
		        	});
				}
            });
            
            textField.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent fe) {
                    SwingUtilities.invokeLater( new Runnable() {
                        public void run() {
                            if (valueSet) {
                                textField.setCaretPosition(1);
                            }
                        }
                    });
                }
            });
            
            textField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae ) {
                    stopCellEditing();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (!valueSet) {
                spinner.setValue(Double.parseDouble(value.toString()));
            }
            
            SwingUtilities.invokeLater( new Runnable() {
                public void run() {
                    textField.requestFocus();
                }
            });
            
            return spinner;
        }

        public boolean isCellEditable( EventObject eo ) {
            if (eo instanceof KeyEvent) {
                KeyEvent ke = (KeyEvent)eo;
                System.err.println("key event: "+ke.getKeyChar());
                textField.setText(String.valueOf(ke.getKeyChar()));
                valueSet = true;
            } else {
                valueSet = false;
            }
            
            return true;
        }

        // Returns the spinners current value.
        public Object getCellEditorValue() {
            return spinner.getValue();
        }

        public boolean stopCellEditing() {
        	valueBE = Double.parseDouble(textField.getText());
            try {
                editor.commitEdit();
                spinner.commitEdit();
                jdDoBuy.jtxtfBarCode.requestFocus();
            } catch (java.text.ParseException e) {
            	JOptionPane.showMessageDialog(jdDoBuy, "El Valor ingresado exede el Límite o es Invalido");
                textField.setText(String.valueOf(valueBE));
            }
            
            return super.stopCellEditing();
        }
	}
	
	//Method that CHECK if the BUY can be DONE
	private void checkBuy() {
		SwingWorker<Boolean, Void> checkBuy = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				boolean biggerTotal = false;
				
				double actualBalance = daoTill.getActualBalance();
				
				if(total > actualBalance)
					biggerTotal = true;
				
				return biggerTotal;
			}

			@Override
			protected void done() {
				try {
					boolean result = get();
					if(result) {
						typeMessage = "biggerTotal";
						message = "El Total de la Compra sobrepasa a el Total en Caja";
						jdDoBuy.setJOPMessages(typeMessage, message);
					} else {
						callSaveBuy = true;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Add a PropertyChangeListener to KNOW when the Thread is Finished
		checkBuy.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(checkBuy.isDone() && callSaveBuy) {
						saveBuy();
						callSaveBuy = false;
					}
				}
			}
		});
		
		//Executes the WORKER
		checkBuy.execute();
	}
	
	//Method that SAVES the Buy
	private void saveBuy() {
		SwingWorker<Boolean, Void> saveBuy = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				//Gets DATE and TIME
				boolean allOK = false;
				boolean saveBuy = false;
				boolean saveDetail = false;
				boolean updateStock = false;
				boolean updateBalance = false;
				
				Date actualDateTime = new Date();
				SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
				SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss aa");
				
				jbBuy = new JBBuys();
				jbBuy.setDate(sdfDate.format(actualDateTime).toString());
				jbBuy.setTime(sdfTime.format(actualDateTime).toString());
				jbBuy.setTotal(total);
				jbBuy.setIDProvider(Integer.parseInt(jdDoBuy.jlblNoProvider.getText()));
				jbBuy.setIDCashier(id_cashier);
				saveBuy = daoBuys.saveBuy(jbBuy);
				
				if(saveBuy) {
					int id_buy = daoBuys.getIDBuy(jbBuy);
					for(int gp = 0; gp < jdDoBuy.dtmProducts.getRowCount(); gp++) {
						JBDetailsBuys jbDetailBuy = new JBDetailsBuys();
						jbDetailBuy.setIDProduct(Integer.parseInt(jdDoBuy.dtmProducts.getValueAt(gp, 0).toString()));
						jbDetailBuy.setActualPrice(alActualPriceProduct.get(gp));
						jbDetailBuy.setQuantity(jdDoBuy.dtmProducts.getValueAt(gp, 3).toString());
						jbDetailBuy.setImporte(Double.parseDouble(jdDoBuy.dtmProducts.getValueAt(gp, 4).toString().replace(",", "")));
						jbDetailBuy.setIDBuy(id_buy);
						saveDetail = daoDetailsBuys.saveDetail(jbDetailBuy);
						
						if(saveBuy && saveDetail) {
							int id_product = Integer.parseInt(jdDoBuy.dtmProducts.getValueAt(gp, 0).toString());
							double actualStock = daoInventoriesProductService.getActualStock(id_product);
							double quantity = Double.parseDouble(jdDoBuy.dtmProducts.getValueAt(gp, 3).toString());
							double newStock = actualStock + quantity;
							updateStock = daoInventoriesProductService.updateStock(id_product, newStock);
							
							if(saveBuy && saveDetail && updateStock) {
								int id_till = daoTill.getIDTill("Abierta");
								double actualBalance = daoTill.getActualBalance();
								double newBalance = actualBalance - total;
								updateBalance = daoTill.updateBalance(id_till, newBalance);
								
								if(saveBuy && saveDetail && updateStock && updateBalance) {
									allOK = true;
									jbBuy.setIDBuy(id_buy);
									jbBuy.setProvider(jdDoBuy.jlblProvider.getText());
									JBCashiers jbCashier = daoCashiers.getCashierName(id_cashier);
									jbBuy.setCashier(jbCashier.getNames() + " " + jbCashier.getFirstName() + " " + jbCashier.getLastName());
								}
							}
						}
					}
				}	
				
				return allOK;
			}

			@Override
			protected void done() {
				try {
					boolean response = get();
					if(response) {
						typeMessage = "buyRegistered";
						message = "La Compra ha sido Registrada";
						jdDoBuy.setJOPMessages(typeMessage, message);
						removePreviousRows();
						clearBarCodeField();
						jdDoBuy.jbtnSearchProduct.setEnabled(false);
						jdDoBuy.jbtnRemove.setEnabled(false);
						ctrlBuys.addBuy(jbBuy);
						jdDoBuy.dispose();
						total = 0.0;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the Worker
		saveBuy.execute();
	}
	
	//Clear the BarCode Field
	public void clearBarCodeField() {
		jdDoBuy.jtxtfBarCode.setText("");
		jdDoBuy.jtxtfBarCode.requestFocus();
	}
	
	//Method that Removes the ROWS on the JTable
	private void removePreviousRows() {
		if(jdDoBuy.dtmProducts.getRowCount() > 0) {
			for(int rr = jdDoBuy.dtmProducts.getRowCount() - 1; rr >= 0; rr--) {
				jdDoBuy.dtmProducts.removeRow(rr);
			}
			total = 0.0;
			jdDoBuy.jlblTotalToPay.setText("0.00");
			jdDoBuy.jbtnRemove.setEnabled(false);
			jdDoBuy.jbtnRegisterBuy.setEnabled(false);
		}
	}
}
