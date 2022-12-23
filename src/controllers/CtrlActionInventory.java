package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import model.inventories.DAOInventoriesMovements;
import model.inventories.DAOInventoriesProductService;
import model.inventories.JBInventoriesMovements;
import model.inventories.JBInventoriesProductService;
import views.JDActionInventory;

public class CtrlActionInventory {
	//Attributes
	private JDActionInventory jdActionInventory;
	private String barcode = "", unit_measurement = "", typeMovement = "", typeMessage = "", message = "";
	private int selectedRow = 0, id_product = 0, id_cashier = 0;
	private DAOInventoriesProductService daoInventoriesProductService;
	private DAOInventoriesMovements daoInventoriesMovements;
	private double actualStock = 0.0, quantity = 0.0,  newStock = 0.0;
	private DecimalFormat dfPiece, dfGranel;
	private CtrlConsultInventoriesServices ctrlInventories;
	private JBInventoriesProductService jbInventoriesProducts;
	private CtrlDoBuy ctrlBuys;
	
	//Method that SETS the VIEW
	public void setJDActionInventory(JDActionInventory jdActionInventory) {
		this.jdActionInventory = jdActionInventory;
	}
	//Method that SETS the DAOInventoriesProductService
	public void setDAOInventoriesProductService(DAOInventoriesProductService daoInventoriesProductService) {
		this.daoInventoriesProductService = daoInventoriesProductService;
	}
	//Method that SETS the DAOInventoriesMovemenmts
	public void setDAOInventoriesMovements(DAOInventoriesMovements daoInventoriesMovements) {
		this.daoInventoriesMovements = daoInventoriesMovements;
	}
	//Method that SETS the Communication between jdActionInventory - JDInventories
	public void setCtrlInventories(CtrlConsultInventoriesServices ctrlInventories) {
		this.ctrlInventories = ctrlInventories;
	}
	//Method that SETS the Communication JDActionInventory - JDBuys
	public void setCtrlBuys(CtrlDoBuy ctrlBuys) {
		this.ctrlBuys = ctrlBuys;
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//This has to be at the END
		jdActionInventory.setVisible(true);
	}
	
	//Method that SETS the DATA
	public void setData(JBInventoriesProductService jbInventoriesProducts, int selectedRow, int id_cashier) {
		this.barcode = jbInventoriesProducts.getBarcode();
		this.unit_measurement = jbInventoriesProducts.getUnitmeasurement();
		this.actualStock = jbInventoriesProducts.getStock();
		this.selectedRow = selectedRow;
		this.id_cashier = id_cashier;
		
		jdActionInventory.jlblDescription.setText(jbInventoriesProducts.getDescription());
		dfPiece = new DecimalFormat("#,###,##0");
		dfGranel = new DecimalFormat("#,###,##0.00");
		
		if(unit_measurement.equals("Pza"))
			jdActionInventory.jlblActualQuantity.setText(dfPiece.format(jbInventoriesProducts.getStock()).toString());
		else if(unit_measurement.equals("Granel"))
			jdActionInventory.jlblActualQuantity.setText(dfGranel.format(jbInventoriesProducts.getStock()).toString());
	}
	
	//Method that SETS the DATA for a Buy
	public void setDataForBuy(JBInventoriesProductService jbInventoriesProducts) {
		this.jbInventoriesProducts = jbInventoriesProducts;
		this.unit_measurement = jbInventoriesProducts.getUnitmeasurement();
		
		jdActionInventory.jlblDescription.setText(jbInventoriesProducts.getDescription());
		dfPiece = new DecimalFormat("#,###,##0");
		dfGranel = new DecimalFormat("#,###,##0.00");
		
		if(unit_measurement.equals("Pza"))
			jdActionInventory.jlblActualQuantity.setText(dfPiece.format(jbInventoriesProducts.getStock()).toString());
		else if(unit_measurement.equals("Granel"))
			jdActionInventory.jlblActualQuantity.setText(dfGranel.format(jbInventoriesProducts.getStock()).toString());
	}
	
