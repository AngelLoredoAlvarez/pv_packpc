package model.sales;

public class JBSales {
	//Attributes
	private int id_sale = 0;
	private String date = "";
	private String time = "";
	private String type_sale = "";
	private double total = 0.0;
	private String status = "";
	private int id_client = 0;
	private String client = "";
	private int id_cashier = 0;
	private String cashier = "";

	public int getIDSell() {
		return id_sale;
	}
	public void setIDSell(int id_sale) {
		this.id_sale = id_sale;
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
	
	public String getTypeSell() {
		return type_sale;
	}
	public void setTypeSell(String type_sale) {
		this.type_sale = type_sale;
	}
	
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getIDClient() {
		return id_client;
	}
	public void setIDClient(int id_client) {
		this.id_client = id_client;
	}
	
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	
	public int getIDCashier() {
		return id_cashier;
	}
	public void setIDCashier(int id_cashier) {
		this.id_cashier = id_cashier;
	}
	
	public String getCashier() {
		return cashier;
	}
	public void setCashier(String cashier) {
		this.cashier = cashier;
	}
}
