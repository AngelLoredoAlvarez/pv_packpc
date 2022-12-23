package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import model.buys.DAOBuys;
import model.cashiers.DAOCashiers;
import model.cashiers.DAOCashiersTurns;
import model.cashiers.JBCashiers;
import model.cashiers.JBCashiersTurns;
import model.clients.DAOClientsMovements;
import model.inventories.DAOInventoriesProductService;
import model.sales.DAODetailsSales;
import model.sales.DAOSales;
import model.sales.JBDetailsSales;
import model.sales.JBSales;
import model.till.DAOTill;
import model.till.DAOTillMovements;
import model.till.JBTillMovements;
import views.JDCloseTurn;
import views.JDTillCourt;

public class CtrlTillCourt {
	//Attributes
	private JDTillCourt jdTillCourt;
	private int id_cashier = 0;
	private DecimalFormat dfQuantities;
	private double beginigBalance = 0.0, finalBalance = 0.0, cashSales = 0.0, creditSales = 0.0, buys = 0.0, totalPayments = 0.0, totalCashInflows = 0.0, totalCashOutflows = 0.0, total_earnings = 0.0;
	private JBCashiers jbCashier;
	private String startDate = "", startTime = "", endDate = "", endTime = "", typeCourt = "";
	private ArrayList<JBCashiersTurns> alCashierTurns;
	private ArrayList<JBTillMovements> cashInflows, cashOutflows;
	private DAOCashiers daoCashiers;
	private DAOCashiersTurns daoCashiersTurns;
	private DAOTill daoTill;
	private DAOSales daoSales;
	private DAOBuys daoBuys;
	private DAOClientsMovements daoClientsMovements;
	private DAOTillMovements daoTillMovements;
	private DAODetailsSales daoDetailsSell;
	private DAOInventoriesProductService daoInventoriesProductsServices;
	private HashMap<String, Double> hmEarningsByDepartment;
	private CtrlCloseTurn ctrlCloseTurn;
	private Date date;
	
	//Method that SETS the VIEW
	public void setJDTillCourt(JDTillCourt jdTillCourt) {
		this.jdTillCourt = jdTillCourt;
	}
	
	//Method that SETS the DAOCashiers
	public void setDAOCashiers(DAOCashiers daoCashiers) {
		this.daoCashiers = daoCashiers;
	}
	//Method that SETS the DAOCashiersTurns
	public void setDAOCashiersTurns(DAOCashiersTurns daoCashiersTurns) {
		this.daoCashiersTurns = daoCashiersTurns;
	}
	//Method that SETS the DAOTill
	public void setDAOTill(DAOTill daoTill) {
		this.daoTill = daoTill;
	}
	//Method that SETS the DAOSells
	public void setDAOSells(DAOSales daoSells) {
		this.daoSales = daoSells;
	}
	//Method that SETS the DAOBuys
	public void setDAOBuys(DAOBuys daoBuys) {
		this.daoBuys = daoBuys;
	}
	//Method that SETS the DAOPayment
	public void setDAOPayment(DAOClientsMovements daoPayment) {
		this.daoClientsMovements = daoPayment;
	}
	//Method that SETS the DAOTillMovements
	public void setDAOTillMovements(DAOTillMovements daoTillMovements) {
		this.daoTillMovements = daoTillMovements;
	}
	//Method that SETS the DAODetailsSell
	public void setDAODetailsSell(DAODetailsSales daoDetailsSell) {
		this.daoDetailsSell = daoDetailsSell;
	}
	//Method that SETS the DAOProducts
	public void setDAOProducts(DAOInventoriesProductService daoProducts) {
		this.daoInventoriesProductsServices = daoProducts;
	}
	
	//Method that SETS the Communication JDTillCourt - JDCloseTurn
	public void setCtrlCloseTurn(CtrlCloseTurn ctrlCloseTurn) {
		this.ctrlCloseTurn = ctrlCloseTurn;
	}
	//Method that SETS the Communication CtrlTillCourt - CtrlCloseSystem
	public void setCtrlCloseSystem(CtrlCloseSystem ctrlCloseSystem) {
	}
	
	//Method that SETS the ID from the Cashier
	public void setDATA(Date startDate, int id_cashier) {
		this.date = startDate;
		this.id_cashier = id_cashier;
		setInfo();
	}
	
