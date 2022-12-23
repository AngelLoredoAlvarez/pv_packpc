package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingWorker;

import model.cashiers.DAOCashiersTurns;
import model.cashiers.JBCashiersTurns;
import model.till.DAOTill;
import views.JDCloseSystem;
import views.JDCloseTurn;

public class CtrlCloseSystem {
	//Attributes
	private JDCloseSystem jdCloseSystem;
	private CtrlCloseTurn ctrlCloseTurn;
	private int id_cashier = 0;
	private JBCashiersTurns jbCashierTurn;
	private DAOTill daoTill;
	private DAOCashiersTurns daoCashiersTurns;
	private double finalBalance = 0.0;
	
	private CtrlMain ctrlSales;
	
	//Method that SETS the VIEW
	public void setJDCloseSystem(JDCloseSystem jdCloseSystem) {
		this.jdCloseSystem = jdCloseSystem;
	}
	//Method that SETS the Communication JDCloseSystem - JDCloseTurn
	public void setCtrlCloseTurn(CtrlCloseTurn ctrlCloseTurn) {
		this.ctrlCloseTurn = ctrlCloseTurn;
	}
	//Method that SETS the Communication JDCloseSystem - JFSells
	public void setCtrlSells(CtrlMain ctrlSales) {
		this.ctrlSales = ctrlSales;
	}
	//Method that SETS the DAOTill
	public void setDAOTill(DAOTill daoTill) {
		this.daoTill = daoTill;
	}
	//Method that SETS the DAOCashiersTurns
	public void setDAOCashiersTurns(DAOCashiersTurns daoCashiersTurns) {
		this.daoCashiersTurns = daoCashiersTurns;
	}
	
	//Method that SETS the Cashier DATA
	public void setCashierData(int id_cashier) {
		this.id_cashier = id_cashier;
	}
	
	//Method that SETS the Communication JDCloseSystem - JFSales
	public void setCtrlSales(CtrlMain ctrlSales) {
		this.ctrlSales = ctrlSales;
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//This has to be at the END
		jdCloseSystem.setVisible(true);
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JButtons
		jdCloseSystem.jbtnCloseTurn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				getData();
			}
		});
		jdCloseSystem.jbtnCloseAndLetOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				ctrlSales.closeJFSales();
				ctrlSales.stopTimer();
			}
		});
		jdCloseSystem.jbtnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdCloseSystem.dispose();
			}
		});
	}
	
	//Method that GETS the finalBalance
	private void getData() {
		SwingWorker<Void, Void> getTillFinalBalance = new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				finalBalance = daoTill.getActualBalance();
				jbCashierTurn = daoCashiersTurns.getInitTurnInfo("Abierto", id_cashier);
				jbCashierTurn.setIDCashier(id_cashier);
				
				return null;
			}

			@Override
			protected void done() {
				JDCloseTurn jdCloseTurn = new JDCloseTurn(jdCloseSystem, "CloseSystem", "Corte de Cajero");
				ctrlCloseTurn.setJDCloseTurn(jdCloseTurn);
				ctrlCloseTurn.setData(jbCashierTurn, finalBalance);
				ctrlCloseTurn.prepareView();
			}
		};
		//Executes the WORKER
		getTillFinalBalance.execute();
	}
}
