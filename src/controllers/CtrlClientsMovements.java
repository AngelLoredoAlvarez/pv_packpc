package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.RowFilter;
import javax.swing.SwingWorker;
import javax.swing.table.TableModel;

import model.clients.JBClientsMovements;
import views.JDClientsMovements;

public class CtrlClientsMovements {
	//Attributes
	private JDClientsMovements jdClientsMovements;
	private DecimalFormat dfQuantity;
	private ArrayList<JBClientsMovements> alClientsMovements;
	private int limit = 0;
	
	//Method that SETS the VIEW
	public void setJDClientsMovements(JDClientsMovements jdClientsMovements) {
		this.jdClientsMovements = jdClientsMovements;
	}
	
	//Method that SETS the LIST
	public void setListMovements(ArrayList<JBClientsMovements> alClientsMovements) {
		dfQuantity = new DecimalFormat("#,###,##0.00");
		this.alClientsMovements = alClientsMovements;
		jdClientsMovements.jcbWhatToShow.setSelectedIndex(0);
		jdClientsMovements.jcbQuantities.setSelectedIndex(1);
		filterJTable();
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//This has to be at the END
		jdClientsMovements.setVisible(true);
	}
	
	//Method that SETS the LISTENERS
	private void setListeners() {
		//JComboBox
		jdClientsMovements.jcbWhatToShow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				filterJTable();
			}
		});
		jdClientsMovements.jcbQuantities.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				filterJTable();
			}
		});
		
		//JButtons
//		jdClientsMovements.jbtnPrint.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent ae) {
//				
//			}
//		});
		jdClientsMovements.jbtnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdClientsMovements.dispose();
			}
		});
	}
	
	//Method that FILTERS the JTable
	private void filterJTable() {
		SwingWorker<ArrayList<JBClientsMovements>, Void> getClientMovements = new SwingWorker<ArrayList<JBClientsMovements>, Void>() {
			@Override
			protected ArrayList<JBClientsMovements> doInBackground() throws Exception {
				//Get PARAMETERS
				String whatToShow = "", movement = "", quantity = "";
				ArrayList<JBClientsMovements> alMovementsToShow = new ArrayList<JBClientsMovements>();
				
				whatToShow = jdClientsMovements.jcbWhatToShow.getSelectedItem().toString();
				if(whatToShow.equals("Todos"))
					movement = "";
				else if(whatToShow.equals("Abonos"))
					movement = "Abono";
				else if(whatToShow.equals("Compras"))
					movement = "Compra";
				
				quantity = jdClientsMovements.jcbQuantities.getSelectedItem().toString();
				if(quantity.equals("Todo"))
					limit = alClientsMovements.size();
				else
					limit = Integer.parseInt(jdClientsMovements.jcbQuantities.getSelectedItem().toString());
				
				if(movement.equals("")) {
					alMovementsToShow = alClientsMovements;
				} else {
					for(int gm = 0; gm < alClientsMovements.size(); gm++) {
						JBClientsMovements jbClientMovement = alClientsMovements.get(gm);
						if(jbClientMovement.getTypeMovement().equals(movement))
							alMovementsToShow.add(jbClientMovement);
					}
				}
				
				//Filter JTable
				RowFilter<TableModel, Object> rf = null;
				rf = RowFilter.regexFilter("(?i)" + movement, 2);
				jdClientsMovements.trsSorter.setRowFilter(rf);
				
				return alMovementsToShow;
			}

			@Override
			protected void done() {
				try {
					ArrayList<JBClientsMovements> retrivedMovements = get();
					
					//Remove Previous Rows
					if(jdClientsMovements.dtmClientsMovements.getRowCount() > 0) {
						for(int rr = jdClientsMovements.dtmClientsMovements.getRowCount() - 1; rr >= 0; rr--) {
							jdClientsMovements.dtmClientsMovements.removeRow(rr);
						}
					}
					
					//Add NEW Rows
					String simbol = "";
					for(int ar = 0; ar < limit; ar++) {
						JBClientsMovements jbClientsMovements = retrivedMovements.get(ar);
						if(jbClientsMovements.getTypeMovement().equals("Abono"))
							simbol = "+";
						else if(jbClientsMovements.getTypeMovement().equals("Compra"))
							simbol = "-";
						
						jdClientsMovements.dtmClientsMovements.addRow(new Object[] {
							jbClientsMovements.getDate(), 
							jbClientsMovements.getTime(),
							jbClientsMovements.getTypeMovement(),
							simbol + " $" + dfQuantity.format(jbClientsMovements.getQuantity())
						});
						
						if(ar == retrivedMovements.size() - 1)
							break;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the WORKER
		getClientMovements.execute();
	}
}
