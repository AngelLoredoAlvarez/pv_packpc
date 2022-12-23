package model.till;

public class JBTillDetails {
	//Attributes
	private int id_detail = 0;
	private String date = "";
	private String time = "";
	private int id_till = 0;
	private String operation = "";
	private int id_user = 0;

	public int getIDDetail() {
		return id_detail;
	}
	public void setIDDetail(int id_detail) {
		this.id_detail = id_detail;
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
	
	public int getIDTill() {
		return id_till;
	}
	public void setIDTill(int id_till) {
		this.id_till = id_till;
	}
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public int getIDUser() {
		return id_user;
	}
	public void setIDUser(int id_user) {
		this.id_user = id_user;
	}
}
