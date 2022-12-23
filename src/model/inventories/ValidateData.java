package model.inventories;

public class ValidateData {
	//Method that CHECKS if all the FIELDS are complete
	public String validateFields(JBInventoriesProductService jbProductService) {
		String field = "allOK";
		
		if(jbProductService.getBarcode().equals("")) {
			field = "barcode";
		} else if(jbProductService.getDescription().equals("")) {
			field = "description";
		} else if(jbProductService.getSellprice() == 0.0) {
			field = "sellprice";
		} else if(!jbProductService.getUnitmeasurement().equals("Servicio")) {
			if(jbProductService.getBuyprice() == 0.0) {
				field = "buyprice";
			} else if(jbProductService.getStock() == 0.0) {
				field = "stock";
			} else if(jbProductService.getMinstock() == 0.0) {
				field = "minstock";
			}
		}
		
		return field;
	}
	
	//Method that CHECKS if the BarCode is already registered
	public boolean checkProductBarCode(String barcode, DAOInventoriesProductService daoInventoriesProductService) {
		boolean barcodeExist;
		
		JBInventoriesProductService jbProductGetted = new JBInventoriesProductService();
		jbProductGetted = daoInventoriesProductService.getProductByBarCode(barcode);
		
		if(!jbProductGetted.getBarcode().equals(""))
			barcodeExist = true;
		else
			barcodeExist = false;
		
		return barcodeExist;
	}
	
	//Method that CHECKS if the Description is already registered
	public boolean checkProductDescription(String description, DAOInventoriesProductService daoInventoriesProductService) {
		boolean descriptionExist;
		
		JBInventoriesProductService jbProductGetted = new JBInventoriesProductService();
		jbProductGetted = daoInventoriesProductService.getProductByDescription(description);
		
		if(!jbProductGetted.getDescription().equals(""))
			descriptionExist = true;
		else
			descriptionExist = false;
		
		return descriptionExist;
	}
	
	//Method that CHECKS if the Buy Price is BIGGER that the Sell Price
	public boolean checkPrices(double buyPrice, double sellPrice) {
		boolean biggerPrice;
		
		if(buyPrice >= sellPrice)
			biggerPrice = true;
		else
			biggerPrice = false;
		
		return biggerPrice;
	}
	
	//Method that CHECKS if the MinStock is BIGGER than the STOCK
	public boolean checkStocks(double stock, double minStock) {
		boolean biggerMinStock;
		
		if(stock <= minStock)
			biggerMinStock = true;
		else 
			biggerMinStock = false;
		
		return biggerMinStock;
	}
}
