package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.clients.DAOClientsMovements;
import model.clients.DAOCredits;
import model.clients.JBClients;
import model.clients.JBClientsMovements;
import model.clients.JBCredits;
import model.inventories.DAOInventoriesMovements;
import model.inventories.DAOInventoriesProductService;
import model.inventories.JBInventoriesMovements;
import model.sales.DAODetailsSales;
import model.sales.DAOSales;
import model.sales.JBDetailsSales;
import model.sales.JBSales;
import model.till.DAOTill;
import views.JDActionClient;
import views.JDCharge;
import views.JDConsultClients;

public class CtrlCharge {
	private JDCharge jdCharge;
	private DAOCredits daoCredits;
	private DAOSales daoSells;
	private DAOInventoriesProductService daoInventoriesProductService;
	private DAODetailsSales daoDetailsSell;
	private DAOInventoriesMovements daoInventoriesMovements;
	private DAOClientsMovements daoClientsMovements;
	private DAOTill daoTill;
	private CtrlMain ctrlSales;
	private ArrayList<JBDetailsSales> alDetailsSale;
	private String typeSale = "", typeMessage = "", message = "", cashier = "", folio = "";
	private int id_cashier, selectedRow;
	private CtrlActionClient ctrlActionClient;
	private boolean root = false, invoiceSale = false;
	private CtrlConsultClients ctrlConsultClients;
	private ArrayList<String> cashierPrivileges;
	private double subTotal = 0.0, irs = 0.0;
	
	//Method that SETS the View
	public void setJDCharge(JDCharge jdCharge) {
		this.jdCharge = jdCharge;
	}
	
	//Method that SETS the Details from the Sale
	public void setALSales(ArrayList<JBDetailsSales> alDetailsSale, String cashier, String folio, double subTotal, double irs) {
		this.alDetailsSale = alDetailsSale;
		this.cashier = cashier;
		this.folio = folio;
		this.subTotal = subTotal;
		this.irs = irs;
	}
	
	//Method that SETS the Communication JDCharge - JFSales
	public void setCtrlSales(CtrlMain ctrlSales) {
		this.ctrlSales = ctrlSales;
	}
	//Method that SETS the Communication JDCharge - JDActionClient
	public void setCtrlActionClient(CtrlActionClient ctrlActionClient) {
		this.ctrlActionClient = ctrlActionClient;
	}
	//Method that SETS the Communication JDCharge - JDConsultClients
	public void setCtrlConsultClients(CtrlConsultClients ctrlConsultClients) {
		this.ctrlConsultClients = ctrlConsultClients;
	}
	
	//Method that SETS the DAOCredits
	public void setDAOClients(DAOCredits daoCredits) {
		this.daoCredits = daoCredits;
	}
	//Method that SETS the DAOSells
	public void setDAOSells(DAOSales daoSells) {
		this.daoSells = daoSells;
	}
	//Method that SETS the DAOProducts
	public void setDAOProducts(DAOInventoriesProductService daoProducts) {
		this.daoInventoriesProductService = daoProducts;
	}
	//Method that SETS the DAODetailsSell
	public void setDAODetailsSell(DAODetailsSales daoDetailsSell) {
		this.daoDetailsSell = daoDetailsSell;
	}
	//Method that SETS the DAOInventoriesMovements
	public void setDAOInventoriesMovements(DAOInventoriesMovements daoInventoriesMovements) {
		this.daoInventoriesMovements = daoInventoriesMovements;
	}
	//Method that SETS the DAOClientsHistory
	public void setDAOClientsMovements(DAOClientsMovements daoClientsMovements) {
		this.daoClientsMovements = daoClientsMovements;
	}
	//Method that SETS the DAOTill
	public void setDAOTill(DAOTill daoTill) {
		this.daoTill = daoTill;
	}
	
