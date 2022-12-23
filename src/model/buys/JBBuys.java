package model.buys;

public class JBBuys {
	private int id_buy = 0;
	private String date = "";
	private String time = "";
	private double total = 0.0;
	private int id_provider = 0;
	private String provider = "";
	private int id_cashier = 0;
	private String cashier = "";
	
	public int getIDBuy() {
		return id_buy;
	}
	public void setIDBuy(int id_buy) {
		this.id_buy = id_buy;
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
	
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	public int getIDProvider() {
		return id_provider;
	}
	public void setIDProvider(int id_provider) {
		this.id_provider = id_provider;
	}
	
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
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
