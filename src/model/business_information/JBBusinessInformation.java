package model.business_information;

public class JBBusinessInformation {
	private int id_business_information = 0;
	private String name_business = "";
	private String company_name = "";
	private String irs = "";
	private String street = "";
	private String ext_number = "";
	private String int_number = "";
	private String colony = "";
	private String city = "";
	private String post_code = "";
	private String email = "";
	private String certificate_route = "";
	private String key_route = "";
	private String pass_key = "";
	
	public int getIDBusinessInformation() {
		return id_business_information;
	}
	public void setIDBusinessInformation(int id_business_information) {
		this.id_business_information = id_business_information;
	}
	
	public String getNameBusiness() {
		return name_business;
	}
	public void setNameBusiness(String name_business) {
		this.name_business = name_business;
	}
	
	public String getCompanyName() {
		return company_name;
	}
	public void setCompanyName(String company_name) {
		this.company_name = company_name;
	}
	
	public String getIRS() {
		return irs;
	}
	public void setIRS(String irs) {
		this.irs = irs;
	}
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getExtNumber() {
		return ext_number;
	}
	public void setExtNumber(String ext_number) {
		this.ext_number = ext_number;
	}
	
	public String getIntNumber() {
		return int_number;
	}
	public void setIntNumber(String int_number) {
		this.int_number = int_number;
	}
	
	public String getColony() {
		return colony;
	}
	public void setColony(String colony) {
		this.colony = colony;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getPostCode() {
		return post_code;
	}
	public void setPostCode(String post_code) {
		this.post_code = post_code;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCertificateRoute() {
		return certificate_route;
	}
	public void setCertificateRoute(String certificate_route) {
		this.certificate_route = certificate_route;
	}
	
	public String getPrivateKeyRoute() {
		return key_route;
	}
	public void setPrivateKeyRoute(String key_route) {
		this.key_route = key_route;
	}
	
	public String getPrivateKeyPass() {
		return pass_key;
	}
	public void setPassKey(String pass_key) {
		this.pass_key = pass_key;
	}
}
