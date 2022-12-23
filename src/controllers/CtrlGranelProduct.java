package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.inventories.JBInventoriesProductService;
import views.JDGranelProduct;

public class CtrlGranelProduct {
	//Attributes
	private JDGranelProduct jdGranelProduct;
	private CtrlMain ctrlSales;
	private double getQuantity = 0.0, getPrice = 0.0, salePrice = 0.0, limit = 0.0;
	private DecimalFormat dfDecimals;
	private String typeMessage = "", message = "";
	
	//Method that SETS the VIEW
	public void setJDGranelSells(JDGranelProduct jdGranelProduct) {
		this.jdGranelProduct = jdGranelProduct;
	}
	
	//Method that SETS the Communication JDGranelProduct - JFSales
	public void setCtrlSales(CtrlMain ctrlSales) {
		this.ctrlSales = ctrlSales;
	}
	
	//Method that SETS the DATA on the GUI
	public void setData(JBInventoriesProductService jbProductService, double limit) {
		salePrice = Double.valueOf(jbProductService.getSellprice());
		jdGranelProduct.jlblProduct.setText(jbProductService.getDescription());
		this.limit = limit;
		dfDecimals = new DecimalFormat("#,###,##0.00");
		jdGranelProduct.jlblUnitaryPrice.setText(dfDecimals.format(jbProductService.getSellprice()));
	}
	
	//Method that PREPARES the VIEW
	public void prepareView() {
		this.setListeners();
		//This has to be at the END
		jdGranelProduct.setVisible(true);
	}
	
	//Method that SETS the DATA when the USER it's about to modify a Granel Product
	public void setDataToRemove(String sendQuantity, String sendPrice) {
		jdGranelProduct.jspQuantity.setValue(Double.valueOf(sendQuantity));
		jdGranelProduct.jspPrice.setValue(Double.valueOf(sendPrice));
	}
	
	//Method that SETS the Corresponding Listeners 
	private void setListeners() {
		//JSpinners
		jdGranelProduct.jspQuantity.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				convertToPrice();
			}
		});
		jdGranelProduct.jspneQuantity.getTextField().getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent de) {}
			@Override
			public void removeUpdate(DocumentEvent de) {}
			@Override
			public void changedUpdate(DocumentEvent de) {
				convertToPrice();
			}
		});
		
		jdGranelProduct.jspPrice.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				convertToQuantity();
			}
		});
		jdGranelProduct.jspnePrice.getTextField().getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent de) {}
			@Override
			public void removeUpdate(DocumentEvent de) {}
			@Override
			public void changedUpdate(DocumentEvent de) {
				convertToQuantity();
			}
		});
		
		//JButtons
		jdGranelProduct.jbtnAccept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if(jdGranelProduct.action.equals("Quitar Producto a Granel")) {
							jdGranelProduct.dispose();
						} else {
							ctrlSales.addGranelProductToList(getQuantity, getPrice);
							jdGranelProduct.dispose();
						}
					}
				});
			}
		});
		
		jdGranelProduct.jbtnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				jdGranelProduct.dispose();
			}
		});
	}
	
	//Method that Converts the Quantity to Price
	private void convertToPrice() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				getQuantity = (double) jdGranelProduct.jspQuantity.getValue();
				getPrice = getQuantity * salePrice;
				jdGranelProduct.jspPrice.setValue(getQuantity * salePrice);
			}
		});
	}
	
	//Method that Converts the Price to Quantity
	private void convertToQuantity() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				getPrice = (double) jdGranelProduct.jspPrice.getValue();
				getQuantity = getPrice / salePrice;
				if(getQuantity > limit) {
					typeMessage = "limitReach";
					message = "Ya no quedan más de este producto en existencia";
					jdGranelProduct.setJOPMessages(typeMessage, message);
					jdGranelProduct.jspQuantity.setValue(limit);
					convertToPrice();
				} else {
					jdGranelProduct.jspQuantity.setValue(getPrice / salePrice);
				}
			}
		});
	}
}
