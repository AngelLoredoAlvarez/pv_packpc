package model.inventories;

public class JBInventoriesMovements {
	//Attributes
	private String date = "";
	private String time = "";
	private int id_product = 0;
	private String description = "";
	private String unit_measurement = "";
	private double existed = 0.0;
	private String movement = "";
	private double quantity = 0.0;
	private int id_cashier = 0;
	private String cashier = "";

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public int getIdProduct() {
		return id_product;
	}
	public void setIdProduct(int id_product) {
		this.id_product = id_product;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String product_description) {
		this.description = product_description;
	}
	
	public String getUnitMeasurement() {
		return unit_measurement;
	}
	public void setUnitMeasurement(String unit_measurement) {
		this.unit_measurement = unit_measurement;
	}
	
	public double getExisted() {
		return existed;
	}
	public void setExisted(double existed) {
		this.existed = existed;
	}
	
	public String getMovement() {
		return movement;
	}
	public void setMovement(String type_movement) {
		this.movement = type_movement;
	}
	
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public int getIdCashier() {
		return id_cashier;
	}
	public void setIdCashier(int id_cashier) {
		this.id_cashier = id_cashier;
	}
	
	public String getCashier() {
		return cashier;
	}
	public void setCashier(String cashier) {
		this.cashier = cashier;
	}
}
