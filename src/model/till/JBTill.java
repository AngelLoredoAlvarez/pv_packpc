package model.till;

public class JBTill {
	//Attributes
	private int id_till;
	private String beginnin_balance = "";
	private String final_balance = "";
	private String status = "";

	public int getIDTill() {
		return id_till;
	}
	public void setIDTill(int id_till) {
		this.id_till = id_till;
	}
	
	public String getBeginninBalance() {
		return beginnin_balance;
	}
	public void setBeginninBalance(String beginnin_balance) {
		this.beginnin_balance = beginnin_balance;
	}
	
	public String getFinalBalance() {
		return final_balance;
	}
	public void setFinalBalance(String final_balance) {
		this.final_balance = final_balance;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
