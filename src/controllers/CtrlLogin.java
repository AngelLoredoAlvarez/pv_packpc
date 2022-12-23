package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import model.cashiers.DAOCashiersLogin;
import model.cashiers.DAOCashiersPrivileges;
import model.cashiers.DAOCashiersTurns;
import model.cashiers.JBCashiersLogin;
import model.cashiers.JBCashiersTurns;
import model.cashiers.ValidateLogin;
import model.sales.DAOSales;
import model.till.DAOTill;
import views.JFLogin;
import views.JFMain;

public class CtrlLogin {
	//Attributes
	private JFLogin jfLogin;
	private DAOCashiersTurns daoCashiersTurns;
	private DAOCashiersLogin daoCashiersLogin;
	private String typeMessage = "", message = "", regex = "^$|^[\\p{L} ]+$";
	private boolean callCheckUnclosedTurn = false, unclosedTurn = false, callGetDataForTurn = false, callSaveTurn = false;
	private DAOCashiersPrivileges daoCashiersPriviliges;
	private DAOTill daoTill;
	private DAOSales daoSales;
	//DATA that will be SEND to the JFSales
	private ArrayList<String> cashierPrivileges;
	private int id_cashier = 0, lastSale = 0;
	private Date startDate;
	private String cashierName = "", status = "";
	private boolean tillOpened = false, root = false;
	//Communication with JFSales
	private CtrlMain ctrlMain;
	
	//Method that SETS the VIEW
	public void setJFLogin(JFLogin jfLogin) {
		this.jfLogin = jfLogin;
	}
	//Method that SETS the DAOCashiersTurns
	public void setDAOCashiersTurns(DAOCashiersTurns daoCashiersTurns) {
		this.daoCashiersTurns = daoCashiersTurns;
	}
	//Method that SETS the DAOCashiersLogin
	public void setDAOCashiersLogin(DAOCashiersLogin daoCashiersLogin) {
		this.daoCashiersLogin = daoCashiersLogin;
	}
	//Method that SETS the DAOCashiersPriviliges
	public void setDAOCashiersPriviliges(DAOCashiersPrivileges daoCashiersPriviliges) {
		this.daoCashiersPriviliges = daoCashiersPriviliges;
	}
	//Method that SETS the DAOTill
	public void setDAOTill(DAOTill daoTill) {
		this.daoTill = daoTill;
	}
	//Method that SETS the DAOSales
	public void setDAOSales(DAOSales daoSales) {
		this.daoSales = daoSales;
	}
	//Method that SETS the Communication JFLogin - JFSales
	public void setCtrlSales(CtrlMain ctrlSales) {
		this.ctrlMain = ctrlSales;
	}
	
