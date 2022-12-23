package controllers;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import model.providers.DAOProviders;
import model.providers.JBProviders;
import views.JDActionProvider;
import views.JDConsultProviders;

public class CtrlConsultProviders {
	//Attributes
	private JDConsultProviders jdConsultProviders;
	private DAOProviders daoProviders;
	private CtrlActionProvider ctrlActionProvider;
	private CtrlDoBuy ctrlBuys;
	private CtrlActionProductService ctrlActionProductService;
	private int selectedRow = 0;
	private String AMProvider = "", DelProvider = "", typeMessage = "", message = "", regex = "^$|^[\\p{L}[0-9] .-]+$";
	private boolean root = false, searchByName = true;
	
	//Method that SETS the VIEW
	public void setJDConsultProviders(JDConsultProviders jdConsultProviders) {
		this.jdConsultProviders = jdConsultProviders;
	}
	//Method that SETS the Relationship with the DAO
	public void setDAOProviders(DAOProviders daoProviders) {
		this.daoProviders = daoProviders;
	}
	//Method that SETS the Communication JDConsultProviders - JDActionProvider
	public void setCtrlActionProvider(CtrlActionProvider ctrlActionProvider) {
		this.ctrlActionProvider = ctrlActionProvider;
	}
	//Method that SETS the Communication JDConsultProviders - JDBuys
	public void setCtrlBuys(CtrlDoBuy ctrlBuys) {
		this.ctrlBuys = ctrlBuys;
	}
	//Method that SETS the Communication JDConsultProviders - JDActionProductService
	public void setCtrlActionProductService(CtrlActionProductService ctrlActionProductService) {
		this.ctrlActionProductService = ctrlActionProductService;
	}
	
	//Method that SETS if its ROOT
	public void setROOT(boolean root) {
		this.root = root;
	}
	
