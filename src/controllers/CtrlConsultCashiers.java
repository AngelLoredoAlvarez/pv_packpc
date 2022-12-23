package controllers;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

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
import model.cashiers.DAOCashiers;
import model.cashiers.DAOCashiersLogin;
import model.cashiers.DAOCashiersPrivileges;
import model.cashiers.JBCashiers;
import views.JDActionCashier;
import views.JDConsultCashiers;

public class CtrlConsultCashiers {
	//Attributes
	private JDConsultCashiers jdConsultCashiers;
	private DAOCashiers daoCashiers;
	private DAOCashiersLogin daoCashiersLogin;
	private DAOCashiersPrivileges daoCashiersPriviliges;
	private CtrlActionCashier ctrlActionCashier;
	private int selectedRow = 0;
	private String sjdIcon = "", title = "", typeMessage = "", message = "", regex = "^$|^[\\p{L} ]+$";
	private JBCashiers cashierData;
	private ArrayList<String> alPrivilegesCashierToModify;
	private boolean root = false;
	
	//Method that SETS the VIEW
	public void setJDConsultCashiers(JDConsultCashiers jdConsultCashiers) {
		this.jdConsultCashiers = jdConsultCashiers;
	}
	//Method that SETS the DAOCashiers
	public void setDAOCashiers(DAOCashiers daoCashiers) {
		this.daoCashiers = daoCashiers;
	}
	//Method that SETS the DAOCashiersLogin
	public void setDAOCashiersLogin(DAOCashiersLogin daoCashiersLogin) {
		this.daoCashiersLogin = daoCashiersLogin;
	}
	//Method that SETS the DAOCashiersPriviliges
	public void setDAOCashiersPriviliges(DAOCashiersPrivileges daoCashiersPriviliges) {
		this.daoCashiersPriviliges = daoCashiersPriviliges;
	}
	//Method that SETS the Communication JDConsultCashiers - JDActionCashier
	public void setCtrlActionCashier(CtrlActionCashier ctrlActionCashier) {
		this.ctrlActionCashier = ctrlActionCashier;
	}
	
