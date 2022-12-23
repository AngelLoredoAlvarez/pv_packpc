package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

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

import model.buys.DAOBuys;
import model.buys.DAODetailsBuys;
import model.buys.JBBuys;
import model.buys.JBDetailsBuys;
import views.JDBuys;
import views.JDDetailsSB;
import views.JDDoBuy;

public class CtrlBuys {
	//Attributes
	private JDBuys jdBuys;
	private Date iDate, eDate;
	private ArrayList<JBBuys> alBuys;
	private boolean root;
	private int id_cashier, selectedRow = 0, column = 3;
	private ArrayList<String> cashierPrivileges;
	private String typeMessage = "", message = "";
	private DAOBuys daoBuys;
	private CtrlDoBuy ctrlDoBuy;
	private CtrlDetailsSB ctrlDetailsSB;
	private DAODetailsBuys daoDetailsBuys;
	
	//Method that SETS the VIEW
	public void setJDBuys(JDBuys jdBuys) {
		this.jdBuys = jdBuys;
	}
	
	//Method that SETS the DAOBuys
	public void setDAOBuys(DAOBuys daoBuys) {
		this.daoBuys = daoBuys;
	}
	//Method that SETS the Communication JDBuys - JDDoBuy
	public void setCtrlDoBuys(CtrlDoBuy ctrlDoBuy) {
		this.ctrlDoBuy = ctrlDoBuy;
	}
	
	//Method that SETS the Communication JDBuys - JDDetailsSB
	public void setCtrlDetailsSB(CtrlDetailsSB ctrlDetailsSB) {
		this.ctrlDetailsSB = ctrlDetailsSB;
	}
	//Method that SETS the DAODetailsBuys
	public void setDAODetailsBuys(DAODetailsBuys daoDetailsBuys) {
		this.daoDetailsBuys = daoDetailsBuys;
	}
	
