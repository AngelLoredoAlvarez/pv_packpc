package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.cashiers.DAOCashiersTurns;
import model.cashiers.JBCashiersTurns;
import model.till.DAOTill;
import model.till.DAOTillDetails;
import model.till.JBTillDetails;
import views.JDCloseTurn;
import views.JFLogin;

public class CtrlCloseTurn {
	//Attributes
	private JDCloseTurn jdCloseTurn;
	private double finalBalance = 0.0, recivedQuantity = 0.0;
	private JBCashiersTurns jbCashierTurn;
	private DecimalFormat dfQuantities;
	private DAOCashiersTurns daoCashiersTurns;
	private DAOTill daoTill;
	private DAOTillDetails daoTillDetails;
	private CtrlMain ctrlSales;
	private CtrlLogin ctrlLogin;
	
	//Method that SETS the VIEW
	public void setJDCloseTurn(JDCloseTurn jdCloseTurn) {
		this.jdCloseTurn = jdCloseTurn;
	}
	
	//Method that SETS the DAOCashiersTurns
	public void setDAOCashiersTurns(DAOCashiersTurns daoCashiersTurns) {
		this.daoCashiersTurns = daoCashiersTurns;
	}
	//Method that SETS the DAOTill
	public void setDAOTill(DAOTill daoTill) {
		this.daoTill = daoTill;
	}
	//Method that SETS the DAOTillDetails
	public void setDAOTillDetails(DAOTillDetails daoTillDetails) {
		this.daoTillDetails = daoTillDetails;
	}
	
	//Method that SETS the Communication JDCloseTurn - JFSells
	public void setCtrlSales(CtrlMain ctrlSales) {
		this.ctrlSales = ctrlSales;
	}
	//Method that SETS the Communication JDCloseTurn - JFLogin
	public void setCtrlLogin(CtrlLogin ctrlLogin) {
		this.ctrlLogin = ctrlLogin;
	}
	
	//Method that RECIVES the Necessary Info
	public void setData(JBCashiersTurns jbCashierTurn, double finalBalance) {
		this.jbCashierTurn = jbCashierTurn;
		this.finalBalance = finalBalance;
		dfQuantities = new DecimalFormat("#,###,##0.00");
		jdCloseTurn.jlblExpectedCash.setText("$" + dfQuantities.format(finalBalance).toString());
		jdCloseTurn.jspTillCash.setValue(finalBalance);
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//This has to be at the END
		jdCloseTurn.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JSpinner
		jdCloseTurn.jspTillCash.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				updateDifferenceStatus();
			}
		});
		jdCloseTurn.jspneTillCash.getTextField().getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent de) {
				updateDifferenceStatus();
			}
			
			@Override
			public void removeUpdate(DocumentEvent de) {
				updateDifferenceStatus();
			}

			@Override
			public void changedUpdate(DocumentEvent de) {
				updateDifferenceStatus();
			}
		});
		
		//JButtons
		jdCloseTurn.jbtnCloseTurn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				closeTurn();
			}
		});
		jdCloseTurn.jbtnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdCloseTurn.dispose();
			}
		});
	}
	
	//Method that UPDATES the Status
	private void updateDifferenceStatus() {
		recivedQuantity = Double.parseDouble(jdCloseTurn.jspTillCash.getValue().toString());
		double difference = recivedQuantity - finalBalance;
		jdCloseTurn.jlblDifference.setText(dfQuantities.format(difference));
		if(difference + recivedQuantity < finalBalance) {
			jdCloseTurn.jlbliStatusIcon.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/small/delete.png")));
			jdCloseTurn.jlblStatus.setText("¡Cuidado!, Existen perdidas");
		} else {
			jdCloseTurn.jlbliStatusIcon.setIcon(new ImageIcon(getClass().getResource("/img/jlabels/small/accept.png")));
			jdCloseTurn.jlblStatus.setText("¡Excelente!, Todo en orden");
		}
	}
	
	//Method that CLOSE the TURN
	private void closeTurn() {
		SwingWorker<Boolean, Void> closeTurn = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				boolean closeTill = false;
				boolean closeTurn = false;
				boolean actionOK = false;
				Date actualDate = new Date();
				SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
				SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss aa");
				
				jbCashierTurn.setEndDate(sdfDate.format(actualDate).toString());
				jbCashierTurn.setEndTime(sdfTime.format(actualDate).toString());
				jbCashierTurn.setStatus("Cerrado");
				closeTurn = daoCashiersTurns.closeTurn(jbCashierTurn);
					
				if(jdCloseTurn.typeCourt.equals("Corte del Día")) {
					JBTillDetails jbTillDetail = new JBTillDetails();
					jbTillDetail.setDate(sdfDate.format(actualDate).toString());
					jbTillDetail.setTime(sdfTime.format(actualDate).toString());
					int id_till = daoTill.getIDTill("Abierta");
					jbTillDetail.setIDTill(id_till);
					jbTillDetail.setIDUser(jbCashierTurn.getIDCashier());
					jbTillDetail.setOperation("Cerró");
					boolean saveTillDetail = daoTillDetails.saveDetailTill(jbTillDetail);
					boolean updateTill = daoTill.closeTill("Cerrada");
					
					if(updateTill && saveTillDetail)
						closeTill = true;
				}
				
				if(closeTurn && closeTill)
					actionOK = true;
				else if(closeTurn) 
					actionOK = true;
				
				ctrlSales.stopTimer();
				
				return actionOK;
			}

			@Override
			protected void done() {
				try {
					boolean resultAction = get();
					if(resultAction) {
						if(jdCloseTurn.whoIsCalling.equals("TillCourt")) {
							ctrlSales.closeJFSales();
							JFLogin jfLogin = new JFLogin();
							ctrlLogin.setJFLogin(jfLogin);
							ctrlLogin.prepareView();
						} else if(jdCloseTurn.whoIsCalling.equals("CloseSystem")) {
							ctrlSales.closeJFSales();
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
		closeTurn.execute();
	}
}
