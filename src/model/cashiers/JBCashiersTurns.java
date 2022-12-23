package model.cashiers;

public class JBCashiersTurns {
	//Attributes
	private int id_turn = 0;
	private String start_date = "";
	private String start_time = "";
	private String end_date = "";
	private String end_time = "";
	private int id_cashier = 0;
	private String status = "";

	public int getIDTurn() {
		return id_turn;
	}
	public void setIDTurn(int id_turn) {
		this.id_turn = id_turn;
	}

	public String getStartDate() {
		return start_date;
	}
	public void setStartDate(String start_date) {
		this.start_date = start_date;
	}
	
	public String getStartTime() {
		return start_time;
	}
	public void setStartTime(String start_time) {
		this.start_time = start_time;
	}
	
	public String getEndDate() {
		return end_date;
	}
	public void setEndDate(String end_date) {
		this.end_date = end_date;
	}
	
	public String getEndTime() {
		return end_time;
	}
	public void setEndTime(String end_time) {
		this.end_time = end_time;
	}
	
	public int getIDCashier() {
		return id_cashier;
	}
	public void setIDCashier(int id_cashier) {
		this.id_cashier = id_cashier;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
