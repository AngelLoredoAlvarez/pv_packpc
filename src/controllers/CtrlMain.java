package controllers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
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
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import model.buys.DAOBuys;
import model.buys.JBBuys;
import model.inventories.DAOInventoriesProductService;
import model.inventories.JBInventoriesProductService;
import model.inventories.ValidateData;
import model.sales.DAOSales;
import model.sales.JBDetailsSales;
import model.sales.JBSales;
import views.JDAskSUDO;
import views.JDBusinessInformation;
import views.JDBuys;
import views.JDCharge;
import views.JDCloseSystem;
import views.JDConsultCashiers;
import views.JDConsultClients;
import views.JDConsultInventoriesServices;
import views.JDConsultProviders;
import views.JDGranelProduct;
import views.JDOpenTill;
import views.JDSales;
import views.JDTillCourt;
import views.JDTillMovements;
import views.JFMain;

public class CtrlMain {
	//Attributes
	private static JFMain jfMain;
	//Global Variables
	private String typeMessage = "", message = "", actionStock = "";
	private boolean root = false, tillOpened = false, pOpenTill = false;
	private ArrayList<String> cashierPrivileges;
	private static int selectedTicket = 1, selectedRow = 0;
	private int id_cashier = 0, cantBarCode = 14, wr = 0, actualQuantityI = 0;
	private double ticketTotal = 0.0, actualPrice = 0.0, actualQuantityD = 0.0, subTotal = 0.0, irs = 0.0;
	private static String barcode = "";
	private Date startDate;
	private String cashierName = "", regex = "^$|^[0-9]+$";
	private boolean callAddToList = false, callSetJTListener = false;
	private boolean granelProduct = false, addPSToList = false, alreadyAdded = false;
	private static JBInventoriesProductService jbProductService;
	private static DefaultTableModel dtmTicket;
	private static JTable jtTicket;
	private static DecimalFormat dfDecimals;
	private static HashMap<Integer, Double> hmTotals;
	private static HashMap<String, Double> hmStocks;
	private Timer time;
	private ArrayList<JBBuys> alBuys;
	private ArrayList<JBSales> alSales;
	private ArrayList<Integer> dateNumbers;
	//Communication with the others VIEWS
	private CtrlOpenTill ctrlOpenTill;
	private CtrlBuys ctrlBuys;
	private CtrlSales ctrlSales;
	private CtrlCloseSystem ctrlCloseSystem;
	private CtrlTillMovements ctrlTillMovements;
	private CtrlTillCourt ctrlTillCourt;
	private CtrlConsultClients ctrlConsultClients;
	private CtrlConsultProviders ctrlConsultProviders;
	private CtrlConsultCashiers ctrlConsultCashiers;
	private CtrlConsultInventoriesServices ctrlConsultInventoriesServices;
	private CtrlGranelProduct ctrlGranelProduct;
	private CtrlCharge ctrlCharge;
	private CtrlAskSUDO ctrlAskSudo;
	private CtrlBusinessInformation ctrlBusinessInformation;
	private String[] dataForActionDB = null;
	
	//DAOS
	private static DAOInventoriesProductService daoInventoriesProductService;
	private DAOBuys daoBuys;
	private DAOSales daoSales;
	
	//Method that SETS the VIEW
	public void setJFSales(JFMain jfMain) {
		CtrlMain.jfMain = jfMain;
	}
	
	//Method that SETS the Communication JFSales - JDOpenTill
	public void setCtrlOpenTill(CtrlOpenTill ctrlOpenTill) {
		this.ctrlOpenTill = ctrlOpenTill;
	}
	//Method that SETS the Communication JFSales - JDCloseSystem
	public void setCtrlCloseSystem(CtrlCloseSystem ctrlCloseSystem) {
		this.ctrlCloseSystem = ctrlCloseSystem;
	}
	//Method that SETS the Communication JFSales - JDTillMovements
	public void setCtrlTillMovements(CtrlTillMovements ctrlTillMovements) {
		this.ctrlTillMovements = ctrlTillMovements;
	}
	//Method that SETS the Communication JFSales - JDBuys
	public void setCtrlBuys(CtrlBuys ctrlBuys) {
		this.ctrlBuys = ctrlBuys;
	}
	//Method that SETS the Communication JFMain - JDSales
	public void setCtrlSales(CtrlSales ctrlSales) {
		this.ctrlSales = ctrlSales;
	}
	//Method that SETS the Communication JFSales - JDTillCourt
	public void setCtrlTillCourt(CtrlTillCourt ctrlTillCourt) {
		this.ctrlTillCourt = ctrlTillCourt;
	}
	//Method that SETS the Communication JFSales - JDConsultClients
	public void setCtrlConsultClients(CtrlConsultClients ctrlConsultClients) {
		this.ctrlConsultClients = ctrlConsultClients;
	}
	//Method that SETS the Communication JFSales - JDConsultProviders
	public void setCtrlConsultProviders(CtrlConsultProviders ctrlConsultProviders) {
		this.ctrlConsultProviders = ctrlConsultProviders;
	}
	//Method that SETS the Communication JFSales - JDConsultCashiers
	public void setCtrlConsultCashiers(CtrlConsultCashiers ctrlConsultCashiers) {
		this.ctrlConsultCashiers = ctrlConsultCashiers;
	}
	//Method that SETS the Communication JFSales - JDConsultInventories
	public void setCtrlConsultInventories(CtrlConsultInventoriesServices ctrlConsultInventories) {
		this.ctrlConsultInventoriesServices = ctrlConsultInventories;
	}
	//Method that SETS the COmmunication JFSales - JDGranelProduct
	public void setCtrlGranelProduct(CtrlGranelProduct ctrlGranelProduct) {
		this.ctrlGranelProduct = ctrlGranelProduct;
	}
	//Method that SETS the Communication JFSales - JDCharge
	public void setCtrlCharge(CtrlCharge ctrlCharge) {
		this.ctrlCharge = ctrlCharge;
	}
	//Method that SETS the Communication JFSales - JDAskSUDO
	public void setCtrlAskSUDO(CtrlAskSUDO ctrlAskSudo) {
		this.ctrlAskSudo = ctrlAskSudo;
	}
	//Method that SETS the Communication JFMain - JDBusinessInformation
	public void setCtrlBusinessInformation(CtrlBusinessInformation ctrlBusinessInformation) {
		this.ctrlBusinessInformation = ctrlBusinessInformation;
	}
	
	//Method that SETS the DAOInventoriesProductService
	public void setDAOInventoriesProductService(DAOInventoriesProductService daoInventoriesProductService) {
		CtrlMain.daoInventoriesProductService = daoInventoriesProductService;
	}
	//Method that SETS the DAOSBuys
	public void setDAOBuys(DAOBuys daoBuys) {
		this.daoBuys = daoBuys;
	}
	//Method that SETS the DAOSales
	public void setDAOSales(DAOSales daoSales) {
		this.daoSales = daoSales;
	}
	
