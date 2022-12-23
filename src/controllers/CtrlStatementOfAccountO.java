package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.clients.DAOClientsMovements;
import model.clients.DAOCredits;
import model.clients.JBClientsMovements;
import model.clients.JBCredits;
import model.sales.DAODetailsSales;
import model.sales.DAOSales;
import model.sales.JBDetailsSales;
import model.sales.JBSales;
import views.JDPayment;
import views.JDClientsMovements;
import views.JDStatementOfAccount;

public class CtrlStatementOfAccountO {
	//Attributes
	private JDStatementOfAccount jdStatementOfAccount;
	//Communication with other Views
	private CtrlPayment ctrlPayment;
	private CtrlClientsMovements ctrlClientsMovements;
	//Variables
	private int id_cashier = 0, id_client, selectedRow = 0;
	private double total = 0.0;
	private ArrayList<JBSales> alSales;
	private ArrayList<JBDetailsSales> alDetailsSale;
	private ArrayList<JBClientsMovements> alHistory;
	private DecimalFormat dfDecimals;
	private Date iDate, eDate;
	private String typeMessage = "", message = "", initDate = "", endDate = "";
	
	//DAOS
	private DAOClientsMovements daoClientsMovements;
	private DAODetailsSales daoDetailsSell;
	private DAOSales daoSales;
	private DAOCredits daoCredits;
	
	//Method that SETS the VIEW
	public void setJDStatementOfAccount(JDStatementOfAccount jdStatementOfAccount) {
		this.jdStatementOfAccount = jdStatementOfAccount;
	}
	
	//Communication with other VIEWS
	//Method that SETS the Communication between JDStatementOfAccount - JDPay
	public void setCtrlPay(CtrlPayment ctrlPay) {
		this.ctrlPayment = ctrlPay;
	}
	//Method that SETS the Communication between JDStatementOfAccount - JDPaymentHistory
	public void setCtrlPaymentHistory(CtrlClientsMovements ctrlPaymentHistory) {
		this.ctrlClientsMovements = ctrlPaymentHistory;
	}
	
	//DAOS
	//Method that SETS the DAOSells
	public void setDAOSells(DAOSales daoSales) {
		this.daoSales = daoSales;
	}
	//Method that SETS the DAODetailsSell
	public void setDAODetailsSell(DAODetailsSales daoDetailsSell) {
		this.daoDetailsSell = daoDetailsSell;
	}
	//Method that SETS the DAOPayment
	public void setDAOPayment(DAOClientsMovements daoPayment) {
		this.daoClientsMovements = daoPayment;
	}
	//Method that SETS the DAOCredits
	public void setDAOCredits(DAOCredits daoCredits) {
		this.daoCredits = daoCredits;
	}
	
	//Method that SETS the Initial Data
	public void setData(JBCredits creditInfo, ArrayList<JBSales> listSales, String dateString, ArrayList<Integer> dateNumbers, int id_client, int id_cashier) {
		this.alSales = listSales;
		this.id_cashier = id_cashier;
		this.id_client = id_client;
		this.initDate = dateString;
		this.endDate = dateString;
		
		//Sets the Info of the credit from the selected Client
		jdStatementOfAccount.jlblNo.setText(String.valueOf(creditInfo.getIDClient()));
		jdStatementOfAccount.jlblClient.setText(creditInfo.getClient());
		jdStatementOfAccount.jlblCurrentBalance.setText(creditInfo.getCurrentBalance());
		jdStatementOfAccount.jlblCreditLimit.setText(creditInfo.getCreditLimit());
		if(creditInfo.getCurrentBalance().equals("0.00")) {
			jdStatementOfAccount.jbtnPay.setEnabled(false);
			jdStatementOfAccount.jbtnLiquidate.setEnabled(false);
		}
		if(creditInfo.getCreditLimit().equals("Sin Límite")) { 
			jdStatementOfAccount.jlbltCoinCreditLimit.setVisible(false);
		}
		
		jdStatementOfAccount.jdpInitDate.getModel().setYear(dateNumbers.get(0));
		jdStatementOfAccount.jdpInitDate.getModel().setMonth(dateNumbers.get(1));
		jdStatementOfAccount.jdpInitDate.getModel().setDay(dateNumbers.get(2));
		jdStatementOfAccount.jdpInitDate.getModel().setSelected(true);
		
		jdStatementOfAccount.jdpEndDate.getModel().setYear(dateNumbers.get(0));
		jdStatementOfAccount.jdpEndDate.getModel().setMonth(dateNumbers.get(1));
		jdStatementOfAccount.jdpEndDate.getModel().setDay(dateNumbers.get(2));
		jdStatementOfAccount.jdpEndDate.getModel().setSelected(true);
		
		//Sets the Sales
		addRowsJTDates();
		
		//Sets the Details
		retriveDetails();
	}
	
