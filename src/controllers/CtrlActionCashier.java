package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import model.cashiers.DAOCashiers;
import model.cashiers.DAOCashiersLogin;
import model.cashiers.DAOCashiersPrivileges;
import model.cashiers.JBCashiers;
import model.cashiers.JBCashiersLogin;
import model.cashiers.JBCashiersPrivileges;
import model.cashiers.ValidateData;
import views.JDActionCashier;

public class CtrlActionCashier {
	//Attributes
	private JDActionCashier jdActionCashier;
	private DAOCashiers daoCashiers;
	private DAOCashiersLogin daoCashiersLogin;
	private DAOCashiersPrivileges daoCashiersPrivileges;
	private CtrlConsultCashiers ctrlConsultCashiers;
	private JBCashiersLogin jbCashierLogin;
	private JBCashiers jbCashier;
	private boolean callCheckCashier = false, callActionCashier = false;
	private String regexNN = "^$|^[\\p{L} ]+$", regexNL = "^$|^[[0-9] ]+$", typeMessage = "", message = ""; 
	private int id_login = 0, id_cashier = 0;
	private ArrayList<String> recivedPrivileges;
	
	//Method that SETS the VIEW
	public void setJDActionCashier(JDActionCashier jdActionCashier) {
		this.jdActionCashier = jdActionCashier;
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
		this.daoCashiersPrivileges = daoCashiersPriviliges;
	}
	
