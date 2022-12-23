package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import model.business_information.DAOBusinessInformation;
import model.business_information.JBBusinessInformation;
import model.business_information.ValidateData;
import views.JDBusinessInformation;

public class CtrlBusinessInformation {
	private JDBusinessInformation jdBusinessInformation;
	private String route = "", action = "", typeMessage = "", message = "", regexNL = "^$|^[0-9]+$", regexWNLDG = "^$|^[\\p{L}[0-9] .-]+$", regexIRS = "^$|^[\\p{L}[0-9]]+$";
	private DAOBusinessInformation daoBusinessInformation;
	private JBBusinessInformation jbBusinessInformation;
	private int id_business_information = 0;
	private boolean callActionBusinessInformation = false;
	
	//Method that SETS the DAOBusinessInformation
	public void setDAOBusinessInformation(DAOBusinessInformation daoBusinessInformation) {
		this.daoBusinessInformation = daoBusinessInformation;
	}
	
	//Method that SETS the VIEW
	public void setJDBusinessInformation(JDBusinessInformation jdBusinessInformation) {
		this.jdBusinessInformation = jdBusinessInformation;
	}
	
	//Method that PREPARES the View
	public void prepareView() {
		this.setListeners();
		//This has to be at the END
		jdBusinessInformation.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JDialog
		jdBusinessInformation.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent we) {
				setBusinessInformation();
			}
		});
		
		//JTextFields
		((AbstractDocument)jdBusinessInformation.jtxtfName.getDocument()).setDocumentFilter(new DocumentFilter() {
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
		((AbstractDocument)jdBusinessInformation.jtxtfCompanyName.getDocument()).setDocumentFilter(new DocumentFilter() {
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
		((AbstractDocument)jdBusinessInformation.jtxtfIRS.getDocument()).setDocumentFilter(new DocumentFilter() {
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
		((AbstractDocument)jdBusinessInformation.jtxtfStreet.getDocument()).setDocumentFilter(new DocumentFilter() {
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
		((AbstractDocument)jdBusinessInformation.jtxtfExtNumber.getDocument()).setDocumentFilter(new DocumentFilter() {
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
		((AbstractDocument)jdBusinessInformation.jtxtfIntNumber.getDocument()).setDocumentFilter(new DocumentFilter() {
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
		((AbstractDocument)jdBusinessInformation.jtxtfColony.getDocument()).setDocumentFilter(new DocumentFilter() {
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
		((AbstractDocument)jdBusinessInformation.jtxtfCity.getDocument()).setDocumentFilter(new DocumentFilter() {
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
		((AbstractDocument)jdBusinessInformation.jtxtfPostCode.getDocument()).setDocumentFilter(new DocumentFilter() {
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
		
		//JButtons
		jdBusinessInformation.jbtnSearchCertificate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						route = jdBusinessInformation.searchFiles("Buscar Certificado de Sellos", "Certificado de Sellos", "cer");
						jdBusinessInformation.jtxtfCertificateRoute.setText(route);
					}
				});
			}
		});
		jdBusinessInformation.jbtnSearchPrivateKey.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						route = jdBusinessInformation.searchFiles("Buscar Clave Privada", "Clave Privada", "key");
						jdBusinessInformation.jtxtfPrivateKeyRoute.setText(route);
						jdBusinessInformation.jpfPrivateKeyPass.requestFocus();
					}
				});
			}
		});
		jdBusinessInformation.jbtnPrevious.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						jdBusinessInformation.jpSATInfo.setVisible(true);
						jdBusinessInformation.jpCK.setVisible(false);
						jdBusinessInformation.jbtnAction.setText("Siguiente >");
						jdBusinessInformation.jbtnPrevious.setEnabled(false);
					}
				});
			}
		});
		jdBusinessInformation.jbtnAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(jdBusinessInformation.jbtnAction.getText().equals("Guardar")) {
					checkData();
				} else {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							jdBusinessInformation.jpSATInfo.setVisible(false);
							jdBusinessInformation.jpCK.setVisible(true);
							jdBusinessInformation.jbtnAction.setText("Guardar");
							jdBusinessInformation.jbtnPrevious.setEnabled(true);
						}
					});
				}
			}
		});
		jdBusinessInformation.jbtnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdBusinessInformation.dispose();
			}
		});
	}
	
	//Method that SETS the Info on the GUI
	private void setBusinessInformation() {
		SwingWorker<Void, Void> setBusinessInformation = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				//Know what to DO
				int quantityRegisters = daoBusinessInformation.retriveQuantityRegisters();
				
				if(quantityRegisters > 0) {
					jbBusinessInformation = daoBusinessInformation.retriveBusinessInformation();
					action = "updateInformation";
					id_business_information = jbBusinessInformation.getIDBusinessInformation();
				} else {
					action = "saveInformation";
				}
				
				return null;
			}

			@Override
			protected void done() {
				if(action.equals("updateInformation")) {
					jdBusinessInformation.jtxtfName.setText(jbBusinessInformation.getNameBusiness());
					jdBusinessInformation.jtxtfCompanyName.setText(jbBusinessInformation.getCompanyName());
					jdBusinessInformation.jtxtfIRS.setText(jbBusinessInformation.getIRS());
					jdBusinessInformation.jtxtfStreet.setText(jbBusinessInformation.getStreet());
					jdBusinessInformation.jtxtfExtNumber.setText(jbBusinessInformation.getExtNumber());
					jdBusinessInformation.jtxtfIntNumber.setText(jbBusinessInformation.getIntNumber());
					jdBusinessInformation.jtxtfColony.setText(jbBusinessInformation.getColony());
					jdBusinessInformation.jtxtfCity.setText(jbBusinessInformation.getCity());
					jdBusinessInformation.jtxtfPostCode.setText(jbBusinessInformation.getPostCode());
					jdBusinessInformation.jtxtfEmail.setText(jbBusinessInformation.getEmail());
					jdBusinessInformation.jtxtfCertificateRoute.setText(jbBusinessInformation.getCertificateRoute());
					jdBusinessInformation.jtxtfPrivateKeyRoute.setText(jbBusinessInformation.getPrivateKeyRoute());
					jdBusinessInformation.jtxtfName.requestFocus();
				}
			}
		};
		
		setBusinessInformation.execute();
	}
	
	private void checkData() {
		SwingWorker<String, Void> checkData = new SwingWorker<String, Void>() {
			@Override
			protected String doInBackground() throws Exception {
				String field = "";
				
				getBusinessInformation();
				
				ValidateData validate = new ValidateData();
				field = validate.checkData(jbBusinessInformation);
				
				return field;
			}

			@Override
			protected void done() {
				try {
					String resultField = get();
					if(!resultField.equals("")) {
						typeMessage = "missing";
						if(resultField.equals("name_company")) {
							message = "Ingresa el Nombre del Negocio";
							jdBusinessInformation.jtxtfName.requestFocus();
							jdBusinessInformation.jpCK.setVisible(false);
							jdBusinessInformation.jpSATInfo.setVisible(true);
							jdBusinessInformation.jbtnAction.setText("Siguiente >");
							jdBusinessInformation.jbtnPrevious.setEnabled(false);
						} else if(resultField.equals("company_name")) {
							message = "Ingresa la Razón Social del Negocio";
							jdBusinessInformation.jtxtfCompanyName.requestFocus();
							jdBusinessInformation.jpCK.setVisible(false);
							jdBusinessInformation.jpSATInfo.setVisible(true);
							jdBusinessInformation.jbtnAction.setText("Siguiente >");
							jdBusinessInformation.jbtnPrevious.setEnabled(false);
						} else if(resultField.equals("irs")) {
							message = "Ingresa el R.F.C. del Proveedor";
							jdBusinessInformation.jtxtfIRS.requestFocus();
							jdBusinessInformation.jpCK.setVisible(false);
							jdBusinessInformation.jpSATInfo.setVisible(true);
							jdBusinessInformation.jbtnAction.setText("Siguiente >");
							jdBusinessInformation.jbtnPrevious.setEnabled(false);
						} else if(resultField.equals("street")) {
							message = "Ingresa la Calle";
							jdBusinessInformation.jtxtfStreet.requestFocus();
							jdBusinessInformation.jpCK.setVisible(false);
							jdBusinessInformation.jpSATInfo.setVisible(true);
							jdBusinessInformation.jbtnAction.setText("Siguiente >");
							jdBusinessInformation.jbtnPrevious.setEnabled(false);
						} else if(resultField.equals("ext_number")) {
							message = "Ingresa el Número Exterior";
							jdBusinessInformation.jtxtfExtNumber.requestFocus();
							jdBusinessInformation.jpCK.setVisible(false);
							jdBusinessInformation.jpSATInfo.setVisible(true);
							jdBusinessInformation.jbtnAction.setText("Siguiente >");
							jdBusinessInformation.jbtnPrevious.setEnabled(false);
						} else if(resultField.equals("colony")) {
							message = "Ingresa la Colonia";
							jdBusinessInformation.jtxtfColony.requestFocus();
							jdBusinessInformation.jpCK.setVisible(false);
							jdBusinessInformation.jpSATInfo.setVisible(true);
							jdBusinessInformation.jbtnAction.setText("Siguiente >");
							jdBusinessInformation.jbtnPrevious.setEnabled(false);
						} else if(resultField.equals("city")) {
							message = "Ingresa la Ciudad";
							jdBusinessInformation.jtxtfCity.requestFocus();
							jdBusinessInformation.jpCK.setVisible(false);
							jdBusinessInformation.jpSATInfo.setVisible(true);
							jdBusinessInformation.jbtnAction.setText("Siguiente >");
							jdBusinessInformation.jbtnPrevious.setEnabled(false);
						} else if(resultField.equals("post_code")) {
							message = "Ingresa el Código Postal";
							jdBusinessInformation.jtxtfPostCode.requestFocus();
							jdBusinessInformation.jpCK.setVisible(false);
							jdBusinessInformation.jpSATInfo.setVisible(true);
							jdBusinessInformation.jbtnAction.setText("Siguiente >");
							jdBusinessInformation.jbtnPrevious.setEnabled(false);
						} else if(resultField.equals("email")) {
							message = "Ingresa la Dirección de Correo Electrónico";
							jdBusinessInformation.jtxtfEmail.requestFocus();
							jdBusinessInformation.jpCK.setVisible(false);
							jdBusinessInformation.jpSATInfo.setVisible(true);
							jdBusinessInformation.jbtnAction.setText("Siguiente >");
							jdBusinessInformation.jbtnPrevious.setEnabled(false);
						} else if(resultField.equals("certificate_route")) {
							message = "Búsca el Certificado de Sellos";
							jdBusinessInformation.jpCK.setVisible(true);
							jdBusinessInformation.jpSATInfo.setVisible(false);
						} else if(resultField.equals("privatekey_route")) {
							message = "Búsca la Clave Privada";
							jdBusinessInformation.jpCK.setVisible(true);
							jdBusinessInformation.jpSATInfo.setVisible(false);
						} else if(resultField.equals("privatekey_pass")) {
							message = "Ingresa la Contraseña de la Clave Privada";
							jdBusinessInformation.jpfPrivateKeyPass.requestFocus();
							jdBusinessInformation.jpCK.setVisible(true);
							jdBusinessInformation.jpSATInfo.setVisible(false);
						}
						
						jdBusinessInformation.setJOPMessages(typeMessage, message);
					} else {
						callActionBusinessInformation = true;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Adds a PropertyChangeListener to KNOW when the Thread is finished
		checkData.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(checkData.isDone() && callActionBusinessInformation) {
						actionBusinessInformation();
						callActionBusinessInformation = false;
					}
				}
			}
		});
		
		//Executes the WORKER
		checkData.execute();
	}
	
	//Method that SAVES/UPDATE the Business Information
	private void actionBusinessInformation() {
		SwingWorker<Boolean, Void> actionBusinessInformation = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				//Get the INFO from the GUI
				getBusinessInformation();
				
				boolean resultAction = false;
				
				if(action.equals("updateInformation"))
					resultAction = daoBusinessInformation.updateBusinessInformation(id_business_information, jbBusinessInformation);
				else if(action.equals("saveInformation"))
					resultAction = daoBusinessInformation.saveBusinessInformation(jbBusinessInformation);
				
				return resultAction;
			}

			@Override
			protected void done() {
				try {
					boolean result = get();
					if(result) {
						
						typeMessage = action;
						
						if(action.equals("updateInformation"))
							message = "La Información ha sido Modificada";
						else if(action.equals("saveInformation"))
							message = "La Información ha sido Guardada";
						
						jdBusinessInformation.setJOPMessages(typeMessage, message);
						
						jdBusinessInformation.dispose();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		actionBusinessInformation.execute();
	}
	
	//Method that GETS the Info from the GUI
	private void getBusinessInformation() {
		jbBusinessInformation = new JBBusinessInformation();
		jbBusinessInformation.setNameBusiness(jdBusinessInformation.jtxtfName.getText());
		jbBusinessInformation.setCompanyName(jdBusinessInformation.jtxtfCompanyName.getText());
		jbBusinessInformation.setIRS(jdBusinessInformation.jtxtfIRS.getText());
		jbBusinessInformation.setStreet(jdBusinessInformation.jtxtfStreet.getText());
		jbBusinessInformation.setExtNumber(jdBusinessInformation.jtxtfExtNumber.getText());
		jbBusinessInformation.setIntNumber(jdBusinessInformation.jtxtfIntNumber.getText());
		jbBusinessInformation.setColony(jdBusinessInformation.jtxtfColony.getText());
		jbBusinessInformation.setCity(jdBusinessInformation.jtxtfCity.getText());
		jbBusinessInformation.setPostCode(jdBusinessInformation.jtxtfPostCode.getText());
		jbBusinessInformation.setEmail(jdBusinessInformation.jtxtfEmail.getText());
		jbBusinessInformation.setCertificateRoute(jdBusinessInformation.jtxtfCertificateRoute.getText());
		jbBusinessInformation.setPrivateKeyRoute(jdBusinessInformation.jtxtfPrivateKeyRoute.getText());
		jbBusinessInformation.setPassKey(new String(jdBusinessInformation.jpfPrivateKeyPass.getPassword()));
	}
}
