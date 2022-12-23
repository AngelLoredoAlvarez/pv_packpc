package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import model.clients.DAOClients;
import model.clients.DAOCredits;
import model.clients.JBClients;
import model.clients.JBCredits;
import model.clients.ValidateData;
import views.JDActionClient;

public class CtrlActionClient {
	//Attributes
	private JDActionClient jdActionClient;
	private CtrlConsultClients ctrlConsultClients;
	private CtrlCharge ctrlCharge;
	private DAOClients daoClients;
	private DAOCredits daoCredits;
	private int id_client = 0;
	private boolean doAction, callCheckClient = false, callActionClient = false, callGetClientByName = false;
	private String typeMessage = "", message = "", regexWNL = "^$|^[\\p{L}[0-9] .]+$", regexNN = "^$|^[\\p{L} ]+$", regexNL = "^$|^[0-9 ]+$", regexIRS = "^$|^[\\p{L}[0-9]]+$";
	private String clientCreditLimit = "", clientCurrentBalance = "";
	
	//Method that SETS the VIEW
	public void setJDActionClient(JDActionClient jdActionClient) {
		this.jdActionClient = jdActionClient;
	}
	
	//Method that SETS the Relationship between JDActionClient with JDConsultClients
	public void setCtrlConsultClients(CtrlConsultClients ctrlConsultClients) {
		this.ctrlConsultClients = ctrlConsultClients;
	}
	//Method that SETS the Communication JDActionClient - JDCharge
	public void setCtrlCharge(CtrlCharge ctrlCharge) {
		this.ctrlCharge = ctrlCharge;
	}
	
