package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import model.providers.DAOProviders;
import model.providers.JBProviders;
import model.providers.ValidateData;
import views.JDActionProvider;

public class CtrlActionProvider {
	//Attributes
	private JDActionProvider jdActionProvider;
	private JBProviders jbProvider;
	private boolean callCheckProvider = false, callActionProvider = false;
	private DAOProviders daoProviders;
	private CtrlConsultProviders ctrlConsultProviders;
	private int id_provider = 0;
	private String typeMessage = "", message = "", regexNL = "^$|^[0-9]+$", regexNLWS = "^$|^[0-9 ]+$", regexWNLDG = "^$|^[\\p{L}[0-9] .-]+$", regexIRS = "^$|^[\\p{L}[0-9]]+$";
	private boolean doAction, callGetProviderByIRS = false;
	
	//Method that SETS the VIEW
	public void setJDActionProvider(JDActionProvider jdActionProvider) {
		this.jdActionProvider = jdActionProvider;
	}
	//Method that SETS the DAO
	public void setDAOProviders(DAOProviders daoProviders) {
		this.daoProviders = daoProviders;
	}
	//Method that SETS the Communication JDActionProvider - JDConsultProviders
	public void setCtrlConsultProviders(CtrlConsultProviders ctrlConsultProviders) {
		this.ctrlConsultProviders = ctrlConsultProviders;
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//This has to be at the END
		jdActionProvider.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JTextFields
		((AbstractDocument)jdActionProvider.jtxtfName.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexWNLDG, string);
				if(matches) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexWNLDG, text);
			    if(matches) {
			    	fb.replace(offset, length, text, attrs);
			    }
			}
		});
		((AbstractDocument)jdActionProvider.jtxtfCompanyName.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexWNLDG, string);
				if(matches) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexWNLDG, text);
			    if(matches) {
			    	fb.replace(offset, length, text, attrs);
			    }
			}
		});
		((AbstractDocument)jdActionProvider.jtxtfIRS.getDocument()).setDocumentFilter(new DocumentFilter() {
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
		((AbstractDocument)jdActionProvider.jtxtfStreet.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexWNLDG, string);
				if(matches) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexWNLDG, text);
			    if(matches) {
			    	fb.replace(offset, length, text, attrs);
			    }
			}
		});
		((AbstractDocument)jdActionProvider.jtxtfExtNumber.getDocument()).setDocumentFilter(new DocumentFilter() {
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
		((AbstractDocument)jdActionProvider.jtxtfIntNumber.getDocument()).setDocumentFilter(new DocumentFilter() {
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
		((AbstractDocument)jdActionProvider.jtxtfColony.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexWNLDG, string);
				if(matches) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexWNLDG, text);
			    if(matches) {
			    	fb.replace(offset, length, text, attrs);
			    }
			}
		});
		((AbstractDocument)jdActionProvider.jtxtfCity.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexWNLDG, string);
				if(matches) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
			    boolean matches = Pattern.matches(regexWNLDG, text);
			    if(matches) {
			    	fb.replace(offset, length, text, attrs);
			    }
			}
		});
		((AbstractDocument)jdActionProvider.jtxtfPostCode.getDocument()).setDocumentFilter(new DocumentFilter() {
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
		((AbstractDocument)jdActionProvider.jtxtfLandLine1.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				boolean matches = Pattern.matches(regexNLWS, string);
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
			    boolean matches = Pattern.matches(regexNLWS, text);
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
		((AbstractDocument)jdActionProvider.jtxtfLandLine2.getDocument()).setDocumentFilter(new DocumentFilter() {
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
			    boolean matches = Pattern.matches(regexNLWS, text);
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
		
		//JButtons
		jdActionProvider.jbtnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				checkData();
			}
		});
		jdActionProvider.jbtnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdActionProvider.dispose();
			}
		});
	}
	
	//Method that CHECKS that there's any DATA missing
	private void checkData() {
		SwingWorker<String, Void> checkData = new SwingWorker<String, Void>() {
			@Override
			protected String doInBackground() throws Exception {
				String field = "";
				
				getProviderData();
				
				ValidateData validate = new ValidateData();
				field = validate.checkData(jbProvider);
				
				return field;
			}

			@Override
			protected void done() {
				try {
					String resultField = get();
					if(!resultField.equals("")) {
						typeMessage = "missing";
						if(resultField.equals("names")) {
							message = "Ingresa el Nombre del Proveedor";
							jdActionProvider.jtxtfName.requestFocus();
						} else if(resultField.equals("companyname")) {
							message = "Ingresa la Razón Social del Proveedor";
							jdActionProvider.jtxtfCompanyName.requestFocus();
						} else if(resultField.equals("irs")) {
							message = "Ingresa el R.F.C. del Proveedor";
							jdActionProvider.jtxtfIRS.requestFocus();
						} else if(resultField.equals("street")) {
							message = "Ingresa la Calle";
							jdActionProvider.jtxtfStreet.requestFocus();
						} else if(resultField.equals("extnumber")) {
							message = "Ingresa el Número Exterior";
							jdActionProvider.jtxtfExtNumber.requestFocus();
						} else if(resultField.equals("colony")) {
							message = "Ingresa la Colonia";
							jdActionProvider.jtxtfColony.requestFocus();
						} else if(resultField.equals("city")) {
							message = "Ingresa la Ciudad";
							jdActionProvider.jtxtfCity.requestFocus();
						} else if(resultField.equals("postcode")) {
							message = "Ingresa el Código Postal";
							jdActionProvider.jtxtfPostCode.requestFocus();
						} else if(resultField.equals("contact")) {
							message = "Es OBLIGATORIO ingresar alguna número de Contacto";
							jdActionProvider.jtxtfLandLine1.requestFocus();
						}
						jdActionProvider.setJOPMessages(typeMessage, message);
					} else {
						callCheckProvider = true;
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
					if(checkData.isDone() && callCheckProvider) {
						checkProvider();
						callCheckProvider = false;
					}
				}
			}
		});
		
		//Executes the WORKER
		checkData.execute();
	}
	
	//Method that CHECKS if the PROVIDERS has been already ADDED
	private void checkProvider() {
		SwingWorker<Boolean, Void> checkProvider = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				String irs = jdActionProvider.jtxtfIRS.getText();
				boolean check = daoProviders.verifyProvider(irs);
				
				return check;
			}
			@Override
			protected void done() {
				try {
					boolean resultCheck = get();
					if(resultCheck) {
						typeMessage = "alreadyRegistered";
						message = "El Proveedor ya se encuentra registrado";
						jdActionProvider.setJOPMessages(typeMessage, message);
						clearFields();
					} else {
						callActionProvider = true;
					}
						
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		checkProvider.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(checkProvider.isDone() && callActionProvider) {
						actionProvider();
						callActionProvider = false;
					}
				}
			}
		});
		
		//Executes the Worker
		checkProvider.execute();
	}
	
	//Method that SAVES or UPDATE the Provider
	private void actionProvider() {
		SwingWorker<Boolean, Void> actionProvider = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				getProviderData();
				
				if(jdActionProvider.action.equals("Agregar Proveedor"))
					doAction = daoProviders.saveProvider(jbProvider);
				else if(jdActionProvider.action.equals("Modificar Proveedor"))
					doAction = daoProviders.updateProvider(jbProvider);
				
				return doAction;
			}
			
			@Override
			protected void done() {
				try {
					boolean resultAction = get();
					if(resultAction) {
						callGetProviderByIRS = true;
						if(jdActionProvider.action.equals("Agregar Proveedor")) {
							typeMessage = "savedOK";
							message = "El Proveedor ha sido Registrado Correctamente";
							jdActionProvider.setJOPMessages(typeMessage, message);
						} else if(jdActionProvider.action.equals("Modificar Proveedor")) {
							typeMessage = "modifiedOK";
							message = "Datos Actualizados Correctamente";
							jdActionProvider.setJOPMessages(typeMessage, message);
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Adds a PropertyChangeListener to KNOW when the Thread is Finished
		actionProvider.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(actionProvider.isDone() && callGetProviderByIRS) {
						getProviderByIRS();
						callGetProviderByIRS = false;
					}
				}
			}
		});
		
		//Executes the Worker
		actionProvider.execute();
	}
	
	//Method that GETS all the DATA by IRS
	private void getProviderByIRS() {
		SwingWorker<JBProviders, Void> getProviderByIRS = new SwingWorker<JBProviders, Void>() {
			@Override
			protected JBProviders doInBackground() throws Exception {
				String irs = jdActionProvider.jtxtfIRS.getText();
				JBProviders jbProvider = daoProviders.getProviderByIRS(irs);
				
				return jbProvider;
			}

			@Override
			protected void done() {
				try {
					JBProviders retrivedProvider = get();
					if(jdActionProvider.action.equals("Agregar Proveedor")) {
						ctrlConsultProviders.addRow(retrivedProvider);
						clearFields();
						typeMessage = "another";
						message = "¿Desea agregar a otro Proveedor?";
						String answer = jdActionProvider.setJOPMessages(typeMessage, message);
						if(answer.equals("NO")) {
							jdActionProvider.dispose();
						}
					} else if(jdActionProvider.action.equals("Modificar Proveedor")) {
						ctrlConsultProviders.updateRow(retrivedProvider);
						jdActionProvider.dispose();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the Worker
		getProviderByIRS.execute();
	}
	
	//Method that GETS the Provider Data
	private void getProviderData() {
		jbProvider = new JBProviders();
		jbProvider.setIDProvider(id_provider);
		jbProvider.setName(jdActionProvider.jtxtfName.getText());
		jbProvider.setCompanyName(jdActionProvider.jtxtfCompanyName.getText());
		jbProvider.setIRS(jdActionProvider.jtxtfIRS.getText());
		jbProvider.setStreet(jdActionProvider.jtxtfStreet.getText());
		jbProvider.setExtNumber(jdActionProvider.jtxtfExtNumber.getText());
		jbProvider.setIntNumber(jdActionProvider.jtxtfIntNumber.getText());
		jbProvider.setColony(jdActionProvider.jtxtfColony.getText());
		jbProvider.setCity(jdActionProvider.jtxtfCity.getText());
		jbProvider.setPostCode(jdActionProvider.jtxtfPostCode.getText());
		jbProvider.setLandLine1(jdActionProvider.jtxtfLandLine1.getText());
		jbProvider.setLandLine2(jdActionProvider.jtxtfLandLine2.getText());
		jbProvider.setEmail(jdActionProvider.jtxtfEmail.getText());
		jbProvider.setComentary(jdActionProvider.jtaComentary.getText());
	}
	
	//Method that SETS the DATA to be Modified
	public void setProviderData(JBProviders jbProvider) {
		id_provider = jbProvider.getIDProvider();
		jdActionProvider.jtxtfName.setText(jbProvider.getName());
		jdActionProvider.jtxtfCompanyName.setText(jbProvider.getCompanyName());
		jdActionProvider.jtxtfIRS.setText(jbProvider.getIRS());
		jdActionProvider.jtxtfStreet.setText(jbProvider.getStreet());
		jdActionProvider.jtxtfExtNumber.setText(jbProvider.getExtNumber());
		jdActionProvider.jtxtfIntNumber.setText(jbProvider.getIntNumber());
		jdActionProvider.jtxtfColony.setText(jbProvider.getColony());
		jdActionProvider.jtxtfCity.setText(jbProvider.getCity());
		jdActionProvider.jtxtfPostCode.setText(jbProvider.getPostCode());
		jdActionProvider.jtxtfLandLine1.setText(jbProvider.getLandLine1());
		jdActionProvider.jtxtfLandLine2.setText(jbProvider.getLandLine2());
		jdActionProvider.jtxtfEmail.setText(jbProvider.getEmail());
		jdActionProvider.jtaComentary.setText(jbProvider.getComentary());
	}
	
	//Method that Clears all the Fields
	private void clearFields() {
		jdActionProvider.jtxtfName.setText("");
		jdActionProvider.jtxtfName.requestFocus();
		jdActionProvider.jtxtfCompanyName.setText("");
		jdActionProvider.jtxtfIRS.setText("");
		jdActionProvider.jtxtfStreet.setText("");
		jdActionProvider.jtxtfIntNumber.setText("");
		jdActionProvider.jtxtfExtNumber.setText("");
		jdActionProvider.jtxtfColony.setText("");
		jdActionProvider.jtxtfCity.setText("");
		jdActionProvider.jtxtfPostCode.setText("");
		jdActionProvider.jtxtfLandLine1.setText("");
		jdActionProvider.jtxtfLandLine2.setText("");
		jdActionProvider.jtxtfEmail.setText("");
		jdActionProvider.jtaComentary.setText("");
	}
}