	//Method that Prepares the VIEW
	public void prepareView() {
		//Initializations
		dfQuantities = new DecimalFormat("#,###,##0.00");
		this.setListeners();
		//This has to be at the END
		jdTillCourt.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JDatePicker
		jdTillCourt.jdpDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				date = (Date) jdTillCourt.jdpDate.getModel().getValue();
				setInfo();
			}
		});
		
		//JButtons
		jdTillCourt.jbtnCloseAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if(jdTillCourt.action.equals("Corte de Cajero"))
							typeCourt = "Corte de Cajero";
						else if(jdTillCourt.action.equals("Corte del Día")) 
							typeCourt = "Corte del Día";
						
						JDCloseTurn jdCloseTurn = new JDCloseTurn(jdTillCourt, "TillCourt", typeCourt);
						ctrlCloseTurn.setJDCloseTurn(jdCloseTurn);
						JBCashiersTurns jbCashierTurn = new JBCashiersTurns();
						jbCashierTurn.setStartDate(startDate);
						jbCashierTurn.setStartTime(startTime);
						jbCashierTurn.setEndDate(endDate);
						jbCashierTurn.setStatus("Cerrado");
						jbCashierTurn.setIDCashier(id_cashier);
						ctrlCloseTurn.setData(jbCashierTurn, finalBalance);
						ctrlCloseTurn.prepareView();
					}
				});
			}
		});
		jdTillCourt.jbtnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdTillCourt.dispose();
			}
		});
	}
	
	//Method that SETS ALL the INFO
	private void setInfo() {
		SwingWorker<Void, Void> setDataCashierCourt = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				//Variables that ARE the SAME for the two types of COURT
				SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
				SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss aa");
				JBSales jbSales = new JBSales();
				JBTillMovements jbTillMovement = new JBTillMovements();
				cashInflows = new ArrayList<JBTillMovements>();
				cashOutflows = new ArrayList<JBTillMovements>();
				hmEarningsByDepartment = new HashMap<String, Double>();
				
				//Data that it's the SAME for the two types of COURT
				beginigBalance = daoTill.getBeginigBalance();
				finalBalance = daoTill.getActualBalance();
				JBCashiersTurns jbCashierTurn = daoCashiersTurns.getInitTurnInfo("Abierto", id_cashier);
				startDate = jbCashierTurn.getStartDate();
				startTime = jbCashierTurn.getStartTime();
				jbCashierTurn.setIDCashier(id_cashier);
				
				//If the COURT is from a Cashier
				if(jdTillCourt.action.equals("Corte de Cajero")) {
					jbCashier = new JBCashiers();
					jbCashier = daoCashiers.getCashierName(id_cashier);
					
					Date actualDate = new Date();
					endDate = sdfDate.format(actualDate).toString();
					endTime = sdfTime.format(actualDate).toString();
					jbCashierTurn.setEndDate(endDate);
					
					if(startDate.equals(endDate)) {
						alCashierTurns = daoCashiersTurns.getTurnsCashier(jbCashierTurn);
						
						jbSales.setDate(endDate);
						jbSales.setIDCashier(id_cashier);
						//Total Cash Sales
						jbSales.setTypeSell("Efectivo");
						cashSales = daoSales.getTotalSaleTypeSaleCashier(startTime, endTime, jbSales);
						//Total Credit Sales
						jbSales.setTypeSell("Crédito");
						creditSales = daoSales.getTotalSaleTypeSaleCashier(startTime, endTime, jbSales);
						
						//Total Buys
						buys = daoBuys.getTotalBuysByCashier(endDate, startTime, endTime, id_cashier);
						
						//Earnings
						ArrayList<Integer> alSalesListByCashier = new ArrayList<Integer>();
						alSalesListByCashier = daoSales.getIDSalesByCashier(startTime, endTime, id_cashier);
						for(int gls = 0; gls < alSalesListByCashier.size(); gls++) {
							int id_sale = alSalesListByCashier.get(gls);
							ArrayList<JBDetailsSales> alProductsInSale = new ArrayList<JBDetailsSales>();
							alProductsInSale = daoDetailsSell.getSoldProducts(id_sale);
							for(int gsp = 0; gsp < alProductsInSale.size(); gsp++) {
								JBDetailsSales jbDetailSell = new JBDetailsSales();
								jbDetailSell = alProductsInSale.get(gsp);
								
								//TotalEarnings
								double importSell = jbDetailSell.getImportPrice();
								double buyPriceProduct = daoInventoriesProductsServices.getBuyPrice(jbDetailSell.getIDProduct());
								double quantity = jbDetailSell.getQuantity();
								double quantityToRest = buyPriceProduct * quantity;
								total_earnings = total_earnings + (importSell - quantityToRest);
								
								//Earnings by Department
								String department = daoInventoriesProductsServices.getDepartmentFromProduct(jbDetailSell.getIDProduct());
								if(hmEarningsByDepartment.containsKey(department)) {
									double actualEarning = hmEarningsByDepartment.get(department);
									double newEarning = actualEarning + (importSell - quantityToRest);
									hmEarningsByDepartment.replace(department, newEarning);
								} else {
									hmEarningsByDepartment.put(department, importSell - quantityToRest);
								}
							}
						}
						
						//Total of Payments
						totalPayments = daoClientsMovements.getTotalPaymentsByCashier(endDate, startTime, endTime, id_cashier);
						
						jbTillMovement.setDate(endDate);
						jbTillMovement.setIDCashier(id_cashier);
						jbTillMovement.setTypeMovement("Entrada");
						jbTillMovement.setDate(endDate);
						totalCashInflows = daoTillMovements.getTotalMovementsByCashier(jbTillMovement, startTime, endTime);
						cashInflows = daoTillMovements.getListMovementsByCashier(jbTillMovement, startTime, endTime);
						jbTillMovement.setTypeMovement("Salida");
						jbTillMovement.setDate(endDate);
						totalCashOutflows = daoTillMovements.getTotalMovementsByCashier(jbTillMovement, startTime, endTime);
						cashOutflows = daoTillMovements.getListMovementsByCashier(jbTillMovement, startTime, endTime);
					} else {
						
					}
				}
				
				//If the COURT is from the ACTUAL DAY
				else if(jdTillCourt.action.equals("Corte del Día")) {
					String dateSelected = sdfDate.format(date).toString(); 
					jbSales.setDate(dateSelected);
					//Total Cash Sales in the Day
					jbSales.setTypeSell("Efectivo");
					cashSales = daoSales.getTotalSalesDay(jbSales);
					//Total Credit Sales in the DAY
					jbSales.setTypeSell("Crédito");
					creditSales = daoSales.getTotalSalesDay(jbSales);
					
					//Total Buys in the Day
					buys = daoBuys.getTotalBuysByDay(dateSelected);
					
					//EARNINGS
					ArrayList<Integer> alSalesListInDay = new ArrayList<Integer>();
					alSalesListInDay = daoSales.getIDSalesInDay(dateSelected);
					for(int gsl = 0; gsl < alSalesListInDay.size(); gsl++) {
						int id_sale = alSalesListInDay.get(gsl);
						ArrayList<JBDetailsSales> alProductsInSale = new ArrayList<JBDetailsSales>();
						alProductsInSale = daoDetailsSell.getSoldProducts(id_sale);
						for(int gsp = 0; gsp < alProductsInSale.size(); gsp++) {
							JBDetailsSales jbDetailSell = new JBDetailsSales();
							jbDetailSell = alProductsInSale.get(gsp);
							//Total Earnings
							double importSell = jbDetailSell.getImportPrice();
							double buyPriceProduct = daoInventoriesProductsServices.getBuyPrice(jbDetailSell.getIDProduct());
							double quantity = jbDetailSell.getQuantity();
							double quantityToRest = buyPriceProduct * quantity;
							total_earnings = total_earnings + (importSell - quantityToRest);
							
							//Earnings by Department
							String department = daoInventoriesProductsServices.getDepartmentFromProduct(jbDetailSell.getIDProduct());
							if(hmEarningsByDepartment.containsKey(department)) {
								double actualEarning = hmEarningsByDepartment.get(department);
								double newEarning = actualEarning + (importSell - quantityToRest);
								hmEarningsByDepartment.replace(department, newEarning);
							} else {
								hmEarningsByDepartment.put(department, importSell - quantityToRest);
							}
						}
					}
					
					totalPayments = daoClientsMovements.getTotalPaymentsInDay(dateSelected);
					
					jbTillMovement.setDate(dateSelected);
					jbTillMovement.setTypeMovement("Entrada");
					totalCashInflows = daoTillMovements.getTotalMovementsByDay(jbTillMovement);
					cashInflows = daoTillMovements.getListMovementsByDay(jbTillMovement);
					jbTillMovement.setTypeMovement("Salida");
					totalCashOutflows = daoTillMovements.getTotalMovementsByDay(jbTillMovement);
					cashOutflows = daoTillMovements.getListMovementsByDay(jbTillMovement);
				}
				
				return null;
			}
				@Override
			protected void done() {
				//Update the GUI
				if(jdTillCourt.action.equals("Corte de Cajero")) {
					jdTillCourt.jlblCashier.setText(jbCashier.getNames());
					//Remove PREVIOUS Turns
					jdTillCourt.jcbTurns.removeAllItems();
					//Add the NEW Turns
					for(int at = 0; at < alCashierTurns.size(); at++) {
						JBCashiersTurns jbCashierTurn = new JBCashiersTurns();
						jbCashierTurn = alCashierTurns.get(at);
						jdTillCourt.jcbTurns.addItem(jbCashierTurn.getStartTime() + "-" + endTime);
					}
					jdTillCourt.jcbTurns.addItem(startTime + "-" + endTime);
					jdTillCourt.jcbTurns.setSelectedIndex(jdTillCourt.jcbTurns.getItemCount() - 1);
					jdTillCourt.jbtnCloseAction.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/close_turn.png")));
					jdTillCourt.jbtnCloseAction.setText("Cerrar Turno");
				} else if(jdTillCourt.action.equals("Corte del Día")) {
					jdTillCourt.jlbltCashier.setVisible(false);
					jdTillCourt.jlblCashier.setVisible(false);
					jdTillCourt.jlbltTurns.setVisible(false);
					jdTillCourt.jcbTurns.setVisible(false);
					jdTillCourt.jbtnCloseAction.setIcon(new ImageIcon(getClass().getResource("/img/jbuttons/small/close_till.png")));
					jdTillCourt.jbtnCloseAction.setText("Cerrar Caja");
				}
				
				jdTillCourt.jlblTotalSales.setText(dfQuantities.format(cashSales + creditSales));
				jdTillCourt.jlblEarnings.setText(dfQuantities.format(total_earnings));
				
				//Remove the Previous Rows
				for(int rrtm = jdTillCourt.dtmTillMoney.getRowCount() - 1; rrtm >= 0; rrtm--) {
					jdTillCourt.dtmTillMoney.removeRow(rrtm);
				}
				for(int rrsd = jdTillCourt.dtmSalesByDepartment.getRowCount() - 1; rrsd >= 0; rrsd--) {
					jdTillCourt.dtmSalesByDepartment.removeRow(rrsd);
				}
				for(int rrci = jdTillCourt.dtmCashInflows.getRowCount() - 1; rrci >= 0; rrci--) {
					jdTillCourt.dtmCashInflows.removeRow(rrci);
				}
				for(int rrco = jdTillCourt.dtmCashOutflows.getRowCount() -1; rrco >= 0; rrco--) {
					jdTillCourt.dtmCashOutflows.removeRow(rrco);
				}
				
				//JTable Till Money====================================================================================
				jdTillCourt.dtmTillMoney.addRow(new Object[] {
					"Saldo Inicial en Caja",
					"$" + dfQuantities.format(beginigBalance)
				});
				jdTillCourt.dtmTillMoney.addRow(new Object[] {
					"Ventas en Efectivo",
					"+ $" + dfQuantities.format(cashSales)
				});
				jdTillCourt.dtmTillMoney.addRow(new Object[] {
						"Ventas a Crédito",
						"$" + dfQuantities.format(creditSales)
				});
				jdTillCourt.dtmTillMoney.addRow(new Object[] {
					"Compras", 
					"$" + dfQuantities.format(buys)
				});
				jdTillCourt.dtmTillMoney.addRow(new Object[] {
					"Abonos en Efectivo", 
					"+ $" + dfQuantities.format(totalPayments)
				});
				jdTillCourt.dtmTillMoney.addRow(new Object[] {
					"Entradas de Efectivo",
					"+ $" + dfQuantities.format(totalCashInflows)
				});
				jdTillCourt.dtmTillMoney.addRow(new Object[] {
					"Salidas de Efectivo",
					"- $" + dfQuantities.format(totalCashOutflows)
				});
				jdTillCourt.dtmTillMoney.addRow(new Object[] {
					"Total de Dinero en Caja",
					"$" + dfQuantities.format(finalBalance)
				});
				//JTable Till Money====================================================================================
				
				//JTable Sales by Department===========================================================================
				Iterator<String> it = hmEarningsByDepartment.keySet().iterator();
				while(it.hasNext()) {
					String key = (String) it.next();
					double earning = hmEarningsByDepartment.get(key);
					jdTillCourt.dtmSalesByDepartment.addRow(new Object[] {
						key,
						"$" + dfQuantities.format(earning)
					});
				}
				
				//JTable Sales by Department===========================================================================
				
				//JTable Cash Inflows==================================================================================
				for(int aci = 0; aci < cashInflows.size(); aci++) {
					JBTillMovements jbCashInflows = new JBTillMovements();
					jbCashInflows = cashInflows.get(aci);
					jdTillCourt.dtmCashInflows.addRow(new Object[] {
						jbCashInflows.getTime(),
						jbCashInflows.getComentCN(),
						"$" + dfQuantities.format(jbCashInflows.getBalance())
					});
				}
				//JTable Cash Inflows==================================================================================
				
				//JTable Cash Outflows=================================================================================
				for(int aco = 0; aco < cashOutflows.size(); aco++) {
					JBTillMovements jbCashOutflows = new JBTillMovements();
					jbCashOutflows = cashOutflows.get(aco);
					jdTillCourt.dtmCashOutflows.addRow(new Object[] {
						jbCashOutflows.getTime(),
						jbCashOutflows.getComentCN(),
						"$" + dfQuantities.format(jbCashOutflows.getBalance())
					});
				}
				//JTable Cash Outflows=================================================================================
				
				//Reset Variables
				total_earnings = 0.0;
			}
		};
		
		//Executes the WORKER
		setDataCashierCourt.execute();
	}
}
