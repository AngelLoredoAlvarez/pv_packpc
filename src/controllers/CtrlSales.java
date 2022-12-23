package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

import model.sales.JBSales;
import model.sales.JBDetailsSales;
import model.sales.DAODetailsSales;
import model.sales.DAOSales;
import views.JDConsultClients;
import views.JDSales;

public class CtrlSales {
	//Attributes
	private JDSales jdSales;
	private boolean root;
	private ArrayList<String> cashierPrivileges;
	private ArrayList<JBSales> alSales;
	private int selectedRow = 0;
	private String typeMessage = "", message = "";
	private DAOSales daoSales;
	private DAODetailsSales daoDetailsSales;
	private Date iDate, eDate;
	private DecimalFormat dfDecimals;
	private double totalSale = 0.0;
	private boolean enableInvoice = false, enableCancel = false;
	private ArrayList<JBDetailsSales> alDetailsSale;
	private CtrlConsultClients ctrlConsultClients;
	
	//Method that SETS the VIEW
	public void setJDSales(JDSales jdSales) {
		this.jdSales = jdSales;
	}
	
	//Method that SETS the DAOSales
	public void setDAOSales(DAOSales daoSales) {
		this.daoSales = daoSales;
	}
	//Method that SETS the DAODetailsSales
	public void setDAODetailsSales(DAODetailsSales daoDetailsSales) {
		this.daoDetailsSales = daoDetailsSales;
	}
	
	//Method that SETS the Communication JDSales - JDConsultClients
	public void setCtrlConsultClients(CtrlConsultClients ctrlConsultClients) {
		this.ctrlConsultClients = ctrlConsultClients;
	}
	
