package controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.SwingWorker;

import model.inventories.DAOInventoriesMovements;
import model.inventories.DAOInventoriesProductService;
import model.inventories.DAOProductProviders;
import model.inventories.JBDepartments;
import model.inventories.JBInventoriesMovements;
import model.inventories.JBInventoriesProductService;
import model.inventories.ValidateData;
import model.providers.JBProviders;
import views.JDActionProductService;
import views.JDConsultDepartments;
import views.JDConsultProviders;

public class CtrlActionProductService {
	//Attributes
	private JDActionProductService jdActionProductService;
	private DAOInventoriesProductService daoInventoriesProductsServices;
	private DAOInventoriesMovements daoInventoriesMovements;
	private CtrlConsultInventoriesServices ctrlConsultInventoriesServices;
	private CtrlConsultProviders ctrlConsultProviders;
	private CtrlConsultDepartments ctrlConsultDepartments;
	private String unit_measurement = "Pza", typeMessage = "", message = "", regexBarCode = "^$|^[\\p{L}0-9]+$", regexDescription = "^$|^[\\p{L}0-9$. '-]+$", actualBarCode = "", actualDescription = "";
	private boolean callCheckData = false, callAction = false, resultAction = false;
	private boolean checkBarCode, checkDescription, checkSalePrice, checkMinStock, root = false;
	private int id_cashier = 0, id_product_service = 0, cantBarCode = 13;
	private double actualStock = 0.0, quantity = 0.0;
	private ArrayList<String> cashierPrivileges;
	private DAOProductProviders daoProductsProviders;
	private HashMap<String, Integer> hmProviders, hmRecivedProviders;
	private ArrayList<String> alActions;
	private ArrayList<Integer> alProviders;
	
	//Method that SETS the VIEW
	public void setJDActionProductService(JDActionProductService jdAddProduct) {
		this.jdActionProductService = jdAddProduct;
	}
	//Method that SETS the DAOProducts
	public void setDAOProductService(DAOInventoriesProductService daoProducts) {
		this.daoInventoriesProductsServices = daoProducts;
	}
	//Method that SETS the DAOInventoriesMovements
	public void setDAOInventoriesMovements(DAOInventoriesMovements daoInventoriesMovements) {
		this.daoInventoriesMovements = daoInventoriesMovements;
	}
	//Method that SETS the Communication JDActionProductService - JDInventories
	public void setCtrlInventories(CtrlConsultInventoriesServices ctrlInventories) {
		this.ctrlConsultInventoriesServices = ctrlInventories;
	}
	//Method that SETS the Communication JDActionProductService - JDConsultProviders
	public void setCtrlConsultProviders(CtrlConsultProviders ctrlConsultProviders) {
		this.ctrlConsultProviders = ctrlConsultProviders;
	}
	//Method that SETS the Communication JDActionProductService - JDConsultDepartments
	public void setCtrlConsultDepartments(CtrlConsultDepartments ctrlConsultDepartments) {
		this.ctrlConsultDepartments = ctrlConsultDepartments;
	}
	//Method that SETS the DAOProductProvider
	public void setDAOProductProvider(DAOProductProviders daoProductProvider) {
		this.daoProductsProviders = daoProductProvider;
	}
	
	//Method that SETS the ID from the Actual Cashier
	public void setCashierAD(boolean root, int id_cashier, ArrayList<String> cashierPrivileges) {
		this.cashierPrivileges = cashierPrivileges;
		this.root = root;
		this.id_cashier = id_cashier;
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//Initializations
		hmProviders = new HashMap<String, Integer>();
		hmProviders.put("Proveedores Varios", 1);
		alActions = new ArrayList<String>();
		alProviders = new ArrayList<Integer>();
		//This has to be at the END
		jdActionProductService.setVisible(true);
	}
	