	//Method that Sets the USER Privileges
	public void setCashierPrivileges(ArrayList<String> cashierPriviliges, boolean root) {
		this.root = root;
		if(root) {
			jdConsultCashiers.jbtnAdd.setVisible(true);
			jdConsultCashiers.jbtnModify.setVisible(true);
			jdConsultCashiers.jbtnDelete.setVisible(true);
		}
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//This has to be at the END
		jdConsultCashiers.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JDialog
		jdConsultCashiers.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent we) {
				getCashiers();
			}
		});
		
		//JTextField
		((AbstractDocument)jdConsultCashiers.jtxtfSearch.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			    filterJTable();
			    if(jdConsultCashiers.jtCashiers.getRowCount() == 0)
					disableJButtons();
				else
					enableJButtons();
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regex, string);
				if(matches) {
					super.insertString(fb, offset, string, attr);
					filterJTable();
					if(jdConsultCashiers.jtCashiers.getRowCount() == 0)
						disableJButtons();
					else
						enableJButtons();
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regex, text);
			    if(matches) {
			    	super.replace(fb, offset, length, text, attrs);
			    	filterJTable();
			    	if(jdConsultCashiers.jtCashiers.getRowCount() == 0)
						disableJButtons();
					else
						enableJButtons();
			    }
			}
		});
		
		//JButtons
		jdConsultCashiers.jbtnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						sjdIcon = "/img/jframes/add_cashier.png";
						title = "Agregar Cajero";
						JDActionCashier jdActionCashier = new JDActionCashier(jdConsultCashiers, sjdIcon, title);
						ctrlActionCashier.setJDActionCashier(jdActionCashier);
						ctrlActionCashier.prepareView();
					}
				});
			}
		});
		jdConsultCashiers.jbtnModify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				getCashierData();
			}
		});
		jdConsultCashiers.jbtnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				deleteCashier();
			}
		});
		jdConsultCashiers.jbtnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdConsultCashiers.dispose();
			}
		});
		
		//JTable
		jdConsultCashiers.jtCashiers.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				if(!lse.getValueIsAdjusting()) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							selectedRow = jdConsultCashiers.jtCashiers.getSelectedRow();
							if(selectedRow >= 0) {
								if(selectedRow == 0) {
									if(root) {
										jdConsultCashiers.jbtnModify.setEnabled(true);
										jdConsultCashiers.jbtnDelete.setEnabled(false);
									} else {
										jdConsultCashiers.jbtnModify.setEnabled(false);
										jdConsultCashiers.jbtnDelete.setEnabled(false);
									}
								} else {
									if(root) {
										jdConsultCashiers.jbtnModify.setEnabled(true);
										jdConsultCashiers.jbtnDelete.setEnabled(true);
									}
								}
								jdConsultCashiers.jtxtfSearch.requestFocus();
							}
						}
					});
				}
			}
		});
	}
	
	//Method that RETRIVES all the Registered Cashiers
	public void getCashiers() {
		SwingWorker<ArrayList<JBCashiers>, Void> getCashiers = new SwingWorker<ArrayList<JBCashiers>, Void>() {
			@Override
			protected ArrayList<JBCashiers> doInBackground() throws Exception {
				ArrayList<JBCashiers> cashiersRetrived = new ArrayList<JBCashiers>();
				cashiersRetrived = daoCashiers.getCashiers();
				return cashiersRetrived;
			}

			@Override
			protected void done() {
				ArrayList<JBCashiers> retrivedList = new ArrayList<JBCashiers>();
				try {
					retrivedList = get();
					//Remove previous List
					for(int r = jdConsultCashiers.dtmCashiers.getRowCount() - 1; r >= 0; r--) {
						jdConsultCashiers.dtmCashiers.removeRow(r);
					}
					//Add the new List
					for(int a = 0; a < retrivedList.size(); a++) {
						JBCashiers jbCashier = new JBCashiers();
						jbCashier = retrivedList.get(a);
						jdConsultCashiers.dtmCashiers.addRow(new Object[] {
							jbCashier.getIDCashier(),
							jbCashier.getNames() + " " + jbCashier.getFirstName() + " " + jbCashier.getLastName(),
							jbCashier.getLandLine(), 
							jbCashier.getCelPhone(),
							jbCashier.getEmail()
						});
					}
					jdConsultCashiers.jtCashiers.setRowSelectionInterval(0, 0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the Worker
		getCashiers.execute();
	}
	
	//Method that GETS the Cashier DATA to be Modified
	private void getCashierData() {
		SwingWorker<Void, Void> getCashierData = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				//Get Cashier Data
				cashierData = new JBCashiers();
				int selectedRow = jdConsultCashiers.jtCashiers.getSelectedRow();
				int id_cashier = Integer.parseInt(jdConsultCashiers.dtmCashiers.getValueAt(selectedRow, 0).toString());
				cashierData = daoCashiers.getCashier(id_cashier);
				
				//Get privileges from the Selected user
				alPrivilegesCashierToModify = new ArrayList<String>();
				int id_login = cashierData.getIDCashierLogin();
				alPrivilegesCashierToModify = daoCashiersPriviliges.getPrivileges(id_login);
				
				return null;
			}

			@Override
			protected void done() {
				sjdIcon = "/img/jframes/edit_cashier.png";
				title = "Modificar Cajero";
				JDActionCashier jdActionCashier = new JDActionCashier(jdConsultCashiers, sjdIcon, title);
				ctrlActionCashier.setJDActionCashier(jdActionCashier);
				ctrlActionCashier.setCashierData(cashierData, alPrivilegesCashierToModify);
				ctrlActionCashier.prepareView();
			}
		};
		//Executes the WORKER
		getCashierData.execute();
	}
	
	//Method that DELETES the Selected Cashier
	private void deleteCashier() {
		typeMessage = "delete";
		String name = jdConsultCashiers.jtCashiers.getValueAt(selectedRow, 1).toString();
		message = "¿Desea Eliminar al Cajero: " + name + "?";
		String answer = jdConsultCashiers.setJOPMessages(typeMessage, message);
		if(answer.equals("YES")) {
			SwingWorker<Boolean, Void> deleteCashier = new SwingWorker<Boolean, Void>() {
				@Override
				protected Boolean doInBackground() throws Exception {
					boolean deleteCashier = false;
					int id_cashier = Integer.parseInt(jdConsultCashiers.dtmCashiers.getValueAt(selectedRow, 0).toString());
					int id_login = daoCashiersLogin.getIDLoginByIDCashier(id_cashier);
					deleteCashier = daoCashiersLogin.deleteLogin(id_login);
					
					return deleteCashier;
				}

				@Override
				protected void done() {
					try {
						boolean resultAction = get();
						if(resultAction) {
							jdConsultCashiers.dtmCashiers.removeRow(selectedRow);
							jdConsultCashiers.jtxtfSearch.setText("");
							jdConsultCashiers.jtxtfSearch.requestFocus();
							if(jdConsultCashiers.jtCashiers.getRowCount() != 0)
								jdConsultCashiers.jtCashiers.setRowSelectionInterval(0, 0);
							else
								disableJButtons();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			};
			
			//Executes the Worker
			deleteCashier.execute();
		}
	}
	
	//Method that ADD's the new Client to the List
	public void addCashierToList(JBCashiers jbCashier) {
		jdConsultCashiers.dtmCashiers.addRow(new Object[] {
			jbCashier.getIDCashier(),
			jbCashier.getNames() + " " + jbCashier.getFirstName() + " " + jbCashier.getLastName(),
			jbCashier.getLandLine(),
			jbCashier.getCelPhone(),
			jbCashier.getEmail()
		});
		
		jdConsultCashiers.jtxtfSearch.requestFocus();
		int cantRows = jdConsultCashiers.jtCashiers.getRowCount() - 1; 
		Rectangle rect = jdConsultCashiers.jtCashiers.getCellRect(cantRows, 0, true);
		jdConsultCashiers.jtCashiers.scrollRectToVisible(rect);
		jdConsultCashiers.jtCashiers.clearSelection();
		jdConsultCashiers.jtCashiers.setRowSelectionInterval(cantRows, cantRows);
	}
		
	//Method that UPDATES the selected Row
	public void updateRow(JBCashiers jbCashier) {
		jdConsultCashiers.dtmCashiers.setValueAt(jbCashier.getNames() + " " + jbCashier.getFirstName() + " " + jbCashier.getLastName(), selectedRow, 1);
		jdConsultCashiers.dtmCashiers.setValueAt(jbCashier.getLandLine(), selectedRow, 2);
		jdConsultCashiers.dtmCashiers.setValueAt(jbCashier.getCelPhone(), selectedRow, 3);
		jdConsultCashiers.dtmCashiers.setValueAt(jbCashier.getEmail(), selectedRow, 4);
	}
	
	//Method that Filter the JTable
	private void filterJTable() {
		String userInput = jdConsultCashiers.jtxtfSearch.getText();
		RowFilter<TableModel, Object> rf = null;
	    try {
	       rf = RowFilter.regexFilter("(?i)" + userInput, 1);
	    } catch (java.util.regex.PatternSyntaxException e) {
	       return;
	    }
	    jdConsultCashiers.trsSorter.setRowFilter(rf);
	}
	
	//Method that Enables the JButtons
	private void enableJButtons() {
		jdConsultCashiers.jbtnModify.setEnabled(true);
		jdConsultCashiers.jbtnDelete.setEnabled(true);
	}
	
	//Method that Disables the JButtons
	private void disableJButtons() {
		jdConsultCashiers.jbtnModify.setEnabled(false);
		jdConsultCashiers.jbtnDelete.setEnabled(false);
	}
}
