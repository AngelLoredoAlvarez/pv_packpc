package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import model.clients.DAOClientsMovements;
import model.clients.DAOCredits;
import model.clients.JBClientsMovements;
import model.till.DAOTill;
import views.JDPayment;

public class CtrlPayment {
	//Attributes
	private JDPayment jdPayment;
	private DAOCredits daoCredits;
	private CtrlStatementOfAccountO ctrlStatementOfAccount;
	private DAOClientsMovements daoPayment;
	private DecimalFormat dfBalance;
	private double pay;
	private int id_cashier = 0;
	private String newBalance;
	private DAOTill daoTill;
	
	//Method that SETS the VIEW
	public void setJDPay(JDPayment jdPay) {
		this.jdPayment = jdPay;
	}
	
	//Method that SETS the Communication between JDPay - JDStatementOfAccount
	public void setCtrlStatementOfAccount(CtrlStatementOfAccountO ctrlStatementOfAccount) {
		this.ctrlStatementOfAccount = ctrlStatementOfAccount;
	}
	
	//Method that SETS the DAOCredits
	public void setDAOCredits(DAOCredits daoCredits) {
		this.daoCredits = daoCredits;
	}
	//Method that SETS the DAOPayment
	public void setDAOPayment(DAOClientsMovements daoPayment) {
		this.daoPayment = daoPayment;
	}
	//Method that SETS the DAOTill
	public void setDAOTill(DAOTill daoTill) {
		this.daoTill = daoTill;
	}
	
	//Method that SETS the ID Cashier
	public void setIDCashier(int id_cashier) {
		this.id_cashier = id_cashier;
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		//Initializations
		dfBalance = new DecimalFormat("#,###,##0.00");
		this.setListener();
		//This has to be at the END
		jdPayment.setVisible(true);
	}
	
	//Method that SETS the Listener
	private void setListener() {
		//JButtons
		jdPayment.jbtnPay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				updateCurrentBalance();
			}
		});
		jdPayment.jbtnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdPayment.dispose();
			}
		});
	}
	
	//Method that UPDATES the CurrentBalance
	private void updateCurrentBalance() {
		SwingWorker<Boolean, Void> updateCurrentBalance = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				boolean allOK = false;
				boolean savePayment = false;
				boolean updateBalance = false;
				boolean updateTillBalance = false;
				JBClientsMovements jbClientMovement = new JBClientsMovements();
				
				int id_client = ctrlStatementOfAccount.returnIDUser();
				double actualBalance = ctrlStatementOfAccount.returnCurrentBalance();
				pay = Double.parseDouble(jdPayment.jspQuantity.getValue().toString().replace(",", ""));
				
				//SAVE the PAYMENT
				Date actualDT = new Date();
				SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
				SimpleDateFormat sdfTime = new SimpleDateFormat("hh':'mm':'ss aa");
				jbClientMovement.setDate(sdfDate.format(actualDT).toString());
				jbClientMovement.setTime(sdfTime.format(actualDT).toString());
				jbClientMovement.setTypeMovement("Abono");
				jbClientMovement.setQuantity(pay);
				jbClientMovement.setIDClient(id_client);
				jbClientMovement.setIDCashier(id_cashier);
				savePayment = daoPayment.saveMovement(jbClientMovement);
				
				//UPDATE the BALANCE
				double result = actualBalance - pay;
				newBalance = dfBalance.format(result).toString();
				updateBalance = daoCredits.updateCurrentBalance(newBalance, id_client);
				int id_till = daoTill.getIDTill("Abierta");
				double actualTillBalance = daoTill.getActualBalance();
				double resultTill = actualTillBalance + pay;
				updateTillBalance = daoTill.updateBalance(id_till, resultTill);
				
				if(savePayment && updateBalance && updateTillBalance)
					allOK = true;
				
				return allOK;
			}

			@Override
			protected void done() {
				try {
					boolean resultAction = get();
					if(resultAction) {
						boolean change = true;
						String typeMessage = "balanceUpdated";
						String message = "Se han abonado $" + dfBalance.format(pay);
						jdPayment.dispose();
						ctrlStatementOfAccount.setNewBalance(typeMessage, message, newBalance, change);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		//Executes the Worker
		updateCurrentBalance.execute();
	}
}