	//Method that SETS the SELLS
	public void prepareView() {
		//Set the Listeners
		this.setListeners();
		//This has to be at the END
		jdStatementOfAccount.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JButtons
		jdStatementOfAccount.jbtnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdStatementOfAccount.dispose();
			}
		});
		jdStatementOfAccount.jbtnPaymentHistory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				getClientsMovements();
			}
		});
		jdStatementOfAccount.jbtnPay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JDPayment jdPayment = new JDPayment(jdStatementOfAccount);
						ctrlPayment.setJDPay(jdPayment);
						ctrlPayment.setIDCashier(id_cashier);
						ctrlPayment.prepareView();
					}
				});
			}
		});
		jdStatementOfAccount.jbtnLiquidate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				liquidateBalance();
			}
		});
		
		//JDatePickers
		jdStatementOfAccount.jdpInitDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				getRange();
			}
		});
		jdStatementOfAccount.jdpEndDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				getRange();
			}
		});

		//JTable Dates
		jdStatementOfAccount.jtDates.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				if(!lse.getValueIsAdjusting()) {
					selectedRow = jdStatementOfAccount.jtDates.getSelectedRow();
					if(selectedRow >= 0) {
						retriveDetails();
					}
				}
			}
		});
	}
		
	//Method that RETRIVES the DETAILS from the Selected Sell
	private void retriveDetails() {
		SwingWorker<Void, Void> retriveDetails = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				int id_sale = Integer.parseInt(jdStatementOfAccount.dtmDates.getValueAt(selectedRow, 0).toString());
				total = daoSales.getTotalSale(id_sale);
				alDetailsSale = new ArrayList<JBDetailsSales>();
				alDetailsSale = daoDetailsSell.getDetails(id_sale);
				dfDecimals = new DecimalFormat("#,###,###.00");
				
				return null;
			}

			@Override
			protected void done() {
				//Remove the Previous List
				removeRowsJTDetails();
				
				//Add the new List
				for(int a = 0; a < alDetailsSale.size(); a++) {
					JBDetailsSales jbDetails = new JBDetailsSales();
					jbDetails = alDetailsSale.get(a);
					jdStatementOfAccount.dtmProducts.addRow(new Object[] {
						jbDetails.getDescription(),
						"$" + dfDecimals.format(jbDetails.getSellPrice()),
						jbDetails.getQuantity(),
						"$" + dfDecimals.format(jbDetails.getImportPrice())
					});
				}
				
				//Update the Total
				jdStatementOfAccount.jlblTotal.setText(dfDecimals.format(total));
			}
		};
		
		//Executes the Worker
		retriveDetails.execute();
	}
	
	//Method that Retrieves the Lowest Sale
	private void getRange() {
		SwingWorker<Void, Void> salesRange = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
				
				//InitDate
				iDate = (Date) jdStatementOfAccount.jdpInitDate.getModel().getValue();
				initDate = sdfDate.format(iDate);
				
				//EndDate
				eDate = (Date) jdStatementOfAccount.jdpEndDate.getModel().getValue();
				endDate = sdfDate.format(eDate);
				
				String selectedInitDate = initDate;
				
				int minSale = daoSales.retriveMinIDFromClient(initDate, id_client);
				int maxSale = daoSales.retriveMaxIDFromClient(endDate, id_client);
				
				int counter = 0;
				
				if(minSale == 0) {
					if(!iDate.after(eDate)) {
						while(true) {
							Calendar cal = Calendar.getInstance();
							cal.setTime(iDate);
							cal.add(Calendar.DAY_OF_MONTH, counter);
							initDate = sdfDate.format(cal.getTime());
							minSale = daoSales.retriveMinIDFromClient(initDate, id_client);
							if(minSale > 0) {
								break;
							} else {
								counter++;
							}
						}
					} else {
						typeMessage = "initLarger";
						message = "La Fecha de Inicio no puede ser mayor a la Fecha de Fin";
					}
				}
				
				if(maxSale == 0) {
					counter = 0;
					if(!eDate.before(iDate)) {
						while(true) {
							Calendar cal = Calendar.getInstance();
							cal.setTime(eDate);
							cal.add(Calendar.DAY_OF_MONTH, counter);
							endDate = sdfDate.format(cal.getTime());
							maxSale = daoSales.retriveMaxIDFromClient(endDate, id_client);
							if(maxSale > 0) {
								break;
							} else {
								if(endDate.equals(selectedInitDate)) {
									break;
								} else {
									counter--;
								}
							}
						}
					} else {
						typeMessage = "endLower";
						message = "La Fecha de Fin no puede ser menor a la Fecha de Inicio";
					}
				}
				
				alSales = daoSales.getSalesRangeFromClient(minSale, maxSale, id_client);
				
				return null;
			}

			@Override
			protected void done() {
				if(typeMessage.equals("") && message.equals("")) {
					if(!alSales.isEmpty()) {
						//Remove the PREVIOUS Rows
						removeRowsJTDates();
						//Adds the NEW Rows
						addRowsJTDates();
					} else {
						removeRowsJTDates();
						removeRowsJTDetails();
					}
				} else {
					jdStatementOfAccount.setJOPMessages(typeMessage, message);
				}
				typeMessage = "";
				message = "";
			}
		};
		
		//Executes the WORKER
		salesRange.execute();
	}
	
	//Method that GETS all the History Payment from the selected Client
	public void getClientsMovements() {
		SwingWorker<Void, Void> getHistoryPayment = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				alHistory = new ArrayList<JBClientsMovements>();
				int id_client = Integer.parseInt(jdStatementOfAccount.jlblNo.getText());
				alHistory = daoClientsMovements.getMovements(id_client);
				
				return null;
			}
			
			@Override
			protected void done() {
				Date date = new Date();
				JDClientsMovements jdClientsMovements = new JDClientsMovements(jdStatementOfAccount, date);
				ctrlClientsMovements.setJDClientsMovements(jdClientsMovements);
				ctrlClientsMovements.setListMovements(alHistory);
				ctrlClientsMovements.prepareView();
			}
		};
		
		//Executes the WORKER
		getHistoryPayment.execute();
	}
	
	//Method that LIQUIDATES the Balance
	private void liquidateBalance() {
		typeMessage = "liquidate";
		message = "Se liquidara el Saldo Actual del Cliente, ¿desea continuar?";
		String answer = jdStatementOfAccount.setJOPMessages(typeMessage, message);
		if(answer.equals("YES")) {
			SwingWorker<Boolean, Void> liquidateBalance = new SwingWorker<Boolean, Void>() {
				@Override
				protected Boolean doInBackground() throws Exception {
					boolean liquidateBalance = false;
					int id_client = returnIDUser();
					liquidateBalance = daoCredits.liquidateBalance(id_client);
					
					return liquidateBalance;
				}

				@Override
				protected void done() {
					try {
						boolean resultLiquidate = get();
						if(resultLiquidate) {
							jdStatementOfAccount.jlblCurrentBalance.setText("0.00");
							jdStatementOfAccount.jbtnPay.setEnabled(false);
							jdStatementOfAccount.jbtnLiquidate.setEnabled(false);
							typeMessage = "notOwn";
							message = "¡El Cliente ya no nos debe!";
							jdStatementOfAccount.setJOPMessages(typeMessage, message);
						}
						typeMessage = "";
						message = "";
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			};
			
			//Executes the WORKER
			liquidateBalance.execute();
		}
	}
	
	//Method that GETS the ID
	public int returnIDUser() {
		int id_client = Integer.parseInt(jdStatementOfAccount.jlblNo.getText());
		return id_client;
	}
	
	//Method that GETS the Current Balance
	public double returnCurrentBalance() {
		double currentBalance = Double.parseDouble(jdStatementOfAccount.jlblCurrentBalance.getText().replace(",", ""));
		return currentBalance;
	}
	
	//Update GUI according to the new Balance
	public void setNewBalance(String typeMessage, String message, String newBalance, boolean change) {
		jdStatementOfAccount.jlblCurrentBalance.setText(newBalance);
		jdStatementOfAccount.setJOPMessages(typeMessage, message);
	}
	
	//Method that Adds the ROWS to JTable Dates
	private void addRowsJTDates() {
		for(int ar = 0; ar < alSales.size(); ar++) {
			JBSales jbSale = alSales.get(ar);
			jdStatementOfAccount.dtmDates.addRow(new Object[] {
				jbSale.getIDSell(),
				jbSale.getDate()
			});
		}
		
		if(jdStatementOfAccount.dtmDates.getRowCount() > 0)
			jdStatementOfAccount.jtDates.setRowSelectionInterval(0, 0);
	}
	
	//Method that Removes the ROWS from JTable Dates
	private void removeRowsJTDates() {
		for(int rdr = jdStatementOfAccount.dtmDates.getRowCount() - 1; rdr >= 0; rdr--) {
			jdStatementOfAccount.dtmDates.removeRow(rdr);
		}
	}
	
	//Method that Removes the ROWS from JTable Details
	private void removeRowsJTDetails() {
		for(int rdtlsr = jdStatementOfAccount.dtmProducts.getRowCount() - 1; rdtlsr >= 0; rdtlsr--) {
			jdStatementOfAccount.dtmProducts.removeRow(rdtlsr);
		}
		
		jdStatementOfAccount.jlblTotal.setText("0.00");
	}
}
