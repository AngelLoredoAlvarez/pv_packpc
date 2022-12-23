package controllers;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import model.business_information.DAOBusinessInformation;
import model.business_information.JBBusinessInformation;
import model.clients.DAOClients;
import model.clients.DAOCredits;
import model.clients.JBClients;
import model.clients.JBCredits;
import model.invoices.DAOInvoices;
import model.invoices.DAOInvoicesDetails;
import model.invoices.JBInvoices;
import model.invoices.JBInvoicesDetails;
import model.sales.DAOSales;
import model.sales.JBDetailsSales;
import model.sales.JBSales;
import mx.bigdata.sat.cfdi.CFDv32;
import mx.bigdata.sat.cfdi.v32.schema.Comprobante;
import mx.bigdata.sat.cfdi.v32.schema.Comprobante.Complemento;
import mx.bigdata.sat.cfdi.v32.schema.Comprobante.Conceptos;
import mx.bigdata.sat.cfdi.v32.schema.Comprobante.Conceptos.Concepto;
import mx.bigdata.sat.cfdi.v32.schema.Comprobante.Emisor;
import mx.bigdata.sat.cfdi.v32.schema.Comprobante.Emisor.RegimenFiscal;
import mx.bigdata.sat.cfdi.v32.schema.Comprobante.Impuestos;
import mx.bigdata.sat.cfdi.v32.schema.Comprobante.Impuestos.Traslados;
import mx.bigdata.sat.cfdi.v32.schema.Comprobante.Impuestos.Traslados.Traslado;
import mx.bigdata.sat.cfdi.v32.schema.Comprobante.Receptor;
import mx.bigdata.sat.cfdi.v32.schema.ObjectFactory;
import mx.bigdata.sat.cfdi.v32.schema.TUbicacion;
import mx.bigdata.sat.cfdi.v32.schema.TUbicacionFiscal;
import mx.bigdata.sat.cfdi.v32.schema.TimbreFiscalDigital;
import mx.bigdata.sat.security.KeyLoaderEnumeration;
import mx.bigdata.sat.security.factory.KeyLoaderFactory;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JasperPrint;
import views.JDActionClient;
import views.JDConsultClients;
import views.JDReports;
import views.JDStatementOfAccount;
import views.JPVViewer;

public class CtrlConsultClients {
	//Attributes
	private JDConsultClients jdConsultClients;
	//Global Variables
	private JBClients jbClient;
	private JBCredits creditInfo;
	private ArrayList<JBSales> alSales;
	private String date = "", typeMessage = "", message = "", regex = "^$|^[\\p{L} ]+$", folio ="", uuid = "";
	private ArrayList<Integer> dateNumbers;
	private double creditLimit = 0.0, subTotal = 0.0, irs = 0.0;
	private int id_cashier = 0, selectedRow = 0, id_client = 0;
	private boolean noSales = false;
	private ArrayList<JBDetailsSales> alDetailsSale;
	
	//To set the Communication with other Views
	private CtrlActionClient ctrlActionClient;
	private CtrlStatementOfAccountO ctrlStatementOfAccount;
	
	//DAOS
	private DAOClients daoClients;
	private DAOSales daoSales;
	private DAOCredits daoCredits;
	private DAOBusinessInformation daoBusinessInformation;
	private DAOInvoices daoInvoices;
	private DAOInvoicesDetails daoInvoicesDetails;
	