	//Method that SETS that the Till was Opened
	public void setOpenedTill(boolean openedTill) {
		this.tillOpened = openedTill;
	}
	
	//Method that SETS the DATA for the Turn
	public void setDataForTurn(ArrayList<String> cashierPrivileges, int id_cashier, boolean root, String cashierName, Date startDate, boolean tillOpened, int lastSale) {
		//SETS the Global Variables
		this.cashierPrivileges = cashierPrivileges;
		this.id_cashier = id_cashier;
		this.cashierName = cashierName;
		this.startDate = startDate;
		this.root = root;
		this.tillOpened = tillOpened;
		
		//Check if there's any Till Open
		if(!tillOpened) {
			typeMessage = "noTillOpened";
			if(root || pOpenTill) {
				message = "Antes de Iniciar las Ventas del Día, debes de Abrir Caja";
				jfMain.setJOPMessages(typeMessage, message);
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JDOpenTill jdOpenTill = new JDOpenTill(jfMain);
						ctrlOpenTill.setJDOpenTill(jdOpenTill);
						ctrlOpenTill.setDataCashierPrivileged(cashierName, id_cashier, id_cashier);
						ctrlOpenTill.prepareView();
					}
				});
			} else {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						message = "No podrás realizar ninguna Acción, hasta que un Supervisor abra una Caja";
						jfMain.setJOPMessages(typeMessage, message);
					}
				});
			}
		} else {
			enableActionsAccordingPrivileges();
		}
		
		jfMain.jtxtfActiveUser.setText(cashierName);
		if(lastSale == 0)
			jfMain.jtxtfFolioNumber.setText("1");
		else
			jfMain.jtxtfFolioNumber.setText(String.valueOf(lastSale + 1));
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		//Initializations
		this.setDateTime();
		hmTotals = new HashMap<Integer, Double>();
		dfDecimals = new DecimalFormat("#,###,##0.00");
		hmStocks = new HashMap<String, Double>();
		this.setListeners();
		//This has to be at the END
		jfMain.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JFrame
		jfMain.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent we) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {						
						//I get the TableModel from the Selected Ticket
						selectedTicket = jfMain.jtpTickets.getSelectedIndex() + 1;
						jtTicket = jfMain.hmJTTickets.get(selectedTicket);
						dtmTicket = (DefaultTableModel) jtTicket.getModel();
						//Set the Initial Total
						hmTotals.put(selectedTicket, 0.0);
					}
				});
			}
		});
		jfMain.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if(tillOpened) {
							JDCloseSystem jdCloseSystem = new JDCloseSystem(jfMain);
							ctrlCloseSystem.setJDCloseSystem(jdCloseSystem);
							ctrlCloseSystem.setCashierData(id_cashier);
							ctrlCloseSystem.prepareView();
						} else {
							stopTimer();
							jfMain.dispose();
						}
					}
				});
			}
		});
		
		//JMenuItems
		//System===============================================================================================================
		jfMain.jmiSBackupDB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						jfMain.actionDB = "backup";
						dataForActionDB = jfMain.jfcActionDB();
						if(dataForActionDB != null) {
							if(checkFile()) {
								typeMessage = "fileExist";
								message = "Ya existe un respaldo llamado '" + dataForActionDB[0] + "." + dataForActionDB[1] + "', ¿Desea reemplazarlo?";
							} else {
								actionDB();
							}
						}
					}
				});
			}
		});
		jfMain.jmiSRestoreDB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						jfMain.actionDB = "restore";
						dataForActionDB = jfMain.jfcActionDB();
						if(dataForActionDB != null) {
							actionDB();
						}
					}
				});
			}
		});
		jfMain.jmiSInvoice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JDBusinessInformation jdBusinessInformation = new JDBusinessInformation(jfMain);
						ctrlBusinessInformation.setJDBusinessInformation(jdBusinessInformation);
						ctrlBusinessInformation.prepareView();
					}
				});
			}
		});
		jfMain.jmiSLogOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JDTillCourt jdTillCourt = new JDTillCourt(jfMain, "Corte de Cajero");
						ctrlTillCourt.setJDTillCourt(jdTillCourt);
						ctrlTillCourt.setDATA(startDate, id_cashier);
						ctrlTillCourt.prepareView();
					}
				});
			}
			
		});
		jfMain.jmiSCloseSystem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if(tillOpened) {
							JDCloseSystem jdCloseSystem = new JDCloseSystem(jfMain);
							ctrlCloseSystem.setJDCloseSystem(jdCloseSystem);
							ctrlCloseSystem.setCashierData(id_cashier);
							ctrlCloseSystem.prepareView();
						} else {
							stopTimer();
							jfMain.dispose();
						}
					}
				});
			}
		});
		//System===============================================================================================================
		
		//Till=================================================================================================================
		jfMain.jmiTOpenTill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if(root || pOpenTill) {
							JDOpenTill jdOpenTill = new JDOpenTill(jfMain);
							ctrlOpenTill.setJDOpenTill(jdOpenTill);
							ctrlOpenTill.setDataCashierPrivileged(cashierName, id_cashier, id_cashier);
							ctrlOpenTill.prepareView();
						} else {
							JDAskSUDO jdAskSudo = new JDAskSUDO(jfMain);
							ctrlAskSudo.setJDAskSUDO(jdAskSudo);
							ctrlAskSudo.setIDCashier(id_cashier);
							ctrlAskSudo.prepareView();
						}
					}
				});
			}
		});
		jfMain.jmiTMovements.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JDTillMovements jdTillMovements = new JDTillMovements(jfMain);
						ctrlTillMovements.setJDTillMovements(jdTillMovements);
						ctrlTillMovements.setIDCashier(id_cashier);
						ctrlTillMovements.setUserPriviliges(cashierPrivileges, root);
						ctrlTillMovements.prepareView();
					}
				});
			}
		});
		jfMain.jmiTBuys.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				getBuys();
			}
		});
		jfMain.jmiTSales.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				getSales();
			}
		});
		jfMain.jmiTCashierCourt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JDTillCourt jdTillCourt = new JDTillCourt(jfMain, "Corte de Cajero");
						ctrlTillCourt.setJDTillCourt(jdTillCourt);
						ctrlTillCourt.setDATA(startDate, id_cashier);
						ctrlTillCourt.prepareView();
					}
				});
			}
		});
		jfMain.jmiTDayCourt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JDTillCourt jdTillCourt = new JDTillCourt(jfMain, "Corte del Día");
						ctrlTillCourt.setJDTillCourt(jdTillCourt);
						ctrlTillCourt.setDATA(startDate, id_cashier);
						ctrlTillCourt.prepareView();
					}
				});
			}
		});
		//Till=================================================================================================================
		
		//Consults=============================================================================================================
		jfMain.jmiCClients.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JDConsultClients jdConsultClients = new JDConsultClients(jfMain, "JFMain");
						ctrlConsultClients.setJDConsultClients(jdConsultClients);
						ctrlConsultClients.setIDCashier(id_cashier);
						ctrlConsultClients.setCashierPriviliges(cashierPrivileges, root);
						ctrlConsultClients.prepareView();
					}
				});
			}
		});
		jfMain.jmiCProviders.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JDConsultProviders jdConsultProviders = new JDConsultProviders(jfMain, "JFSales");
						ctrlConsultProviders.setJDConsultProviders(jdConsultProviders);
						ctrlConsultProviders.setROOT(root);
						ctrlConsultProviders.setCashierPrivileges(cashierPrivileges);
						ctrlConsultProviders.prepareView();
					}
				});
			}
		});
		jfMain.jmiCCashiers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JDConsultCashiers jdConsultCashiers = new JDConsultCashiers(jfMain);
						ctrlConsultCashiers.setJDConsultCashiers(jdConsultCashiers);
						ctrlConsultCashiers.setCashierPrivileges(cashierPrivileges, root);
						ctrlConsultCashiers.prepareView();
					}
				});
			}
		});
		jfMain.jmiCInventories.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JDConsultInventoriesServices jdConsultInventories = new JDConsultInventoriesServices(jfMain, "JFSales");
						ctrlConsultInventoriesServices.setJDInventories(jdConsultInventories);
						ctrlConsultInventoriesServices.setIDCashier(id_cashier);
						ctrlConsultInventoriesServices.setROOT(root);
						ctrlConsultInventoriesServices.setCashierPrivileges(cashierPrivileges);
						ctrlConsultInventoriesServices.prepareView();
					}
				});
			}
		});
		//Consults=============================================================================================================
		
		//JTextField
		((AbstractDocument)jfMain.jtxtfBarCode.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
				super.remove(fb, offset, length);
				if(fb.getDocument().getLength() > 0)
					jfMain.jbtnAddtoList.setEnabled(true);
				else
					jfMain.jbtnAddtoList.setEnabled(false);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				System.out.println(string.length());
				if(string.length() > 0) {
					jfMain.jbtnAddtoList.setEnabled(true);
					boolean matches = Pattern.matches(regex, string);
					if(matches) {
						super.insertString(fb, offset, string, attr);
						if(fb.getDocument().getLength() + string.length() == cantBarCode) {
							checkProductServiceExist();
						}
					}
				} else {
					jfMain.jbtnAddtoList.setEnabled(false);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				if(fb.getDocument().getLength() + text.length() > 0) {
					jfMain.jbtnAddtoList.setEnabled(true);
					boolean matches = Pattern.matches(regex, text);
				    if(matches) {
				    	fb.replace(offset, length, text, attrs);
				    	if(fb.getDocument().getLength() + text.length() == cantBarCode) {
				    		checkProductServiceExist();
						}
				    }
				} else {
					jfMain.jbtnAddtoList.setEnabled(false);
				}
			}
		});
		
		//JButtons
		jfMain.jbtnAddtoList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				checkProductServiceExist();
			}
		});
		jfMain.jbtnAddTicket.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						int ticketNumber = jfMain.jtpTickets.getTabCount() + 1;
						jfMain.jtpTickets.addTab("Ticket " + ticketNumber, jfMain.setJTTicket());
						jfMain.jtpTickets.setSelectedIndex(ticketNumber - 1);
						hmTotals.put(ticketNumber, 0.0);
						jfMain.jlblTotal.setText(dfDecimals.format(hmTotals.get(ticketNumber)));
						jfMain.jbtnRemoveProduct.setEnabled(false);
						jfMain.jbtnCharge.setEnabled(false);
						clearBarCodeField();
					}
				});
			}
		});
		jfMain.jbtnCancelTicket.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if(jfMain.jtpTickets.getTabCount() == 1) {
							for(int rr = dtmTicket.getRowCount() - 1; rr >= 0 ; rr--) {
								dtmTicket.removeRow(rr);
							}
							hmTotals.replace(selectedTicket, 0.0);
							jfMain.jlblTotal.setText(dfDecimals.format(hmTotals.get(selectedTicket)));
							jfMain.jbtnRemoveProduct.setEnabled(false);
							jfMain.jbtnCharge.setEnabled(false);
						} else {
							removeTicket();
						}
						
						clearBarCodeField();
					}
				});
			}
		});
		jfMain.jbtnRemoveProduct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						double totalToSubstract = Double.parseDouble(dtmTicket.getValueAt(selectedRow, 3).toString().replace(",", ""));
						double actualTotal = hmTotals.get(selectedTicket);
						double newTotal = actualTotal - totalToSubstract;
						hmTotals.replace(selectedTicket, newTotal);
						dtmTicket.removeRow(selectedRow);
						jfMain.jlblTotal.setText(dfDecimals.format(newTotal));
						
						if(dtmTicket.getRowCount() == 0) {
							if(jfMain.jtpTickets.getTabCount() > 1) {
								if(jfMain.jtpTickets.getSelectedIndex() == 0) {
									removeTicket();
								} else {
									removeTicket();
								}
							}
							jfMain.jbtnCharge.setEnabled(false);
						} else {
							int lr = jtTicket.getRowCount() - 1;
							jtTicket.setRowSelectionInterval(lr, lr);
						}
						
						jfMain.jbtnRemoveProduct.setEnabled(false);
						
						clearBarCodeField();
					}
				});
			}
		});
		jfMain.jbtnCharge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				callChargeSale();
			}
		});
		
		//JTabedPane
		jfMain.jtpTickets.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						//I get the TableModel from the Selected Ticket
						selectedTicket = jfMain.jtpTickets.getSelectedIndex() + 1;
						jtTicket = jfMain.hmJTTickets.get(selectedTicket);
						dtmTicket = (DefaultTableModel) jtTicket.getModel();
						
						//And sets the Total from that Ticket
						jfMain.jlblTotal.setText(dfDecimals.format(hmTotals.get(selectedTicket)));
						
						//To set the Current Sale price and the Correct Renderer Class
						selectedRow = jtTicket.getSelectedRow();
						if(selectedRow >= 0) {
							setPSCC();
						}
						
						//Selects the First Row
						if(dtmTicket.getRowCount() > 0) {
							int cantRows = dtmTicket.getRowCount() - 1;
							jtTicket.setRowSelectionInterval(cantRows, cantRows);
						}
						
						clearBarCodeField();
					}
				});
			}
		});
	}
	
	//Method that SETS the Actual DATE and sets the Actual TIME
	private void setDateTime() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd'/'MM'/'yyyy");
		
		Date actualD = new Date();
		String actualDate = sdfDate.format(actualD);
		jfMain.jtxtfDate.setText(actualDate);
		
		time = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						Date actualT = new Date();
						SimpleDateFormat sdfTime = new SimpleDateFormat("hh':'mm aa");
						String actualTime = sdfTime.format(actualT);
						jfMain.jtxtfTime.setText(actualTime);
					}
					
				});
			}
		});
		time.start();
	}
	public void stopTimer() {
		time.stop();
	}
	
	//Method that CHECKS if the Product or Service Exist on the DB
	private void checkProductServiceExist() {
		SwingWorker<Boolean, Void> checkProductServiceExist = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				barcode = jfMain.jtxtfBarCode.getText();
				boolean existProductService = false;
				ValidateData exist = new ValidateData();
				existProductService = exist.checkProductBarCode(barcode, daoInventoriesProductService);
				
				return existProductService;
			}

			@Override
			protected void done() {
				try {
					boolean resultChecking = get();
					if(resultChecking) {
						callAddToList = true;
					} else {
						typeMessage = "notExist";
						message = "El Producto o Servicio Ingresado no esta Registrado";
						jfMain.setJOPMessages(typeMessage, message);
						clearBarCodeField();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Add a PropertyChangeListener to KNOW when the Thread is finished
		checkProductServiceExist.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(checkProductServiceExist.isDone() && callAddToList) {
						addToList();
						callAddToList = false;
					}
				}
			}
		});
		
		//Executes WORKER
		checkProductServiceExist.execute();
	}
	
	//Method that ADDS the Product or Service to the List on the corresponding Ticket
	private void addToList() {
		SwingWorker<Void, Void> addToList = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				//First, I get the Product/Service from the DB
				jbProductService = daoInventoriesProductService.getProductByBarCode(barcode);
				
				if(jbProductService.getUnitmeasurement().equals("Servicio")) {
					doActionProductService();
				} else {
					//Prepare the Corresponding Message
					if(jbProductService.getStock() == 0.0) {
						actionStock = "outOfStock";
					} else if(jbProductService.getStock() < jbProductService.getMinstock()) {
						actionStock = "lessMinStock";
						doActionProductService();
					} else if(jbProductService.getStock() == jbProductService.getMinstock()) {
						actionStock = "reachMinStock";
						doActionProductService();
					} else {
						doActionProductService();
					}
				}
				
				callSetJTListener = true;
				
				return null;
			}

			@Override
			protected void done() {
				if(actionStock.equals("outOfStock")) {
					typeMessage = "outOfStock";
					message = "El Producto '" + jbProductService.getDescription() + "', se ha AGOTADO";
					jfMain.setJOPMessages(typeMessage, message);
					clearBarCodeField();
				} else {
					if(granelProduct) {
						if(actionStock.equals("reachMinStock")) {
							typeMessage = "reachMinStock";
							message = "El Producto '" + jbProductService.getDescription() + "', ha llegado al Stock Mínimo";
							jfMain.setJOPMessages(typeMessage, message);
							JDGranelProduct("/img/jframes/add.png", "Producto a Granel");
						} else if(actionStock.equals("lessMinStock")) {
							typeMessage = "lessMinStock";
							message = "¡El Producto '" + jbProductService.getDescription() + "', está por AGOTARSE!";
							jfMain.setJOPMessages(typeMessage, message);
							JDGranelProduct("/img/jframes/add.png", "Producto a Granel");
						} else {
							JDGranelProduct("/img/jframes/add.png", "Producto a Granel");
						}
						granelProduct = false;
					} else if(addPSToList) {
						if(jbProductService.getUnitmeasurement().equals("Servicio")) {
							addPSToView();
						} else {
							if(actionStock.equals("reachMinStock")) {
								typeMessage = "reachMinStock";
								message = "El Producto '" + jbProductService.getDescription() + "', ha llegado al Stock Mínimo";
								jfMain.setJOPMessages(typeMessage, message);
								addPSToView();
							} else if(actionStock.equals("lessMinStock")) {
								typeMessage = "lessMinStock";
								message = "¡El Producto '" + jbProductService.getDescription() + "', está por AGOTARSE!";
								jfMain.setJOPMessages(typeMessage, message);
								addPSToView();
							} else {
								addPSToView();
							}
						}
						addPSToList = false;
					} else if(alreadyAdded) {
						if(actionStock.equals("reachMinStock")) {
							typeMessage = "reachMinStock";
							message = "El Producto '" + jbProductService.getDescription() + "', ha llegado al Stock Mínimo";
							jfMain.setJOPMessages(typeMessage, message);
							updateQuantityAAPS();
						} else if(actionStock.equals("lessMinStock")) {
							typeMessage = "lessMinStock";
							message = "¡El Producto '" + jbProductService.getDescription() + "', está por AGOTARSE!";
							jfMain.setJOPMessages(typeMessage, message);
							updateQuantityAAPS();
						} else {
							updateQuantityAAPS();
						}
						alreadyAdded = false;
					}
				}
				if(dtmTicket.getRowCount() > 0) {
					int addedRow = jtTicket.getRowCount() - 1;
					jtTicket.setRowSelectionInterval(addedRow, addedRow);
					jfMain.jbtnAddtoList.setEnabled(false);
					jfMain.jbtnCancelTicket.setEnabled(true);
					jfMain.jbtnRemoveProduct.setEnabled(true);
					jfMain.jbtnCharge.setEnabled(true);
				}
				actionStock = "";
				typeMessage = "";
				message = "";
			}
		};
		
		//Adds a PropertyChangeListener to KNOW when the Thread is Finished
		addToList.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(addToList.isDone() && callSetJTListener) {
						addJTableListener();
						callSetJTListener = false;
					}
				}
			}
		});
		
		//Executes the WORKER
		addToList.execute();
	}
	
	//Method that DO the Action with the Product
	private void doActionProductService() {
		//Then, I check the Type of Product/Service, to KNOW what to do
		if(jbProductService.getUnitmeasurement().equals("Granel")) {
			granelProduct = true;
		} else {
			//Then, I check if the Product/Service has been already added to the List
			int actualNumberRows = dtmTicket.getRowCount();
			if(actualNumberRows != 0) {
				if(alreadyAdded()) {
					//Do the MATH if the current stock it's different to zero
					if(!jbProductService.getUnitmeasurement().equals("Servicio")) {
						if(hmStocks.get(jbProductService.getBarcode()) > 0.0) {
							actualQuantityI = Integer.parseInt(dtmTicket.getValueAt(wr, 3).toString());
							actualPrice = Double.parseDouble(dtmTicket.getValueAt(wr, 4).toString().replace(",", ""));
							ticketTotal = hmTotals.get(selectedTicket) + jbProductService.getSellprice();
							hmTotals.put(selectedTicket, ticketTotal);
						}
					} else {
						actualQuantityI = Integer.parseInt(dtmTicket.getValueAt(wr, 3).toString());
						actualPrice = Double.parseDouble(dtmTicket.getValueAt(wr, 4).toString().replace(",", ""));
						ticketTotal = hmTotals.get(selectedTicket) + jbProductService.getSellprice();
						hmTotals.put(selectedTicket, ticketTotal);
					}
				} else {
					hmStocks.put(jbProductService.getBarcode(), jbProductService.getStock());
					ticketTotal = hmTotals.get(selectedTicket) + jbProductService.getSellprice();
					hmTotals.put(selectedTicket, ticketTotal);
					addPSToList = true;
				}
			} else {
				hmStocks.put(jbProductService.getBarcode(), jbProductService.getStock());
				hmTotals.put(selectedTicket, jbProductService.getSellprice());
				addPSToList = true;
			}
		}
	}
	
	//Method that CHECKS if the Product/Service has been already ADDED
	private boolean alreadyAdded() {
		for(wr = 0; wr < dtmTicket.getRowCount(); wr++) {
			String actualDescription = dtmTicket.getValueAt(wr, 1).toString();
			if(actualDescription.equals(jbProductService.getDescription())){ 
				alreadyAdded = true;
				break;
			}
		}
		
		return alreadyAdded;
	}
	
	//Method that UPDATES the Quantity of the ALREADY ADDED Product
	private void updateQuantityAAPS() {
		if(!jbProductService.getUnitmeasurement().equals("Servicio")) {
			if(hmStocks.get(jbProductService.getBarcode()) == 0.0) {
				typeMessage = "limitReach";
				message = "Ya no quedan más de este producto en existencia";
				jfMain.setJOPMessages(typeMessage, message);
				clearBarCodeField();
			} else {
				dtmTicket.setValueAt(actualQuantityI + 1, wr, 3);
				dtmTicket.setValueAt(dfDecimals.format(actualPrice + jbProductService.getSellprice()), wr, 4);
				clearBarCodeField();
				alreadyAdded = false;
				jfMain.jlblTotal.setText(dfDecimals.format(hmTotals.get(selectedTicket)));
				
				//Subtract 1
				double oldValue = hmStocks.get(jbProductService.getBarcode());
				double newValue = oldValue - 1;
				hmStocks.replace(jbProductService.getBarcode(), oldValue, newValue);
			}
		} else {
			dtmTicket.setValueAt(actualQuantityI + 1, wr, 3);
			dtmTicket.setValueAt(dfDecimals.format(actualPrice + jbProductService.getSellprice()), wr, 4);
			clearBarCodeField();
			alreadyAdded = false;
			jfMain.jlblTotal.setText(dfDecimals.format(hmTotals.get(selectedTicket)));
		}
	}
	
	//Method that ADDS the Product to the LIST
	private void addPSToView() {
		if(!jbProductService.getUnitmeasurement().equals("Servicio")) {
			//Subtract 1
			double actualStock = hmStocks.get(jbProductService.getBarcode()) - 1;
			hmStocks.put(jbProductService.getBarcode(), actualStock);
		}

		//Add the Product/Service to the VIEW
		dtmTicket.addRow(new Object[] {
			jbProductService.getBarcode(),
			jbProductService.getDescription(),
			jbProductService.getUnitmeasurement(),
			1,
			dfDecimals.format(jbProductService.getSellprice())
		});
		TableColumnModel tcm = jtTicket.getColumnModel();
		TableColumn tc = tcm.getColumn(3);
		tc.setCellEditor(new SpinnerEditorI());
		
		clearBarCodeField();
		jfMain.jlblTotal.setText(dfDecimals.format(hmTotals.get(selectedTicket)));
	}
	
	//Method that SETS and CALL the JDGranelProduct
	private void JDGranelProduct(String icon, String title) {
		clearBarCodeField();
		JDGranelProduct jdGranelProduct = new JDGranelProduct(jfMain, icon, title, jbProductService.getStock());
		ctrlGranelProduct.setJDGranelSells(jdGranelProduct);
		ctrlGranelProduct.setData(jbProductService, jbProductService.getStock());
		ctrlGranelProduct.prepareView();
	}
	
	//Method that ADDS the ROW
	public void addGranelProductToList(double quantity, double price) {
		if(alreadyAdded()) {
			DecimalFormat dfQuantities = new DecimalFormat("#,###,##0.0");
			actualQuantityD = Double.parseDouble(dtmTicket.getValueAt(wr, 3).toString());
			actualPrice = Double.parseDouble(dtmTicket.getValueAt(wr, 4).toString());
			dtmTicket.setValueAt(dfQuantities.format(actualQuantityD + quantity), wr, 3);
			dtmTicket.setValueAt(dfDecimals.format(actualPrice + price), wr, 4);
			alreadyAdded = false;
		} else {
			dtmTicket.addRow(new Object[] {
				jbProductService.getBarcode(),
				jbProductService.getDescription(),
				jbProductService.getUnitmeasurement(),
				quantity,
				dfDecimals.format(price)
			});
			TableColumnModel tcm = jtTicket.getColumnModel();
			TableColumn tc = tcm.getColumn(3);
			tc.setCellEditor(new SpinnerEditorD());
		}
		ticketTotal = hmTotals.get(selectedTicket) + price;
		hmTotals.put(selectedTicket, ticketTotal);
		jfMain.jlblTotal.setText(dfDecimals.format(hmTotals.get(selectedTicket)));
		int addedRow = jtTicket.getRowCount() - 1;
		jtTicket.setRowSelectionInterval(addedRow, addedRow);
	}
	
	//Add the ROW with the Product/Service and the Selected Quantity
	public void addRowProductServiceQuantity(JBInventoriesProductService jbProductService, int quantity) {
		if(dtmTicket.getRowCount() != 0) {
			CtrlMain.jbProductService = jbProductService;
			if(alreadyAdded()) {
				actualQuantityI = Integer.parseInt(dtmTicket.getValueAt(wr, 3).toString());
				actualPrice = Double.parseDouble(dtmTicket.getValueAt(wr, 4).toString());
				dtmTicket.setValueAt(actualQuantityI + quantity, wr, 3);
				dtmTicket.setValueAt("$" + dfDecimals.format(actualPrice + (quantity * jbProductService.getSellprice())), wr, 4);
				alreadyAdded = false;
			} else {
				dtmTicket.addRow(new Object[] {
					jbProductService.getBarcode(),
					jbProductService.getDescription(),
					jbProductService.getUnitmeasurement(),
					quantity,
					dfDecimals.format(quantity * jbProductService.getSellprice())
				});
			}
		} else {
			dtmTicket.addRow(new Object[] {
				jbProductService.getBarcode(),
				jbProductService.getDescription(),
				jbProductService.getUnitmeasurement(),
				quantity,
				dfDecimals.format(quantity * jbProductService.getSellprice())
			});
		}
		ticketTotal = hmTotals.get(selectedTicket) + (quantity * jbProductService.getSellprice());
		hmTotals.put(selectedTicket, ticketTotal);
		jfMain.jlblTotal.setText(dfDecimals.format(hmTotals.get(selectedTicket)));
		clearBarCodeField();
	}
	
	//Class that RENDERER the Integer Spinner
	public static class SpinnerEditorI extends DefaultCellEditor {
		private static final long serialVersionUID = 1L;
		private JSpinner spinner;
        private JSpinner.DefaultEditor editor;
        private JTextField textField;
        private boolean valueSet;
        private int slrBE = 0, valueBE = 0, limit = 0;

        public SpinnerEditorI() {
            super(new JTextField());
            if(jbProductService.getUnitmeasurement().equals("Servicio")) {
            	limit = 1000;
            } else {
            	limit = (int) jbProductService.getStock();
            }
            SpinnerModel spmQuantity = new SpinnerNumberModel(1, 1, limit, 1);
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
							int value = (int) spinner.getValue();
							
							//Update the Stock
							double newStock = limit - value; 
							hmStocks.replace(jbProductService.getBarcode(), newStock);
							
							//Update GUI
							slrBE = jtTicket.getSelectedRow();
							int quantity = Integer.parseInt(spinner.getValue().toString());
			                double price = jbProductService.getSellprice();
			                double totalPrice = quantity * price; 
			                dtmTicket.setValueAt(dfDecimals.format(totalPrice), slrBE, 4);
			                
			                double ip = 0.0;
			                for(int gi = 0; gi < dtmTicket.getRowCount(); gi++) { 
			                	String valueIP = dtmTicket.getValueAt(gi, 4).toString().replace(",", "");
			                	ip += Double.parseDouble(valueIP);
			                }
			                
			                jfMain.jlblTotal.setText(dfDecimals.format(ip).toString());
			                hmTotals.put(selectedTicket, ip);
			                jfMain.jtxtfBarCode.requestFocus();
						}	
					});
				}
            });
            
            textField.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent fe) {
                    SwingUtilities.invokeLater( new Runnable() {
                        public void run() {
                            if(valueSet) {
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
                spinner.setValue(value);
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
        	valueBE = Integer.parseInt(spinner.getValue().toString());
        	try {
        		editor.commitEdit();
                spinner.commitEdit();
                jfMain.jtxtfBarCode.requestFocus();
            } catch (java.text.ParseException e) {
                JOptionPane.showMessageDialog(jfMain, "El Valor ingresado exede el Límite o es Invalido");
                textField.setText(String.valueOf(valueBE));
            }
        	
            return super.stopCellEditing();
        }
	}
	
	//Class that RENDERER the Decimals Spinner
	public static class SpinnerEditorD extends DefaultCellEditor {
		private static final long serialVersionUID = 1L;
		private JSpinner spinner;
        private JSpinner.DefaultEditor editor;
        private JTextField textField;
        private boolean valueSet;
        private int slrBE = 0;
        private double valueBE = 0.0, limit = 0.0;

        public SpinnerEditorD() {
            super(new JTextField());
            limit = jbProductService.getStock();
            SpinnerModel spmQuantity = new SpinnerNumberModel(0.1, 0.1, limit, 0.1);
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
							double value = (double) spinner.getValue();
							
							//Update the Stock
							double newStock = limit - value; 
							hmStocks.replace(jbProductService.getBarcode(), newStock);
							
							//Update GUI
							slrBE = jtTicket.getSelectedRow();
							double quantity = Double.parseDouble(spinner.getValue().toString());
			                double price = jbProductService.getSellprice();
			                double totalPrice = quantity * price; 
			                dtmTicket.setValueAt(dfDecimals.format(totalPrice), slrBE, 4);
			                
			                double ip = 0.0;
			                for(int gi = 0; gi < dtmTicket.getRowCount(); gi++) { 
			                	String valueIP = dtmTicket.getValueAt(gi, 4).toString().replace(",", "");
			                	ip += Double.parseDouble(valueIP);
			                }
			                
			                jfMain.jlblTotal.setText(dfDecimals.format(ip).toString());
			                hmTotals.put(selectedTicket, ip);
			                jfMain.jtxtfBarCode.requestFocus();
						}
					});
				}
            });
            
            textField.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent fe) {
                	slrBE = jtTicket.getSelectedRow();
                    SwingUtilities.invokeLater( new Runnable() {
                        public void run() {
                            if(valueSet) {
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
        	valueBE = Double.parseDouble(spinner.getValue().toString());
            try {
                editor.commitEdit();
                spinner.commitEdit();
            } catch (java.text.ParseException e) {
                JOptionPane.showMessageDialog(jfMain, "El Valor ingresado exede el Límite o es Invalido");
                textField.setText(String.valueOf(valueBE));
            }
            
            return super.stopCellEditing();
        }
	}
	
	//Add the Listener to the Actual JTable
	private void addJTableListener() {
		jtTicket.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				if(!lse.getValueIsAdjusting()) {
					selectedRow = jtTicket.getSelectedRow();
					if(selectedRow >= 0) {
						SwingWorker<Void, Void> getPSData = new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								setPSCC();
								return null;
							}

							@Override
							protected void done() {
								jfMain.jbtnRemoveProduct.setEnabled(true);
							}
						};
						
						//Executes the Worker
						getPSData.execute();
					}
				}
			}
		});
		
		jtTicket.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						jfMain.jtxtfBarCode.requestFocus();
					}
				});
			}
		});
	}
	
	public void enableActionsAccordingPrivileges() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if(root) {
					//JMenu System
					jfMain.jmDataBaseAction.setVisible(true);
					jfMain.jmiSBackupDB.setVisible(true);
					jfMain.jmiSRestoreDB.setVisible(true);
					jfMain.jmiSLogOut.setVisible(true);
					jfMain.jmiSInvoice.setVisible(true);
					//JMenu Till
					jfMain.jmiTMovements.setVisible(true);
					jfMain.jmiTBuys.setVisible(true);
					jfMain.jmiTCashierCourt.setVisible(true);
					jfMain.jmiTDayCourt.setVisible(true);
					//JMenu Consults
					jfMain.jmConsults.setEnabled(true);
				} else {
					for(int cp = 0; cp < cashierPrivileges.size(); cp++) {
						if(cashierPrivileges.get(cp).equals("CashInFlow")) {
							jfMain.jmiTMovements.setVisible(true);
						} else if(cashierPrivileges.get(cp).equals("CashOutFlow")) { 
							jfMain.jmiTMovements.setVisible(true);
						} else if(cashierPrivileges.get(cp).equals("DoBuys")) {
							jfMain.jmiTBuys.setVisible(true);
						} else if(cashierPrivileges.get(cp).equals("CourtDay")) {
							jfMain.jmiTDayCourt.setVisible(true);
						}
					}
				}
				//Actions
				jfMain.jmiSLogOut.setVisible(true);
				jfMain.jmiTOpenTill.setVisible(false);
				jfMain.jmTCourtCase.setVisible(true);
				jfMain.jmConsults.setEnabled(true);
				jfMain.jtxtfBarCode.setEnabled(true);
				jfMain.jtxtfBarCode.requestFocus();
				jfMain.jbtnAddTicket.setEnabled(true);
				jfMain.jbtnCancelTicket.setEnabled(true);
			}
		});
	}
		
	//Method that CLOSE the JFSales
	public void closeJFSales() {
		jfMain.dispose();
	}
	
	//Method that CLEAR the BarCode Field
	public void clearBarCodeField() {
		jfMain.jtxtfBarCode.setText("");
		jfMain.jtxtfBarCode.requestFocus();
	}
	
	//Method that SETS the info from the Last Cash Sale
	public void setLastSaleInfo(String total, double payWith, String change) {
		jfMain.jlbllTotal.setText(total);
		jfMain.jlbllPay.setText(dfDecimals.format(payWith).toString());
		jfMain.jlbllChange.setText(change);
	}
	
	//Method that CLOSE the Ticket
	public void closeTicket() {
		if(jfMain.jtpTickets.getTabCount() == 1) {
			for(int rr = dtmTicket.getRowCount() - 1; rr >= 0 ; rr--) {
				dtmTicket.removeRow(rr);
			}
			hmTotals.put(selectedTicket, 0.0);
			jfMain.jlblTotal.setText(dfDecimals.format(hmTotals.get(selectedTicket)));
		} else {
			jfMain.jtpTickets.removeTabAt(jfMain.jtpTickets.getSelectedIndex());
			hmTotals.remove(selectedTicket);
			jfMain.jtpTickets.setSelectedIndex(selectedTicket - 2);
		}
		int folio = Integer.parseInt(jfMain.jtxtfFolioNumber.getText());
		folio++;
		jfMain.jtxtfFolioNumber.setText(String.valueOf(folio));
		jfMain.jtxtfBarCode.requestFocus();
		jfMain.jbtnRemoveProduct.setEnabled(false);
		jfMain.jbtnCharge.setEnabled(false);
	}
	
	//Method that CHECKS if the File was already done
	private boolean checkFile() {
		boolean fileExist = false;
		File file = new File(dataForActionDB[0] + "." + dataForActionDB[1]);
		if(file.exists() && !file.isDirectory()) {
			fileExist = true;
		}
		return fileExist;
	}
	
	//Method that SETS the CURRENT ProductService and SETS the CORRECT Class to Renderer
	private void setPSCC() {
		barcode = dtmTicket.getValueAt(selectedRow, 0).toString();
		jbProductService = daoInventoriesProductService.getProductByBarCode(barcode);
		if(jbProductService.getUnitmeasurement().equals("Pza")) {
			TableColumnModel tcm = jtTicket.getColumnModel();
			TableColumn tc = tcm.getColumn(2);
			tc.setCellEditor(new SpinnerEditorI());
		} else if(jbProductService.getUnitmeasurement().equals("Granel")) {
			TableColumnModel tcm = jtTicket.getColumnModel();
			TableColumn tc = tcm.getColumn(2);
			tc.setCellEditor(new SpinnerEditorD());
		}
		
		clearBarCodeField();
	}
	
	//Method that REMOVES the Ticket
	private void removeTicket() {
		//First, remove all the Values with the selected Key
		jfMain.hmJTTickets.remove(selectedTicket);
		hmTotals.remove(selectedTicket);
		jfMain.jtpTickets.removeTabAt(selectedTicket - 1);
		
		//ArrayList that SAVES the Values
		ArrayList<JTable> alValues = new ArrayList<JTable>();
		//ArrayList that SAVES the Totals
		ArrayList<Double> alTotals = new ArrayList<Double>();
		
		Iterator<Integer> it = jfMain.hmJTTickets.keySet().iterator();
		while(it.hasNext()) {
			int key = (int) it.next();
			//Save VALUE
			alValues.add(jfMain.hmJTTickets.get(key));
			//Save DOUBLE
			alTotals.add(hmTotals.get(key));
		}
		
		//Clean the HashMaps
		jfMain.hmJTTickets.clear();
		hmTotals.clear();
		hmStocks.clear();
		
		//Puts the KEYS and VALUES
		for(int a = 0; a < alValues.size(); a++) {
			jfMain.hmJTTickets.put(a + 1, alValues.get(a));
			jfMain.jtpTickets.setTitleAt(a, "Ticket " + (a + 1));
			hmTotals.put(a + 1, alTotals.get(a));
		}
	}
	
	//Charge Sale
	private void callChargeSale() {
		SwingWorker<ArrayList<JBDetailsSales>, Void> callChargeSale = new SwingWorker<ArrayList<JBDetailsSales>, Void>() {
			@Override
			protected ArrayList<JBDetailsSales> doInBackground() throws Exception {
				ArrayList<JBDetailsSales> alDetailsSale = new ArrayList<JBDetailsSales>();
				for(int gtd = 0; gtd < dtmTicket.getRowCount(); gtd++) {
					String barcode = dtmTicket.getValueAt(gtd, 0).toString();
					int id_product_service = daoInventoriesProductService.getIDProductService(barcode);
					double importPrice = Double.parseDouble(dtmTicket.getValueAt(gtd, 4).toString().replace(",", ""));
					
					//Get Details
					JBDetailsSales jbDetailSale = new JBDetailsSales();
					jbDetailSale.setIDProduct(id_product_service);
					jbDetailSale.setDescription(dtmTicket.getValueAt(gtd, 1).toString());
					jbDetailSale.setUnitMeasurement(dtmTicket.getValueAt(gtd, 2).toString());
					jbDetailSale.setQuantity(Double.parseDouble(dtmTicket.getValueAt(gtd, 3).toString()));
					jbDetailSale.setImportPrice(importPrice);
					alDetailsSale.add(jbDetailSale);
					
					//Calculate SubTotal and IRS
					double iva = daoInventoriesProductService.getProductServiceIRS(id_product_service);
					subTotal += Double.parseDouble(dfDecimals.format(importPrice / iva).replace(",", ""));
				}
				
				irs = Double.parseDouble(dfDecimals.format(hmTotals.get(selectedTicket) - subTotal).replace(",", ""));
				
				return alDetailsSale;
			}

			@Override
			protected void done() {
				try {
					ArrayList<JBDetailsSales> retrivedDetails = get();
					JDCharge jdCharge = new JDCharge(jfMain);
					ctrlCharge.setJDCharge(jdCharge);
					ctrlCharge.setALSales(retrivedDetails, jfMain.jtxtfActiveUser.getText(), jfMain.jtxtfFolioNumber.getText(), subTotal, irs);
					ctrlCharge.setTotalQuantityCashier(hmTotals.get(selectedTicket), id_cashier);
					ctrlCharge.setCashierPrivileges(root, cashierPrivileges);
					ctrlCharge.prepareView();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				subTotal = 0.0;
				irs = 0.0;
			}
		};
		
		//Executes the WORKER
		callChargeSale.execute();
	}
	
	//Method that SAVES the DB
	private void actionDB() {
		SwingWorker<Integer, Void> saveBackUP = new SwingWorker<Integer, Void>() {
			@Override
			protected Integer doInBackground() throws Exception {
				int actionStatus = -1;
				Process doingAction = null;
				
				if(jfMain.actionDB.equals("backup")) {
					String action = "";
					if(System.getProperty("os.name").equals("Linux") || System.getProperty("os.name").equals("Mac OSX"))
						action = "mysqldump -u root --database pv_papeleria -r " + dataForActionDB[0] + "." + dataForActionDB[1];
					else
						action = "C:/Program Files/MySQL/MySQL Server 5.6/bin/mysqldump -u root --database pv_papeleria -r " + dataForActionDB[0] + "." + dataForActionDB[1];
					
					doingAction = Runtime.getRuntime().exec(action);
				} else if(jfMain.actionDB.equals("restore")) {
					String[] action = null;
					if(System.getProperty("os.name").equals("Linux") || System.getProperty("os.name").equals("Mac OSX"))
						action = new String[]{"mysql", "pv_papeleria", "-u" + "root", "-e", " source " + dataForActionDB[0]};
					else
					    action = new String[]{"C:/Program Files/MySQL/MySQL Server 5.6/bin/mysql", "pv_papeleria", "-u" + "root", "-e", " source " + dataForActionDB[0]};
					
					doingAction = Runtime.getRuntime().exec(action);
				}
				
				actionStatus = doingAction.waitFor();
	
				return actionStatus;
			}

			@Override
			protected void done() {
				try {
					int resultBackup = get();
					if(resultBackup == 0) {
						if(jfMain.actionDB.equals("backup")) {
							typeMessage = "backupOK";
							message = "La Base de Datos ha sido Respaldada";
						} else if(jfMain.actionDB.equals("restore")) {
							typeMessage = "restoreOK";
							message = "La Base de Datos ha sido Restaurada";
						}
						jfMain.setJOPMessages(typeMessage, message);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the WORKER
		saveBackUP.execute();
	}
	
	//Method that GETS the BUYS
	private void getBuys() {
		SwingWorker<Void, Void> getBuys = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
				String date = sdfDate.format(startDate).toString();
				alBuys = daoBuys.getBuysByDate(date);
				
				if(alBuys.isEmpty()) {
					int lastBuy = daoBuys.getLastBuyID();
					date = daoBuys.getDateBuy(lastBuy);
					alBuys = daoBuys.getBuysByDate(date);
					
					if(alBuys.isEmpty()) {
						Calendar cDate = Calendar.getInstance();
						cDate.setTime(startDate);
						dateNumbers = new ArrayList<Integer>();
						dateNumbers.add(cDate.get(Calendar.YEAR));
						dateNumbers.add(cDate.get(Calendar.MONTH));
						dateNumbers.add(cDate.get(Calendar.DAY_OF_MONTH));
					} else {
						Date dateToConver = sdfDate.parse(date);
						Calendar cDate = Calendar.getInstance();
						cDate.setTime(dateToConver);
						dateNumbers = new ArrayList<Integer>();
						dateNumbers.add(cDate.get(Calendar.YEAR));
						dateNumbers.add(cDate.get(Calendar.MONTH));
						dateNumbers.add(cDate.get(Calendar.DAY_OF_MONTH));
					}
				} else {
					Date dateToConver = sdfDate.parse(date);
					Calendar cDate = Calendar.getInstance();
					cDate.setTime(dateToConver);
					dateNumbers = new ArrayList<Integer>();
					dateNumbers.add(cDate.get(Calendar.YEAR));
					dateNumbers.add(cDate.get(Calendar.MONTH));
					dateNumbers.add(cDate.get(Calendar.DAY_OF_MONTH));
				}
				
				return null;
			}

			@Override
			protected void done() {
				JDBuys jdBuys = new JDBuys(jfMain);
				ctrlBuys.setJDBuys(jdBuys);
				ctrlBuys.setData(alBuys, dateNumbers, root, id_cashier, cashierPrivileges);
				ctrlBuys.prepareView();
			}
		};
		
		//Executes the WORKER
		getBuys.execute();
	}
	
	//Method that GETS the Sales
	private void getSales() {
		SwingWorker<Void , Void> getSales = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
				String date = sdfDate.format(startDate).toString();
				alSales = daoSales.getSalesRange(date);
				
				if(alSales.isEmpty()) {
					int lastSale = daoSales.getLastSale();
					date = daoSales.getSaleDate(lastSale);
					alSales = daoSales.getSalesRange(date);
					
					if(alSales.isEmpty()) {
						Calendar cDate = Calendar.getInstance();
						cDate.setTime(startDate);
						dateNumbers = new ArrayList<Integer>();
						dateNumbers.add(cDate.get(Calendar.YEAR));
						dateNumbers.add(cDate.get(Calendar.MONTH));
						dateNumbers.add(cDate.get(Calendar.DAY_OF_MONTH));
					} else {
						Date dateToConver = sdfDate.parse(date);
						Calendar cDate = Calendar.getInstance();
						cDate.setTime(dateToConver);
						dateNumbers = new ArrayList<Integer>();
						dateNumbers.add(cDate.get(Calendar.YEAR));
						dateNumbers.add(cDate.get(Calendar.MONTH));
						dateNumbers.add(cDate.get(Calendar.DAY_OF_MONTH));
					}
				} else {
					Date dateToConver = sdfDate.parse(date);
					Calendar cDate = Calendar.getInstance();
					cDate.setTime(dateToConver);
					dateNumbers = new ArrayList<Integer>();
					dateNumbers.add(cDate.get(Calendar.YEAR));
					dateNumbers.add(cDate.get(Calendar.MONTH));
					dateNumbers.add(cDate.get(Calendar.DAY_OF_MONTH));
				}
				
				return null;
			}

			@Override
			protected void done() {
				JDSales jdSales = new JDSales(jfMain);
				ctrlSales.setJDSales(jdSales);
				ctrlSales.setData(alSales, dateNumbers, root, cashierPrivileges);
				ctrlSales.prepareView();
			}
		};
		
		//Executes the Worker
		getSales.execute();
	}
}
