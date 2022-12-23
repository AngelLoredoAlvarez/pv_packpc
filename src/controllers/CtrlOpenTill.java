package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import model.cashiers.DAOCashiersTurns;
import model.cashiers.JBCashiersTurns;
import model.till.DAOTill;
import model.till.DAOTillDetails;
import model.till.JBTill;
import model.till.JBTillDetails;
import views.JDOpenTill;

public class CtrlOpenTill {
	//Attributes
	private JDOpenTill jdOpenTill;
	private DAOTill daoTill;
	private DAOTillDetails daoTillDetails;
	private DAOCashiersTurns daoCashiersTurns;
	private boolean callSaveTurn = false;
	private CtrlMain ctrlSales;
	private String typeMessage = "", message = "";
	private int id_till = 0, id_cashier_openning = 0, id_cashier_logged = 0;
	
	//Method that SETS the VIEW
	public void setJDOpenTill(JDOpenTill jdOpenTill) {
		this.jdOpenTill = jdOpenTill;
	}
	//Method that SETS the DAOTill
	public void setDAOTill(DAOTill daoTill) {
		this.daoTill = daoTill;
	}
	//Method that SETS the DAOTillDetails
	public void setDAOTillDetails(DAOTillDetails daoTillDetails) {
		this.daoTillDetails = daoTillDetails;
	}
	//Method that SETS the DAOCashiersTurns
	public void setDAOCashiersTurns(DAOCashiersTurns daoCashiersTurns) {
		this.daoCashiersTurns = daoCashiersTurns;
	}
	
	//Method that SETS the Communication JDOpenTill - JFSells
	public void setCtrlSales(CtrlMain ctrlSales) {
		this.ctrlSales = ctrlSales;
	}
	
	//Method that SETS the Initial DATA
	public void setDataCashierPrivileged(String cashierName, int id_cashier_privileged, int id_cashier) {
		jdOpenTill.jtxtfUser.setText(cashierName);
		this.id_cashier_openning = id_cashier_privileged;
		this.id_cashier_logged = id_cashier;
		Date date = new Date();
		SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
		SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss aa");
		jdOpenTill.jtxtfDate.setText(sdfDate.format(date));
		jdOpenTill.jtxtfTime.setText(sdfTime.format(date));
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//This has to be at the END
		jdOpenTill.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JButtons
		jdOpenTill.jbtnOpenTill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				saveTill();
			}
		});
		jdOpenTill.jbtnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdOpenTill.dispose();
			}
		});
	}
	
	private void saveTill() {
		SwingWorker<Boolean, Void> saveTill = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				boolean saveTill = false;
				boolean saveTillDetail = false;
				boolean openTill = false;
				
				JBTill jbTill = new JBTill();
				String balances = jdOpenTill.jspInitialBalance.getValue().toString();
				String nBalances = balances.replace(",", "");
				jbTill.setBeginninBalance(nBalances);
				jbTill.setFinalBalance(nBalances);
				jbTill.setStatus("Abierta");
				saveTill = daoTill.saveTill(jbTill);
				
				//If the TILL was SAVED
				if(saveTill) {
					id_till = daoTill.getIDTill("Abierta");
					JBTillDetails jbTillDetail = new JBTillDetails();
					jbTillDetail.setDate(jdOpenTill.jtxtfDate.getText());
					jbTillDetail.setTime(jdOpenTill.jtxtfTime.getText());
					jbTillDetail.setIDTill(id_till);
					jbTillDetail.setIDUser(id_cashier_openning);
					jbTillDetail.setOperation("Abrío");
					saveTillDetail = daoTillDetails.saveDetailTill(jbTillDetail);
				}
				if(saveTill && saveTillDetail)
					openTill = true;
				return openTill;
			}

			@Override
			protected void done() {
				try {
					boolean resultOpenTill = get();
					if(resultOpenTill) {
						typeMessage = "tillOpened";
						message = "La Caja No. " + id_till + " fue Aperturada";
						jdOpenTill.setJOPMessages(typeMessage, message);
						ctrlSales.enableActionsAccordingPrivileges();
						jdOpenTill.dispose();
						callSaveTurn = true;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Add a PropertyChangeListener to KNOW when the Thread is Finished
		saveTill.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent pce) {
				if("state".equalsIgnoreCase(pce.getPropertyName())) {
					if(saveTill.isDone() && callSaveTurn) {
						saveTurn();
						callSaveTurn = false;
					}
				}
			}
		});
		
		//Executes the Worker
		saveTill.execute();
	}
	
	//Method that SAVES the Turn, if there's any UnclosedTurn
	private void saveTurn() {
		SwingWorker<Void, Void> saveTurn = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				Date actualDate = new Date();
				SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
				SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss aa");
				
				JBCashiersTurns jbCashierTurn = new JBCashiersTurns();
				jbCashierTurn.setStartDate(sdfDate.format(actualDate).toString());
				jbCashierTurn.setStartTime(sdfTime.format(actualDate).toString());
				jbCashierTurn.setIDCashier(id_cashier_logged);
				jbCashierTurn.setStatus("Abierto");
				daoCashiersTurns.saveTurn(jbCashierTurn);
				
				ctrlSales.setOpenedTill(true);
					
				return null;
			}
		};
			
		//Executes the WORKER
		saveTurn.execute();
	}
}
