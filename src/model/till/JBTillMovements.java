package model.till;

public class JBTillMovements {
	private String typeMovement = "";
	private String date = "";
	private String time = "";
	private double balance = 0.0;
	private String comentcn = "";
	private int id_cashier = 0;
	private String cashier = "";
	private int id_till = 0;

	public String getTypeMovement() {
		return typeMovement;
	}
	public void setTypeMovement(String typeMovement) {
		this.typeMovement = typeMovement;
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
	
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public String getComentCN() {
		return comentcn;
	}
	public void setComentCN(String comentcn) {
		this.comentcn = comentcn;
	}
	
	public int getIDCashier() {
		return id_cashier;
	}
	public void setIDCashier(int id_user) {
		this.id_cashier = id_user;
	}
	
	public String getCashier() {
		return cashier;
	}
	public void setCashier(String user) {
		this.cashier = user;
	}
	
	public int getIDTill() {
		return id_till;
	}
	public void setIDTill(int id_till) {
		this.id_till = id_till;
	}
}
