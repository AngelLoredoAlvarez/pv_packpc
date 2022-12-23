package controllers;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.inventories.DAODepartments;
import model.inventories.DAOInventoriesProductService;
import model.inventories.JBDepartments;
import model.inventories.JBInventoriesProductService;
import model.providers.DAOProviders;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import views.JDActionInventory;
import views.JDActionProductService;
import views.JDConsultInventoriesServices;
import views.JDInventoriesMovements;
import views.JDReports;
import views.JPVViewer;

public class CtrlConsultInventoriesServices {
	//Attributes
	public JDConsultInventoriesServices jdConsultInventoriesServices;
	private DAOInventoriesProductService daoInventoriesProductsServices;
	private DecimalFormat dfDecimals, dfPiece;
	private ArrayList<JBInventoriesProductService> alWhatToShow;
	private double inventoryCost = 0.0, totalPieceProducts = 0.0, totalGranelProducts = 0.0;
	private CtrlInventoriesMovements ctrlInventoryMovements;
	private CtrlActionInventory ctrlActionInventory;
	private CtrlActionProductService ctrlActionProductService;
	private int selectedRow = 0, id_cashier = 0, columnToFilter = 0, cantBarCode = 13, type_inventories = 0, id_provider = 0;
	private ArrayList<String> cashierPrivileges;
	private JBInventoriesProductService jbInventoriesProductService;
	private boolean searchByBarCode = true, root = false;
	private String AMProductService = "", DelProductService = "", AdjustProduct = "";
	private String whatToShow = "", regex = "^$|^[0-9]+$", unit_measurement = "", nameSheet = "", typeMessage = "", message = "";
	private String[] dataForSave = null;
	private XSSFWorkbook workbook;
	private CellStyle csData, csPrices, csIntegers, csDecimals, csEIIntegers, csLIIntegers, csEIDecimals, csLIDecimals;
	private Font fHeaders;
	private JasperReportBuilder reportInventories;
	private CtrlReports ctrlReports;
	private JPVViewer jpv;
	private CtrlDoBuy ctrlBuys;
	private DAOProviders daoProviders;
	private HashMap<String, Integer> hmProvidersProduct;
	private DAODepartments daoDepartments;
	
	//Method that SETS the View
	public void setJDInventories(JDConsultInventoriesServices jdConsultInventories) {
		this.jdConsultInventoriesServices = jdConsultInventories;
	}
	//Method that SETS the DAOProducts
	public void setDAOProducts(DAOInventoriesProductService daoProducts) {
		this.daoInventoriesProductsServices = daoProducts;
	}
	//Method that SETS the Communication JDInventories - JDInventoryMovements
	public void setCtrlInventoryMovements(CtrlInventoriesMovements ctrlInventoryMovements) {
		this.ctrlInventoryMovements = ctrlInventoryMovements;
	}
	//Method that SETS the Communication JDInventories - JDActionInventory
	public void setCtrlActionInventory(CtrlActionInventory ctrlActionInventory) {
		this.ctrlActionInventory = ctrlActionInventory;
	}
	//Method that SETS the Communication JDInventories - JDActionProductService
	public void setCtrlActionProductService(CtrlActionProductService ctrlActionProductService) {
		this.ctrlActionProductService = ctrlActionProductService;
	}
	//Method that SETS the Communication JDInventories - JDReports
	public void setCtrlReports(CtrlReports ctrlReports) {
		this.ctrlReports = ctrlReports;
	}
	//Method that SETS the Communication JDBuys - JDConsultInventories
	public void setCtrlBuys(CtrlDoBuy ctrlBuys) {
		this.ctrlBuys = ctrlBuys;
	}
	//Method that SETS the DAOProviders
	public void setDAOProviders(DAOProviders daoProviders) {
		this.daoProviders = daoProviders;
	}
	//Method that SETS the DAODepartments
	public void setDAODepartments(DAODepartments daoDepartments) {
		this.daoDepartments = daoDepartments;
	}
	
	//Method that SETS the ID Cashier
	public void setIDCashier(int id_cashier) {
		this.id_cashier = id_cashier;
	}
	//Method that SETS if it's ROOT
	public void setROOT(boolean root) {
		this.root = root;
	}
	//Method that SETS the Cashier Privileges
	public void setCashierPrivileges(ArrayList<String> cashierPrivileges) {
		if(root) {
			jdConsultInventoriesServices.jbtnNewProductService.setVisible(true);
			jdConsultInventoriesServices.jbtnModifyProductService.setVisible(true);
			jdConsultInventoriesServices.jbtnDelete.setVisible(true);
			jdConsultInventoriesServices.jbtnAdjustInventory.setVisible(true);
		} else {
			this.cashierPrivileges = cashierPrivileges;
			for(int rp = 0; rp < cashierPrivileges.size(); rp++) {
				if(cashierPrivileges.get(rp).equals("AMProduct")) {
					jdConsultInventoriesServices.jbtnNewProductService.setVisible(true);
					jdConsultInventoriesServices.jbtnModifyProductService.setVisible(true);
					AMProductService = "yes";
				} else if(cashierPrivileges.get(rp).equals("DelProduct")) {
					jdConsultInventoriesServices.jbtnDelete.setVisible(true);
					DelProductService = "yes";
				} else if(cashierPrivileges.get(rp).equals("AdjustInventory")) {
					jdConsultInventoriesServices.jbtnAdjustInventory.setVisible(true);
					AdjustProduct = "yes";
				}
			}
		}
	}
	//Method that SETS the ID Provider
	public void setIDProvider(int id_provider) {
		this.id_provider = id_provider;
	}
	