	//Method that SETS the USER Privileges
	public void setCashierPrivileges(ArrayList<String> cashierPrivileges) {
		if(root) {
			this.root = true;
			jdConsultProviders.jbtnAdd.setVisible(true);
			jdConsultProviders.jbtnModify.setVisible(true);
			jdConsultProviders.jbtnDelete.setVisible(true);
		} else {
			for(int sp = 0; sp < cashierPrivileges.size(); sp++) {
				if(cashierPrivileges.get(sp).equals("AMProvider")) {
					jdConsultProviders.jbtnAdd.setVisible(true);
					jdConsultProviders.jbtnModify.setVisible(true);
					AMProvider = "yes";
				}
				if(cashierPrivileges.get(sp).equals("DelProvider")) {
					jdConsultProviders.jbtnDelete.setVisible(true);
					DelProvider = "yes";
				}
			}
		}
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//This one has to be at the END
		jdConsultProviders.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JDialog
		jdConsultProviders.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent we) {
				getProviders();
			}
		});
		
		//JTextFields
		((AbstractDocument)jdConsultProviders.jtxtfSearch.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			    filterJTable();
				if(jdConsultProviders.jtProviders.getRowCount() == 0)
					disableJButtons();
				else
					enableJButtons();
			}
			
			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regex, string);
				if(matches && searchByName) {
					super.insertString(fb, offset, string, attr);
					filterJTable();
					if(jdConsultProviders.jtProviders.getRowCount() == 0)
						disableJButtons();
					else
						enableJButtons();
				} else if(matches) {
					if(fb.getDocument().getLength() + string.length() <= 13) {
						super.insertString(fb, offset, string.toUpperCase(), attr);
						filterJTable();
						if(jdConsultProviders.jtProviders.getRowCount() == 0)
							disableJButtons();
						else
							enableJButtons();
					}
				}
			}
			
			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regex, text);
			    if(matches && searchByName) {
			    	fb.replace(offset, length, text, attrs);
			    	filterJTable();
					if(jdConsultProviders.jtProviders.getRowCount() == 0)
						disableJButtons();
					else
						enableJButtons();
			    } else if(matches) {
			    	if(fb.getDocument().getLength() + text.length() <= 13) {
			    		fb.replace(offset, length, text.toUpperCase(), attrs);
			    		filterJTable();
						if(jdConsultProviders.jtProviders.getRowCount() == 0)
							disableJButtons();
						else
							enableJButtons();
					}
			    }
			}
		});
		
		//JButtons
		jdConsultProviders.jbtnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						String title = "Agregar Proveedor", icon = "/img/jframes/add.png";
						JDActionProvider jdActionProvider = new JDActionProvider(jdConsultProviders, title, icon);
						ctrlActionProvider.setJDActionProvider(jdActionProvider);
						ctrlActionProvider.prepareView();
					}
				});
			}
		});
		jdConsultProviders.jbtnModify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						getProvider();
					}
				});
			}
		});
		jdConsultProviders.jbtnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				deleteProvider();
			}
		});
		jdConsultProviders.jbtnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdConsultProviders.dispose();
			}
		});
		
		//JTable
		jdConsultProviders.jtProviders.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				if(!lse.getValueIsAdjusting()) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							selectedRow = jdConsultProviders.jtProviders.getSelectedRow();
							if(selectedRow >= 0) {
								if(selectedRow == 0) {
									jdConsultProviders.jbtnModify.setEnabled(false);
									jdConsultProviders.jbtnDelete.setEnabled(false);
								} else {
									if(root) {
										jdConsultProviders.jbtnModify.setEnabled(true);
										jdConsultProviders.jbtnDelete.setEnabled(true);
									} else {
										if(AMProvider.equals("yes"))
											jdConsultProviders.jbtnModify.setEnabled(true);
										if(DelProvider.equals("yes"))
											jdConsultProviders.jbtnDelete.setEnabled(true);
									}
								}
								
								jdConsultProviders.jtxtfSearch.requestFocus();
							}
						}
					});
				}
			}
		});
		jdConsultProviders.jtProviders.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
			   if(me.getClickCount() == 2) {
				   SwingUtilities.invokeLater(new Runnable() {
					   @Override
						public void run() {
						   JBProviders jbSelectedProvider = new JBProviders();
						   jbSelectedProvider.setIDProvider(Integer.parseInt(jdConsultProviders.dtmProviders.getValueAt(selectedRow, 0).toString()));
						   jbSelectedProvider.setName(jdConsultProviders.dtmProviders.getValueAt(selectedRow, 1).toString());
						   
						   if(jdConsultProviders.father.equals("JDBuys"))
							   ctrlBuys.setProviderData(jbSelectedProvider);
						   else if(jdConsultProviders.father.equals("JDActionProductService"))
							   ctrlActionProductService.addProvider(jbSelectedProvider);
							   
						   jdConsultProviders.dispose();
					   }
				  });
			   }
			}
		});
	}
	
	//Method that GETS the Providers List
	public void getProviders() {
		SwingWorker<ArrayList<JBProviders>, Void> getProviders = new SwingWorker<ArrayList<JBProviders>, Void>() {
			@Override
			protected ArrayList<JBProviders> doInBackground() throws Exception {
				ArrayList<JBProviders> providersList = new ArrayList<JBProviders>();
				providersList = daoProviders.getProviders();
				return providersList;
			}
			
			@Override
			protected void done() {
				ArrayList<JBProviders> retrivedList = new ArrayList<JBProviders>();
				try {
					retrivedList = get();
					if(retrivedList.size() != 0) {
						//Remove Previous List
						for(int a = jdConsultProviders.dtmProviders.getRowCount() - 1; a >= 0; a--) {
							jdConsultProviders.dtmProviders.removeRow(a);
						}
						//Add the new List
						for(int b = 0; b < retrivedList.size(); b++) {
							JBProviders jbProvider = new JBProviders();
							jbProvider = retrivedList.get(b);
							if(jbProvider.getName().equals("Proveedores Varios")) {
								jdConsultProviders.dtmProviders.addRow(new Object[] {
									jbProvider.getIDProvider(),
									jbProvider.getName(),
								});
							} else {
								jdConsultProviders.dtmProviders.addRow(new Object[] {
									jbProvider.getIDProvider(),
									jbProvider.getName(),
									jbProvider.getStreet() + " #" + jbProvider.getExtNumber() + ", Col. " + jbProvider.getColony() + ", " + jbProvider.getCity(),
									jbProvider.getLandLine1(),
									jbProvider.getLandLine2(),
									jbProvider.getEmail()
								});
							}
						}
						jdConsultProviders.jtProviders.setRowSelectionInterval(0, 0);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the Worker
		getProviders.execute();
	}
	
	//Method that GETS an Individual Provider
	private void getProvider() {
		SwingWorker<JBProviders, Void> getClient = new SwingWorker<JBProviders, Void>() {
			@Override
			protected JBProviders doInBackground() throws Exception {
				JBProviders jbProvider = new JBProviders();
				String id_provider = jdConsultProviders.jtProviders.getValueAt(selectedRow, 0).toString();
				jbProvider = daoProviders.getProviderByID(id_provider);
				return jbProvider;
			}

			@Override
			protected void done() {
				JBProviders providerRetrived = new JBProviders();
				try {
					providerRetrived = get();
					String title = "Modificar Proveedor", icon = "/img/jframes/edit.png";
					JDActionProvider jdActionProvider = new JDActionProvider(jdConsultProviders, title, icon);
					ctrlActionProvider.setJDActionProvider(jdActionProvider);
					ctrlActionProvider.setProviderData(providerRetrived);
					ctrlActionProvider.prepareView();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		//Executes the Worker
		getClient.execute();
	}
	
	//Delete the Selected Provider
	private void deleteProvider() {
		typeMessage = "delete";
		String name = jdConsultProviders.jtProviders.getValueAt(selectedRow, 1).toString();
		message = "¿Desea Eliminar al Proveedor " + name + "?";
		String answer = jdConsultProviders.setJOPMessages(typeMessage, message);
		if(answer.equals("YES")) {
			SwingWorker<Boolean, Void> deleteProvider = new SwingWorker<Boolean, Void>() {
				@Override
				protected Boolean doInBackground() throws Exception {
					int selectedRow = jdConsultProviders.jtProviders.getSelectedRow();
					String id_provider = jdConsultProviders.jtProviders.getValueAt(selectedRow, 0).toString();
					boolean delete = daoProviders.deleteProvider(id_provider);
					return delete;
				}

				@Override
				protected void done() {
					try {
						boolean resultDelete = get();
						if(resultDelete) {
							jdConsultProviders.dtmProviders.removeRow(selectedRow);
							jdConsultProviders.jtxtfSearch.setText("");
							jdConsultProviders.jtxtfSearch.requestFocus();
							if(jdConsultProviders.jtProviders.getRowCount() != 0)
								jdConsultProviders.jtProviders.addRowSelectionInterval(0, 0);
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
			deleteProvider.execute();
		}
	}
	
	//Method that ADDS the new ROW to the JTable
	public void addRow(JBProviders jbProvider) {
		jdConsultProviders.dtmProviders.addRow(new Object[] {
			jbProvider.getIDProvider(),
			jbProvider.getName(),
			jbProvider.getStreet() + " #" + jbProvider.getExtNumber() + ", Col. " + jbProvider.getColony() + ", " + jbProvider.getCity(),
			jbProvider.getLandLine1(),
			jbProvider.getLandLine2(),
			jbProvider.getEmail()
		});
		
		jdConsultProviders.jtxtfSearch.requestFocus();
		int cantRows = jdConsultProviders.jtProviders.getRowCount() - 1; 
		Rectangle rect = jdConsultProviders.jtProviders.getCellRect(cantRows, 0, true);
		jdConsultProviders.jtProviders.scrollRectToVisible(rect);
		jdConsultProviders.jtProviders.clearSelection();
		jdConsultProviders.jtProviders.setRowSelectionInterval(cantRows, cantRows);
	}
	
	//Method that UPDATES the Selected ROW
	public void updateRow(JBProviders jbProvider) {
		jdConsultProviders.dtmProviders.setValueAt(jbProvider.getName(), selectedRow, 1);
		jdConsultProviders.dtmProviders.setValueAt(jbProvider.getIRS(), selectedRow, 2);
		String address = jbProvider.getStreet() + " #" + jbProvider.getExtNumber() + ", Col. " + jbProvider.getColony() + ", " + jbProvider.getCity();
		jdConsultProviders.dtmProviders.setValueAt(address, selectedRow, 3);
		jdConsultProviders.dtmProviders.setValueAt(jbProvider.getLandLine1(), selectedRow, 4);
		jdConsultProviders.dtmProviders.setValueAt(jbProvider.getLandLine2(), selectedRow, 5);
		jdConsultProviders.dtmProviders.setValueAt(jbProvider.getEmail(), selectedRow, 6);
	}
	
	//Method that Filter the JTable
	private void filterJTable() {
		String userInput = jdConsultProviders.jtxtfSearch.getText();
		RowFilter<TableModel, Object> rf = null;
	    try {
	        rf = RowFilter.regexFilter("(?i)" + userInput, 1);
	    } catch (java.util.regex.PatternSyntaxException e) {
	        return;
	    }
	    jdConsultProviders.trsSorter.setRowFilter(rf);
	}
	
	//Method that Disables the Actions
	private void enableJButtons() {
		jdConsultProviders.jbtnModify.setEnabled(true);
		jdConsultProviders.jbtnDelete.setEnabled(true);
	}
		
	//Method that Disables the Actions
	private void disableJButtons() {
		jdConsultProviders.jbtnModify.setEnabled(false);
		jdConsultProviders.jbtnDelete.setEnabled(false);
	}
}