	//Method that SETS the Communication JDActionCashier - JDConsultCashiers
	public void setCtrlConsultCashiers(CtrlConsultCashiers ctrlConsultCashiers) {
		this.ctrlConsultCashiers = ctrlConsultCashiers;
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//This has to be at the END
		jdActionCashier.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JTextFields
		((AbstractDocument)jdActionCashier.jtxtfNames.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexNN, string);
				if(matches) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexNN, text);
			    if(matches) {
			    	super.replace(fb, offset, length, text, attrs);
			    }
			}
		});
		((AbstractDocument)jdActionCashier.jtxtfFirstName.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexNN, string);
				if(matches) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexNN, text);
			    if(matches) {
			    	super.replace(fb, offset, length, text, attrs);
			    }
			}
		});
		((AbstractDocument)jdActionCashier.jtxtfLastName.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexNN, string);
				if(matches) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexNN, text);
			    if(matches) {
			    	super.replace(fb, offset, length, text, attrs);
			    }
			}
		});
		((AbstractDocument)jdActionCashier.jtxtfLandLine.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexNL, string);
				if(matches) {
					if(fb.getDocument().getLength() + string.length() <= 14) {
						if(fb.getDocument().getLength() + string.length() == 3)
							string += " ";
						else if(fb.getDocument().getLength() + string.length() == 7)
							string += " ";
						else if(fb.getDocument().getLength() + string.length() == 10)
							string += " ";
						
						super.insertString(fb, offset, string, attr);
					}
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexNL, text);
			    if(matches) {
			    	if(fb.getDocument().getLength() + text.length() <= 14) {
			    		if(fb.getDocument().getLength() + text.length() == 3)
							text += " ";
						else if(fb.getDocument().getLength() + text.length() == 6)
							text += " ";
						else if(fb.getDocument().getLength() + text.length() == 8)
							text += " ";
						else if(fb.getDocument().getLength() + text.length() == 11)
							text += " ";

			    		fb.replace(offset, length, text, attrs);
			    	}
			    }
			}
		});
		((AbstractDocument)jdActionCashier.jtxtfCelPhone.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexNL, string);
				if(matches) {
					if(fb.getDocument().getLength() + string.length() <= 13) {
						if(fb.getDocument().getLength() + string.length() == 3)
							string += " ";
						else if(fb.getDocument().getLength() + string.length() == 7)
							string += " ";
						else if(fb.getDocument().getLength() + string.length() == 10)
							string += " ";

						super.insertString(fb, offset, string, attr);
					}
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexNL, text);
			    if(matches) {
			    	if(fb.getDocument().getLength() + text.length() <= 13) {
			    		if(fb.getDocument().getLength() + text.length() == 3)
							text += " ";
						else if(fb.getDocument().getLength() + text.length() == 7)
							text += " ";
						else if(fb.getDocument().getLength() + text.length() == 10)
							text += " ";

			    		fb.replace(offset, length, text, attrs);
			    	}
			    }
			}
		});
		((AbstractDocument)jdActionCashier.jtxtfUserName.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexNN, string);
				if(matches) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexNN, text);
			    if(matches) {
			    	super.replace(fb, offset, length, text, attrs);
			    }
			}
		});
		
		//JButtons
		jdActionCashier.jbtnChangePass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				resetPassword();
			}
		});
		jdActionCashier.jbtnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				checkData();
			}
		});
		jdActionCashier.jbtnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdActionCashier.dispose();
			}
		});
	}
	
	//Method that CHECKS if there's any DATA missing
	private void checkData() {
		SwingWorker<String, Void> checkData = new SwingWorker<String, Void>() {
			@Override
			protected String doInBackground() throws Exception {
				String field = "";
				getCashierData();
				ValidateData validate = new ValidateData();
				field = validate.checkData(jbCashier);
				
				return field;
			}

			@Override
			protected void done() {
				try {
					String resultField = get();
					if(!resultField.equals("")) {
						typeMessage = "missing";
						if(resultField.equals("names")) {
							message = "Ingresa el Nombre del Cajero";
							jdActionCashier.jtxtfNames.requestFocus();
						} else if(resultField.equals("firstname")) {
							message = "Ingresa el Apellido Paterno del Cajero";
							jdActionCashier.jtxtfFirstName.requestFocus();
						} else if(resultField.equals("lastname")) {
							message = "Ingresa el Apellido Materno del Cajero";
							jdActionCashier.jtxtfLastName.requestFocus();
						} else if(resultField.equals("contact")) {
							message = "Es OBLIGATORIO ingresar alguna número de Contacto, Celular o Fijo";
							jdActionCashier.jtxtfLandLine.requestFocus();
						} else if(resultField.equals("username")) {
							message = "Ingresa el Nombre de Usuario con el que ingresara al Sistema";
							jdActionCashier.jtxtfUserName.requestFocus();
						}
						jdActionCashier.setJOPMessages(typeMessage, message);
					} else {
						callCheckCashier = true;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Add a PropertyChangeListener to KNOW when the Thread is finished
		checkData.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(checkData.isDone() && callCheckCashier) {
						checkCashier();
						callCheckCashier = false;
					}
				}
			}
		});
		
		//Executes the WORKER
		checkData.execute();
	}
	
	//Method that CHECKS if the Cashier has been already registered
	private void checkCashier() {
		SwingWorker<Boolean, Void> checkCashier = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				boolean checkCashier = false;
				
				if(jdActionCashier.action.equals("Agregar Cajero")) {
					JBCashiers jbCashier = new JBCashiers();
					jbCashier.setNames(jdActionCashier.jtxtfNames.getText());
					jbCashier.setFirstName(jdActionCashier.jtxtfFirstName.getText());
					jbCashier.setLastName(jdActionCashier.jtxtfLastName.getText());
					checkCashier = daoCashiers.verifyCashier(jbCashier);
				}
				
				return checkCashier;
			}

			@Override
			protected void done() {
				try {
					boolean resultChecking = get();
					if(resultChecking) {
						typeMessage = "alreadyRegistered";
						message = "El Cajero ya esta Registrado";
						jdActionCashier.setJOPMessages(typeMessage, message);
						clearFields();
					} else {
						callActionCashier = true;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Add a PropertyChangeListener to KNOW when the Thread is finished
		checkCashier.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(checkCashier.isDone() && callActionCashier) {
						actionCashier();
						callActionCashier = false;
					}
				}
			}
		});
		
		//Executes the WORKER
		checkCashier.execute();
	}
	
	//Method that do the Corresponding ACTION to the Cashier
	private void actionCashier() {
		SwingWorker<Boolean, Void> actionCashier = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				boolean actionOK = false;
				boolean actionLogin = false;
				boolean actionCashier = false;
				boolean actionPrivilege = false;
				
				getCashierData();
				
				if(jdActionCashier.action.equals("Agregar Cajero")) {
					//Save Login
					actionLogin = daoCashiersLogin.saveLogin(jbCashierLogin);
					
					//Save Cashier
					if(actionLogin) {
						id_login = daoCashiersLogin.getLoginIDByUN(jdActionCashier.jtxtfUserName.getText());
						jbCashier.setIDLogin(id_login);
						actionCashier = daoCashiers.saveCashier(jbCashier);
						jbCashier.setIDCashier(daoCashiers.getCashierID(jbCashier));
						
						//Save Privileges
						if(actionCashier) {
							for(int gsp = 0; gsp < jdActionCashier.alPrivileges.size(); gsp++) {
								if(jdActionCashier.alPrivileges.get(gsp).isSelected()) {
									JBCashiersPrivileges jbCashierPrivilege = new JBCashiersPrivileges();
									jbCashierPrivilege.setPrivilige(jdActionCashier.alPrivileges.get(gsp).getName());
									jbCashierPrivilege.setIDCashierLogin(id_login);
									actionPrivilege = daoCashiersPrivileges.savePrivilige(jbCashierPrivilege);
								}
							}
						}
					}
				} else if(jdActionCashier.action.equals("Modificar Cajero")) {
					jbCashierLogin.setIDCashierLogin(id_login);
					actionLogin = daoCashiersLogin.updateUserName(jbCashierLogin);
					
					if(actionLogin) {
						//Update the DATA
						jbCashier.setIDCashier(id_cashier);
						actionCashier = daoCashiers.updateData(jbCashier);
						
						if(actionCashier) {
							//Update the Privileges
							for(int rpui = 0; rpui < jdActionCashier.alPrivileges.size(); rpui++) {
								boolean exist = false;
								String privilegeUI = jdActionCashier.alPrivileges.get(rpui).getName();
								
								for(int rcp = 0; rcp < recivedPrivileges.size(); rcp++) {
									String cashierPrivilege = recivedPrivileges.get(rcp);
									if(privilegeUI.equals(cashierPrivilege)) {
										exist = true;
									}
								}
								
								JBCashiersPrivileges jbCashierPrivilege = new JBCashiersPrivileges();
								jbCashierPrivilege.setPrivilige(privilegeUI);
								jbCashierPrivilege.setIDCashierLogin(id_login);
								
								if(exist) {
									if(!jdActionCashier.alPrivileges.get(rpui).isSelected()) {
										actionPrivilege = daoCashiersPrivileges.deletePrivilige(jbCashierPrivilege);
									}
								} else {
									if(jdActionCashier.alPrivileges.get(rpui).isSelected())
										actionPrivilege = daoCashiersPrivileges.savePrivilige(jbCashierPrivilege);
								}
							}
						}
					}
				}
				
				if(actionLogin && actionCashier && actionPrivilege)
					actionOK = true;
				else if(actionLogin && actionCashier && id_cashier == 1)
					actionOK = true;
				else if(actionLogin && actionCashier)
					actionOK = true;
				
				return actionOK;
			}

			@Override
			protected void done() {
				try {
					boolean resultSaving = get();
					if(resultSaving) {
						if(jdActionCashier.action.equals("Agregar Cajero")) {
							ctrlConsultCashiers.addCashierToList(jbCashier);
							typeMessage = "saveCashier";
							message = "El Nuevo Cajero fue Registrado";
							jdActionCashier.setJOPMessages(typeMessage, message);
							jdActionCashier.dispose();
						} else if(jdActionCashier.action.equals("Modificar Cajero")) {
							ctrlConsultCashiers.updateRow(jbCashier);
							typeMessage = "updateCashier";
							message = "La información del Cajero fue Modificada correctamente";
							jdActionCashier.setJOPMessages(typeMessage, message);
							jdActionCashier.dispose();
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the Worker
		actionCashier.execute();
	}
	
	private void getCashierData() {
		//Get Login DATA
		jbCashierLogin = new JBCashiersLogin();
		jbCashierLogin.setUserName(jdActionCashier.jtxtfUserName.getText());
		jbCashierLogin.setPassword("");
		if(jdActionCashier.action.equals("Agregar Cajero"))
			jbCashierLogin.setStatus("newCashier");
		
		//Get Cashier DATA
		jbCashier = new JBCashiers();
		jbCashier.setNames(jdActionCashier.jtxtfNames.getText());
		jbCashier.setFirstName(jdActionCashier.jtxtfFirstName.getText());
		jbCashier.setLastName(jdActionCashier.jtxtfLastName.getText());
		jbCashier.setLandLine(jdActionCashier.jtxtfLandLine.getText());
		jbCashier.setCelPhone(jdActionCashier.jtxtfCelPhone.getText());
		jbCashier.setEmail(jdActionCashier.jtxtfEmail.getText());
		jbCashier.setUsername(jdActionCashier.jtxtfUserName.getText());
	}
		
	//Method that SETS the Data from the Cashier to Modify
	public void setCashierData(JBCashiers jbCashier, ArrayList<String> cashierPrivileges) {
		id_cashier = jbCashier.getIDCashier();
		
		if(id_cashier == 1) {
			jdActionCashier.jtpPrivileges.setEnabled(false);
			for(int rpui = 0; rpui < jdActionCashier.alPrivileges.size(); rpui++) {
				jdActionCashier.alPrivileges.get(rpui).setEnabled(false);
			}
		}
			
		jdActionCashier.jtxtfNames.setText(jbCashier.getNames());
		jdActionCashier.jtxtfFirstName.setText(jbCashier.getFirstName());
		jdActionCashier.jtxtfLastName.setText(jbCashier.getLastName());
		jdActionCashier.jtxtfLandLine.setText(jbCashier.getLandLine());
		jdActionCashier.jtxtfCelPhone.setText(jbCashier.getCelPhone());
		jdActionCashier.jtxtfEmail.setText(jbCashier.getEmail());
		id_login = jbCashier.getIDCashierLogin();
		jdActionCashier.jtxtfUserName.setText(jbCashier.getUsername());
		
		jdActionCashier.jbtnChangePass.setVisible(true);
		
		recivedPrivileges = cashierPrivileges;
		
		//Put the Privileges
		for(int pc = 0; pc < cashierPrivileges.size(); pc++) {
			String privilegeCashier = cashierPrivileges.get(pc);
			for(int pv = 0; pv < jdActionCashier.alPrivileges.size(); pv++) {
				String privilegeView = jdActionCashier.alPrivileges.get(pv).getName();
				if(privilegeCashier.equals(privilegeView)) {
					jdActionCashier.alPrivileges.get(pv).setSelected(true);
				}
			}
		}
	}
	
	//Method that SETS the Password on BLANK
	private void resetPassword() {
		SwingWorker<Boolean, Void> resetPassword = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				boolean updatePassword = daoCashiersLogin.passwordUpdateSet(id_login, "", "changePW");
				return updatePassword;
			}
			@Override
			protected void done() {
				try {
					boolean resultUpdating = get();
					if(resultUpdating) {
						typeMessage = "resetPass";
						message = "La contraseña ha sido reseteada";
						jdActionCashier.setJOPMessages(typeMessage, message);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the WORKER
		resetPassword.execute();
	}
	
	private void clearFields() {
		jdActionCashier.jtxtfNames.setText("");
		jdActionCashier.jtxtfNames.requestFocus();
		jdActionCashier.jtxtfFirstName.setText("");
		jdActionCashier.jtxtfLastName.setText("");
		jdActionCashier.jtxtfLandLine.setText("");
		jdActionCashier.jtxtfCelPhone.setText("");
		jdActionCashier.jtxtfEmail.setText("");
		jdActionCashier.jtxtfUserName.setText("");
		for(int rc = 0; rc < jdActionCashier.alPrivileges.size(); rc++) {
			if(jdActionCashier.alPrivileges.get(rc).isSelected()) {
				jdActionCashier.alPrivileges.get(rc).setSelected(false);
			}
		}
	}
}