	//Method that SETS the VIEW
	public void setJDConsultClients(JDConsultClients jdConsultClients) {
		this.jdConsultClients = jdConsultClients;
	}
	//Method that SETS the DAOClients
	public void setDAOClients(DAOClients daoClients) {
		this.daoClients = daoClients;
	}
	//Method that SETS the Communication JDConsultClients - JDActionClient
	public void setCtrlActionClient(CtrlActionClient ctrlActionClient) {
		this.ctrlActionClient = ctrlActionClient;
	}
	//Method that SETS the Communication JDConsultClients - JDStatementOfAccount
	public void setCtrlStatementOfAccount(CtrlStatementOfAccountO ctrlStatementOfAccount) {
		this.ctrlStatementOfAccount = ctrlStatementOfAccount;
	}
	//Method that SETS the DAOCredits
	public void setDAOCredits(DAOCredits daoCredits) {
		this.daoCredits = daoCredits;
	}
	//Method that SETS the DAOSells
	public void setDAOSells(DAOSales daoSells) {
		this.daoSales = daoSells;
	}
	//Method that SETS the DAOBusinessInformation
	public void setDAOBusinessInformation(DAOBusinessInformation daoBusinessInformation) {
		this.daoBusinessInformation = daoBusinessInformation;
	}
	//Method that SETS the DAOInvoices
	public void setDAOInvoices(DAOInvoices daoInvoices) {
		this.daoInvoices = daoInvoices;
	}
	//Method that SETS the DAOInvicesDetails
	public void setDAOInvoicesDetails(DAOInvoicesDetails daoInvoicesDetails) {
		this.daoInvoicesDetails = daoInvoicesDetails;
	}
	
	//Method that SETS the Info for the Invoice
	public void setInvoiceInfo(ArrayList<JBDetailsSales> alDetailsSale, String folio, double subTotal, double irs) {
		this.alDetailsSale = alDetailsSale;
		this.folio = folio;
		this.subTotal = subTotal;
		this.irs = irs;
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//This has to be at the END
		jdConsultClients.setVisible(true);
	}
	
	//Method that SETS the ID Cashier
	public void setIDCashier(int id_cashier) {
		this.id_cashier = id_cashier;
	}
	
