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
import model.cashiers.DAOCashiersLogin;
import model.cashiers.DAOCashiersPrivileges;
import model.cashiers.JBCashiersLogin;
import model.cashiers.ValidateLogin;
import views.JDAskSUDO;
import views.JDOpenTill;

public class CtrlAskSUDO {
	//Attributes
	private JDAskSUDO jdAskSudo;
	private int id_cashier = 0, id_cashier_privileged = 0;
	private String typeMessage = "", message = "", regex = "^$|^[\\p{L} ]+$", name = ""; 
	private boolean callCheckOpenTillPrivilege = false;
	//Communication with other Views
	private CtrlOpenTill ctrlOpenTill;
	//DAOS
	private DAOCashiersLogin daoCashiersLogin;
	private DAOCashiersPrivileges daoCashierPrivileges;
	
	//Method that SETS the Communication JDAskSUDO - JDOpenTill
	public void setCtrlOpenTill(CtrlOpenTill ctrlOpenTill) {
		this.ctrlOpenTill = ctrlOpenTill;
	}
	
	//Method that SETS the DAOCashiersLogin
	public void setDAOCashiersLogin(DAOCashiersLogin daoCashiersLogin) {
		this.daoCashiersLogin = daoCashiersLogin;
	}
	//Method that SETS the DAOCashierPrivileges
	public void setDAOCashierPrivileges(DAOCashiersPrivileges daoCashierPrivileges) {
		this.daoCashierPrivileges = daoCashierPrivileges;
	}
	
	//Method that SETS the ID from the Cashier
	public void setIDCashier(int id_cashier) {
		this.id_cashier = id_cashier;
	}
	
	//Method that SETS the VIEW
	public void setJDAskSUDO(JDAskSUDO jdAskSudo) {
		this.jdAskSudo = jdAskSudo;
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//This has to be at the END
		jdAskSudo.setVisible(true);
	}
	
	//Method that SETS the Listener
	private void setListeners() {
		//JTextField
		((AbstractDocument)jdAskSudo.jtxtfUserName.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regex, string);
				if(matches) {
					super.insertString(fb, offset, string, attr);
				}				
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				boolean matches = Pattern.matches(regex, text);
				if(matches) {
					super.replace(fb, offset, length, text, attrs);
				}
			}
		});
		
		//JButtons
		jdAskSudo.jbtnAccept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				checkCashierExist();
			}
		});
		jdAskSudo.jbtnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdAskSudo.dispose();
			}
		});
	}
	
	//Method that CHECKS if the Cashier EXIST
	private void checkCashierExist() {
		SwingWorker<Boolean, Void> checkCashierExist = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				boolean exist = false;
				ValidateLogin validate = new ValidateLogin();
				JBCashiersLogin jbCashierLogin = new JBCashiersLogin();
				jbCashierLogin.setUserName(jdAskSudo.jtxtfUserName.getText());
				jbCashierLogin.setPassword(new String(jdAskSudo.jpfPassword.getPassword()));
				exist = validate.checkUserExistence(jbCashierLogin, daoCashiersLogin);
				
				return exist;
			}

			@Override
			protected void done() {
				try {
					boolean result = get();
					if(result) {
						callCheckOpenTillPrivilege = true;
					} else {
						typeMessage = "notExist";
						message = "Nombre de Usuario y/o Contraseña Incorrectos";
						jdAskSudo.setJOPMessages(typeMessage, message);
						clearFields();
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
		
		//Adds a PropertyChangeListener to KNOW when the Thread is DONE
		checkCashierExist.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(checkCashierExist.isDone() && callCheckOpenTillPrivilege) {
						checkOpenTillPrivilege();
						callCheckOpenTillPrivilege = false;
					}
				}
			}
		});
		
		//Executes WORKER
		checkCashierExist.execute();
	}
	
	//Method that CHECK if the Cashier has the OpenTill Privilege
	private void checkOpenTillPrivilege() {
		SwingWorker<String, Void> checkOpenTillPrivilege = new SwingWorker<String, Void>() {
			@Override
			protected String doInBackground() throws Exception {
				JBCashiersLogin jbCashierLogin = new JBCashiersLogin();
				jbCashierLogin.setUserName(jdAskSudo.jtxtfUserName.getText());
				jbCashierLogin.setPassword(new String(jdAskSudo.jpfPassword.getPassword()));
				id_cashier_privileged = daoCashiersLogin.getCashierID(jbCashierLogin);
				int id_login = daoCashiersLogin.getIDLoginByIDCashier(id_cashier_privileged);
				name = daoCashiersLogin.getCashierName(id_cashier_privileged);
				
				String whoIsLogin = "";
				ArrayList<String> alPrivileges = new ArrayList<String>();
				alPrivileges = daoCashierPrivileges.getPrivileges(id_login);
				for(int rp = 0; rp < alPrivileges.size(); rp++) {
					if(alPrivileges.get(rp).equals("FullACCESS"))
						whoIsLogin = "root";
					else if(alPrivileges.get(rp).equals("OpenTill"))
						whoIsLogin = "supervisor";
					else
						whoIsLogin = "noPrivilege";
				}
				
				return whoIsLogin;
			}

			@Override
			protected void done() {
				try {
					String result = get();
					if(result.equals("root") || result.equals("supervisor")) {
						JDOpenTill jdOpenTill = new JDOpenTill(jdAskSudo);
						ctrlOpenTill.setJDOpenTill(jdOpenTill);
						ctrlOpenTill.setDataCashierPrivileged(name, id_cashier_privileged, id_cashier);
						ctrlOpenTill.prepareView();
						jdAskSudo.dispose();
					} else {
						typeMessage = "";
						message = "";
						jdAskSudo.setJOPMessages(typeMessage, message);
						clearFields();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the WORKER
		checkOpenTillPrivilege.execute();
	}
	
	private void clearFields() {
		jdAskSudo.jtxtfUserName.setText("");
		jdAskSudo.jtxtfUserName.requestFocus();
		jdAskSudo.jpfPassword.setText("");
	}
}
