package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import model.inventories.DAODepartments;
import model.inventories.JBDepartments;
import views.JDActionDepartment;

public class CtrlActionDepartment {
	//Attributes
	private JDActionDepartment jdActionDepartment;
	private DAODepartments daoDepartments;
	private int id_department = 0;
	private String typeMessage = "", message = "";
	private JBDepartments jbDepartment;
	private CtrlConsultDepartments ctrlConsultDepartments;
	boolean callDoAction = false;
	
	//Method that SETS the VIEW
	public void setJDActionDepartment(JDActionDepartment jdActionDepartment) {
		this.jdActionDepartment = jdActionDepartment;
	}
	
	//Method that SETS the DAODepartments
	public void setDAODepartments(DAODepartments daoDepartments) {
		this.daoDepartments = daoDepartments;
	}
	
	//Method that SETS the Communication JDActionDepartment - JDConsultDepartments
	public void setCtrlConsultDepartments(CtrlConsultDepartments ctrlConsultDepartments) {
		this.ctrlConsultDepartments = ctrlConsultDepartments;
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//This has to be at the END
		jdActionDepartment.setVisible(true);
	}
	
	//Method that SETS the Listeners
	public void setListeners() {
		//JButtons
		jdActionDepartment.jbtnAccept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				checkDepartment();
			}
		});
		jdActionDepartment.jbtnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdActionDepartment.dispose();
			}
		});
	}
	
	//Method that CHECKS if the Department ALREADY exist
	private void checkDepartment() {
		SwingWorker<Boolean, Void> checkDepartment = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				boolean checkDepartment = false;
				checkDepartment = daoDepartments.checkDepartment(jdActionDepartment.jtxtfName.getText());
				
				return checkDepartment;
			}

			@Override
			protected void done() {
				try {
					boolean resultCheck = get();
					if(!resultCheck) {
						callDoAction = true;
					} else {
						typeMessage = "alreadyExist";
						message = "El Departamento ya esta Registrado";
						jdActionDepartment.setJOPMessages(typeMessage, message);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Add a PropertyChangeListener to KNOW when the Thread is Finished
		checkDepartment.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(checkDepartment.isDone() && callDoAction) {
						doAction();
						callDoAction = false;
					}
				}
			}
		});
		
		//Executes the WORKER
		checkDepartment.execute();
	}
	
	//Method that DO the Corresponding Action
	private void doAction() {
		SwingWorker<Boolean, Void> doAction = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				boolean doAction = false;
				
				jbDepartment = new JBDepartments();
				jbDepartment.setIDDepartment(id_department);
				jbDepartment.setDepartment(jdActionDepartment.jtxtfName.getText());
				
				if(jdActionDepartment.action.equals("Agregar Departamento")) {
					doAction = daoDepartments.saveDepartment(jbDepartment);
					if(doAction) {
						jbDepartment = daoDepartments.getDepartmentByName(jbDepartment.getDepartment());
					}
				} else if(jdActionDepartment.action.equals("Modificar Departamento")) {
					doAction = daoDepartments.updateDepartment(jbDepartment);
				}
				
				return doAction;
			}

			@Override
			protected void done() {
				try {
					boolean resutlAction = get();
					if(resutlAction) {
						typeMessage = "SMCorrectly";
						if(jdActionDepartment.action.equals("Agregar Departamento")) {
							ctrlConsultDepartments.addRow(jbDepartment);
							message = "El Departamentp ha sido agregado correctamente";
						} else if(jdActionDepartment.action.equals("Modificar Departamento")) {
							ctrlConsultDepartments.updateRow(jbDepartment);
							message = "El Departamento ha sido modificado correctamente";
						}
						jdActionDepartment.setJOPMessages(typeMessage, message);
						jdActionDepartment.dispose();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the WORKER
		doAction.execute();
	}
	
	//Method that SETS the Data to be Modified
	public void setDataToModify(JBDepartments jbDepartment) {
		id_department = jbDepartment.getIDDepartment();
		jdActionDepartment.jtxtfName.setText(jbDepartment.getDepartment());
	}
}