	//Method that SETS the Cashier Privileges
	public void setCashierPrivileges(boolean root, ArrayList<String> cashierPrivileges) {
		this.root = root;
		this.cashierPrivileges = cashierPrivileges;
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListener();
		//This has to be at the END
		jdCharge.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListener() {
		//JDialog
		jdCharge.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent we) {
				getClientsWithCredit();
			}
		});
		
		//JButtons
		jdCharge.jbtnChargeSale.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				saveSale();
			}
		});
		jdCharge.jbtnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdCharge.dispose();
			}
		});
		jdCharge.jbtnAddClient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						String title = "Agregar Cliente", icon = "/img/jframes/add_client.png";
						JDActionClient jdActionClient = new JDActionClient(jdCharge, title, icon, "JDCharge");
						ctrlActionClient.setJDActionClient(jdActionClient);
						ctrlActionClient.prepareView();
					}
				});
			}
		});
		
		//JSPinner
		jdCharge.jspPayWith.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				double total = Double.parseDouble(jdCharge.jlblTotal.getText().replace(",", ""));
				double payWith = Double.parseDouble(jdCharge.jspPayWith.getValue().toString().replace(",", ""));
				DecimalFormat dfChange = new DecimalFormat("#,###,##0.00");
				jdCharge.jlblChange.setText(dfChange.format(payWith - total));
			}
		});
		
		//JTabbedPane
		jdCharge.jtpTypePayment.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if(jdCharge.jtpTypePayment.getSelectedIndex() == 0) {
							jdCharge.jbtnChargeSale.setEnabled(true);
						} else if(jdCharge.jtpTypePayment.getSelectedIndex() == 1) {
							if(jdCharge.dtmClients.getRowCount() > 0) {
								jdCharge.jbtnChargeSale.setEnabled(true);
								jdCharge.jtxtfSearchClient.setEnabled(true);
								jdCharge.jtxtfSearchClient.requestFocus();
							} else {
								jdCharge.jbtnChargeSale.setEnabled(false);
								jdCharge.jtxtfSearchClient.setEnabled(false);
							}
						}
					}
				});
			}
		});
		
		//JTextField
		jdCharge.jtxtfSearchClient.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent de) {
				String client = jdCharge.jtxtfSearchClient.getText();
				if(client.length() == 0) {
					jdCharge.rsFilterClient.setRowFilter(null);
				} else {
					jdCharge.rsFilterClient.setRowFilter(RowFilter.regexFilter("(?i)" + client));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent de) {
				String client = jdCharge.jtxtfSearchClient.getText();
				if(client.length() == 0) {
					jdCharge.rsFilterClient.setRowFilter(null);
				} else {
					jdCharge.rsFilterClient.setRowFilter(RowFilter.regexFilter("(?i)" + client));
				}
			}

			@Override
			public void changedUpdate(DocumentEvent de) {
				String client = jdCharge.jtxtfSearchClient.getText();
				if(client.length() == 0) {
					jdCharge.rsFilterClient.setRowFilter(null);
				} else {
					jdCharge.rsFilterClient.setRowFilter(RowFilter.regexFilter("(?i)" + client));
				}
			}
		});
		
		//JTable
		jdCharge.jtClients.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				if(!lse.getValueIsAdjusting()) {
					selectedRow = jdCharge.jtClients.getSelectedRow();
					if(selectedRow >= 0) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								jdCharge.jbtnChargeSale.setEnabled(true);
								jdCharge.jtxtfSearchClient.setEnabled(true);
								jdCharge.jtxtfSearchClient.requestFocus();
							}
						});
					}
				}
			}
		});
	}
	
	//Method that SETS the Total
	public void setTotalQuantityCashier(double total, int id_cashier) {
		this.id_cashier = id_cashier;
		DecimalFormat dfDecimals = new DecimalFormat("#,###,##0.00");
		jdCharge.jlblTotal.setText(dfDecimals.format(total));
		jdCharge.jspPayWith.setValue(total);
	}
	
	//Method that GETS the Clients List
	private void getClientsWithCredit() {
		SwingWorker<ArrayList<JBCredits>, Void> getClients = new SwingWorker<ArrayList<JBCredits>, Void>() {
			@Override
			protected ArrayList<JBCredits> doInBackground() throws Exception {
				ArrayList<JBCredits> creditsList = new ArrayList<JBCredits>();
				creditsList = daoCredits.getClientsCredits();
				return creditsList;
			}

			@Override
			protected void done() {
				ArrayList<JBCredits> listRetrived = new ArrayList<JBCredits>();
				try {
					listRetrived = get();
					//Remove PREVIOUS list
					for(int r = jdCharge.dtmClients.getRowCount() - 1; r >= 0; r--) {
						jdCharge.dtmClients.removeRow(r);
					}
					//Add the new List
					for(int a = 0; a < listRetrived.size(); a++) {
						JBCredits jbClientWithCredit = new JBCredits();
						jbClientWithCredit = listRetrived.get(a);
						if(jbClientWithCredit.getIDClient() != 1) {
							jdCharge.dtmClients.addRow(new Object[] {
								jbClientWithCredit.getIDClient(),
								jbClientWithCredit.getClient(),
								jbClientWithCredit.getCreditLimit(),
								jbClientWithCredit.getCurrentBalance()
							});
						}
					}
					if(jdCharge.jtClients.getRowCount() > 0)
						jdCharge.jtClients.setRowSelectionInterval(0, 0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the Worker
		getClients.execute();
	}
	
	//Method that DO a Sell
	private void saveSale() {
		SwingWorker<Boolean, Void> saveSale = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				//KNOW the Type of Sell
				if(jdCharge.jtpTypePayment.getSelectedIndex() == 0)
					typeSale = "cash";
				else if(jdCharge.jtpTypePayment.getSelectedIndex() == 1)
					typeSale = "credit";
				
				//FLAGS
				boolean saveData = false; //Flag to KNOW if everything went good
				boolean saveSale = false;
				boolean updateTillBalance = false;
				boolean saveDetail = false;
				boolean updateBalance = false;
				boolean updateStock = false;
				boolean saveInventoryMovement = false;
				boolean saveClientMovement = false;
				
				//SALE
				DecimalFormat dfDecimals = new DecimalFormat("#########.##");
				JBSales jbSale = new JBSales();
				Date date = new Date();
				SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
				jbSale.setDate(sdfDate.format(date).toString());
				SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss a");
				jbSale.setTime(sdfTime.format(date));
				jbSale.setTotal(Double.parseDouble(jdCharge.jlblTotal.getText().replace(",", "")));
				jbSale.setIDCashier(id_cashier);
				
				if(typeSale.equals("cash")) {
					jbSale.setIDClient(1);
					jbSale.setTypeSell("Efectivo");
					jbSale.setStatus("Pagada");
					saveSale = daoSells.saveSale(jbSale);
					int id_till = daoTill.getIDTill("Abierta");
					double actualBalance = daoTill.getActualBalance();
					double totalSell = Double.parseDouble(jdCharge.jlblTotal.getText().toString().replace(",", ""));
					double newBalance = Double.parseDouble(dfDecimals.format(actualBalance + totalSell));
					updateTillBalance = daoTill.updateBalance(id_till, newBalance);
				} else if(typeSale.equals("credit")) {
					jbSale.setIDClient(Integer.parseInt(jdCharge.jtClients.getValueAt(selectedRow, 0).toString()));
					jbSale.setTypeSell("Crédito");
					jbSale.setStatus("Pendiente");
					saveSale = daoSells.saveSale(jbSale);
					
					//Save Client Movement
					if(saveSale) {
						JBClientsMovements jbClientsHistory = new JBClientsMovements();
						jbClientsHistory.setDate(jbSale.getDate());
						jbClientsHistory.setTime(jbSale.getTime());
						jbClientsHistory.setTypeMovement("Compra");
						jbClientsHistory.setQuantity(jbSale.getTotal());
						jbClientsHistory.setIDClient(jbSale.getIDClient());
						jbClientsHistory.setIDCashier(jbSale.getIDCashier());
						saveClientMovement = daoClientsMovements.saveMovement(jbClientsHistory);
					}
				}
	
				//SALE DETAILS
				String currentBalanceString = "";
				int id_sale = daoSells.getIDSale(jbSale);
				double actualBalance, actualCost, newBalance;
				double actualStock = 0, newStock = 0;
				
				if(saveSale) {
					for(int row = 0; row < alDetailsSale.size(); row++) {
						//Get the Detail, and set the ID from the Corresponding Sale
						JBDetailsSales jbDetailsSale = alDetailsSale.get(row);
						jbDetailsSale.setIDSale(id_sale);
						saveDetail = daoDetailsSell.saveDetailsSale(jbDetailsSale);
						
						int id_product = jbDetailsSale.getIDProduct();
						
						//UPDATE the STOCK
						actualStock = daoInventoriesProductService.getActualStock(id_product);
						newStock = actualStock - jbDetailsSale.getQuantity();
						updateStock = daoInventoriesProductService.updateStock(id_product, newStock);
						
						//SAVES the MOVEMENT from the Inventory
						JBInventoriesMovements jbInventoryMovement = new JBInventoriesMovements();
						jbInventoryMovement.setDate(sdfDate.format(date));
						jbInventoryMovement.setTime(sdfTime.format(date));
						jbInventoryMovement.setIdProduct(id_product);
						jbInventoryMovement.setExisted(actualStock);
						jbInventoryMovement.setMovement("Salida");
						jbInventoryMovement.setQuantity(jbDetailsSale.getQuantity());
						jbInventoryMovement.setIdCashier(id_cashier);
						saveInventoryMovement = daoInventoriesMovements.saveInventoryMovement(jbInventoryMovement);
					}
					
					if(saveDetail && updateStock && saveInventoryMovement && typeSale.equals("credit")) {
						int selectedRow = jdCharge.jtClients.getSelectedRow();
						int id_client = Integer.parseInt(jdCharge.dtmClients.getValueAt(selectedRow, 0).toString());
						currentBalanceString = daoCredits.getCurrentBalance(id_client);
						actualBalance = Double.parseDouble(currentBalanceString.replace(",", ""));
						actualCost = Double.parseDouble(jdCharge.jlblTotal.getText().replace(",", ""));
						newBalance = actualBalance + actualCost;
						DecimalFormat dfCredit = new DecimalFormat("###,##0.00");
						updateBalance = daoCredits.updateCurrentBalance(dfCredit.format(newBalance), id_client);
					}
				}
				
				if(typeSale.equals("cash") && jdCharge.jchbInvoiceSale.isSelected())
					invoiceSale = true;
				
				//If the SELL was a Credit Sell
				if(saveSale && saveClientMovement && saveDetail && updateBalance && updateStock && saveInventoryMovement)
					saveData = true;
				//If the SELL was a Cash Sell
				else if(saveSale && updateTillBalance && saveDetail && updateStock && saveInventoryMovement)
					saveData = true;
				
				return saveData;
			}

			@Override
			protected void done() {
				try {
					boolean resultSaving = get();
					if(resultSaving) {
						if(invoiceSale) {
							JDConsultClients jdConsultClients = new JDConsultClients(jdCharge, "Invoice");
							ctrlConsultClients.setJDConsultClients(jdConsultClients);
							ctrlConsultClients.setIDCashier(id_cashier);
							ctrlConsultClients.setCashierPriviliges(cashierPrivileges, root);
							ctrlConsultClients.setInvoiceInfo(alDetailsSale, folio, subTotal, irs);
							ctrlConsultClients.prepareView();
						} else if(typeSale.equals("cash")) {
							String total = jdCharge.jlblTotal.getText();
							double payWith = Double.parseDouble(jdCharge.jspPayWith.getValue().toString());
							String change = jdCharge.jlblChange.getText();
							typeMessage = "cashSell";
							message = "La Venta ha sido Registrada";
							jdCharge.setJOPMessages(typeMessage, message);
							ctrlSales.setLastSaleInfo(total, payWith, change);
							ctrlSales.closeTicket();
						} else if(resultSaving && typeSale.equals("credit")) {
							typeMessage = "creditSell";
							message = "La Venta a sido cargada al Crédito del Cliente";
							jdCharge.setJOPMessages(typeMessage, message);
							ctrlSales.closeTicket();
						}
					} 
					jdCharge.dispose();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Add a PropertyChangeListener to KNOW when the Thread was finished
		saveSale.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(saveSale.isDone()) {
						printTicket();
					}
				}
			}
		});
		
		//Executes the Worker
		saveSale.execute();
	}
	
	//Method that ADDS the new Row
	public void addNewRow(JBClients jbClient, String creditLimit, String currentBalance) {
		jdCharge.dtmClients.addRow(new Object[] {
			jbClient.getIDClient(),
			jbClient.getNames() + " " + jbClient.getFirstName() + " " + jbClient.getLastName(),
			creditLimit,
			currentBalance
		});
		
		int rowCount = jdCharge.jtClients.getRowCount() - 1;
		jdCharge.jtClients.setRowSelectionInterval(rowCount, rowCount);
		
		jdCharge.jtxtfSearchClient.requestFocus();
	}
	
	//Method that GENERATES and PRINT the Ticket
	private void printTicket() {
		SwingWorker<Void, Void> printTicket = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				//Decimals
				//DecimalFormat dfDecimals = new DecimalFormat("#,###,##0.00");
				
				//Date - Time
				Date date = new Date();
				SimpleDateFormat sdfDH = new SimpleDateFormat("dd'-'MM'-'yyyy hh:mm a");
				StringBuilder sbDateTime = new StringBuilder(sdfDH.format(date));
				for(int qdt = sbDateTime.length(); qdt < 40; qdt++)
					sbDateTime.insert(0, " ");
				//Cashier
				StringBuilder sbCashier = new StringBuilder(cashier);
				for(int qc = sbCashier.length(); qc < 29; qc++)
					sbCashier.insert(0, " ");
				//Sale Number
				StringBuilder sbFolio = new StringBuilder(folio);
				for(int qf = sbFolio.length(); qf < 27; qf++)
					sbFolio.insert(0, " ");
				
				//Ticket Structure
//				String ticket1 = "               PACK-PC\n"
//						       + "          Calle Molinos #145\n"
//						       + "              Casa Blanca\n"
//						       + "             Cd. Fernández\n"
//						       + "             CADG800427LW5\n\n"
//						       + sbDateTime +"\n"
//						       + "Le Atendio:" + sbCashier + "\n"
//						       + "No. de Folio:" + sbFolio + "\n\n"
//						       + "Cant.            Precio          Importe\n"
//						       + "========================================\n";
//				
//				String ticket2 = "";
//				for(int gds = 0; gds < alDetailsSale.size(); gds++) {
//					JBDetailsSales jbDetailSale = alDetailsSale.get(gds);
//					
//					//Description
//					String description = jbDetailSale.getDescription();
//					
//					//Quantity
//					StringBuilder sbQuantity = new StringBuilder(jbDetailSale.getQuantity());
//					if(sbQuantity.length() <= 5) {
//						int lenghtSBQuantity = sbQuantity.length();
//						switch(lenghtSBQuantity) {
//							case 1:
//								sbQuantity.insert(0, "    ");
//								break;
//							case 2:
//								sbQuantity.insert(0, "   ");
//								break;
//							case 3:
//								sbQuantity.insert(0, "  ");
//								break;
//							case 4:
//								sbQuantity.insert(0, " ");
//								break;
//						}
//					}
//					
//					//Price
//					int newLenghtQuantity = sbQuantity.length();
//					StringBuilder sbPrice = new StringBuilder("$" + String.valueOf(dfDecimals.format(jbDetailSale.getImportPrice())));
//					int wherePutPrice = 23 - sbPrice.length();
//					for(int spp = newLenghtQuantity; spp < wherePutPrice; spp++) {
//						sbPrice.insert(0, " ");
//					}
//					
//					//Import
//					int newLenghtPrice = sbPrice.length() + newLenghtQuantity;
//					StringBuilder sbImport = new StringBuilder("$" + dfDecimals.format(jbDetailSale.getImportPrice()));
//					int wherePutImport = 40 - sbImport.length();
//					for(int spi = newLenghtPrice; spi < wherePutImport; spi++) {
//						sbImport.insert(0, " ");
//					}
//					
//					ticket2 = ticket2 + description + "\n" + sbQuantity + sbPrice + sbImport + "\n";
//				}
//				
//				//Total
//				StringBuilder sbTotal = new StringBuilder("$" + jdCharge.jlblTotal.getText());
//				for(int spt = sbTotal.length(); spt < 13; spt++) {
//					sbTotal.insert(0, " ");
//				}
//				//Pay With
//				StringBuilder sbPayWith = new StringBuilder("$" + dfDecimals.format(jdCharge.jspPayWith.getValue()));
//				for(int sppw = sbPayWith.length(); sppw < 13; sppw++) {
//					sbPayWith.insert(0, " ");
//				}
//				
//				//Change
//				StringBuilder sbChange = new StringBuilder("$" + jdCharge.jlblChange.getText());
//				for(int spc = sbChange.length(); spc < 13; spc ++) {
//					sbChange.insert(0, " ");
//				}
//				
//			    String ticket3 = "========================================\n\n"
//						       + "                     Total:" + sbTotal + "\n"
//						       + "                  Pagó Con:" + sbPayWith + "\n"
//						       + "                 Su Cambió:" + sbChange + "\n\n"
//						       + "            ¡GRACIAS POR SU COMPRA!\n"
//						       + "              pack-pc@hotmail.com";
			    
//			    System.out.println(ticket1 + ticket2 + ticket3);
				
//			    //Send to Printer
//			    String ticket = ticket1 + ticket2 + ticket3;
//			    DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
//			    PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
//			    PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
//			    PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
//			    System.out.println(defaultService.getName());
//			    PrintService service = ServiceUI.printDialog(null, 700, 200, printService, defaultService, flavor, pras);
//			    byte[] bytes = ticket.getBytes();
//			    Doc doc = new SimpleDoc(bytes,flavor,null);
//			    DocPrintJob job = service.createPrintJob();
//			    job.print(doc, null);
			     
				return null;
			}
		};
		
		//Executes the WORKER
		printTicket.execute();
	}
}
