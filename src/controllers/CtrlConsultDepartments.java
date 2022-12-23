package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.inventories.DAODepartments;
import model.inventories.JBDepartments;
import views.JDActionDepartment;
import views.JDConsultDepartments;

public class CtrlConsultDepartments {
	//Attributes
	private JDConsultDepartments jdConsultDepartments;
	private DAODepartments daoDepartments;
	private CtrlActionDepartment ctrlActionDepartment;
	private int selectedRow = 0;
	private CtrlActionProductService ctrlActionProductService;
	private String typeMessage = "", message = "";
	
	//Method that SETS the VIEW
	public void setJDConsultDepartments(JDConsultDepartments jdConsultDepartments) {
		this.jdConsultDepartments = jdConsultDepartments;
	}
	
	//Method that SETS the DAODepartments
	public void setDAODepartments(DAODepartments daoDepartments) {
		this.daoDepartments = daoDepartments;
	}
	
	//Method that SETS the Communication JDConsultDepartments - JDActionDepartment
	public void setCtrlActionDepartment(CtrlActionDepartment ctrlActionDepartment) {
		this.ctrlActionDepartment = ctrlActionDepartment;
	}
	//Method that SETS the Communication JDConsultDepartments - JDActionProductService
	public void setCtrlActionProductService(CtrlActionProductService ctrlActionProductService) {
		this.ctrlActionProductService = ctrlActionProductService;
	}
	
	//Method that SETS the ROOT
	public void setROOT(boolean root) {
		if(root) {
			jdConsultDepartments.jbtnAdd.setVisible(true);
			jdConsultDepartments.jbtnModify.setVisible(true);
			jdConsultDepartments.jbtnDelete.setVisible(true);
		}
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//This has to be at the END
		jdConsultDepartments.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JDialog
		jdConsultDepartments.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent we) {
				getDepartments();
			}
		});
		
		//JButtons
		jdConsultDepartments.jbtnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						String action = "Agregar Departamento";
						String icon = "/img/jframes/add.png";
						JDActionDepartment jdActionDepartment = new JDActionDepartment(jdConsultDepartments, action, icon);
						ctrlActionDepartment.setJDActionDepartment(jdActionDepartment);
						ctrlActionDepartment.prepareView();
					}
				});
			}
		});
		jdConsultDepartments.jbtnModify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						String action = "Modificar Departamento";
						String icon = "/img/jframes/edit.png";
						JDActionDepartment jdActionDepartment = new JDActionDepartment(jdConsultDepartments, action, icon);
						ctrlActionDepartment.setJDActionDepartment(jdActionDepartment);
						ctrlActionDepartment.setDataToModify(getDataFromSelectedDepartment());
						ctrlActionDepartment.prepareView();
					}
				});
			}
		});
		jdConsultDepartments.jbtnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				deleteDepartment();
			}
		});
		jdConsultDepartments.jbtnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdConsultDepartments.dispose();
			}
		});
		
		//JTable
		jdConsultDepartments.jtDepartments.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				if(!lse.getValueIsAdjusting()) {
					selectedRow = jdConsultDepartments.jtDepartments.getSelectedRow();
					if(selectedRow >= 0) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								if(selectedRow == 0)
									jdConsultDepartments.jbtnDelete.setEnabled(false);
								else
									jdConsultDepartments.jbtnDelete.setEnabled(true);
								
								jdConsultDepartments.jtxtfSearch.requestFocus();
							}
						});
					}
				}
			}
		});
		jdConsultDepartments.jtDepartments.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount() == 2) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							ctrlActionProductService.setSelectedDepartment(getDataFromSelectedDepartment());
							jdConsultDepartments.dispose();
						}
					});
				}
			}
		});
	}
	
	//Method that RETRIVES all the Departments
	private void getDepartments() {
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
					if(!retrivedDepartments.isEmpty()) {
						//Remove previous rows
						for(int rr = jdConsultDepartments.jtDepartments.getRowCount() - 1; rr >= 0; rr--) {
							jdConsultDepartments.dtmDepartments.removeRow(rr);
						}
						//Add the new rows
						for(int ar = 0; ar < retrivedDepartments.size(); ar++) {
							JBDepartments jbDepartment = retrivedDepartments.get(ar);
							jdConsultDepartments.dtmDepartments.addRow(new Object[] {
								jbDepartment.getIDDepartment(),
								jbDepartment.getDepartment()
							});
						}
						//Set the Selection Row
						jdConsultDepartments.jtDepartments.setRowSelectionInterval(0, 0);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the Worker
		getDepartments.execute();
	}
	
	//Get data from the Selected Department
	private JBDepartments getDataFromSelectedDepartment() {
		JBDepartments jbDepartment = new JBDepartments();
		jbDepartment.setIDDepartment(Integer.parseInt(jdConsultDepartments.dtmDepartments.getValueAt(selectedRow, 0).toString()));
		jbDepartment.setDepartment(jdConsultDepartments.dtmDepartments.getValueAt(selectedRow, 1).toString());
		
		return jbDepartment;
	}
	
	//Method that ADD's the new Row
	public void addRow(JBDepartments jbDepartment) {
		jdConsultDepartments.dtmDepartments.addRow(new Object[] {
			jbDepartment.getIDDepartment(),
			jbDepartment.getDepartment()
		});
		int lastRow = jdConsultDepartments.jtDepartments.getRowCount() - 1;
		jdConsultDepartments.jtDepartments.setRowSelectionInterval(lastRow, lastRow);
	}
	
	//Method that UPDATES the Row
	public void updateRow(JBDepartments jbDepartment) {
		jdConsultDepartments.dtmDepartments.setValueAt(jbDepartment.getIDDepartment(), selectedRow, 0);
		jdConsultDepartments.dtmDepartments.setValueAt(jbDepartment.getDepartment(), selectedRow, 1);
		jdConsultDepartments.jtDepartments.setRowSelectionInterval(selectedRow, selectedRow);
	}
	
	//Method that DELETES the Selected Department
	private void deleteDepartment() {
		typeMessage = "delete";
		String department = jdConsultDepartments.dtmDepartments.getValueAt(selectedRow, 1).toString();
		message = "¿Desea Eliminar el Departamento: " + department + "?";
		String answer = jdConsultDepartments.setJOPMessages(typeMessage, message);
		if(answer.equals("YES")) {
			SwingWorker<Boolean, Void> deleteDepartment = new SwingWorker<Boolean, Void>() {
				@Override
				protected Boolean doInBackground() throws Exception {
					int id_department = Integer.parseInt(jdConsultDepartments.dtmDepartments.getValueAt(selectedRow, 0).toString());
					boolean deleteDepartment = daoDepartments.deleteDepartment(id_department);
					
					return deleteDepartment;
				}

				@Override
				protected void done() {
					try {
						boolean resultDelete = get();
						if(resultDelete) {
							jdConsultDepartments.dtmDepartments.removeRow(selectedRow);
							jdConsultDepartments.jtxtfSearch.setText("");
							jdConsultDepartments.jtxtfSearch.requestFocus();
							if(jdConsultDepartments.dtmDepartments.getRowCount() != 0) {
								int lastRow = jdConsultDepartments.dtmDepartments.getRowCount() - 1;
								jdConsultDepartments.jtDepartments.setRowSelectionInterval(lastRow, lastRow);
							}
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			};
			
			//Executes the WORKER
			deleteDepartment.execute();
		}
	}
}