	//Method that SETS the Relationship with the DAOClients
	public void setDAOClients(DAOClients daoClients) {
		this.daoClients = daoClients;
	}
	//Method that SETS the Relationship with DAOCredits
	public void setDAOCredits(DAOCredits daoCredits) {
		this.daoCredits = daoCredits;
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//END
		jdActionClient.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JTextFields
		((AbstractDocument)jdActionClient.jtxtfNames.getDocument()).setDocumentFilter(new DocumentFilter() {
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
			    	fb.replace(offset, length, text, attrs);
			    }
			}
		});
		((AbstractDocument)jdActionClient.jtxtfFirstName.getDocument()).setDocumentFilter(new DocumentFilter() {
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
			    	fb.replace(offset, length, text, attrs);
			    }
			}
		});
		((AbstractDocument)jdActionClient.jtxtfLastName.getDocument()).setDocumentFilter(new DocumentFilter() {
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
			    	fb.replace(offset, length, text, attrs);
			    }
			}
		});
		((AbstractDocument)jdActionClient.jtxtfStreet.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexWNL, string);
				if(matches) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexWNL, text);
			    if(matches) {
			    	fb.replace(offset, length, text, attrs);
			    }
			}
		});
		((AbstractDocument)jdActionClient.jtxtfExtNum.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexNL, string);
				if(matches) {
					if(fb.getDocument().getLength() + string.length() <= 5) {
						super.insertString(fb, offset, string, attr);
					}
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexNL, text);
			    if(matches) {
			    	if(fb.getDocument().getLength() + text.length() <= 5) {
			    		fb.replace(offset, length, text, attrs);
			    	}
			    }
			}
		});
		((AbstractDocument)jdActionClient.jtxtfIntNum.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexNL, string);
				if(matches) {
					if(fb.getDocument().getLength() + string.length() <= 5) {
						super.insertString(fb, offset, string, attr);
					}
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexNL, text);
			    if(matches) {
			    	if(fb.getDocument().getLength() + text.length() <= 5) {
			    		fb.replace(offset, length, text, attrs);
			    	}
			    }
			}
		});
		((AbstractDocument)jdActionClient.jtxtfColony.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexWNL, string);
				if(matches) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexWNL, text);
			    if(matches) {
			    	fb.replace(offset, length, text, attrs);
			    }
			}
		});
		((AbstractDocument)jdActionClient.jtxtfCity.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexWNL, string);
				if(matches) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexWNL, text);
			    if(matches) {
			    	fb.replace(offset, length, text, attrs);
			    }
			}
		});
		((AbstractDocument)jdActionClient.jtxtfPostCode.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexNL, string);
				if(matches) {
					if(fb.getDocument().getLength() + string.length() <= 5) {
						super.insertString(fb, offset, string, attr);
					}
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexNL, text);
			    if(matches) {
			    	if(fb.getDocument().getLength() + text.length() <= 5) {
			    		fb.replace(offset, length, text, attrs);
			    	}
			    }
			}
		});
		((AbstractDocument)jdActionClient.jtxtfLandLine.getDocument()).setDocumentFilter(new DocumentFilter() {
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
		((AbstractDocument)jdActionClient.jtxtfCelPhone.getDocument()).setDocumentFilter(new DocumentFilter() {
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
		((AbstractDocument)jdActionClient.jtxtfIRS.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexIRS, string);
				if(matches) {
					if(fb.getDocument().getLength() + string.length() <= 13) {
						super.insertString(fb, offset, string.toUpperCase(), attr);
					}
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexIRS, text);
			    if(matches) {
			    	if(fb.getDocument().getLength() + text.length() <= 13) {
			    		fb.replace(offset, length, text.toUpperCase(), attrs);
			    	}
			    }
			}
		});
		
		//JButtons
		jdActionClient.jbtnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				checkData();
			}
		});
		jdActionClient.jbtnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdActionClient.dispose();
			}
		});
	}
	
	//Method that CHECKS if there's any data Missing
	private void checkData() {
		SwingWorker<String, Void> checkData = new SwingWorker<String, Void>() {
			@Override
			protected String doInBackground() throws Exception {
				String field = "";
				JBClients jbClient = new JBClients();
				jbClient.setNames(jdActionClient.jtxtfNames.getText());
				jbClient.setFirstName(jdActionClient.jtxtfFirstName.getText());
				jbClient.setLastName(jdActionClient.jtxtfLastName.getText());
				jbClient.setStreet(jdActionClient.jtxtfStreet.getText());
				jbClient.setExtNumber(jdActionClient.jtxtfExtNum.getText());
				jbClient.setIntNumber(jdActionClient.jtxtfIntNum.getText());
				jbClient.setColony(jdActionClient.jtxtfColony.getText());
				jbClient.setCity(jdActionClient.jtxtfCity.getText());
				jbClient.setPostCode(jdActionClient.jtxtfPostCode.getText());
				jbClient.setLandLine(jdActionClient.jtxtfLandLine.getText());
				jbClient.setCelPhone(jdActionClient.jtxtfCelPhone.getText());
				jbClient.setEmail(jdActionClient.jtxtfEmail.getText());
				jbClient.setIRS(jdActionClient.jtxtfIRS.getText());
				
				ValidateData validate = new ValidateData();
				field = validate.checkData(jbClient);
				
				return field;
			}

			@Override
			protected void done() {
				try {
					String resultField = get();
					
					if(!resultField.equals("")) {
						typeMessage = "missing";
						
						if(resultField.equals("names")) {
							message = "Ingresa el Nombre del Cliente";
							jdActionClient.jtxtfNames.requestFocus();
						} else if(resultField.equals("firstname")) {
							message = "Ingresa su Apellido Paterno";
							jdActionClient.jtxtfFirstName.requestFocus();
						} else if(resultField.equals("lastname")) {
							message = "Ingresa su Apellido Materno";
							jdActionClient.jtxtfLastName.requestFocus();
						} else if(resultField.equals("street")) {
							message = "Ingresa el Nombre de la Calle";
							jdActionClient.jtxtfStreet.requestFocus();
						} else if(resultField.equals("extnumber")) {
							message = "Ingresa el Número Exterior";
							jdActionClient.jtxtfExtNum.requestFocus();
						} else if(resultField.equals("colony")) {
							message = "Ingresa la Colonia";
							jdActionClient.jtxtfColony.requestFocus();
						} else if(resultField.equals("city")) {
							message = "Ingresa la Ciudad";
							jdActionClient.jtxtfCity.requestFocus();
						} else if(resultField.equals("contact")) {
							message = "Es OBLIGATORIO ingresar alguna número de Contacto, Celular o Fijo";
							jdActionClient.jtxtfLandLine.requestFocus();
						} else if(resultField.equals("irs")) {
							message = "Ingresa el R.F.C.";
							jdActionClient.jtxtfIRS.requestFocus();
						} else if(resultField.equals("postcode")) {
							message = "Ingresa el Código Postal";
							jdActionClient.jtxtfPostCode.requestFocus();
						}
							
						jdActionClient.setJOPMessages(typeMessage, message);
					} else {
						callCheckClient = true;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Add a PropertyChangeListener to KNOW when the Thread is Finished
		checkData.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(checkData.isDone() && callCheckClient) {
						checkClient();
						callCheckClient = false;
					}
				}
			}
		});
		
		//Executes the WORKER
		checkData.execute();
	}
	
	//Check if the CLIENT it's been ADDED
	private void checkClient() {
		SwingWorker<Boolean, Void> checkClient = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				boolean checkClient = false;
				
				if(jdActionClient.action.equals("Agregar Cliente")) {
					JBClients jbClient = new JBClients();
					jbClient.setNames(jdActionClient.jtxtfNames.getText());
					jbClient.setFirstName(jdActionClient.jtxtfFirstName.getText());
					jbClient.setLastName(jdActionClient.jtxtfLastName.getText());
					checkClient = daoClients.verifyClient(jbClient);
				}
				
				return checkClient;
			}

			@Override
			protected void done() {
				try {
					boolean resultChecking = get();
					if(resultChecking) {
						typeMessage = "alreadyRegistered";
						message = "El Cliente ya esta Registrado";
						jdActionClient.setJOPMessages(typeMessage, message);
						clearFields();
					} else {
						callActionClient = true;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Add a PropertyChangeListener to KNOW when the Thread is Finished
		checkClient.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(checkClient.isDone() && callActionClient) {
						actionClient();
						callActionClient = false;
					}
				}
			}
		});
		
		//Executes the Worker
		checkClient.execute();
	}
	
	//Method that SAVES the new Client
	private void actionClient() {
		SwingWorker<Boolean, Void> actionClient = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				//Credit DATA
				JBCredits jbCredit = new JBCredits();
				Date date = new Date();
				SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
				SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm a");
				jbCredit.setOpeningDate(sdfDate.format(date).toString());
				jbCredit.setOpeningTime(sdfTime.format(date).toString());
				DecimalFormat dfCredit = new DecimalFormat("#,###,##0.00");
				if(jdActionClient.jspCreditLimit.getValue().toString().equals("0.0"))
					jbCredit.setCreditLimit("Sin Límite");
				else
					jbCredit.setCreditLimit(dfCredit.format(jdActionClient.jspCreditLimit.getValue()).toString());

				jbCredit.setCurrentBalance("0.00");
				
				clientCreditLimit = jbCredit.getCreditLimit();
				clientCurrentBalance = jbCredit.getCurrentBalance();
				
				//Client DATA
				JBClients jbClient = new JBClients();
				jbClient.setNames(jdActionClient.jtxtfNames.getText());
				jbClient.setFirstName(jdActionClient.jtxtfFirstName.getText());
				jbClient.setLastName(jdActionClient.jtxtfLastName.getText());
				jbClient.setStreet(jdActionClient.jtxtfStreet.getText());
				jbClient.setExtNumber(jdActionClient.jtxtfExtNum.getText());
				jbClient.setIntNumber(jdActionClient.jtxtfIntNum.getText());
				jbClient.setColony(jdActionClient.jtxtfColony.getText());
				jbClient.setCity(jdActionClient.jtxtfCity.getText());
				jbClient.setPostCode(jdActionClient.jtxtfPostCode.getText());
				jbClient.setLandLine(jdActionClient.jtxtfLandLine.getText());
				jbClient.setCelPhone(jdActionClient.jtxtfCelPhone.getText());
				jbClient.setEmail(jdActionClient.jtxtfEmail.getText());
				jbClient.setIRS(jdActionClient.jtxtfIRS.getText());
				jbClient.setComentary(jdActionClient.jtaComentary.getText());
				
				if(jdActionClient.action.equals("Agregar Cliente") && jdActionClient.parent.equals("JDCharge") || jdActionClient.action.equals("Agregar Cliente") && jdActionClient.parent.equals("JDConsultClients")) {
					boolean saveCredit = daoCredits.saveCredit(jbCredit);
					int id_credit = daoCredits.getIDCredit(jbCredit);
					jbClient.setIDCredit(id_credit);
					if(saveCredit && id_credit != 0) {
						boolean saveClient = daoClients.saveClient(jbClient);
						if(saveClient && saveCredit)
							doAction = true;
					}
				} else if(jdActionClient.action.equals("Modificar Cliente")) {
					jbClient.setIDClient(id_client);
					boolean updateClient = daoClients.updateClient(jbClient);
					if(updateClient && id_client != 0) {
						boolean updateCredit = daoCredits.updateCreditLimit(jbCredit.getCreditLimit(), id_client);
						if(updateClient && updateCredit)
							doAction = true;
					}
				}
				
				return doAction;
			}

			@Override
			protected void done() {
				try {
					boolean resultAction = get();
					if(resultAction) {
						callGetClientByName = true;
						if(jdActionClient.action.equals("Agregar Cliente")) {
							typeMessage = "savedOK";
							message = "Cliente Registrado Correctamente";
							jdActionClient.setJOPMessages(typeMessage, message);
						} else if(jdActionClient.action.equals("Modificar Cliente")) {
							typeMessage = "modifiedOK";
							message = "Datos Modificados Correctamente";
							jdActionClient.setJOPMessages(typeMessage, message);
							jdActionClient.dispose();
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Add a PropertyChangeListener to KNOW when the Thread is Finished
		actionClient.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(actionClient.isDone() && callGetClientByName) {
						getClienByName();
						callGetClientByName = false;
					}
				}
			}
		});
		
		//Executes the Worker
		actionClient.execute();
	}
	
	//Method that GETS the Client by Name
	private void getClienByName() {
		SwingWorker<JBClients, Void> getClientByName = new SwingWorker<JBClients, Void>() {
			@Override
			protected JBClients doInBackground() throws Exception {
				JBClients jbClient = new JBClients();
				jbClient.setNames(jdActionClient.jtxtfNames.getText());
				jbClient.setFirstName(jdActionClient.jtxtfFirstName.getText());
				jbClient.setLastName(jdActionClient.jtxtfLastName.getText());
				
				JBClients jbRetrivedClient = daoClients.getClientByName(jbClient);
				
				return jbRetrivedClient;
			}

			@Override
			protected void done() {
				try {
					JBClients jbResultClient = get();
					if(jdActionClient.action.equals("Agregar Cliente") && jdActionClient.parent.equals("JDCharge")) {
						ctrlCharge.addNewRow(jbResultClient, clientCreditLimit, clientCurrentBalance);
						clearFields();
						typeMessage = "another";
						message = "¿Desea agregar a otro Cliente?";
						String answer = jdActionClient.setJOPMessages(typeMessage, message);
						if(answer.equals("NO"))
							jdActionClient.dispose();
					} else if(jdActionClient.action.equals("Agregar Cliente") && jdActionClient.parent.equals("JDConsultClients")) {
						ctrlConsultClients.addClientToList(jbResultClient);
						clearFields();
						typeMessage = "another";
						message = "¿Desea agregar a otro Cliente?";
						String answer = jdActionClient.setJOPMessages(typeMessage, message);
						if(answer.equals("NO"))
							jdActionClient.dispose();
					} else if(jdActionClient.action.equals("Modificar Cliente")) {
						ctrlConsultClients.updateRow(jbResultClient);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the WORKER
		getClientByName.execute();
	}
	
	private void clearFields() {
		jdActionClient.jtxtfNames.setText("");
		jdActionClient.jtxtfNames.requestFocus();
		jdActionClient.jtxtfFirstName.setText("");
		jdActionClient.jtxtfLastName.setText("");
		jdActionClient.jtxtfStreet.setText("");
		jdActionClient.jtxtfExtNum.setText("");
		jdActionClient.jtxtfIntNum.setText("");
		jdActionClient.jtxtfColony.setText("");
		jdActionClient.jtxtfCity.setText("");
		jdActionClient.jtxtfPostCode.setText("");
		jdActionClient.jtxtfLandLine.setText("");
		jdActionClient.jtxtfCelPhone.setText("");
		jdActionClient.jtxtfEmail.setText("");
		jdActionClient.jtxtfIRS.setText("");
		jdActionClient.jspCreditLimit.setValue(new Double(0.00));
		jdActionClient.jtaComentary.setText("");
	}
	
	//Method that SETS the DATA to Modify it
	public void setClientData(JBClients jbClient, double creditLimit) {
		id_client = jbClient.getIDClient();
		jdActionClient.jtxtfNames.setText(jbClient.getNames());
		jdActionClient.jtxtfNames.requestFocus();
		jdActionClient.jtxtfFirstName.setText(jbClient.getFirstName());
		jdActionClient.jtxtfLastName.setText(jbClient.getLastName());
		jdActionClient.jtxtfStreet.setText(jbClient.getStreet());
		jdActionClient.jtxtfExtNum.setText(jbClient.getExtNumber());
		jdActionClient.jtxtfIntNum.setText(jbClient.getIntNumber());
		jdActionClient.jtxtfColony.setText(jbClient.getColony());
		jdActionClient.jtxtfCity.setText(jbClient.getCity());
		jdActionClient.jtxtfPostCode.setText(jbClient.getPostCode());
		jdActionClient.jtxtfLandLine.setText(jbClient.getLandLine());
		jdActionClient.jtxtfCelPhone.setText(jbClient.getCelPhone());
		jdActionClient.jtxtfEmail.setText(jbClient.getEmail());
		jdActionClient.jtxtfIRS.setText(jbClient.getIRS());
		if(creditLimit != 0.0)
			jdActionClient.jspCreditLimit.setValue(creditLimit);
		else
			jdActionClient.jspCreditLimit.setValue(new Double(0.0));
		jdActionClient.jtaComentary.setText(jbClient.getComentary());
	}
}
