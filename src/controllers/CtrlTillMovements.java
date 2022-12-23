package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import model.till.DAOTill;
import model.till.DAOTillMovements;
import model.till.JBTillMovements;
import views.JDTillMovements;

public class CtrlTillMovements {
	//Attributes
	private JDTillMovements jdTillMovements;
	private int id_cashier = 0;
	private DAOTill daoTill;
	private DAOTillMovements daoTillMovements;
	private double newBalance = 0.00;
	private String typeMessage = "", message = "";;
	
	//Method that SETS the VIEW
	public void setJDTillMovements(JDTillMovements jdTillMovements) {
		this.jdTillMovements = jdTillMovements;
	}
	
	//Method that SETS the DAOTill
	public void setDAOTill(DAOTill daoTill) {
		this.daoTill = daoTill;
	}
	//Method that SETS the DAOTillMovements
	public void setDAOTillMovements(DAOTillMovements daoTillMovements) {
		this.daoTillMovements = daoTillMovements;
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListener();
		//This has to be at the END
		jdTillMovements.setVisible(true);
	}
	
	//Method that SETS the ID Cashier
	public void setIDCashier(int id_cashier) {
		this.id_cashier = id_cashier;
	}
	
	//Method that SETS the USER Privileges
	public void setUserPriviliges(ArrayList<String> cashierPriviliges, boolean root) {
		if(!root) {
			int cantOptions = 0;
			for(int cp = 0; cp < cashierPriviliges.size(); cp++) {
				if(cashierPriviliges.get(cp).equals("CashInFlow") || cashierPriviliges.get(cp).equals("CashOutFlow"))
					cantOptions++;
			}
			if(cantOptions == 1) {
				for(int sp = 0; sp < cashierPriviliges.size(); sp++) {
					if(cashierPriviliges.get(sp).equals("CashInFlow"))
						jdTillMovements.jcbTypeMovement.removeItemAt(1);
					else if(cashierPriviliges.get(sp).equals("CashOutFlow"))
						jdTillMovements.jcbTypeMovement.removeItemAt(0);
				}
			}
		}
	}
	
	//Method that SETS the LISTENERS
	private void setListener() {
		//JSpinner
		jdTillMovements.jspBalance.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				doAction();
			}
		});
		
		//JTextArea
		((AbstractDocument)jdTillMovements.jtaComentary.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			    super.remove(fb, offset, length);
			    doAction();
			}

			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
				super.insertString(fb, offset, string, attr);
				doAction();
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
				super.replace(fb, offset, length, text, attrs);
				doAction();
			}
		});
		
		//JButtons
		jdTillMovements.jbtnAccept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				saveTillMovement();
			}
		});
		jdTillMovements.jbtnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdTillMovements.dispose();
			}
		});
	}
	
	//Method that SAVES the Till Movement
	private void saveTillMovement() {
		SwingWorker<Boolean, Void> saveTillMovement = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				boolean saveMovement = false;
				boolean updateBalance = false;
				boolean movementStatus = false;
				Date actualDate = new Date();
				SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
				SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm:ss a");
				
				JBTillMovements jbTillMovement = new JBTillMovements();
				jbTillMovement.setTypeMovement(jdTillMovements.jcbTypeMovement.getSelectedItem().toString());
				jbTillMovement.setDate(sdfDate.format(actualDate).toString());
				jbTillMovement.setTime(sdfTime.format(actualDate).toString());
				jbTillMovement.setBalance(Double.parseDouble(jdTillMovements.jspBalance.getValue().toString()));
				jbTillMovement.setComentCN(jdTillMovements.jtaComentary.getText());
				jbTillMovement.setIDCashier(id_cashier);
				int id_till = daoTill.getIDTill("Abierta");
				jbTillMovement.setIDTill(id_till);
				saveMovement = daoTillMovements.saveMovement(jbTillMovement);
				
				if(saveMovement) {
					double finalBalance = daoTill.getActualBalance();
					double amount = Double.parseDouble(jdTillMovements.jspBalance.getValue().toString());
					if(jdTillMovements.jcbTypeMovement.getSelectedItem().toString().equals("Entrada"))
						newBalance = finalBalance + amount;
					else
						newBalance = finalBalance - amount;
					updateBalance = daoTill.updateBalance(id_till, newBalance);
				}
				
				if(saveMovement && updateBalance)
					movementStatus = true;
				
				return movementStatus;
			}

			@Override
			protected void done() {
				try {
					boolean resultMovement = get();
					if(resultMovement) {
						typeMessage = "movementOK";
						message = "Se ha realizado el movimiento";
						jdTillMovements.setJOPMessages(typeMessage, message);
						jdTillMovements.dispose();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the Worker
		saveTillMovement.execute();
	}
	
	//Method that ENABLES the JButton
	private void doAction() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if(jdTillMovements.jtaComentary.getText().length() > 0 && !jdTillMovements.jspBalance.getValue().toString().equals("0.0"))
			    	jdTillMovements.jbtnAccept.setEnabled(true);
			    else
			    	jdTillMovements.jbtnAccept.setEnabled(false);
			}
		});
	}
}