	//Method that SETS the Listeners
	private void setListeners() {
		//JButtons
		jdActionInventory.jbtnAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(jdActionInventory.father.equals("JDBuys")) {
					setQuantities();
				} else {
					updateStock();
				}
			}
		});
		jdActionInventory.jbtnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdActionInventory.dispose();
			}
		});
	}
	
	//Method that UPDATES the STOCK
	private void updateStock() {
		SwingWorker<Boolean, Void> updateStock = new SwingWorker<Boolean, Void>() {
			@Override
			protected Boolean doInBackground() throws Exception {
				boolean allOK = false;
				boolean update = false;
				boolean saveMovement = false;
				
				id_product = daoInventoriesProductService.getIDProductService(barcode);
				
				if(jdActionInventory.name.equals("Agregar a Inventario")) {
					typeMovement = "Entrada";
					quantity = Double.parseDouble(jdActionInventory.jspQuantity.getValue().toString());
					newStock = quantity + actualStock;
				}
				else if(jdActionInventory.name.equals("Ajustar Inventario")) {
					typeMovement = "Ajuste";
					newStock = Double.parseDouble(jdActionInventory.jspQuantity.getValue().toString());
					quantity = newStock;
				}
				
				update = daoInventoriesProductService.updateStock(id_product, newStock);
				saveMovement = saveMovement();
				
				if(update && saveMovement)
					allOK = true;
					
				return allOK;
			}

			@Override
			protected void done() {
				try {
					boolean result = get();
					if(result && jdActionInventory.name.equals("Agregar a Inventario")) {
						typeMessage = "addToInventory";
						message = "Se han agregado " + quantity + " Unidades al Inventario";
						jdActionInventory.setJOPMessages(typeMessage, message);
					} else if(result && jdActionInventory.name.equals("Ajustar Inventario")) {
						typeMessage = "adjustInventory";
						message = "Se ha ajustado el Inventario a " + newStock + " Unidades";
						jdActionInventory.setJOPMessages(typeMessage, message);
					}
					
					if(unit_measurement.equals("Pza"))
						ctrlInventories.jdConsultInventoriesServices.dtmInventoriesServices.setValueAt(dfPiece.format(newStock), selectedRow, 6);
					else if(unit_measurement.equals("Granel")) 
						ctrlInventories.jdConsultInventoriesServices.dtmInventoriesServices.setValueAt(dfGranel.format(newStock), selectedRow, 6);
					
					ctrlInventories.setTotals();
					jdActionInventory.dispose();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Executes the WORKER
		updateStock.execute();
	}
	
	//Method that SAVES the Movement
	private boolean saveMovement() {
		boolean saveMovement = false;
		
		Date date = new Date();
		SimpleDateFormat sdfDate = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
		SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm a");
		JBInventoriesMovements jbInventoryMovement = new JBInventoriesMovements();
		jbInventoryMovement.setDate(sdfDate.format(date).toString());
		jbInventoryMovement.setTime(sdfTime.format(date).toString());
		jbInventoryMovement.setIdProduct(id_product);
		jbInventoryMovement.setExisted(actualStock);
		jbInventoryMovement.setMovement(typeMovement);
		jbInventoryMovement.setQuantity(quantity);
		jbInventoryMovement.setIdCashier(id_cashier);
		saveMovement = daoInventoriesMovements.saveInventoryMovement(jbInventoryMovement);
		
		return saveMovement;
	}
	
	//Method that SETS the Quantities on the JTable
	private void setQuantities() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				double selectedQuantity = Double.parseDouble(jdActionInventory.jspQuantity.getValue().toString());
				double importe = selectedQuantity * jbInventoriesProducts.getBuyprice();
				ctrlBuys.addProductToList(jbInventoriesProducts, selectedQuantity, importe);
				jdActionInventory.dispose();
			}
		});
	}
}
