package model.cashiers;

public class JBCashiers {
	//Attributes
	private int id_cashier = 0;
	private String names = "";
	private String first_name = "";
	private String last_name = "";
	private String land_line = "";
	private String cel_phone = "";
	private String email = "";
	private int id_login = 0;
	private String username = "";

	public int getIDCashier() {
		return id_cashier;
	}
	public void setIDCashier(int id_cashier) {
		this.id_cashier = id_cashier;
	}
	
	public String getNames() {
		return names;
	}
	public void setNames(String name) {
		this.names = name;
	}
	
	public String getFirstName() {
		return first_name;
	}
	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}
	
	public String getLastName() {
		return last_name;
	}
	public void setLastName(String last_name) {
		this.last_name = last_name;
	}
	
	public String getLandLine() {
		return land_line;
	}
	public void setLandLine(String land_line) {
		this.land_line = land_line;
	}
	
	public String getCelPhone() {
		return cel_phone;
	}
	public void setCelPhone(String cel_phone) {
		this.cel_phone = cel_phone;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getIDCashierLogin() {
		return id_login;
	}
	public void setIDLogin(int id_login) {
		this.id_login = id_login;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
