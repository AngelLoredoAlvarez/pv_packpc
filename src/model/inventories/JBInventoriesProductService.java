package model.inventories;

public class JBInventoriesProductService {
	private int id_product = 0;
	private String barcode = "";
	private String description = "";
	private String unitmeasurement = "";
	private String department = "";
	private int id_department = 0;
	private int id_provider = 0;
	private String provider = "";
	private double iva = 0.0;
	private double buyprice = 0.0;
	private double sellprice = 0.0;
	private double stock = 0.0;
	private double minstock = 0.0;
	
	public int getIDProductService() {
		return id_product;
	}
	public void setIDProductService(int id_product) {
		this.id_product = id_product;
	}
	
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getUnitmeasurement() {
		return unitmeasurement;
	}
	public void setUnitmeasurement(String unitMeasurement) {
		this.unitmeasurement = unitMeasurement;
	}
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public int getIDDepartment() {
		return id_department;
	}
	public void setIDDepartment(int id_department) {
		this.id_department = id_department;
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
	
	public double getIva() {
		return iva;
	}
	public void setIva(double iva) {
		this.iva = iva;
	}
	
	public double getBuyprice() {
		return buyprice;
	}
	public void setBuyprice(double buyprice) {
		this.buyprice = buyprice;
	}
	
	public double getSellprice() {
		return sellprice;
	}
	public void setSaleprice(double sellprice) {
		this.sellprice = sellprice;
	}
	
	public double getStock() {
		return stock;
	}
	public void setStock(double stock) {
		this.stock = stock;
	}
	
	public double getMinstock() {
		return minstock;
	}
	public void setMinstock(double minstock) {
		this.minstock = minstock;
	}
}
