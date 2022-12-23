package model.providers;

public class JBProviders {
	private int id_provider;
	private String name = "";
	private String company_name = "";
	private String rfc = "";
	private String street = "";
	private String int_number = "";
	private String ext_number = "";
	private String colony = "";
	private String city = "";
	private String post_code = "";
	private String land_line1 = "";
	private String land_line2 = "";
	private String email = "";
	private String comentary = "";

	public int getIDProvider() {
		return id_provider;
	}
	public void setIDProvider(int id_provider) {
		this.id_provider = id_provider;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCompanyName() {
		return company_name;
	}
	public void setCompanyName(String company_name) {
		this.company_name = company_name;
	}
	
	public String getIRS() {
		return rfc;
	}
	public void setIRS(String rfc) {
		this.rfc = rfc;
	}
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getIntNumber() {
		return int_number;
	}
	public void setIntNumber(String int_number) {
		this.int_number = int_number;
	}
	
	public String getExtNumber() {
		return ext_number;
	}
	public void setExtNumber(String ext_number) {
		this.ext_number = ext_number;
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
	
	public String getLandLine1() {
		return land_line1;
	}
	public void setLandLine1(String land_line1) {
		this.land_line1 = land_line1;
	}
	
	public String getLandLine2() {
		return land_line2;
	}
	public void setLandLine2(String land_line2) {
		this.land_line2 = land_line2;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getComentary() {
		return comentary;
	}
	public void setComentary(String comentary) {
		this.comentary = comentary;
	}
}