	//Method that SETS the USER Privileges
	public void setCashierPriviliges(ArrayList<String> cashierPriviliges, boolean root) {
		if(root) {
			jdConsultClients.jbtnAdd.setVisible(true);
			jdConsultClients.jbtnModify.setVisible(true);
			jdConsultClients.jbtnDelete.setVisible(true);
			jdConsultClients.jbtnCreditStatus.setVisible(true);
		} else {
			for(int sp = 0; sp < cashierPriviliges.size(); sp++) {
				if(cashierPriviliges.get(sp).equals("AMClient")) {
					jdConsultClients.jbtnAdd.setVisible(true);
					jdConsultClients.jbtnModify.setVisible(true);
				}
				
				if(cashierPriviliges.get(sp).equals("DelClient"))
					jdConsultClients.jbtnDelete.setVisible(true);
				
				if(cashierPriviliges.get(sp).equals("ManageClientCredit"))
					jdConsultClients.jbtnCreditStatus.setVisible(true);
			}
		}
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JDialog
		jdConsultClients.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent we) {
				getClients();
			}
		});
		
		//JTextField
		((AbstractDocument)jdConsultClients.jtxtfSearch.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			    filterJTable();
			    if(jdConsultClients.jtClients.getRowCount() == 0)
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
					if(jdConsultClients.jtClients.getRowCount() == 0)
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
			    	if(jdConsultClients.jtClients.getRowCount() == 0)
						disableJButtons();
					else
						enableJButtons();
			    }
			}
		});
		
		//JButtons
		jdConsultClients.jbtnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						String title = "Agregar Cliente", icon = "/img/jframes/add_client.png";
						JDActionClient jdActionClient = new JDActionClient(jdConsultClients, title, icon, "JDConsultClients");
						ctrlActionClient.setJDActionClient(jdActionClient);
						ctrlActionClient.prepareView();
					}
				});
			}
		});
		jdConsultClients.jbtnModify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						getClient();
					}
				});
			}
		});
		jdConsultClients.jbtnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				deleteClient();
			}
		});
		jdConsultClients.jbtnCreditStatus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				getClientCreditStatus();
			}
		});
		jdConsultClients.jbtnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdConsultClients.dispose();
			}
		});
		
		//JTable
		jdConsultClients.jtClients.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent lse) {
				if(!lse.getValueIsAdjusting()) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							selectedRow = jdConsultClients.jtClients.getSelectedRow();
							if(selectedRow >= 0) {
								if(selectedRow == 0) {
									jdConsultClients.jbtnModify.setEnabled(false);
									jdConsultClients.jbtnDelete.setEnabled(false);
									jdConsultClients.jbtnCreditStatus.setEnabled(false);
								} else {
									jdConsultClients.jbtnModify.setEnabled(true);
									jdConsultClients.jbtnDelete.setEnabled(true);
									jdConsultClients.jbtnCreditStatus.setEnabled(true);
								}
								jdConsultClients.jtxtfSearch.requestFocus();
							}
						}
					});
				}
			}
		});
		jdConsultClients.jtClients.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount() == 2) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							if(jdConsultClients.father.equals("Invoice")) {
								invoiceSale();
								jdConsultClients.dispose();
							}
						}
					});
				}
			}
		});
	}
	
	//Method that GETS all the Registered Clients
	public void getClients() {
		SwingWorker<ArrayList<JBClients>, Void> getClients = new SwingWorker<ArrayList<JBClients>, Void>() {
			@Override
			protected ArrayList<JBClients> doInBackground() throws Exception {
				ArrayList<JBClients> alGetClients = daoClients.getClients();
				return alGetClients;
			}

			@Override
			protected void done() {
				try {
					ArrayList<JBClients> alClients = get();
					//Remove the previous List
					for(int a = jdConsultClients.dtmClients.getRowCount() - 1; a >= 0; a--) {
						jdConsultClients.dtmClients.removeRow(a);
					}
					//Add the new List
					for(int a = 0; a < alClients.size(); a++) {
						JBClients jbClient = new JBClients();
						jbClient = alClients.get(a);
						if(jbClient.getIDClient() == 1) {
							jdConsultClients.dtmClients.addRow(new Object[] {
								jbClient.getIDClient(),
								jbClient.getIRS(),
								jbClient.getNames() + " " + jbClient.getFirstName() + " " + jbClient.getLastName()
							});
						} else {
							jdConsultClients.dtmClients.addRow(new Object[] {
								jbClient.getIDClient(),
								jbClient.getIRS(),
								jbClient.getNames() + " " + jbClient.getFirstName() + " " + jbClient.getLastName(),
								jbClient.getStreet() + " #" + jbClient.getExtNumber() + ", Col. " + jbClient.getColony() + ", " + jbClient.getCity(),
								jbClient.getLandLine(),
								jbClient.getCelPhone(),
								jbClient.getEmail()
							});
						}
					}
					
					jdConsultClients.jtClients.setRowSelectionInterval(0, 0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		//Executes the Worker
		getClients.execute();
	}
	
	//Method that GETS an Individual Client to Modify him
	private void getClient() {
		SwingWorker<Void, Void> getClient = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				int selectedRow = jdConsultClients.jtClients.getSelectedRow();
				int id_client = Integer.parseInt(jdConsultClients.jtClients.getValueAt(selectedRow, 0).toString());
				jbClient = daoClients.getClientByID(id_client);
				String sCL = daoCredits.getCreditLimit(id_client);
				if(!sCL.equals("Sin Límite"))
					creditLimit = Double.parseDouble(sCL.replace(",", ""));
				
				return null;
			}

			@Override
			protected void done() {
				String title = "Modificar Cliente", icon = "/img/jframes/edit_client.png";
				JDActionClient jdActionClient = new JDActionClient(jdConsultClients, title, icon, "JDConsultClients");
				ctrlActionClient.setJDActionClient(jdActionClient);
				ctrlActionClient.setClientData(jbClient, creditLimit);
				ctrlActionClient.prepareView();
				creditLimit = 0.0;
			}
			
		};
		//Executes the Worker
		getClient.execute();
	}
	
	//Method that DELETES a Client from the DB
	private void deleteClient() {
		typeMessage = "delete";
		String name = jdConsultClients.jtClients.getValueAt(selectedRow, 1).toString();
		message = "¿Desea Eliminar al Cliente: " + name + "?";
		String answer = jdConsultClients.setJOPMessages(typeMessage, message);
		if(answer.equals("YES")) {
			SwingWorker<Boolean, Void> deleteClient = new SwingWorker<Boolean, Void>() {
				@Override
				protected Boolean doInBackground() throws Exception {
					String id_client = jdConsultClients.jtClients.getValueAt(selectedRow, 0).toString();
					boolean deleteClient = daoClients.deleteClient(id_client);
					
					return deleteClient;
				}

				@Override
				protected void done() {
					try {
						boolean resultDelete = get();
						if(resultDelete) {
							jdConsultClients.dtmClients.removeRow(selectedRow);
							jdConsultClients.jtxtfSearch.setText("");
							jdConsultClients.jtxtfSearch.requestFocus();
							if(jdConsultClients.jtClients.getRowCount() != 0)
								jdConsultClients.jtClients.setRowSelectionInterval(0, 0);
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
			deleteClient.execute();
		}
	}
	
	//Method that GETS the Credit Status from the selected Client
	private void getClientCreditStatus() {
		SwingWorker<Void, Void> getClientCreditStatus = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				int selectedRow = jdConsultClients.jtClients.getSelectedRow();
				id_client = Integer.parseInt(jdConsultClients.dtmClients.getValueAt(selectedRow, 0).toString());
				
				//Get Client Credit Info
				creditInfo = daoCredits.getCreditInfo(id_client);
				
				//Get the Sales 
				Date actualDate = new Date();
				SimpleDateFormat sdfActualDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
				date = sdfActualDate.format(actualDate);
				alSales = daoSales.getSalesFromClient(id_client, date);
				if(alSales.isEmpty()) {
					int lastSaleFromClient = daoSales.getLastSaleFromClient(id_client);
					date = daoSales.getSaleDate(lastSaleFromClient);
					alSales = daoSales.getSalesFromClient(id_client, date);
					
					if(alSales.isEmpty())
						noSales = true;
				}
				
				Date dateToConvert = sdfActualDate.parse(date);
				Calendar cDate = Calendar.getInstance();
				cDate.setTime(dateToConvert);
				dateNumbers = new ArrayList<Integer>();
				dateNumbers.add(cDate.get(Calendar.YEAR));
				dateNumbers.add(cDate.get(Calendar.MONTH));
				dateNumbers.add(cDate.get(Calendar.DAY_OF_MONTH));
				
				return null;
			}

			@Override
			protected void done() {
				if(noSales) {
					typeMessage = "noSales";
					message = "El Cliente no ha realizado ninguna Compra a Crédito";
					jdConsultClients.setJOPMessages(typeMessage, message);
				} else {
					JDStatementOfAccount jdStatementOfAccount = new JDStatementOfAccount(jdConsultClients);
					ctrlStatementOfAccount.setJDStatementOfAccount(jdStatementOfAccount);
					ctrlStatementOfAccount.setData(creditInfo, alSales, date, dateNumbers, id_client, id_cashier);
					ctrlStatementOfAccount.prepareView();
				}
				noSales = false;
			}
		};
		
		//Execute the Worker
		getClientCreditStatus.execute();
	}
	
	//Method that ADD's the new Client to the List
	public void addClientToList(JBClients jbClient) {
		jdConsultClients.dtmClients.addRow(new Object[] {
			jbClient.getIDClient(),
			jbClient.getNames() + " " + jbClient.getFirstName() + " " + jbClient.getLastName(),
			jbClient.getStreet() + " #" + jbClient.getExtNumber() + ", Col. " + jbClient.getColony() + ", " + jbClient.getCity(),
			jbClient.getLandLine(),
			jbClient.getCelPhone(),
			jbClient.getEmail()
		});
		
		jdConsultClients.jtxtfSearch.requestFocus();
		int cantRows = jdConsultClients.jtClients.getRowCount() - 1; 
		Rectangle rect = jdConsultClients.jtClients.getCellRect(cantRows, 0, true);
		jdConsultClients.jtClients.scrollRectToVisible(rect);
		jdConsultClients.jtClients.clearSelection();
		jdConsultClients.jtClients.setRowSelectionInterval(cantRows, cantRows);
	}
	
	//Method that UPDATES the selected Row
	public void updateRow(JBClients jbClient) {
		jdConsultClients.dtmClients.setValueAt(jbClient.getNames() + " " + jbClient.getFirstName() + " " + jbClient.getLastName(), selectedRow, 1);
		jdConsultClients.dtmClients.setValueAt(jbClient.getStreet() + " #" + jbClient.getExtNumber() + ", Col. " + jbClient.getColony() + ", " + jbClient.getCity(), selectedRow, 2);
		jdConsultClients.dtmClients.setValueAt(jbClient.getLandLine(), selectedRow, 3);
		jdConsultClients.dtmClients.setValueAt(jbClient.getCelPhone(), selectedRow, 4);
		jdConsultClients.dtmClients.setValueAt(jbClient.getEmail(), selectedRow, 5);
	}
	
	//Method that Filter the JTable
	private void filterJTable() {
	   String userInput = jdConsultClients.jtxtfSearch.getText();
	   RowFilter<TableModel, Object> rf = null;
	   try {
	       rf = RowFilter.regexFilter("(?i)" + userInput, 1);
	   } catch (java.util.regex.PatternSyntaxException e) {
	       return;
	   }
	   jdConsultClients.trsSorter.setRowFilter(rf);
	}
	
	//Method that Enables the JButtons
	private void enableJButtons() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				jdConsultClients.jbtnModify.setEnabled(true);
				jdConsultClients.jbtnDelete.setEnabled(true);
				jdConsultClients.jbtnCreditStatus.setEnabled(true);
			}
		});
	}
	
	//Method that Disables the JButtons
	private void disableJButtons() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				jdConsultClients.jbtnModify.setEnabled(false);
				jdConsultClients.jbtnDelete.setEnabled(false);
				jdConsultClients.jbtnCreditStatus.setEnabled(false);
			}			
		});
	}
	
	//Method that INVOICE the Sale
	private void invoiceSale() {
		SwingWorker<Boolean, Void> invoiceSale = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception { 
				ObjectFactory of = new ObjectFactory();
				DecimalFormat dfDecimals = new DecimalFormat("######0.00");
				
				//Get EMISOR Information
				JBBusinessInformation jbBusinessInformation = daoBusinessInformation.retriveBusinessInformation();
				Emisor emisor = of.createComprobanteEmisor();
				emisor.setNombre(jbBusinessInformation.getNameBusiness().toUpperCase());
				emisor.setRfc(jbBusinessInformation.getIRS());
				//Fiscal Address
				TUbicacionFiscal fa = of.createTUbicacionFiscal();
				fa.setCalle(jbBusinessInformation.getStreet());
				fa.setNoExterior(jbBusinessInformation.getExtNumber());
				fa.setColonia(jbBusinessInformation.getColony());
				fa.setLocalidad(jbBusinessInformation.getCity());
				fa.setMunicipio(jbBusinessInformation.getCity());
				fa.setCodigoPostal(jbBusinessInformation.getPostCode());
				fa.setEstado("San Luis Potosí");
				fa.setPais("México");
				emisor.setDomicilioFiscal(fa);
				//Expedition Address
				TUbicacion ea = of.createTUbicacion();
				ea.setCalle(jbBusinessInformation.getStreet());
				ea.setNoExterior(jbBusinessInformation.getExtNumber());
				ea.setColonia(jbBusinessInformation.getColony());
				ea.setLocalidad(jbBusinessInformation.getCity());
				ea.setMunicipio(jbBusinessInformation.getCity());
				ea.setCodigoPostal(jbBusinessInformation.getPostCode());
				ea.setEstado("San Luis Potosí");
				ea.setPais("México");
				emisor.setExpedidoEn(ea);
				RegimenFiscal fr = of.createComprobanteEmisorRegimenFiscal();
				fr.setRegimen("Persona Física");
				emisor.getRegimenFiscal().add(fr);
				
				//Set RECEPTOR Information
				int id_client = Integer.parseInt(jdConsultClients.dtmClients.getValueAt(selectedRow, 0).toString());
				JBClients jbClient = daoClients.getClientByID(id_client);
				Receptor receptor = of.createComprobanteReceptor();
				receptor.setNombre(jbClient.getNames() + " " + jbClient.getFirstName() + " " + jbClient.getLastName());
				receptor.setRfc(jbClient.getIRS());
				//Receptor Address
				TUbicacion ra = of.createTUbicacion();
				ra.setCalle(jbClient.getStreet());
				ra.setNoExterior(jbClient.getExtNumber());
				ra.setColonia(jbClient.getColony());
				ra.setLocalidad(jbClient.getCity());
				ra.setMunicipio(jbClient.getCity());
				ra.setCodigoPostal(jbClient.getPostCode());
				ra.setEstado("San Luis Potosí");
				ra.setPais("México");
				
				//Concepts
				Conceptos cps = of.createComprobanteConceptos();
				List<Concepto> list = cps.getConcepto();
				for(int gc = 0; gc < alDetailsSale.size(); gc++) {
					Concepto cpt = of.createComprobanteConceptosConcepto();
					JBDetailsSales jbDetailSale = alDetailsSale.get(gc);
					cpt.setUnidad(jbDetailSale.getUnitMeasurement());
					cpt.setImporte(new BigDecimal(dfDecimals.format(jbDetailSale.getImportPrice())));
					cpt.setCantidad(new BigDecimal(jbDetailSale.getQuantity()));
					cpt.setDescripcion(jbDetailSale.getDescription());
					cpt.setValorUnitario(new BigDecimal(dfDecimals.format(jbDetailSale.getSellPrice())));
					list.add(cpt);
				}
				
				//IRS
				Impuestos imps = of.createComprobanteImpuestos();
				Traslados trs = of.createComprobanteImpuestosTraslados();
				List<Traslado> listTraslados = trs.getTraslado();
				Traslado traslado = of.createComprobanteImpuestosTrasladosTraslado();
				traslado.setImporte(new BigDecimal(dfDecimals.format(irs)));
				traslado.setImpuesto("IVA");
				traslado.setTasa(new BigDecimal("16.00"));
				listTraslados.add(traslado);
				imps.setTraslados(trs);
				
				//Receipt
				Comprobante comp = of.createComprobante();
				comp.setVersion("3.2");
				comp.setFecha(new Date());
				comp.setFormaDePago("Pago en una sola Exhibición");
				comp.setTipoDeComprobante("ingreso");
				comp.setSubTotal(new BigDecimal(dfDecimals.format(subTotal)));
				comp.setTotal(new BigDecimal(dfDecimals.format(subTotal + irs)));
				comp.setMetodoDePago("Efectivo");
				comp.setLugarExpedicion("México");
				
				//Set all the Info
				comp.setEmisor(emisor);
				comp.setReceptor(receptor);
				comp.setConceptos(cps);
				comp.setImpuestos(imps);
				
				//Get the Private Key
				PrivateKey key = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PRIVATE_KEY_LOADER, new FileInputStream(jbBusinessInformation.getPrivateKeyRoute()), jbBusinessInformation.getPrivateKeyPass()).getKey();
				//Get Certificate
			    X509Certificate cert = KeyLoaderFactory.createInstance(KeyLoaderEnumeration.PUBLIC_KEY_LOADER, new FileInputStream(jbBusinessInformation.getCertificateRoute())).getKey();
			    
			    //Sail Certificate
			    CFDv32 cfdSailComprobante = new CFDv32(comp);
			    Comprobante saild = cfdSailComprobante.sellarComprobante(key, cert);
				cfdSailComprobante.validar();
			    cfdSailComprobante.verificar();
			    
			    //Save the XML
			    String os = System.getProperty("os.name");
			    String route = "";
			    if(os.equals("Linux") || os.equals("Mac OSX"))
			    	route = System.getProperty("user.home") + "/";
			    else 
			    	route = System.getProperty("user.home") + "/Documents/";
//			    
//			    cfdSailComprobante.guardar(new FileOutputStream(route + "XMLPorTimbrar.xml"));

//			    //Convert XML content into one line
//			    BufferedReader br = new BufferedReader(new FileReader(new File(route + "XMLPorTimbrar.xml")));
//			    String line;
//			    StringBuilder sbXMLOneLine = new StringBuilder();
//			    while((line = br.readLine()) != null){
//			        sbXMLOneLine.append(line.trim());
//			    }
//			    br.close();
//			    
//			    //Stamping
//			    WSTFD service = new WSTFD();
//			    IWSTFD port = service.getSoapHttpEndpoint();
//			    RespuestaTFD serverAnswer = port.timbrarCFDI("DEMO8102142Q2", "oh3rBMZy=", sbXMLOneLine.toString(), folio);
			    
//			    if(serverAnswer.isOperacionExitosa()) {
//			    	//Write the stamped Invoice XML File
//			    	FileWriter fileToWrite = new FileWriter(route + "XMLTimbrado.xml");
//			    	fileToWrite.write(serverAnswer.getXMLResultado().getValue());
//			    	fileToWrite.close();
			    	
			    	//Verify the Stamping
			    	TimbreFiscalDigital timbre = of.createTimbreFiscalDigital();
			        timbre.setFechaTimbrado(new Date());
			        timbre.setNoCertificadoSAT(saild.getNoCertificado());
			        Complemento complemento = of.createComprobanteComplemento();
			        complemento.getAny().add(timbre);
			        saild.setComplemento(complemento);
			        
			        //XMLTimbrado.xml
			        CFDv32 cfdTimbrado = new CFDv32(new FileInputStream(route + "8f72a42f-1959-476b-880c-ff20fb8493ef_FACTURA 36A.xml"));
			        cfdTimbrado.validar();
			        cfdTimbrado.verificar();
			        
			        //Get all the NEEDED Information from the CFDI
			        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			        DocumentBuilder db = dbf.newDocumentBuilder();
			        Document document = db.parse(new FileInputStream(route + "8f72a42f-1959-476b-880c-ff20fb8493ef_FACTURA 36A.xml"));
			        XPathFactory xpathFact = XPathFactory.newInstance();
			        XPath xpath = xpathFact.newXPath();
			        //Certificate Number
			        String certificateCFDINumber = (String) xpath.evaluate("/Comprobante/@noCertificado", document, XPathConstants.STRING);
			        //UUID
			        uuid = (String) xpath.evaluate("/Comprobante/Complemento/TimbreFiscalDigital/@UUID", document, XPathConstants.STRING);
			        //cfdiSail
			        String cfdiSail = (String) xpath.evaluate("/Comprobante/Complemento/TimbreFiscalDigital/@selloCFD", document, XPathConstants.STRING);
			        //satSail
			        String satSail = (String) xpath.evaluate("/Comprobante/Complemento/TimbreFiscalDigital/@selloSAT", document, XPathConstants.STRING);
			        //originalCFDIString
			        StreamSource sourceCFDIXSL = new StreamSource(getClass().getResourceAsStream("/sat/cadenaoriginal_3_2.xslt"));
			        StreamSource sourceCFDIString = new StreamSource(new FileInputStream(route + "8f72a42f-1959-476b-880c-ff20fb8493ef_FACTURA 36A.xml"));
			        TransformerFactory tfCFDI = TransformerFactory.newInstance();
			        Transformer tCFDI = tfCFDI.newTransformer(sourceCFDIXSL);
			        StringWriter swCFDI = new StringWriter();
			        tCFDI.transform(sourceCFDIString, new StreamResult(swCFDI));
			        String originalCFDIString = swCFDI.toString();
			        //originalSATString
			        StreamSource sourceTFDXSL = new StreamSource(getClass().getResourceAsStream("/sat/cadenaoriginal_TFD_1_0.xslt"));
			        StreamSource sourceSATString = new StreamSource(new FileInputStream(route + "8f72a42f-1959-476b-880c-ff20fb8493ef_FACTURA 36A.xml"));
			        TransformerFactory tfSAT = TransformerFactory.newInstance();
			        Transformer tSAT = tfSAT.newTransformer(sourceTFDXSL);
			        StringWriter swSAT = new StringWriter();
			        tSAT.transform(sourceSATString, new StreamResult(swSAT));
			        String originalSATString = swSAT.toString();
			        //certificateSATNumber
			        String certificateSATNumber = (String) xpath.evaluate("/Comprobante/Complemento/TimbreFiscalDigital/@noCertificadoSAT", document, XPathConstants.STRING);
			        
			        System.out.println("No. de Certificado: " + certificateCFDINumber);
			        System.out.println("UUID: " + uuid);
			        System.out.println("Sello del CFDI: " + cfdiSail);
			        System.out.println("Sello SAT: " + satSail);
			        System.out.println("Cadena original del CFDI: " + originalCFDIString);
			        System.out.println("Cadena original del Timbre Fiscal Digital del SAT: " + originalSATString);
			        System.out.println("No. de Certificado del SAT: " + certificateSATNumber);
			        
			        //Save the Invoice and the Details of the Invoice
			        SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
			        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss aa");
			        Date date = new Date();
			        JBInvoices jbInvoice = new JBInvoices();
			        jbInvoice.setDate(sdfDate.format(date));
			        jbInvoice.setTime(sdfTime.format(date));
			        jbInvoice.setUUID(uuid);
			        jbInvoice.setCertificateCFDINumber(certificateCFDINumber);
			        jbInvoice.setIDBusinessInformation(jbBusinessInformation.getIDBusinessInformation());
			        jbInvoice.setIDClient(id_client);
			        jbInvoice.setPaymentMethod(comp.getFormaDePago());
			        jbInvoice.setWayToPay(comp.getMetodoDePago());
			        jbInvoice.setSubTotal(subTotal);
			        jbInvoice.setIRS(irs);
			        jbInvoice.setTotal(new Double(dfDecimals.format(subTotal + irs)));
			        jbInvoice.setCFDISail(cfdiSail);
			        jbInvoice.setSATSail(satSail);
			        jbInvoice.setOriginalCFDIString(originalCFDIString);
			        jbInvoice.setOriginalSATString(originalSATString);
			        jbInvoice.setCertificateSATNumber(certificateSATNumber);
//			        boolean savingInvoice = daoInvoices.saveInvoice(jbInvoice);
//			        
//			        if(savingInvoice) {
//			        	int id_invoice = daoInvoices.getIDInvoiceByUUID(uuid);
//			        	for(int dtl = 0; dtl < alDetailsSale.size(); dtl++) {
//			        		JBDetailsSales jbDetailSale = alDetailsSale.get(dtl);
//			        		JBInvoicesDetails jbInvoiceDetails = new JBInvoicesDetails();
//			        		jbInvoiceDetails.setIDProductService(jbDetailSale.getIDProduct());
//				        	jbInvoiceDetails.setQuantity(jbDetailSale.getQuantity());
//				        	jbInvoiceDetails.setImporte(jbDetailSale.getImportPrice());
//				        	jbInvoiceDetails.setIDInvoice(id_invoice);
//				        }
//			        }
			        
			        //Make Visual Representation
			        
			        //Generate QR Code
			        DecimalFormat dfAdd = new DecimalFormat("00000000.00000000");
			        String importe = dfAdd.format(subTotal + irs);
			        String qrString = "?re=" + jbBusinessInformation.getIRS() + "&" + "rr=" + jbClient.getIRS() + "&" + "tt=" + importe + "&" + "id=" + uuid;
					String filePath = route + "QRCode.png";
					String charset = "UTF-8";
					Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
					hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
					BitMatrix matrix = new MultiFormatWriter().encode(new String(qrString.getBytes(charset), charset), BarcodeFormat.QR_CODE, 200, 200, hintMap);
					MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath));
			        
//			        //Delete previous XML
//			    	File XMLPorTimbrarD = new File(route + "XMLPorTimbrar.xml");
//			    	XMLPorTimbrarD.delete();
//			    	
//			    	File XMLTimbradoD = new File(route + "XMLTimbrado.xml");
//			    	XMLTimbradoD.delete();
//			    } else {
//			    	typeMessage = "";
//			    	message = "";
//			    }
			    return true;
			     
//				return serverAnswer.isOperacionExitosa();
			}

			@Override
			protected void done() {
				try {
					boolean resultInvoice = get();
					if(resultInvoice) {
						System.out.println("Todo OK");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the WORKER
		invoiceSale.execute();
	}
}