	//Method that PREPARES the View
	public void prepareView() {
		//Initializations
		dfDecimals = new DecimalFormat("#,###,##0.00");
		dfPiece = new DecimalFormat("#,###,##0");
		this.setListeners();
		//This has to be at the END
		jdConsultInventoriesServices.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JDialog
		jdConsultInventoriesServices.addWindowListener(new WindowAdapter() {
			//Window Opened
			public void windowOpened(WindowEvent we) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if(jdConsultInventoriesServices.father.equals("JDBuys")) {
							for(int ro = 0; ro < jdConsultInventoriesServices.jcbWhatToShow.getItemCount(); ro++) {
								String item = jdConsultInventoriesServices.jcbWhatToShow.getItemAt(ro).toString();
								if(item.equals("Todo") || item.equals("Todos los Servicios")) {
									jdConsultInventoriesServices.jcbWhatToShow.removeItem(item);
								}
							}
						}
					}
				});
				setTotals();
				getWhatToShow();
				getDepartments();
			}
			//Window Closed
			public void windowClosed(WindowEvent we) {
				AMProductService = "";
				DelProductService = "";
				AdjustProduct = "";
			}
		});
		
		//JComboBox
		jdConsultInventoriesServices.jcbWhatToShow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				getWhatToShow();
			}
		});
		jdConsultInventoriesServices.jcbDepartments.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				filterJTable();
			}
		});
		
		//JRadioButtons
		jdConsultInventoriesServices.jrbBarCode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				regex = "^$|^[0-9]+$";
				columnToFilter = 0;
				searchByBarCode = true;
				jdConsultInventoriesServices.jtxtfSearch.setText("");
			}
		});
		jdConsultInventoriesServices.jrbDescription.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				regex = "^$|^[\\p{L}0-9 '-]+$";
				columnToFilter = 1;
				searchByBarCode = false;
				jdConsultInventoriesServices.jtxtfSearch.setText("");
			}
		});
		
		//JTextField
		((AbstractDocument)jdConsultInventoriesServices.jtxtfSearch.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			    filterJTable();
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regex, string);
				if(matches && searchByBarCode) {
					if(fb.getDocument().getLength() + string.length() <= cantBarCode) {
						super.insertString(fb, offset, string, attr);
						filterJTable();
					}
				} else if(matches) {
					super.insertString(fb, offset, string, attr);
					filterJTable();
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regex, text);
			    if(matches && searchByBarCode) {
			    	if(fb.getDocument().getLength() + text.length() <= cantBarCode) {
			    		fb.replace(offset, length, text.toUpperCase(), attrs);
			    		filterJTable();
					}
			    } else if(matches) {
			    	super.replace(fb, offset, length, text, attrs);
			    	filterJTable();			    
			    }
			}
		});
		
		//JButtons
		jdConsultInventoriesServices.jbtnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdConsultInventoriesServices.dispose();
				whatToShow = "Todo";
			}
		});
		jdConsultInventoriesServices.jbtnNewProductService.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						String icon = "/img/jframes/add.png", title = "Nuevo Producto", parent = "JDConsultProductsServices";
						JDActionProductService jdAddProduct = new JDActionProductService(jdConsultInventoriesServices, icon, title, parent);
						ctrlActionProductService.setJDActionProductService(jdAddProduct);
						ctrlActionProductService.setCashierAD(root, id_cashier, cashierPrivileges);
						ctrlActionProductService.prepareView();
					}
				});
			}
		});
		jdConsultInventoriesServices.jbtnModifyProductService.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				getData();
			}
		});
		jdConsultInventoriesServices.jbtnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				deleteProduct();
			}
		});
		jdConsultInventoriesServices.jbtnAdjustInventory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						setDataActionInventory();
						String icon = "/img/jframes/edit.png";
						String title = "Ajustar Inventario";
						String father = "JDConsultInventories";
						String unit_measurement = jbInventoriesProductService.getUnitmeasurement();
						JDActionInventory jdActionInventory = new JDActionInventory(jdConsultInventoriesServices, icon, title, father, unit_measurement);
						ctrlActionInventory.setJDActionInventory(jdActionInventory);
						ctrlActionInventory.setData(jbInventoriesProductService, selectedRow, id_cashier);
						ctrlActionInventory.prepareView();
					}
				});
			}
		});
		jdConsultInventoriesServices.jbtnGenerateSpreadsheet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdConsultInventoriesServices.jbtnGenerateSpreadsheet.setEnabled(false);
				setNameSheet();
				jdConsultInventoriesServices.nameSheet = nameSheet;
				dataForSave = jdConsultInventoriesServices.retriveDataForSaveSpreadSheet();
				
				if(dataForSave != null) {
					if(checkFile()) {
						typeMessage = "fileExist";
						message = "Ya existe un archivo llamado '" + nameSheet + "." + dataForSave[1] + "', ¿Desea reemplazarlo?";
						int response = jdConsultInventoriesServices.setJOPMessages(typeMessage, message);
						if(response == JOptionPane.YES_OPTION) {
							generateSpreadSheet();
						}
					} else {
						generateSpreadSheet();
					}
				} else {
					jdConsultInventoriesServices.jbtnGenerateSpreadsheet.setEnabled(true);
				}
			}
		});
		jdConsultInventoriesServices.jbtnGenerateReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdConsultInventoriesServices.jbtnGenerateReport.setEnabled(false);
				setNameSheet();
				generateReport();
			}
		});
		jdConsultInventoriesServices.jbtnPrintInventories.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				setNameSheet();
				MessageFormat mfHeader = new MessageFormat(nameSheet);
				MessageFormat mfFooter = new MessageFormat("Página {0}");
				JTable.PrintMode jtpm = JTable.PrintMode.FIT_WIDTH;
				try {
					boolean printingComplete = jdConsultInventoriesServices.jtInventories.print(
												jtpm, mfHeader, mfFooter, true, null, true, null);
					if(printingComplete) {
						typeMessage = "printingOK";
						message = "Impresión Terminada Correctamente";
						jdConsultInventoriesServices.setJOPMessages(typeMessage, message);
					} else {
						typeMessage = "printingCancel";
						message = "Impresión Cancelada";
						jdConsultInventoriesServices.setJOPMessages(typeMessage, message);
					}
				} catch (PrinterException e) {
					e.printStackTrace();
				}
			}
		});
		jdConsultInventoriesServices.jbtnInventoryMovements.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JDInventoriesMovements jdInventoryMovements = new JDInventoriesMovements(jdConsultInventoriesServices);
						ctrlInventoryMovements.setJDInventoryMovements(jdInventoryMovements);
						ctrlInventoryMovements.prepareView();
					}
				});
			}
		});
		
		//JTable
		jdConsultInventoriesServices.jtInventories.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				if(!lse.getValueIsAdjusting()) {
					selectedRow = jdConsultInventoriesServices.jtInventories.getSelectedRow();
					if(selectedRow >= 0) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								unit_measurement = jdConsultInventoriesServices.dtmInventoriesServices.getValueAt(selectedRow, 2).toString();
								if(unit_measurement.equals("Servicio")) {
									if(root) {
										jdConsultInventoriesServices.jbtnModifyProductService.setEnabled(true);
										jdConsultInventoriesServices.jbtnDelete.setEnabled(true);
										jdConsultInventoriesServices.jbtnAdjustInventory.setEnabled(true);
									} else {
										//Modify Button
										if(AMProductService.equals("yes"))
											jdConsultInventoriesServices.jbtnModifyProductService.setEnabled(true);
										else
											jdConsultInventoriesServices.jbtnModifyProductService.setEnabled(false);
										//Delete Button
										if(DelProductService.equals("yes"))
											jdConsultInventoriesServices.jbtnDelete.setEnabled(true);
										else
											jdConsultInventoriesServices.jbtnDelete.setEnabled(false);
									}
									jdConsultInventoriesServices.jbtnAdjustInventory.setEnabled(false);
								} else {
									if(root) {
										jdConsultInventoriesServices.jbtnModifyProductService.setEnabled(true);
										jdConsultInventoriesServices.jbtnDelete.setEnabled(true);
										jdConsultInventoriesServices.jbtnAdjustInventory.setEnabled(true);
									} else {
										//Modify Button
										if(AMProductService.equals("yes"))
											jdConsultInventoriesServices.jbtnModifyProductService.setEnabled(true);
										else
											jdConsultInventoriesServices.jbtnModifyProductService.setEnabled(false);
										//Delete Button
										if(DelProductService.equals("yes"))
											jdConsultInventoriesServices.jbtnDelete.setEnabled(true);
										else
											jdConsultInventoriesServices.jbtnDelete.setEnabled(false);
										//Adjust Button
										if(AdjustProduct.equals("yes"))
											jdConsultInventoriesServices.jbtnAdjustInventory.setEnabled(true);
										else
											jdConsultInventoriesServices.jbtnAdjustInventory.setEnabled(false);
									}
								}
								
								//Give Focus
								jdConsultInventoriesServices.jtxtfSearch.requestFocus();
							}
						});
					}
				}
			}
		});
		jdConsultInventoriesServices.jtInventories.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
			   if (me.getClickCount() == 2) {
				   if(jdConsultInventoriesServices.father.equals("JDBuys")) {
					   getDataForBuy();
				   }
			   }
			}
		});
	}
	
	//Method that GETS all the Inventories
	public void getWhatToShow() {
		SwingWorker<Void, Void> getWhatToShow = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				whatToShow = jdConsultInventoriesServices.jcbWhatToShow.getSelectedItem().toString();
				
				switch(whatToShow) {
					case "Todo":
						alWhatToShow = daoInventoriesProductsServices.getEverything();
					break;
						
					case "Productos en Inventario":
						if(jdConsultInventoriesServices.father.equals("JDBuys"))
							alWhatToShow = daoInventoriesProductsServices.getProductsByProvider(id_provider);
						else
							alWhatToShow = daoInventoriesProductsServices.getProducts();
					break;
						
					case "Productos por Agotarse":
						if(jdConsultInventoriesServices.father.equals("JDBuys"))
							alWhatToShow = daoInventoriesProductsServices.getInventoriesToFinishByProvider(id_provider);
						else
							alWhatToShow = daoInventoriesProductsServices.getInventoriesToFinish();
					break;
						
					case "Todos los Servicios":
						alWhatToShow = daoInventoriesProductsServices.getServices();
					break;
				}
				
				return null;
			}

			@Override
			protected void done() {
				removePreviousRows();
				addDataFromDB();
			}
		};
		
		//Executes the WORKER
		getWhatToShow.execute();
	}
	
	//Method that SETS the Departments
	public void getDepartments() {
		SwingWorker<ArrayList<JBDepartments>, Void> getDepartments = new SwingWorker<ArrayList<JBDepartments>, Void>() {
			@Override
			protected ArrayList<JBDepartments> doInBackground() throws Exception {
				ArrayList<JBDepartments> alDepartments = daoDepartments.getDepartments();
				
				return alDepartments;
			}

			@Override
			protected void done() {
				try {
					ArrayList<JBDepartments> retrivedDepartments = get();
					
					//Remove Previous Departments
					jdConsultInventoriesServices.jcbDepartments.removeAllItems();
					jdConsultInventoriesServices.jcbDepartments.addItem("Todos");
					
					//Add the New Departments
					for(int ad = 0; ad < retrivedDepartments.size(); ad++) {
						jdConsultInventoriesServices.jcbDepartments.addItem(retrivedDepartments.get(ad).getDepartment());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				
			}
		};
		
		//Executes the WORKER
		getDepartments.execute();
	}
	
	//Method that GETS the DATA from the Product, and SEND IT to the JDActionProductService
	private void getData() {
		SwingWorker<Void, Void> getData = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				String barcode = jdConsultInventoriesServices.dtmInventoriesServices.getValueAt(selectedRow, 0).toString();
				jbInventoriesProductService = daoInventoriesProductsServices.getProductByBarCode(barcode);
				hmProvidersProduct = daoProviders.getProvidersProduct(jbInventoriesProductService.getIDProductService());
				return null;
			}

			@Override
			protected void done() {
				String icon = "/img/jframes/edit.png", title = "Modificar Producto", parent = "JDInventories";
				JDActionProductService jdActionProductService = new JDActionProductService(jdConsultInventoriesServices, icon, title, parent);
				ctrlActionProductService.setJDActionProductService(jdActionProductService);
				ctrlActionProductService.setCashierAD(root, id_cashier, cashierPrivileges);
				ctrlActionProductService.setDatatoModify(jbInventoriesProductService, hmProvidersProduct);
				ctrlActionProductService.prepareView();
			}
		};
		
		//Executes the WORKER
		getData.execute();
	}
	
	//Method that GETS the Data from the Selected Product, and Send it to the JDBuys
	private void getDataForBuy() {
		SwingWorker<JBInventoriesProductService, Void> getDataForBuy = new SwingWorker<JBInventoriesProductService, Void>() {
			@Override
			protected JBInventoriesProductService doInBackground() throws Exception {
				JBInventoriesProductService jbInventoriesProductService = new JBInventoriesProductService();
				String barcode = jdConsultInventoriesServices.dtmInventoriesServices.getValueAt(selectedRow, 0).toString();
				jbInventoriesProductService = daoInventoriesProductsServices.getProductByBarCode(barcode);
				return jbInventoriesProductService;
			}

			@Override
			protected void done() {
				try {
					JBInventoriesProductService retrivedJBInventoriesProducts = get();
					jdConsultInventoriesServices.dispose();
					ctrlBuys.callJDActionInventory(retrivedJBInventoriesProducts);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the WORKER
		getDataForBuy.execute();
	}
	
	//Method that SETS the TOTAL of Inventories according the Type of Product
	public void setTotals() {
		SwingWorker<Void, Void> setTotals = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				ArrayList<JBInventoriesProductService> alGetInventoryCost = new ArrayList<JBInventoriesProductService>();
				alGetInventoryCost = daoInventoriesProductsServices.getInventoryCost();
				for(int gic = 0; gic < alGetInventoryCost.size(); gic++) {
					JBInventoriesProductService jbProduct = new JBInventoriesProductService();
					jbProduct = alGetInventoryCost.get(gic);
					double buy_price = jbProduct.getBuyprice();
					double stock = jbProduct.getStock();
					inventoryCost = inventoryCost + (buy_price * stock);
				}
				totalPieceProducts = daoInventoriesProductsServices.getTotalProducts("Pza");
				totalGranelProducts = daoInventoriesProductsServices.getTotalProducts("Granel");
				return null;
			}

			@Override
			protected void done() {
				jdConsultInventoriesServices.jlblInventoryCost.setText("$" + dfDecimals.format(inventoryCost));
				jdConsultInventoriesServices.jlblTotalPieceProducts.setText(dfPiece.format(totalPieceProducts));
				jdConsultInventoriesServices.jlblTotalGranelProducts.setText(dfDecimals.format(totalGranelProducts));
				//Reset Variables
				inventoryCost = 0.0;
			}
		};
		//Executes the WORKER
		setTotals.execute();
	}
	
	//Method that REMOVES the Previous ROWS
	private void removePreviousRows() {
		for(int rr = jdConsultInventoriesServices.dtmInventoriesServices.getRowCount() - 1; rr >= 0; rr--) {
			jdConsultInventoriesServices.dtmInventoriesServices.removeRow(rr);
		}
	}
	
	//Method that ADDS the ROWS of DATA from the DB
	private void addDataFromDB() {
		if(!alWhatToShow.isEmpty()) {
			for(int ai = 0; ai < alWhatToShow.size(); ai++) {
				JBInventoriesProductService jbProductService = new JBInventoriesProductService();
				jbProductService = alWhatToShow.get(ai);
				if(jbProductService.getUnitmeasurement().equals("Pza")) {
					jdConsultInventoriesServices.dtmInventoriesServices.addRow(new Object[] {
						jbProductService.getBarcode(),
						jbProductService.getDescription(),
						jbProductService.getUnitmeasurement(),
						jbProductService.getDepartment(),
						"$" + dfDecimals.format(jbProductService.getBuyprice()),
						"$" + dfDecimals.format(jbProductService.getSellprice()),
						dfPiece.format(jbProductService.getMinstock()),
						dfPiece.format(jbProductService.getStock())
					});
				} else if(jbProductService.getUnitmeasurement().equals("Granel")) {
					jdConsultInventoriesServices.dtmInventoriesServices.addRow(new Object[] {
						jbProductService.getBarcode(),
						jbProductService.getDescription(),
						jbProductService.getUnitmeasurement(),
						jbProductService.getDepartment(),
						"$" + dfDecimals.format(jbProductService.getBuyprice()),
						"$" + dfDecimals.format(jbProductService.getSellprice()),
						dfDecimals.format(jbProductService.getMinstock()),
						dfDecimals.format(jbProductService.getStock())
					});
				} else if(jbProductService.getUnitmeasurement().equals("Servicio")) {
					jdConsultInventoriesServices.dtmInventoriesServices.addRow(new Object[] {
						jbProductService.getBarcode(),
						jbProductService.getDescription(),
						jbProductService.getUnitmeasurement(),
						jbProductService.getDepartment(),
						"$" + dfDecimals.format(jbProductService.getBuyprice()),
						"$" + dfDecimals.format(jbProductService.getSellprice())
					});
				}
			}
			enableJButtons();
		} else {
			disableJButtons();
		}
	}
	
	//Method that ADDS the Row with the new Added Product
	public void addRow(JBInventoriesProductService jbProduct) {
		String minStock = "", stock = "";
		if(jbProduct.getUnitmeasurement().equals("Pza")) {
			minStock = dfPiece.format(jbProduct.getMinstock());
			stock = dfPiece.format(jbProduct.getStock());
		} else if(jbProduct.getUnitmeasurement().equals("Granel")) {
			minStock = dfDecimals.format(jbProduct.getMinstock());
			stock = dfDecimals.format(jbProduct.getStock());
		}
		
		jdConsultInventoriesServices.dtmInventoriesServices.addRow(new Object[] {
			jbProduct.getBarcode(),
			jbProduct.getDescription(),
			jbProduct.getUnitmeasurement(),
			jbProduct.getDepartment(),
			"$" + dfDecimals.format(jbProduct.getBuyprice()),
			"$" + dfDecimals.format(jbProduct.getSellprice()),
			minStock,
			stock
		});
		
		jdConsultInventoriesServices.jtxtfSearch.requestFocus();
		int cantRows = jdConsultInventoriesServices.jtInventories.getRowCount() - 1; 
		Rectangle rect = jdConsultInventoriesServices.jtInventories.getCellRect(cantRows, 0, true);
		jdConsultInventoriesServices.jtInventories.scrollRectToVisible(rect);
		jdConsultInventoriesServices.jtInventories.clearSelection();
		jdConsultInventoriesServices.jtInventories.setRowSelectionInterval(cantRows, cantRows);
		enableJButtons();
	}
	
	//Method that DELETS the SELECTED Product
	private void deleteProduct() {
		typeMessage = "delete";
		message = "¿Desea eliminar el producto: " + jdConsultInventoriesServices.jtInventories.getValueAt(selectedRow, 1).toString() + "?";
		int result = jdConsultInventoriesServices.setJOPMessages(typeMessage, message);
		if(result == JOptionPane.YES_OPTION) {
			String barcode = jdConsultInventoriesServices.jtInventories.getValueAt(selectedRow, 0).toString();
			SwingWorker<Boolean, Void> deleteProduct = new SwingWorker<Boolean, Void>() {
				@Override
				protected Boolean doInBackground() throws Exception {
					boolean result = daoInventoriesProductsServices.deleteProductService(barcode);
					return result;
				}
				
				@Override
				protected void done() {
					boolean response;
					try {
						response = get();
						if(response) {
							jdConsultInventoriesServices.dtmInventoriesServices.removeRow(selectedRow);
							jdConsultInventoriesServices.jtxtfSearch.setText("");
							jdConsultInventoriesServices.jtxtfSearch.requestFocus();
							if(jdConsultInventoriesServices.jtInventories.getRowCount() != 0)
								jdConsultInventoriesServices.jtInventories.setRowSelectionInterval(0, 0);
							else
								disableJButtons();
							setTotals();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			};
			
			//Executes Worker
			deleteProduct.execute();
		}
	}
	
	//Method that SETS the JAVABEAN for the Action on the Inventory
	private void setDataActionInventory() {
		jbInventoriesProductService = new JBInventoriesProductService();
		jbInventoriesProductService.setBarcode(jdConsultInventoriesServices.jtInventories.getValueAt(selectedRow, 0).toString());
		jbInventoriesProductService.setDescription(jdConsultInventoriesServices.jtInventories.getValueAt(selectedRow, 1).toString());
		jbInventoriesProductService.setUnitmeasurement(jdConsultInventoriesServices.jtInventories.getValueAt(selectedRow, 2).toString());
		jbInventoriesProductService.setStock(Double.parseDouble(jdConsultInventoriesServices.jtInventories.getValueAt(selectedRow, 6).toString()));
	}
	
	//Method that UPDATES the GUI
	public void updateRow(JBInventoriesProductService jbProductUpdated) {
		jdConsultInventoriesServices.dtmInventoriesServices.setValueAt(jbProductUpdated.getBarcode(), selectedRow, 0);
		jdConsultInventoriesServices.dtmInventoriesServices.setValueAt(jbProductUpdated.getDescription(), selectedRow, 1);
		jdConsultInventoriesServices.dtmInventoriesServices.setValueAt(jbProductUpdated.getUnitmeasurement(), selectedRow, 2);
		jdConsultInventoriesServices.dtmInventoriesServices.setValueAt(jbProductUpdated.getDepartment(), selectedRow, 3);
		jdConsultInventoriesServices.dtmInventoriesServices.setValueAt("$" + dfDecimals.format(jbProductUpdated.getBuyprice()), selectedRow, 4);
		jdConsultInventoriesServices.dtmInventoriesServices.setValueAt("$" + dfDecimals.format(jbProductUpdated.getSellprice()), selectedRow, 5);
		if(jbProductUpdated.getUnitmeasurement().equals("Pza")) {
			jdConsultInventoriesServices.dtmInventoriesServices.setValueAt(dfPiece.format(jbProductUpdated.getMinstock()), selectedRow, 6);
			jdConsultInventoriesServices.dtmInventoriesServices.setValueAt(dfPiece.format(jbProductUpdated.getStock()), selectedRow, 7);
		} else if(jbProductUpdated.getUnitmeasurement().equals("Granel")) {
			jdConsultInventoriesServices.dtmInventoriesServices.setValueAt(dfDecimals.format(jbProductUpdated.getMinstock()), selectedRow, 6);
			jdConsultInventoriesServices.dtmInventoriesServices.setValueAt(dfDecimals.format(jbProductUpdated.getStock()), selectedRow, 7);
		}
	}
	
	//Method that SETS the Name of the Sheet
	private void setNameSheet() {
		type_inventories = jdConsultInventoriesServices.jcbWhatToShow.getSelectedIndex();
		switch(type_inventories) {
			case 0:
				nameSheet = "Todos los Inventarios";
				break;
			
			case 1:
				nameSheet = "Productos Bajos en Inventarios";
				break;
		}
	}
	
	//Method that CHECKS if the File was already done
	private boolean checkFile() {
		boolean fileExist = false;
		File file = new File(dataForSave[0] + "." + dataForSave[1]);
		if(file.exists() && !file.isDirectory()) {
			fileExist = true;
		}
		return fileExist;
	}
	
	//Method that UPDATES the value on STOCK
	public void updateStock(int row, double stock) {
		String getUM = jdConsultInventoriesServices.dtmInventoriesServices.getValueAt(row, 2).toString(); 
		if(getUM.equals("Pza"))
			jdConsultInventoriesServices.dtmInventoriesServices.setValueAt(dfPiece.format(stock), row, 6);
		else if(getUM.equals("Granel"))
			jdConsultInventoriesServices.dtmInventoriesServices.setValueAt(dfDecimals.format(stock), row, 6);
		
		setTotals();
	}
	
	//Method that GENERATES the SpreadSheet
	private void generateSpreadSheet() {
		SwingWorker<Void, Void >generateSpreadSheet = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {		
				//Create the Excel Book
				workbook = new XSSFWorkbook();
				
				//Font for the Headers Cells
				fHeaders = workbook.createFont();
				fHeaders.setFontHeightInPoints((short)14);
				fHeaders.setFontName("Liberation Sans");
				fHeaders.setBold(true);
				fHeaders.setColor(IndexedColors.WHITE.getIndex());
				
				//Font for the DATA Cells
				Font fData = workbook.createFont();
				fData.setFontHeightInPoints((short)12);
				fData.setFontName("Liberation Sans");
				//Create the Global Cell Style for the DATA
				csData = workbook.createCellStyle();
				csData.setFont(fData);
				csData.setAlignment(CellStyle.ALIGN_CENTER);
				csData.setBorderLeft(CellStyle.BORDER_THIN);
				csData.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				csData.setBorderBottom(CellStyle.BORDER_THIN);
				csData.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				csData.setBorderRight(CellStyle.BORDER_THIN);
				csData.setRightBorderColor(IndexedColors.BLACK.getIndex());
				
				//Create Global Cell Style to Format the Cell Buy and Sell Price
				DataFormat price = workbook.createDataFormat();
				csPrices = workbook.createCellStyle();
				csPrices.setFont(fData);
				csPrices.setAlignment(CellStyle.ALIGN_CENTER);
				csPrices.setBorderLeft(CellStyle.BORDER_THIN);
				csPrices.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				csPrices.setBorderBottom(CellStyle.BORDER_THIN);
				csPrices.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				csPrices.setBorderRight(CellStyle.BORDER_THIN);
				csPrices.setRightBorderColor(IndexedColors.BLACK.getIndex());
				csPrices.setDataFormat(price.getFormat("$#,###,###.00"));
				
				DataFormat integers = workbook.createDataFormat();
				DataFormat decimals = workbook.createDataFormat();
				
				//Creates the Cell Styles to format the Integer o Decimals numbers
				csIntegers = workbook.createCellStyle();
				csIntegers.setFont(fData);
				csIntegers.setAlignment(CellStyle.ALIGN_CENTER);
				csIntegers.setBorderLeft(CellStyle.BORDER_THIN);
				csIntegers.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				csIntegers.setBorderBottom(CellStyle.BORDER_THIN);
				csIntegers.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				csIntegers.setBorderRight(CellStyle.BORDER_THIN);
				csIntegers.setRightBorderColor(IndexedColors.BLACK.getIndex());
				csIntegers.setDataFormat(integers.getFormat("#,###,##0"));
				
				csDecimals = workbook.createCellStyle();
				csDecimals.setFont(fData);
				csDecimals.setAlignment(CellStyle.ALIGN_CENTER);
				csDecimals.setBorderLeft(CellStyle.BORDER_THIN);
				csDecimals.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				csDecimals.setBorderBottom(CellStyle.BORDER_THIN);
				csDecimals.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				csDecimals.setBorderRight(CellStyle.BORDER_THIN);
				csDecimals.setRightBorderColor(IndexedColors.BLACK.getIndex());
				csDecimals.setDataFormat(decimals.getFormat("#,###,##0.00"));
				
				//Creates the Cell Styles for Actual Stock, with their Respective Format, when the Inventory it's Equals o Lower
				Font fEI = workbook.createFont();
				fEI.setFontName("Liberation Sans");
				fEI.setFontHeightInPoints((short)12);
				fEI.setBold(true);
				fEI.setColor(IndexedColors.ORANGE.getIndex());
				
				Font fLI = workbook.createFont();
				fLI.setFontName("Liberation Sans");
				fLI.setFontHeightInPoints((short)12);
				fLI.setBold(true);
				fLI.setColor(IndexedColors.RED.getIndex());
				
				csEIIntegers = workbook.createCellStyle();
				csEIIntegers.setFont(fEI);
				csEIIntegers.setAlignment(CellStyle.ALIGN_CENTER);
				csEIIntegers.setBorderLeft(CellStyle.BORDER_THIN);
				csEIIntegers.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				csEIIntegers.setBorderBottom(CellStyle.BORDER_THIN);
				csEIIntegers.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				csEIIntegers.setBorderRight(CellStyle.BORDER_THIN);
				csEIIntegers.setRightBorderColor(IndexedColors.BLACK.getIndex());
				csEIIntegers.setDataFormat(integers.getFormat("#,###,##0"));
				
				csLIIntegers = workbook.createCellStyle();
				csLIIntegers.setFont(fLI);
				csLIIntegers.setAlignment(CellStyle.ALIGN_CENTER);
				csLIIntegers.setBorderLeft(CellStyle.BORDER_THIN);
				csLIIntegers.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				csLIIntegers.setBorderBottom(CellStyle.BORDER_THIN);
				csLIIntegers.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				csLIIntegers.setBorderRight(CellStyle.BORDER_THIN);
				csLIIntegers.setRightBorderColor(IndexedColors.BLACK.getIndex());
				csLIIntegers.setDataFormat(integers.getFormat("#,###,##0"));

				csEIDecimals = workbook.createCellStyle();
				csEIDecimals.setFont(fEI);
				csEIDecimals.setAlignment(CellStyle.ALIGN_CENTER);
				csEIDecimals.setBorderLeft(CellStyle.BORDER_THIN);
				csEIDecimals.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				csEIDecimals.setBorderBottom(CellStyle.BORDER_THIN);
				csEIDecimals.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				csEIDecimals.setBorderRight(CellStyle.BORDER_THIN);
				csEIDecimals.setRightBorderColor(IndexedColors.BLACK.getIndex());
				csEIDecimals.setDataFormat(decimals.getFormat("#,###,##0.00"));
				
				csLIDecimals = workbook.createCellStyle();
				csLIDecimals.setFont(fLI);
				csLIDecimals.setAlignment(CellStyle.ALIGN_CENTER);
				csLIDecimals.setBorderLeft(CellStyle.BORDER_THIN);
				csLIDecimals.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				csLIDecimals.setBorderBottom(CellStyle.BORDER_THIN);
				csLIDecimals.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				csLIDecimals.setBorderRight(CellStyle.BORDER_THIN);
				csLIDecimals.setRightBorderColor(IndexedColors.BLACK.getIndex());
				csLIDecimals.setDataFormat(decimals.getFormat("#,###,##0.00"));
				
				switch(type_inventories) {
					case 0:
						fillSheetInventories();
						fillSheetLowInventories();
						break;
					
					case 1:
						fillSheetLowInventories();
						break;
				}
				
				//Writes the File
				FileOutputStream fileOut = new FileOutputStream(dataForSave[0] + "." + dataForSave[1]);
				workbook.write(fileOut);
				fileOut.flush();
				fileOut.close();
				
				return null;
			}

			@Override
			protected void done() {
				//Open the Workbook
				Desktop dt = Desktop.getDesktop();
				try {
					dt.open(new File(dataForSave[0] + "." + dataForSave[1]));
				} catch (IOException e) {
					e.printStackTrace();
				}
				jdConsultInventoriesServices.jbtnGenerateSpreadsheet.setEnabled(true);
			}
		};
		
		//Executes the WORKER
		generateSpreadSheet.execute();
	}
	private void fillSheetInventories() {
		int row = 0, lessToGetRow = 0;
		
		XSSFSheet sheetInventories = workbook.createSheet("Inventarios Actuales");
		
		Row rowHeadersInventories = sheetInventories.createRow(0);
		rowHeadersInventories.setHeightInPoints(20);
		
		//Sets the Style
		CellStyle csHeaders = workbook.createCellStyle();
		csHeaders.setFont(fHeaders);
		csHeaders.setAlignment(CellStyle.ALIGN_CENTER);
		csHeaders.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		csHeaders.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		csHeaders.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		//BarCode
		Cell barcodeHeaderInventories = rowHeadersInventories.createCell(0);
		barcodeHeaderInventories.setCellValue("Código de Barras");
		barcodeHeaderInventories.setCellStyle(csHeaders);
		//Description
		Cell descriptionHeaderInventories = rowHeadersInventories.createCell(1);
		descriptionHeaderInventories.setCellValue("Descripción");
		descriptionHeaderInventories.setCellStyle(csHeaders);
		//Unit Measurement
		Cell unitmeasurementHeaderInventories = rowHeadersInventories.createCell(2);
		unitmeasurementHeaderInventories.setCellValue("Unidad de Medida");
		unitmeasurementHeaderInventories.setCellStyle(csHeaders);
		//Buy Price
		Cell buypriceHeaderInventories = rowHeadersInventories.createCell(3);
		buypriceHeaderInventories.setCellValue("Precio de Compra");
		buypriceHeaderInventories.setCellStyle(csHeaders);
		//Sell Price
		Cell sellpriceHeaderInventories = rowHeadersInventories.createCell(4);
		sellpriceHeaderInventories.setCellValue("Precio de Venta");
		sellpriceHeaderInventories.setCellStyle(csHeaders);
		//Minimum Stock
		Cell minimumstockHeaderInventories = rowHeadersInventories.createCell(5);
		minimumstockHeaderInventories.setCellValue("Stock Mínimo");
		minimumstockHeaderInventories.setCellStyle(csHeaders);
		//Actual Stock
		Cell actualstockHeaderInventories = rowHeadersInventories.createCell(6);
		actualstockHeaderInventories.setCellValue("Stock Actual");
		actualstockHeaderInventories.setCellStyle(csHeaders);
		
		//Fill the Sheet with the DATA from the DB
		for(int gdi = 0; gdi < alWhatToShow.size(); gdi++) {
			JBInventoriesProductService jbProduct = new JBInventoriesProductService();
			jbProduct = alWhatToShow.get(gdi);
			
			if(jbProduct.getStock() > jbProduct.getMinstock()) {
				row = gdi - lessToGetRow;
				
				Row rowProduct = sheetInventories.createRow(row + 1);
				
				Cell cBarCode = rowProduct.createCell(0);
				cBarCode.setCellValue(jbProduct.getBarcode());
				cBarCode.setCellStyle(csData);
				
				Cell cDescription = rowProduct.createCell(1);
				cDescription.setCellValue(jbProduct.getDescription());
				cDescription.setCellStyle(csData);
				
				Cell cUnitMeasurement = rowProduct.createCell(2);
				cUnitMeasurement.setCellValue(jbProduct.getUnitmeasurement());
				cUnitMeasurement.setCellStyle(csData);
				
				Cell cBuyPrice = rowProduct.createCell(3);
				cBuyPrice.setCellValue(jbProduct.getBuyprice());
				cBuyPrice.setCellStyle(csPrices);
				
				Cell cSellPrices = rowProduct.createCell(4);
				cSellPrices.setCellValue(jbProduct.getSellprice());
				cSellPrices.setCellStyle(csPrices);
				
				Cell cMinimumStock = rowProduct.createCell(5);
				cMinimumStock.setCellValue(jbProduct.getMinstock());
				
				Cell cActualStock = rowProduct.createCell(6);
				cActualStock.setCellValue(jbProduct.getStock());
				
				if(jbProduct.getUnitmeasurement().equals("Pza")) {
					cMinimumStock.setCellStyle(csIntegers);
					cActualStock.setCellStyle(csIntegers);
				} else if(jbProduct.getUnitmeasurement().equals("Granel")) {
					cMinimumStock.setCellStyle(csDecimals);
					cActualStock.setCellStyle(csDecimals);
				}
			} else {
				lessToGetRow++;
			}
		}
		
		//Resize the Columns
		for(int column = 0; column < rowHeadersInventories.getLastCellNum(); column++) {
			sheetInventories.autoSizeColumn(column);
		}
	}
	private void fillSheetLowInventories() {
		int row = 0, lessToGetRow = 0;
		
		XSSFSheet sheetLowInventories = workbook.createSheet("Productos Bajos en Inventarios");
		
		Row rowHeadersLowInventories = sheetLowInventories.createRow(0);
		rowHeadersLowInventories.setHeightInPoints(20);
		
		//Sets the Style
		CellStyle csHeaders = workbook.createCellStyle();
		csHeaders.setFont(fHeaders);
		csHeaders.setAlignment(CellStyle.ALIGN_CENTER);
		csHeaders.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		csHeaders.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		csHeaders.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		//BarCode
		Cell barcodeHeaderLowInventories = rowHeadersLowInventories.createCell(0);
		barcodeHeaderLowInventories.setCellValue("Código de Barras");
		barcodeHeaderLowInventories.setCellStyle(csHeaders);
		//Description
		Cell descriptionHeaderLowInventories = rowHeadersLowInventories.createCell(1);
		descriptionHeaderLowInventories.setCellValue("Descripción");
		descriptionHeaderLowInventories.setCellStyle(csHeaders);
		//Unit Measurement
		Cell unitmeasurementHeaderLowInventories = rowHeadersLowInventories.createCell(2);
		unitmeasurementHeaderLowInventories.setCellValue("Unidad de Medida");
		unitmeasurementHeaderLowInventories.setCellStyle(csHeaders);
		//Buy Price
		Cell buypriceHeaderLowInventories = rowHeadersLowInventories.createCell(3);
		buypriceHeaderLowInventories.setCellValue("Precio de Compra");
		buypriceHeaderLowInventories.setCellStyle(csHeaders);
		//Sell Price
		Cell sellpriceHeaderLowInventories = rowHeadersLowInventories.createCell(4);
		sellpriceHeaderLowInventories.setCellValue("Precio de Venta");
		sellpriceHeaderLowInventories.setCellStyle(csHeaders);
		//Minimum Stock
		Cell minimumstockHeaderLowInventories = rowHeadersLowInventories.createCell(5);
		minimumstockHeaderLowInventories.setCellValue("Stock Mínimo");
		minimumstockHeaderLowInventories.setCellStyle(csHeaders);
		//Actual Stock
		Cell actualstockHeaderLowInventories = rowHeadersLowInventories.createCell(6);
		actualstockHeaderLowInventories.setCellValue("Stock Actual");
		actualstockHeaderLowInventories.setCellStyle(csHeaders);
		
		//Fill the Sheet with the DATA from the DB
		for(int gdli = 0; gdli < alWhatToShow.size(); gdli++) {
			JBInventoriesProductService jbProduct = new JBInventoriesProductService();
			jbProduct = alWhatToShow.get(gdli);
			
			if(jbProduct.getStock() <= jbProduct.getMinstock()) {
				row = gdli - lessToGetRow;
				
				Row rowProduct = sheetLowInventories.createRow(row + 1);
				
				Cell cBarCode = rowProduct.createCell(0);
				cBarCode.setCellValue(jbProduct.getBarcode());
				cBarCode.setCellStyle(csData);
				
				Cell cDescription = rowProduct.createCell(1);
				cDescription.setCellValue(jbProduct.getDescription());
				cDescription.setCellStyle(csData);
				
				Cell cUnitMeasurement = rowProduct.createCell(2);
				cUnitMeasurement.setCellValue(jbProduct.getUnitmeasurement());
				cUnitMeasurement.setCellStyle(csData);
				
				Cell cBuyPrice = rowProduct.createCell(3);
				cBuyPrice.setCellValue(jbProduct.getBuyprice());
				cBuyPrice.setCellStyle(csPrices);
				
				Cell cSellPrices = rowProduct.createCell(4);
				cSellPrices.setCellValue(jbProduct.getSellprice());
				cSellPrices.setCellStyle(csPrices);
				
				Cell cMinimumStock = rowProduct.createCell(5);
				cMinimumStock.setCellValue(jbProduct.getMinstock());
				if(jbProduct.getUnitmeasurement().equals("Pza"))
					cMinimumStock.setCellStyle(csIntegers);
				else if(jbProduct.getUnitmeasurement().equals("Granel"))
					cMinimumStock.setCellStyle(csDecimals);		
				
				Cell cActualStock = rowProduct.createCell(6);
				cActualStock.setCellValue(jbProduct.getStock());
				if(jbProduct.getMinstock() == jbProduct.getStock() && jbProduct.getUnitmeasurement().equals("Pza")) {
					cActualStock.setCellStyle(csEIIntegers);
				} else if(jbProduct.getMinstock() > jbProduct.getStock() && jbProduct.getUnitmeasurement().equals("Pza")) {
					cActualStock.setCellStyle(csLIIntegers);
				} else if(jbProduct.getMinstock() == jbProduct.getStock() && jbProduct.getUnitmeasurement().equals("Granel")) {
					cActualStock.setCellStyle(csEIDecimals);
				} else if(jbProduct.getMinstock() > jbProduct.getStock() && jbProduct.getUnitmeasurement().equals("Granel")) {
					cActualStock.setCellStyle(csLIDecimals);
				}
			} else {
				lessToGetRow++;
			}
		}
		
		//Resize the Columns
		for(int column = 0; column < rowHeadersLowInventories.getLastCellNum(); column++) {
			sheetLowInventories.autoSizeColumn(column);
		}
	}
	
	//Method that GENERATES the Report
	private void generateReport() {
		SwingWorker<Void, Void> generateReport = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				//Creates the Report, and SETS the Orientations of the Page
				reportInventories = DynamicReports.report();
				reportInventories.setPageFormat(PageType.LETTER, PageOrientation.LANDSCAPE);
				
				//Fonts
				StyleBuilder centered = stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER);
				StyleBuilder fontTitle = stl.style(centered).setFont(DynamicReports.stl.font("Liberation Sans", true, false, 18));
				StyleBuilder formatTable = stl.style(centered).setBorder(DynamicReports.stl.pen1Point()).setBackgroundColor(Color.LIGHT_GRAY);
				StyleBuilder fontHeaders = stl.style(formatTable).setFont(DynamicReports.stl.font("Liberation Sans", true, false, 14));
				StyleBuilder fontRows = stl.style(centered).setFont(DynamicReports.stl.font("Liberation Sans", false, false, 12));
				
				//Report Title
				TextFieldBuilder<String> reportTitle = cmp.text(nameSheet);
				reportTitle.setStyle(fontTitle).setHorizontalAlignment(HorizontalAlignment.CENTER);
				reportInventories.title(reportTitle);
				
				//Columns and Rows
				TextColumnBuilder<Integer> rowNumber = col.reportRowNumberColumn("No.").setFixedColumns(2).setHorizontalAlignment(HorizontalAlignment.CENTER);
				TextColumnBuilder<String> barcode = Columns.column("Código de Barras", "barcode", type.stringType());
				TextColumnBuilder<String> description = Columns.column("Descripción del Producto", "description", type.stringType());
				TextColumnBuilder<String> unitMeasurement = Columns.column("Unidad de Medida", "unitmeasurement", type.stringType());
				TextColumnBuilder<Double> buyPrice = Columns.column("Precio de Compra", "buyprice", type.doubleType());
				TextColumnBuilder<Double> sellPrice = Columns.column("Precio de Venta", "sellprice", type.doubleType());
				TextColumnBuilder<Double> minimumStock = Columns.column("Stock Mínimo", "minstock", type.doubleType());
				TextColumnBuilder<Double> actualStock = Columns.column("Stock Actual", "stock", type.doubleType());
				reportInventories.addColumn(rowNumber.setFixedWidth(50));
				reportInventories.addColumn(barcode.setFixedWidth(120));
				reportInventories.addColumn(description.setFixedWidth(190));
				reportInventories.addColumn(unitMeasurement.setFixedWidth(80));
				reportInventories.addColumn(buyPrice.setFixedWidth(80));
				reportInventories.addColumn(sellPrice.setFixedWidth(80));
				reportInventories.addColumn(minimumStock.setFixedWidth(80));
				reportInventories.addColumn(actualStock.setFixedWidth(80));
				
				//Format the Report
				reportInventories.setColumnTitleStyle(fontHeaders);
				reportInventories.setColumnStyle(fontRows);
				reportInventories.highlightDetailEvenRows();
				
				//DataSource
				reportInventories.setDataSource(new JRBeanCollectionDataSource(alWhatToShow));
				
				//Show the Number of the Page
				reportInventories.pageFooter(cmp.pageXofY());
			
				return null;
			}

			@Override
			protected void done() {
				try {
					JasperPrint jp = reportInventories.toJasperPrint();
					jpv = new JPVViewer(jp);
					JDReports jdReports = new JDReports(jdConsultInventoriesServices, nameSheet, jpv);
					ctrlReports.setJDReports(jdReports);
					ctrlReports.setJasperViewer(jpv);
					ctrlReports.prepareView();
				} catch (DRException e) {
					e.printStackTrace();
				}
				jdConsultInventoriesServices.jbtnGenerateReport.setEnabled(true);
			}
		};
		
		//Executes the WORKER
		generateReport.execute();
	}
	
	//Method that Filter the JTable
	private void filterJTable() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				String input = jdConsultInventoriesServices.jtxtfSearch.getText();
				String department = jdConsultInventoriesServices.jcbDepartments.getSelectedItem().toString();
				if(department.equals("Todos"))
					department = "";
				
				List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(2);
				filters.add(RowFilter.regexFilter("(?i)" + input, columnToFilter));
				filters.add(RowFilter.regexFilter("(?i)" + department, 3));
				
				RowFilter<TableModel, Object> rf = null;
		        try {
		            rf = RowFilter.andFilter(filters);
		        } catch (java.util.regex.PatternSyntaxException e) {
		            return;
		        }
		        
		        jdConsultInventoriesServices.trsSorter.setRowFilter(rf);
		        
		        int rowCount = jdConsultInventoriesServices.jtInventories.getRowCount();
		        if(rowCount > 0) {
		        	enableJButtons();
		        	jdConsultInventoriesServices.jtInventories.setRowSelectionInterval(0, 0);
		        } else {
		        	disableJButtons();
		        }
			}
		});
	}
	
	//Method that Enables some JButtons and Selects the First Row on the JTable
	private void enableJButtons() {
		jdConsultInventoriesServices.jbtnGenerateSpreadsheet.setEnabled(true);
		jdConsultInventoriesServices.jbtnGenerateReport.setEnabled(true);
		jdConsultInventoriesServices.jbtnPrintInventories.setEnabled(true);
		jdConsultInventoriesServices.jbtnInventoryMovements.setEnabled(true);
		if(jdConsultInventoriesServices.dtmInventoriesServices.getRowCount() != 0)
			jdConsultInventoriesServices.jtInventories.setRowSelectionInterval(0, 0);
	}
	
	//Method that Disables the Buttons
	private void disableJButtons() {
		jdConsultInventoriesServices.jbtnModifyProductService.setEnabled(false);
		jdConsultInventoriesServices.jbtnDelete.setEnabled(false);
		jdConsultInventoriesServices.jbtnAdjustInventory.setEnabled(false);
		jdConsultInventoriesServices.jbtnGenerateSpreadsheet.setEnabled(false);
		jdConsultInventoriesServices.jbtnGenerateReport.setEnabled(false);
		jdConsultInventoriesServices.jbtnPrintInventories.setEnabled(false);
		jdConsultInventoriesServices.jbtnInventoryMovements.setEnabled(false);
	}
}
