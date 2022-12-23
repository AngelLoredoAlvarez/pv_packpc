package model.clients;

public class JBClientsMovements {
	//Attributes
	private int id_history = 0;
	private String date = "";
	private String time = "";
	private String type_movement = "";
	private double quantity = 0.0;
	private int id_client = 0;
	private int id_cashier = 0;
	
	public int getIDHistory() {
		return id_history;
	}
	public void setIDHistory(int id_payment) {
		this.id_history = id_payment;
	}
	
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
	
	public String getTypeMovement() {
		return type_movement;
	}
	public void setTypeMovement(String type_movement) {
		this.type_movement = type_movement;
	}
	
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public int getIDClient() {
		return id_client;
	}
	public void setIDClient(int id_client) {
		this.id_client = id_client;
	}
	
	public int getIDCashier() {
		return id_cashier;
	}
	public void setIDCashier(int id_cashier) {
		this.id_cashier = id_cashier;
	}
}
