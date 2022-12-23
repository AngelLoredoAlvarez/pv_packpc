package model.cashiers;

public class JBCashiersPrivileges {
	//Attributes
	private int id_privilige;
	private String privilige = "";
	private int id_login;
	
	public int getIDPrivilige() {
		return id_privilige;
	}
	public void setIDPrivilige(int id_privilige) {
		this.id_privilige = id_privilige;
	}
	
	public String getPrivilige() {
		return privilige;
	}
	public void setPrivilige(String privilige) {
		this.privilige = privilige;
	}
	
	public int getIDCashierLogin() {
		return id_login;
	}
	public void setIDCashierLogin(int id_login) {
		this.id_login = id_login;
	}
}