	//Method that PREPARES the View
	public void prepareView() {
		setListeners();
		//This has to be at the END
		jfLogin.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JFrame
		jfLogin.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent we) {
				setUserNames();
			}
		});
		
		//JTextField
		((AbstractDocument)jfLogin.jtxtfUserName.getDocument()).setDocumentFilter(new DocumentFilter() {
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
		jfLogin.jtxtfUserName.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent fe) {
				checkLoginStatus();
			}
		});
		
		//JPasswordField
		jfLogin.jpfUserPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
					loginCashier();
				}
			}
		});
		
		//JButtons
		jfLogin.jbtnLogIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				loginCashier();
			}
		});
		jfLogin.jbtnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jfLogin.dispose();
			}
		});
	}
	
	//Method that SETS the USER NAMES
	private void setUserNames() {
		SwingWorker<ArrayList<String>, Void> setUserNames = new SwingWorker<ArrayList<String>, Void>() {
			@Override
			protected ArrayList<String> doInBackground() throws Exception {
				ArrayList<String> userNameList = daoCashiersLogin.getUserNames();
				
				return userNameList;
			}

			@Override
			protected void done() {
				try {
					ArrayList<String> retrivedList = get();
					
					//Remove Previous List
					jfLogin.jcbUserName.removeAllItems();
					
					//Adds the New List
					for(int al = 0; al < retrivedList.size(); al++) {
						jfLogin.jcbUserName.addItem(retrivedList.get(al));
					}
					callCheckUnclosedTurn = true;
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Add a PropertyChangeListener to KNOW when the Thread is Finished
		setUserNames.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(setUserNames.isDone() && callCheckUnclosedTurn) {
						checkUnclosedTurn();
						callCheckUnclosedTurn = false;
					}
				}
			}
		});
		
		//Executes the WORKER
		setUserNames.execute();
	}
	
	//Method that CHECK if there's any UNCLOSED TURN
	private void checkUnclosedTurn() {
		SwingWorker<ArrayList<String>, Void> checkUnclosedTurn = new SwingWorker<ArrayList<String>, Void>() {
			@Override
			protected ArrayList<String> doInBackground() throws Exception {
				
				id_cashier = daoCashiersTurns.checkUnclosedTurn();
				
				ArrayList<String> cashierInfo = new ArrayList<String>();
				
				if(id_cashier != 0) {
					cashierInfo.add(daoCashiersLogin.getCashierName(id_cashier));
					cashierInfo.add(daoCashiersLogin.getUserName(id_cashier));
					unclosedTurn = true;
				} else {
					unclosedTurn = false;
					callSaveTurn = true;
				}
				
				return cashierInfo;
			}

			@Override
			protected void done() {
				try {
					ArrayList<String> retrivedCashierInfo = get();
					if(unclosedTurn) {
						typeMessage = "unclosedTurn";
						message = "El Cajero: '" + retrivedCashierInfo.get(0) + "' dejó su Turno Abierto, sólo él, o el"
								+ "\n"
								+ " ADMINISTRADOR, pueden Accesar nuevamente al Sistema";
						jfLogin.setJOPMessage(typeMessage, message);
						
						ArrayList<String> itemsToList = new ArrayList<String>();
						
						for(int gi = 0; gi < jfLogin.jcbUserName.getItemCount(); gi++) {
							String item = jfLogin.jcbUserName.getItemAt(gi);
							String userName = retrivedCashierInfo.get(1);
							if(item.equals("ADMIN") || item.equals(userName)) {
								itemsToList.add(item);
							}
						}
						
						jfLogin.jcbUserName.removeAllItems();
						
						for(int ai = 0; ai < itemsToList.size(); ai++) {
							String item = itemsToList.get(ai);
							jfLogin.jcbUserName.addItem(item);
						}
						
						jfLogin.jcbUserName.setSelectedItem(retrivedCashierInfo.get(1));
						jfLogin.jpfUserPass.requestFocus();
						
						typeMessage = "";
						message = "";
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the WORKER
		checkUnclosedTurn.execute();
	}
	
	//Method that CHECKS the STATUS from the Selected Login
	private void checkLoginStatus() {
		SwingWorker<String, Void> checkLoginStatus = new SwingWorker<String, Void>() {
			@Override
			protected String doInBackground() throws Exception {
				String userName = jfLogin.jcbUserName.getSelectedItem().toString();
				status = daoCashiersLogin.checkLoginStatus(userName);
				return status;
			}

			@Override
			protected void done() {
				try {
					String resultStatus = get();
					if(!resultStatus.equals("OK")) {
						if(resultStatus.equals("changeADPW")) {
							typeMessage = "changeADPW";
							message = "Se sugiere CAMBIAR la contraseña de la CUENTA ADMIN que viene "
									+ "\n"
									+ "por DEFAULT, puedes hacer esto desde el menu de MODIFICAR CAJERO";
							jfLogin.setJOPMessage(typeMessage, message);
						} else if(resultStatus.equals("newCashier")) {
							typeMessage = "newCashier";
							message = "Eres un CAJERO NUEVO, tu CONTRASEÑA, será"
									+ "\n"
									+ "lo que digites en el campo de Contraseña";
							jfLogin.setJOPMessage(typeMessage, message);
						} else if(resultStatus.equals("changePW")) {
							typeMessage = "changePW";
							message = "Haz solicitado un CAMBIO DE CONTRASEÑA, tu nueva Contraseña,"
									+ "\n"
									+ "será lo que digites en el campo de Contraseña";
							jfLogin.setJOPMessage(typeMessage, message);
						}
						typeMessage = "";
						message = "";
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the Worker
		checkLoginStatus.execute();
	}
	
	private void loginCashier() {
		SwingWorker<Boolean, Void> loginCashier = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				boolean action = false;
				
				JBCashiersLogin jbCashierLogin = new JBCashiersLogin();
				jbCashierLogin.setUserName(jfLogin.jcbUserName.getSelectedItem().toString());
				jbCashierLogin.setPassword(new String(jfLogin.jpfUserPass.getPassword()));
				
				if(status.equals("newCashier") || status.equals("changePW")) {
					int id_login = daoCashiersLogin.getLoginIDByUN(jbCashierLogin.getUserName());
					action = daoCashiersLogin.passwordUpdateSet(id_login, jbCashierLogin.getPassword(), "OK");
				} else {
					ValidateLogin validate = new ValidateLogin();
					action = validate.checkUserExistence(jbCashierLogin, daoCashiersLogin);
				}
				
				return action;
			}

			@Override
			protected void done() {
				try {
					boolean resultChecking = get();
					if(resultChecking) {
						callGetDataForTurn = true;
					} else {
						typeMessage = "incorrectUNorP";
						message = "Nombre de Usuario y/o Contraseña ¡INCORRECTOS!";
						jfLogin.setJOPMessage(typeMessage, message);
						jfLogin.jtxtfUserName.setText("");
						jfLogin.jtxtfUserName.requestFocus();
						jfLogin.jpfUserPass.setText("");
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
		
		//Add a PropertyChangeListener to KNOW when the Thread is finished
		loginCashier.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(loginCashier.isDone() && callGetDataForTurn) {
						getDataForTurn();
						callGetDataForTurn = false;
					}
				}
			}
		});
		
		//Executes the WORKER
		loginCashier.execute();
	}
	
	private void getDataForTurn() {
		SwingWorker<Void, Void> getDataForTurn = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				JBCashiersLogin jbCashierLogin = new JBCashiersLogin();
				jbCashierLogin.setUserName(jfLogin.jcbUserName.getSelectedItem().toString());
				jbCashierLogin.setPassword(new String(jfLogin.jpfUserPass.getPassword()));
				
				int id_login = daoCashiersLogin.getLoginIDByUP(jbCashierLogin);
				
				cashierPrivileges = daoCashiersPriviliges.getPrivileges(id_login);
				for(int cr = 0; cr < cashierPrivileges.size(); cr++) {
					if(cashierPrivileges.get(cr).equals("FullACCESS")) {
						root = true;
						break;
					} else {
						root = false;
					}
				}
	
				id_cashier = daoCashiersLogin.getCashierID(jbCashierLogin);
				
				cashierName = daoCashiersLogin.getCashierName(id_cashier);
				
				startDate = new Date();
				
				tillOpened = daoTill.checkOpenTill();
				
				lastSale = daoSales.getLastSale();
				
				if(tillOpened && !unclosedTurn)
					callSaveTurn = true;
				else 
					callSaveTurn = false;
				
				return null;
			}

			@Override
			protected void done() {
				jfLogin.dispose();
				JFMain jfSales = new JFMain();
				ctrlMain.setJFSales(jfSales);
				ctrlMain.setDataForTurn(cashierPrivileges, id_cashier, root, cashierName, startDate, tillOpened, lastSale);
				ctrlMain.prepareView();
				root = false;
			}
		};
		
		//Adds a PropertyChangeListener to KNOW when the Thread is finished
		getDataForTurn.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(getDataForTurn.isDone() && callSaveTurn) {
						saveTurn();
						callSaveTurn = false;
					}
				}
			}
		});
		
		//Executes the WORKER
		getDataForTurn.execute();
	}
	
	//Method that SAVES the Turn, if there's any UnclosedTurn
	private void saveTurn() {
		SwingWorker<Void, Void> saveTurn = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				startDate = new Date();
				SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
				SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss aa");
				JBCashiersTurns jbCashierTurn = new JBCashiersTurns();
				jbCashierTurn.setStartDate(sdfDate.format(startDate).toString());
				jbCashierTurn.setStartTime(sdfTime.format(startDate).toString());
				jbCashierTurn.setIDCashier(id_cashier);
				jbCashierTurn.setStatus("Abierto");
				daoCashiersTurns.saveTurn(jbCashierTurn);
				
				return null;
			}
		};
		
		//Executes the WORKER
		saveTurn.execute();
	}
}