	//Method that SETS the DATE where was the Last Sale
	public void setData(ArrayList<JBSales> alSales, ArrayList<Integer> dateNumbers, boolean root, ArrayList<String> cashierPrivileges) {
		this.alSales = alSales;
		this.root = root;
		this.cashierPrivileges = cashierPrivileges;
		
		jdSales.jdpInitDate.getModel().setYear(dateNumbers.get(0));
		jdSales.jdpInitDate.getModel().setMonth(dateNumbers.get(1));
		jdSales.jdpInitDate.getModel().setDay(dateNumbers.get(2));
		jdSales.jdpInitDate.getModel().setSelected(true);
		
		jdSales.jdpEndDate.getModel().setYear(dateNumbers.get(0));
		jdSales.jdpEndDate.getModel().setMonth(dateNumbers.get(1));
		jdSales.jdpEndDate.getModel().setDay(dateNumbers.get(2));
		jdSales.jdpEndDate.getModel().setSelected(true);
		
		iDate = (Date) jdSales.jdpInitDate.getModel().getValue();
		eDate = (Date) jdSales.jdpEndDate.getModel().getValue();
		
		addRowsJTSales();
		getDetailsSale();
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//This has to be at the END
		jdSales.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JDatePickers
		jdSales.jdpInitDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				iDate = (Date) jdSales.jdpInitDate.getModel().getValue();
				getSalesRange();
			}
		});
		jdSales.jdpEndDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				eDate = (Date) jdSales.jdpEndDate.getModel().getValue();
				getSalesRange();
			}
		});
		
		//JTextField
		((AbstractDocument)jdSales.jtxtfSearch.getDocument()).setDocumentFilter(new DocumentFilter() {
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
		jdSales.jbtnInvoiceSale.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JDConsultClients jdConsultClients = new JDConsultClients(jdSales, "JDSales");
						ctrlConsultClients.setJDConsultClients(jdConsultClients);
						ctrlConsultClients.setCashierPriviliges(cashierPrivileges, root);
						ctrlConsultClients.prepareView();
					}
				});
			}
		});
		jdSales.jbtnCancelSale.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				
			}
		});
		jdSales.jbtnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdSales.dispose();
			}
		});
		
		//JTable
		jdSales.jtSales.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				if(!lse.getValueIsAdjusting()) {
					selectedRow = jdSales.jtSales.getSelectedRow();
					getDetailsSale();
				}
			}
		});
	}
	
	//Method that GETS all the Sales
	private void getSalesRange() {
		SwingWorker<ArrayList<JBSales>, Void> getSales = new SwingWorker<ArrayList<JBSales>, Void>() {
			@Override
			protected ArrayList<JBSales> doInBackground() throws Exception {
				SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
				String initDate = sdfDate.format(iDate);
				String endDate = sdfDate.format(eDate);
				String selectedInitDate = initDate;
				String selectedEndDate = endDate;
				
				int minSale = daoSales.retriveMinID(initDate);
				int maxSale = daoSales.retriveMaxID(endDate);
				int counter = 0;
				
				if(minSale == 0) {
					if(!iDate.after(eDate)) {
						while(true) {
							Calendar cal = Calendar.getInstance();
							cal.setTime(iDate);
							cal.add(Calendar.DAY_OF_MONTH, counter);
							initDate = sdfDate.format(cal.getTime());
							minSale = daoSales.retriveMinID(initDate);
							if(minSale > 0) {
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
				
				if(maxSale == 0) {
					counter = 0;
					if(!eDate.before(iDate)) {
						while(true) {
							Calendar cal = Calendar.getInstance();
							cal.setTime(eDate);
							cal.add(Calendar.DAY_OF_MONTH, counter);
							endDate = sdfDate.format(cal.getTime());
							maxSale = daoSales.retriveMaxID(endDate);
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
				
				alSales = daoSales.getSalesRange(minSale, maxSale);
				
				return null;
			}

			@Override
			protected void done() {
				if(typeMessage.equals("") && message.equals("")) {
					//Remove ROWS
					removeRowsJTSales();
					//Add the ROWS
					addRowsJTSales();
				} else {
					jdSales.setJOPMessages(typeMessage, message);
				}
				
				typeMessage = "";
				message = "";
				jdSales.jtxtfSearch.requestFocus();
			}
		};
		
		//Execute the WORKER
		getSales.execute();
	}
	private void removeRowsJTSales() {
		for(int rr = jdSales.dtmSales.getRowCount() - 1; rr >= 0; rr--) {
			jdSales.dtmSales.removeRow(rr);
		}
	}
	private void addRowsJTSales() {
		for(int ad = 0; ad < alSales.size(); ad++) {
			JBSales jbSale = alSales.get(ad);
			jdSales.dtmSales.addRow(new Object[] {
				jbSale.getIDSell(),
				jbSale.getDate(),
				jbSale.getTime(),
				jbSale.getClient(),
				jbSale.getStatus()
			});
		}
		
		if(jdSales.jtSales.getRowCount() > 0)
			jdSales.jtSales.setRowSelectionInterval(0, 0);
	}
	
	//Method that GETS the Details from the Sale
	private void getDetailsSale() {
		SwingWorker<Void, Void> getDetailsSale = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				int id_sale = Integer.parseInt(jdSales.dtmSales.getValueAt(selectedRow, 0).toString());
				alDetailsSale = daoDetailsSales.getDetails(id_sale);
				
				dfDecimals = new DecimalFormat("#,###,##0.00");
				totalSale = daoSales.getTotalSale(id_sale);
				
				//Enable Invoice and Cancel Sale
				SimpleDateFormat sdfDateToConvert = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy hh:mm:ss aa");
				Date saleDate = sdfDateToConvert.parse(jdSales.dtmSales.getValueAt(selectedRow, 1).toString() + " " + jdSales.dtmSales.getValueAt(selectedRow, 2).toString());
				Date actualDate = new Date();
				
				long diff = actualDate.getTime() - saleDate.getTime();
				long diffHours = diff / (60 * 60 * 1000);
				
				if(diffHours < 72 && jdSales.dtmSales.getValueAt(selectedRow, 4).toString().equals("Pagada"))
					enableInvoice = true;
				else
					enableInvoice = false;
				
				if(diffHours < 24)
					enableCancel = true;
				else
					enableCancel = false;

				return null;
			}

			@Override
			protected void done() {
				//Enable-Disable Invoice - Cancel Sale
				if(enableInvoice)
					jdSales.jbtnInvoiceSale.setEnabled(true);
				else
					jdSales.jbtnInvoiceSale.setEnabled(false);
				
				if(enableCancel)
					jdSales.jbtnCancelSale.setEnabled(true);
				else
					jdSales.jbtnCancelSale.setEnabled(false);

				//Remove Previous ROWS
				removeRowsJTDetailsSale();
				//Add New ROWS
				addRowsJTDetailsSale();
				//Total Sale
				jdSales.jlblTotalSale.setText("$" + dfDecimals.format(totalSale));
				//Focus to the Search Field
				jdSales.jtxtfSearch.requestFocus();
			}
		};
		
		//Executes the WORKER
		getDetailsSale.execute();
	}
	private void removeRowsJTDetailsSale() {
		for(int rr = jdSales.dtmDetailsSale.getRowCount() - 1; rr >= 0; rr--) {
			jdSales.dtmDetailsSale.removeRow(rr);
		}
	}
	private void addRowsJTDetailsSale() {
		DecimalFormat dfDecimals = new DecimalFormat("#,###,##0.00");
		for(int ar = 0; ar < alDetailsSale.size(); ar++) {
			JBDetailsSales jbDetailsSale = alDetailsSale.get(ar);
			jdSales.dtmDetailsSale.addRow(new Object[] {
				jbDetailsSale.getQuantity(),
				jbDetailsSale.getDescription(),
				"$" + dfDecimals.format(jbDetailsSale.getImportPrice())
			});
		}
	}
	
	//Method that FILTER the JTable
	private void filterJTable() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				String userInput = jdSales.jtxtfSearch.getText();
				RowFilter<TableModel, Object> rf = null;
				try {
				    rf = RowFilter.regexFilter("(?i)" + userInput, 3);
				} catch (java.util.regex.PatternSyntaxException e) {
				    return;
				}
				jdSales.trsSorter.setRowFilter(rf);
			}
		});
	}
}