	//Method that SETS the LISTENERS
	private void setListeners() {		
		//JTextFields
		((AbstractDocument)jdActionProductService.jtxtfBarCode.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexBarCode, string);
				if(matches) {
					if(fb.getDocument().getLength() + string.length() <= cantBarCode) {
						super.insertString(fb, offset, string, attr);
						if(fb.getDocument().getLength() + string.length() == cantBarCode + 1) {
							jdActionProductService.jtxtfDescription.requestFocus();
						}
					}
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexBarCode, text);
			    if(matches) {
			    	if(fb.getDocument().getLength() + text.length() <= cantBarCode) {
			    		fb.replace(offset, length, text.toUpperCase(), attrs);
			    		if(fb.getDocument().getLength() + text.length() == cantBarCode + 1) {
							jdActionProductService.jtxtfDescription.requestFocus();
						}
			    	}
			    }
			}
		});
		((AbstractDocument)jdActionProductService.jtxtfDescription.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexDescription, string);
				if(matches) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexDescription, text);
			    if(matches) {
			    	super.replace(fb, offset, length, text, attrs);
			    }
			}
		});
		jdActionProductService.jtxtfJSPSellPrice.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent fe) {
				if(checkSalePrice) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							jdActionProductService.jtxtfJSPSellPrice.selectAll();
							jdActionProductService.jtxtfJSPSellPrice.setSelectedTextColor(Color.RED);
						}
					});
				}
			}
		});
		jdActionProductService.jtxtfJSPMinStock.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent fe) {
				if(checkMinStock) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							jdActionProductService.jtxtfJSPMinStock.selectAll();
							jdActionProductService.jtxtfJSPMinStock.setSelectedTextColor(Color.RED);
						}
					});
				}
			}
		});
		
		//JRadioButtons
		jdActionProductService.jrbPice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						unit_measurement = "Pza";
						enableJSpinners();
						enableActionsForProduct();
					}
				});
			}
		});
		jdActionProductService.jrbGranel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						unit_measurement = "Granel";
						enableJSpinners();
						enableActionsForProduct();
					}
				});
			}
		});
		jdActionProductService.jrbService.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						unit_measurement = "Servicio";
						disableJSpinners();
						disableActionsForService();
					}
				});
			}
		});
		
		//JButtons
		jdActionProductService.jbtnAddProvider.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				JDConsultProviders jdConsultProviders = new JDConsultProviders(jdActionProductService, "JDActionProductService");
				ctrlConsultProviders.setJDConsultProviders(jdConsultProviders);
				ctrlConsultProviders.setROOT(root);
				ctrlConsultProviders.setCashierPrivileges(cashierPrivileges);
				ctrlConsultProviders.prepareView();
			}
		});
		jdActionProductService.jbtnRemoveProvider.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				removeProvider();
			}
		});
		jdActionProductService.jbtnSelectDepartment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JDConsultDepartments jdConsultDepartments = new JDConsultDepartments(jdActionProductService);
						ctrlConsultDepartments.setJDConsultDepartments(jdConsultDepartments);
						ctrlConsultDepartments.setROOT(root);
						ctrlConsultDepartments.prepareView();
					}
				});
			}
		});
		jdActionProductService.jbtnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				checkMissingFields();
			}
		});
		jdActionProductService.jbtnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdActionProductService.dispose();
			}
		});
	}
	
	//Method that CHECKS if there's any missing field
	private void checkMissingFields() {
		SwingWorker<String, Void> checkMissingFields = new SwingWorker<String, Void>() {
			@Override
			protected String doInBackground() throws Exception {
				ValidateData validate = new ValidateData();				
				String field = validate.validateFields(getData());
				
				return field;
			}

			@Override
			protected void done() {
				try {
					String retrivedField = get();
					if(retrivedField.equals("barcode")) {
						typeMessage = "barcode";
						message = "Ingresa un Código de Barras";
						jdActionProductService.setJOPMessages(typeMessage, message);
						jdActionProductService.jtxtfBarCode.requestFocus();
					} else if(retrivedField.equals("description")) {
						typeMessage = "description";
						message = "Ingresa una Descripción";
						jdActionProductService.setJOPMessages(typeMessage, message);
						jdActionProductService.jtxtfDescription.requestFocus();
					} else if(retrivedField.equals("buyprice")) {
						typeMessage = "buyprice";
						message = "Ingresa el Precio de Compra";
						jdActionProductService.setJOPMessages(typeMessage, message);
						jdActionProductService.jtxtfJSPBuyPrice.requestFocus();
					} else if(retrivedField.equals("sellprice")) {
						typeMessage = "sellprice";
						message = "Ingresa el Precio de Venta";
						jdActionProductService.setJOPMessages(typeMessage, message);
						jdActionProductService.jtxtfJSPSellPrice.requestFocus();
					} else if(retrivedField.equals("stock")) {
						typeMessage = "stock";
						message = "Ingresa la Cantidad en Stock Actual";
						jdActionProductService.setJOPMessages(typeMessage, message);
						jdActionProductService.jtxtfJSPStock.requestFocus();
					} else if(retrivedField.equals("minstock")) {
						typeMessage = "minstock";
						message = "Ingresa la Cantidad en Stock Mínima";
						jdActionProductService.setJOPMessages(typeMessage, message);
						jdActionProductService.jtxtfJSPMinStock.requestFocus();
					} else {
						callCheckData = true;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the WORKER
		checkMissingFields.execute();
		
		//Adds a PropertyChangeListener to KNOW when the Thread is Finished
		checkMissingFields.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(checkMissingFields.isDone() && callCheckData) {
						validatesData();
						callCheckData = false;
					}
				}
			}
		});
	}
	
	//Method that CHECKS that the DATA is Correct
	private void validatesData() {
		SwingWorker<Void, Void> checkData = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				ValidateData validate = new ValidateData();
				JBInventoriesProductService jbProductService = new JBInventoriesProductService();
				jbProductService = getData();
				
				if(jdActionProductService.action.equals("Nuevo Producto")) {
					checkBarCode = validate.checkProductBarCode(jbProductService.getBarcode(), daoInventoriesProductsServices);
					if(!checkBarCode) {
						checkDescription = validate.checkProductDescription(jbProductService.getDescription(), daoInventoriesProductsServices);
						if(!checkDescription) {
							checkSalePrice = validate.checkPrices(jbProductService.getBuyprice(), jbProductService.getSellprice());
							if(!checkSalePrice) {
								if(!jbProductService.getUnitmeasurement().equals("Servicio"))
									checkMinStock = validate.checkStocks(jbProductService.getStock(), jbProductService.getMinstock());
							}
						}
					}
				} else {
					if(!jbProductService.getBarcode().equals(actualBarCode)) {
						checkBarCode = validate.checkProductBarCode(jbProductService.getBarcode(), daoInventoriesProductsServices);
					} else if(!jbProductService.getDescription().equals(actualDescription)) {
						checkDescription = validate.checkProductDescription(jbProductService.getDescription(), daoInventoriesProductsServices);
					} else {
						checkSalePrice = validate.checkPrices(jbProductService.getBuyprice(), jbProductService.getSellprice());
						if(!checkSalePrice) {
							if(!jbProductService.getUnitmeasurement().equals("Servicio"))
								checkMinStock = validate.checkStocks(jbProductService.getStock(), jbProductService.getMinstock());
						}
					}
				}
				return null;
			}

			@Override
			protected void done() {
				if(checkBarCode) {
					typeMessage = "barcodeExist";
					message = "El Código de Barras ingresado ya esta Registrado";
					jdActionProductService.setJOPMessages(typeMessage, message);
					jdActionProductService.jtxtfBarCode.requestFocus();
					jdActionProductService.jtxtfBarCode.selectAll();
					jdActionProductService.jtxtfBarCode.setSelectedTextColor(Color.RED);
					checkBarCode = false;
				} else if(checkDescription) {
					typeMessage = "descriptionExist";
					message = "La Descripción ingresada ya esta Registrada";
					jdActionProductService.setJOPMessages(typeMessage, message);
					jdActionProductService.jtxtfDescription.requestFocus();
					jdActionProductService.jtxtfDescription.selectAll();
					jdActionProductService.jtxtfDescription.setSelectedTextColor(Color.RED);
					checkDescription = false;
				} else if(checkSalePrice) {
					typeMessage = "biggerPrice";
					message = "El Precio de Venta, no debe ser MENOR ni IGUAL que el Precio de Compra";
					jdActionProductService.setJOPMessages(typeMessage, message);
					jdActionProductService.jtxtfJSPSellPrice.requestFocus();
					checkSalePrice = false;
				} else if(checkMinStock) {
					typeMessage = "biggerMinStock";
					message = "El Stock Mínimo, no debe ser MAYOR ni IGUAL que el Stock Actual";
					jdActionProductService.setJOPMessages(typeMessage, message);
					jdActionProductService.jtxtfJSPMinStock.requestFocus();
					checkMinStock = false;
				} else {
					callAction = true;
				}
			}
		};
		
		//Executes the WORKER
		checkData.execute();
		
		//Adds a PropertyChangeListener to kNOW when the Thread is Finished
		checkData.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(checkData.isDone() && callAction) {
						actionProductService();
						callAction = false;
					}
				}
			}
		});
	}
	
	//Method that SAVES the Product or Service on the DB
	private void actionProductService() {
		SwingWorker<Void, Void> actionProductService = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				boolean doAction = false; 
				boolean actionProductProvider = false;
				boolean savingInventoryMovement = false;
				JBInventoriesProductService jbProductService = getData();
				
				if(jdActionProductService.action.equals("Nuevo Producto")) {
					//Save Product Service
					doAction = daoInventoriesProductsServices.saveProductService(jbProductService);
					
					//Save Product - Provider
					if(doAction) {
						id_product_service = daoInventoriesProductsServices.getIDProductService(jbProductService.getBarcode());
						//Save Providers
						Iterator<String> it = hmProviders.keySet().iterator();
						while(it.hasNext()) {
							String key = (String) it.next();
							int id_provider = hmProviders.get(key);
							actionProductProvider = daoProductsProviders.saveProductProvider(id_product_service, id_provider);
						}
					}
				} else if(jdActionProductService.action.equals("Modificar Producto")) {
					//Update Product Service
					doAction = daoInventoriesProductsServices.updateProductService(jbProductService);
					//Save the NEW added or DELETED Providers
					if(doAction) {
						for(int r = 0; r < alActions.size(); r++) {
							if(alActions.get(r).equals("A"))
								actionProductProvider = daoProductsProviders.saveProductProvider(id_product_service, alProviders.get(r));
							else if(alActions.get(r).equals("D"))
								actionProductProvider = daoProductsProviders.deleteProvider(id_product_service, alProviders.get(r));
						}
					}
				}
				
				//Save the Inventory Movement
				Date date = new Date();
				SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
				SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm a");
				JBInventoriesMovements jbInventoryMovement = new JBInventoriesMovements();
				jbInventoryMovement.setDate(sdfDate.format(date).toString());
				jbInventoryMovement.setTime(sdfTime.format(date).toString());
				jbInventoryMovement.setIdProduct(id_product_service);
				jbInventoryMovement.setExisted(actualStock);
				if(jdActionProductService.action.equals("Nuevo Producto")) {
					jbInventoryMovement.setMovement("Entrada");
				} else if(jdActionProductService.action.equals("Modificar Producto")) {
					if(actualStock > quantity) {
						jbInventoryMovement.setMovement("Ajuste");
					} else {
						jbInventoryMovement.setMovement("Entrada");
					}
				}
				jbInventoryMovement.setQuantity(quantity);
				jbInventoryMovement.setIdCashier(id_cashier);
				savingInventoryMovement = daoInventoriesMovements.saveInventoryMovement(jbInventoryMovement);
				
				if(jdActionProductService.action.equals("Nuevo Producto") && doAction && actionProductProvider && savingInventoryMovement)
					resultAction = true;
				else if(jdActionProductService.action.equals("Modificar Producto") && doAction && actionProductProvider && savingInventoryMovement) 
					resultAction = true;
				else if(jdActionProductService.action.equals("Modificar Producto") && doAction && savingInventoryMovement)
					resultAction = true;
				
				hmProviders.clear();
				hmProviders.put("Proveedores Varios", 1);
				hmRecivedProviders.clear();
				
				return null;
			}

			@Override
			protected void done() {
				if(resultAction) {
					typeMessage = "actionCorrectly";
					if(jdActionProductService.action.equals("Nuevo Producto")) {
						ctrlConsultInventoriesServices.addRow(getData());
						ctrlConsultInventoriesServices.setTotals();
						enableJSpinners();
						clearFields();
						typeMessage = "another";
						message = "¿Desea agregar otro Producto o Servicio?";
						String answer = jdActionProductService.setJOPMessages(typeMessage, message);
						if(answer.equals("NO"))
							jdActionProductService.dispose();
					} else if(jdActionProductService.action.equals("Modificar Producto")) {
						message = "El la Información ha sido Modificada exitosamente";
						jdActionProductService.setJOPMessages(typeMessage, message);
						if(jdActionProductService.parent.equals("JDConsultProductsServices")) {
							ctrlConsultInventoriesServices.updateRow(getData());
						} else if(jdActionProductService.parent.equals("JDInventories")) {
							ctrlConsultInventoriesServices.updateRow(getData());
							ctrlConsultInventoriesServices.setTotals();
						}
						jdActionProductService.dispose();
					}
					actualStock = 0.0;
					quantity = 0.0;
				}
			}
		};
		
		//Executes the WORKER
		actionProductService.execute();
	}
	
	//Method that RETRIVES the DATA from the GUI
	private JBInventoriesProductService getData() {
		JBInventoriesProductService jbProductService = new JBInventoriesProductService();
		jbProductService.setIDProductService(id_product_service);
		jbProductService.setBarcode(jdActionProductService.jtxtfBarCode.getText());
		jbProductService.setDescription(jdActionProductService.jtxtfDescription.getText());
		jbProductService.setUnitmeasurement(unit_measurement);
		jbProductService.setIDDepartment(Integer.parseInt(jdActionProductService.jlblIDDepartment.getText()));
		jbProductService.setDepartment(jdActionProductService.jlblDepartment.getText());
		
		double iva = Double.valueOf(jdActionProductService.jspIVA.getValue().toString()) / 100;
		jbProductService.setIva(iva);
		if(jdActionProductService.jspBuyPrice.getValue() != null)
			jbProductService.setBuyprice(Double.parseDouble(jdActionProductService.jspBuyPrice.getValue().toString()));
		if(jdActionProductService.jspSalePrice.getValue() != null)
			jbProductService.setSaleprice(Double.parseDouble(jdActionProductService.jspSalePrice.getValue().toString()));
		if(jdActionProductService.jspStock.getValue() != null) {
			jbProductService.setStock(Double.parseDouble(jdActionProductService.jspStock.getValue().toString()));
			quantity = Double.parseDouble(jdActionProductService.jspStock.getValue().toString());
		}
		if(jdActionProductService.jspMinStock.getValue() != null)
			jbProductService.setMinstock(Double.parseDouble(jdActionProductService.jspMinStock.getValue().toString()));
		
		return jbProductService;
	}

	//Method that SETS the DATA of the Product to Modify
	public void setDatatoModify(JBInventoriesProductService jbProductService, HashMap<String, Integer> hmProvidersProduct) {
		id_product_service = jbProductService.getIDProductService();
		jdActionProductService.jtxtfBarCode.setText(jbProductService.getBarcode());
		actualBarCode = jbProductService.getBarcode();
		jdActionProductService.jtxtfDescription.setText(jbProductService.getDescription());
		actualDescription = jbProductService.getDescription();
		jdActionProductService.jrbPice.setEnabled(false);
		jdActionProductService.jrbGranel.setEnabled(false);
		jdActionProductService.jrbService.setEnabled(false);
		
		switch(jbProductService.getUnitmeasurement()) {
			case "Pza":
				jdActionProductService.jrbPice.setEnabled(true);
				jdActionProductService.jrbPice.setSelected(true);
				unit_measurement = "Pza";
			break;
			case "Granel":
				jdActionProductService.jrbGranel.setEnabled(true);
				jdActionProductService.jrbGranel.setSelected(true);
				unit_measurement = "Granel";
			break;
			case "Servicio":
				jdActionProductService.jrbService.setEnabled(true);
				jdActionProductService.jrbService.setSelected(true);
				unit_measurement = "Servicio";
			break;
		}
		
		if(!unit_measurement.equals("Servicio")) {
			if(hmProvidersProduct.size() != 1)
				jdActionProductService.jbtnRemoveProvider.setEnabled(true);
			
			jdActionProductService.jcbProvidersList.removeAllItems();
			Iterator<String> it = hmProvidersProduct.keySet().iterator();
			while(it.hasNext()) {
			  String key = (String) it.next();
			  jdActionProductService.jcbProvidersList.addItem(key);
			}
			this.disableJSpinners();
		} else {
			this.disableActionsForService();
		}
		
		hmRecivedProviders = hmProvidersProduct;
		
		jdActionProductService.jlblIDDepartment.setText(String.valueOf(jbProductService.getIDDepartment()));
		jdActionProductService.jlblDepartment.setText(jbProductService.getDepartment());
		
		jdActionProductService.jspIVA.setValue(jbProductService.getIva() * 100);
		jdActionProductService.jspBuyPrice.setValue(jbProductService.getBuyprice());
		jdActionProductService.jspSalePrice.setValue(jbProductService.getSellprice());
		jdActionProductService.jspStock.setValue(jbProductService.getStock());
		actualStock = jbProductService.getStock();
		jdActionProductService.jspMinStock.setValue(jbProductService.getMinstock());
	}
	
	//Method that ENABLES the JSpinners Stock and MinStock
	private void enableJSpinners() {
		jdActionProductService.jspStock.setEnabled(true);
		jdActionProductService.jspMinStock.setEnabled(true);
	}
	//Method that DISABLES the JSpinners Stock and MinStock
	private void disableJSpinners() {
		jdActionProductService.jspStock.setEnabled(false);
		jdActionProductService.jspMinStock.setEnabled(false);
	}
	
	//Method that ENABLES the Actions for a Provider
	private void enableActionsForProduct() {
		jdActionProductService.jcbProvidersList.setEnabled(true);
		jdActionProductService.jbtnAddProvider.setEnabled(true);
		if(jdActionProductService.jcbProvidersList.getItemCount() != 1) {
			jdActionProductService.jbtnRemoveProvider.setEnabled(true);
		}
		jdActionProductService.jspIVA.setValue(new Integer(16));
		jdActionProductService.jspIVA.setEnabled(true);
	}
	//Method that DISABLES the Actions for a Provider
	private void disableActionsForService() {
		jdActionProductService.jcbProvidersList.setEnabled(false);
		jdActionProductService.jbtnAddProvider.setEnabled(false);
		jdActionProductService.jbtnRemoveProvider.setEnabled(false);
	}
	
	//Clear all the Fields
	private void clearFields() {
		jdActionProductService.jtxtfBarCode.setText("");
		jdActionProductService.jtxtfBarCode.requestFocus();
		jdActionProductService.jtxtfDescription.setText("");
		jdActionProductService.jrbPice.setSelected(true);
		for(int rp = 0; rp < jdActionProductService.jcbProvidersList.getItemCount(); rp++) {
			jdActionProductService.jcbProvidersList.removeItemAt(rp);
		}
		jdActionProductService.jcbProvidersList.addItem("Proveedores Varios");
		jdActionProductService.jcbProvidersList.setSelectedIndex(0);
		jdActionProductService.jlblIDDepartment.setText("1");
		jdActionProductService.jlblDepartment.setText("Sin Departamento");
		jdActionProductService.jbtnRemoveProvider.setEnabled(false);
		jdActionProductService.jspIVA.setValue(new Double(16));
		jdActionProductService.jspBuyPrice.setValue(new Double(0.0));
		jdActionProductService.jspSalePrice.setValue(new Double(0.0));
		jdActionProductService.jspStock.setValue(new Double(0.0));
		jdActionProductService.jspMinStock.setValue(new Double(0.0));
	}
	
	//Method that ADDS the Data from the Selected Provider
	public void addProvider(JBProviders jbSelectedProvider) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				boolean alreadyAdded = false;
				int providerAlreadyAdded = 0;
				
				for(int rpl = 0; rpl < jdActionProductService.jcbProvidersList.getItemCount(); rpl++) {
					if(jdActionProductService.jcbProvidersList.getItemAt(rpl).equals(jbSelectedProvider.getName())) {
						alreadyAdded = true;
						providerAlreadyAdded = rpl;
						break;
					}
				}
				
				if(!alreadyAdded) {
					jdActionProductService.jcbProvidersList.addItem(jbSelectedProvider.getName());
					jdActionProductService.jcbProvidersList.setSelectedItem(jbSelectedProvider.getName());
					
					if(jdActionProductService.action.equals("Nuevo Producto")) {
						hmProviders.put(jbSelectedProvider.getName(), jbSelectedProvider.getIDProvider());
					} else if(jdActionProductService.action.equals("Modificar Producto")) {
						alActions.add("A");
						alProviders.add(jbSelectedProvider.getIDProvider());
					}
					
					jdActionProductService.jbtnRemoveProvider.setEnabled(true);
				} else {
					jdActionProductService.jcbProvidersList.setSelectedIndex(providerAlreadyAdded);
				}
			}
		});
	}
	
	//Method that REMOVES the Selected Provider
	private void removeProvider() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				int selectedProvider = jdActionProductService.jcbProvidersList.getSelectedIndex();
				String key = jdActionProductService.jcbProvidersList.getItemAt(selectedProvider).toString();
				jdActionProductService.jcbProvidersList.removeItemAt(selectedProvider);
				
				if(jdActionProductService.action.equals("Nuevo Producto")) {
					hmProviders.remove(key);
					
					if(hmProviders.size() == 1)
						jdActionProductService.jbtnRemoveProvider.setEnabled(false);
					else
						jdActionProductService.jbtnRemoveProvider.setEnabled(true);
					
				} else if(jdActionProductService.action.equals("Modificar Producto")) {
					alActions.add("D");
					alProviders.add(hmRecivedProviders.get(key));
					
					if(jdActionProductService.jcbProvidersList.getItemCount() == 1)
						jdActionProductService.jbtnRemoveProvider.setEnabled(false);
					else
						jdActionProductService.jbtnRemoveProvider.setEnabled(true);
				}
			}
		});
	}
	
	public void setSelectedDepartment(JBDepartments jbDepartment) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				jdActionProductService.jlblDepartment.setText(jbDepartment.getDepartment());
				jdActionProductService.jlblIDDepartment.setText(String.valueOf(jbDepartment.getIDDepartment()));
			}
		});
	}
}