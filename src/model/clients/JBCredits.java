package model.clients;

public class JBCredits {
	private int id_credit;
	private String opening_date = "";
	private String opening_time = "";
	private String credit_limit = "";
	private String current_balance = "";
	private int id_client = 0;
	private String client = "";

	public int getIDCredit() {
		return id_credit;
	}
	public void setIDCredit(int id_credit) {
		this.id_credit = id_credit;
	}
	
	public String getOpeningDate() {
		return opening_date;
	}
	public void setOpeningDate(String opening_date) {
		this.opening_date = opening_date;
	}
	
	public String getOpeningTime() {
		return opening_time;
	}
	public void setOpeningTime(String opening_time) {
		this.opening_time = opening_time;
	}
	
	public String getCreditLimit() {
		return credit_limit;
	}
	public void setCreditLimit(String credit_limit) {
		this.credit_limit = credit_limit;
	}
	
	public String getCurrentBalance() {
		return current_balance;
	}
	public void setCurrentBalance(String current_balance) {
		this.current_balance = current_balance;
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
}
