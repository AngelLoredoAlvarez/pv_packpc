package model.cashiers;

public class JBCashiersLogin {
	//Attributes
	private int id_login = 0;
	private String username = "";
	private String password = "";
	private String status = "";

	public int getIDLogin() {
		return id_login;
	}
	public void setIDCashierLogin(int id_login) {
		this.id_login = id_login;
	}
	
	public String getUserName() {
		return username;
	}
	public void setUserName(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
