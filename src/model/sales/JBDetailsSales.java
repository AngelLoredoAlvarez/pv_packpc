package model.sales;

public class JBDetailsSales {
	//Attributes
	private int id_detail = 0;
	private int id_product = 0;
	private String description = "";
	private String unit_measurement = "";
	private double sale_price = 0.0;
	private double quantity = 0.0;
	private double importPrice = 0.0;
	private int id_sale = 0;

	public int getIDDetail() {
		return id_detail;
	}
	public void setIDDetail(int id_detail) {
		this.id_detail = id_detail;
	}
	
	public int getIDProduct() {
		return id_product;
	}
	public void setIDProduct(int id_product) {
		this.id_product = id_product;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getUnitMeasurement() {
		return unit_measurement;
	}
	public void setUnitMeasurement(String unit_measurement) {
		this.unit_measurement = unit_measurement;
	}
	
	public double getSellPrice() {
		return sale_price;
	}
	public void setSellPrice(double sale_price) {
		this.sale_price = sale_price;
	}
	
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
	public double getImportPrice() {
		return importPrice;
	}
	public void setImportPrice(double importPrice) {
		this.importPrice = importPrice;
	}
	
	public int getIDSell() {
		return id_sale;
	}
	public void setIDSale(int id_sale) {
		this.id_sale = id_sale;
	}
}