	//Method that SETS the DATE where was the Last Buy
	public void setData(ArrayList<JBBuys> alBuys, ArrayList<Integer> dateNumbers, boolean root, int id_cashier, ArrayList<String> cashierPrivileges) {
		this.alBuys = alBuys;
		this.root = root;
		this.id_cashier = id_cashier;
		this.cashierPrivileges = cashierPrivileges;
		
		jdBuys.jdpInitDate.getModel().setYear(dateNumbers.get(0));
		jdBuys.jdpInitDate.getModel().setMonth(dateNumbers.get(1));
		jdBuys.jdpInitDate.getModel().setDay(dateNumbers.get(2));
		jdBuys.jdpInitDate.getModel().setSelected(true);
		
		jdBuys.jdpEndDate.getModel().setYear(dateNumbers.get(0));
		jdBuys.jdpEndDate.getModel().setMonth(dateNumbers.get(1));
		jdBuys.jdpEndDate.getModel().setDay(dateNumbers.get(2));
		jdBuys.jdpEndDate.getModel().setSelected(true);
		
		iDate = (Date) jdBuys.jdpInitDate.getModel().getValue();
		eDate = (Date) jdBuys.jdpEndDate.getModel().getValue();
		
		//Add ROWS
		addRows();
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//This has to be at the END
		jdBuys.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JDatePickers
		jdBuys.jdpInitDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				iDate = (Date) jdBuys.jdpInitDate.getModel().getValue();
				getBuysRange();
			}
		});
		jdBuys.jdpEndDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				eDate = (Date) jdBuys.jdpEndDate.getModel().getValue();
				getBuysRange();
			}
		});
		
		//JRadioButtons
		jdBuys.jrbProvider.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						column = 3;
						jdBuys.jtxtfSearch.setText("");
						jdBuys.jtxtfSearch.requestFocus();
					}
				});
			}
		});
		jdBuys.jrbCashier.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						column = 4;
						jdBuys.jtxtfSearch.setText("");
						jdBuys.jtxtfSearch.requestFocus();
					}
				});
			}
		});
		
		//JTextField
		((AbstractDocument)jdBuys.jtxtfSearch.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			    filterJTable();
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				super.insertString(fb, offset, string, attr);
				filterJTable();
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				super.replace(fb, offset, length, text, attrs);
				filterJTable();
			}
		});
		
		//JButtons
		jdBuys.jbtnNewBuy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JDDoBuy jdDoBuy = new JDDoBuy(jdBuys);
						ctrlDoBuy.setJDDoBuy(jdDoBuy);
						ctrlDoBuy.setDataForCashier(root, id_cashier, cashierPrivileges);
						ctrlDoBuy.prepareView();
					}
				});
			}
		});
		jdBuys.jbtnDetailsBuy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				getDetailsBuy();
			}
		});
		jdBuys.jbtnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdBuys.dispose();
			}
		});
		
		//JTable
		jdBuys.jtBuys.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				if(!lse.getValueIsAdjusting()) {
					selectedRow = jdBuys.jtBuys.getSelectedRow();
					if(selectedRow >= 0) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								jdBuys.jbtnDetailsBuy.setEnabled(true);
								jdBuys.jtxtfSearch.requestFocus();
							}
						});
					}
				}
			}
		});
	}
	
	//Method that GETS all the Buys
	private void getBuysRange() {
		SwingWorker<Void, Void> getBuys = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
				String initDate = sdfDate.format(iDate);
				String endDate = sdfDate.format(eDate);
				String selectedInitDate = initDate;
				String selectedEndDate = endDate;
				
				int minBuy = daoBuys.retriveMinID(initDate);
				int maxBuy = daoBuys.retriveMaxID(endDate);
				int counter = 0;
				
				if(minBuy == 0) {
					if(!iDate.after(eDate)) {
						while(true) {
							Calendar cal = Calendar.getInstance();
							cal.setTime(iDate);
							cal.add(Calendar.DAY_OF_MONTH, counter);
							initDate = sdfDate.format(cal.getTime());
							minBuy = daoBuys.retriveMinID(initDate);
							if(minBuy > 0) {
								break;
							} else {
								if(initDate.equals(selectedEndDate)) {
									break;
								} else {
									counter++;
								}
							}
						}
					} else {
						typeMessage = "initLarger";
						message = "La Fecha de Inicio no puede ser mayor a la Fecha de Fin";
					}
				}
				
				if(maxBuy == 0) {
					counter = 0;
					if(!eDate.before(iDate)) {
						while(true) {
							Calendar cal = Calendar.getInstance();
							cal.setTime(eDate);
							cal.add(Calendar.DAY_OF_MONTH, counter);
							endDate = sdfDate.format(cal.getTime());
							maxBuy = daoBuys.retriveMaxID(endDate);
							if(maxBuy > 0) {
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
				
				alBuys = daoBuys.retriveBuysRange(minBuy, maxBuy);
				
				return null;
			}

			@Override
			protected void done() {
				if(typeMessage.equals("") && message.equals("")) {
					//Remove ROWS
					removeRows();
					//Add ROWS
					addRows();
				} else {
					jdBuys.setJOPMessages(typeMessage, message);
				}
				typeMessage = "";
				message = "";
				jdBuys.jtxtfSearch.requestFocus();
			}
		};
		
		//Execute the WORKER
		getBuys.execute();
	}
	
	//Method that REMOVES the ROWS
	private void removeRows() {
		for(int rr = jdBuys.dtmBuys.getRowCount() - 1; rr >= 0; rr--) {
			jdBuys.dtmBuys.removeRow(rr);
		}
	}
	
	//Method that ADDS the ROWS
	private void addRows() {
		DecimalFormat dfDecimals = new DecimalFormat("#,###,##0.00");
		for(int ad = 0; ad < alBuys.size(); ad++) {
			JBBuys jbBuy = alBuys.get(ad);
			jdBuys.dtmBuys.addRow(new Object[] {
				jbBuy.getIDBuy(),
				jbBuy.getDate(),
				"$" + dfDecimals.format(jbBuy.getTotal()),
				jbBuy.getProvider(),
				jbBuy.getCashier()
			});
		}
		
		if(jdBuys.jtBuys.getRowCount() > 0) {
			jdBuys.jtBuys.setRowSelectionInterval(0, 0);
			jdBuys.jbtnDetailsBuy.setEnabled(true);
		}
	}
	
	//Method that ADDS to the List the NEW Buys
	public void addBuy(JBBuys jbBuy) {
		DecimalFormat dfDecimals = new DecimalFormat("#,###,##0.00");
		jdBuys.dtmBuys.addRow(new Object[] {
			jbBuy.getIDBuy(),
			jbBuy.getDate(),
			"$" + dfDecimals.format(jbBuy.getTotal()),
			jbBuy.getProvider(),
			jbBuy.getCashier()
		});
		
		int rowsCount = jdBuys.jtBuys.getRowCount() - 1;
		jdBuys.jtBuys.setRowSelectionInterval(rowsCount, rowsCount);
	}
	
	//Method that RETRIVES and SHOWS the Details
	private void getDetailsBuy() {
		SwingWorker<ArrayList<JBDetailsBuys>, Void> getDetailsBuy = new SwingWorker<ArrayList<JBDetailsBuys>, Void>() {
			@Override
			protected ArrayList<JBDetailsBuys> doInBackground() throws Exception {
				int id_buy = Integer.parseInt(jdBuys.dtmBuys.getValueAt(selectedRow, 0).toString());
				ArrayList<JBDetailsBuys> alDetailsBuy = daoDetailsBuys.getDetails(id_buy);

				return alDetailsBuy;
			}

			@Override
			protected void done() {
				try {
					ArrayList<JBDetailsBuys> retrivedDetails = get();
					JDDetailsSB jdDetailsSB = new JDDetailsSB(jdBuys, "Detalles de Compra");
					ctrlDetailsSB.setJDDetailsSB(jdDetailsSB);
					ctrlDetailsSB.setData(retrivedDetails, "Buys");
					ctrlDetailsSB.prepareView();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the WORKER
		getDetailsBuy.execute();
	}
	
	//Method that Filters the JTable
	private void filterJTable() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				String userInput = jdBuys.jtxtfSearch.getText();
				RowFilter<TableModel, Object> rf = null;
				try {
				    rf = RowFilter.regexFilter("(?i)" + userInput, column);
				} catch (java.util.regex.PatternSyntaxException e) {
				    return;
				}
				jdBuys.trsSorter.setRowFilter(rf);
			}
		});
	}
}
