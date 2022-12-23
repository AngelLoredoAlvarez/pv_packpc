package model.buys;

public class JBDetailsBuys {
	private int id_detail = 0;
	private int id_product = 0;
	private String description = "";
	private double actualPrice = 0.0;
	private String quantity = "";
	private double importe = 0.0;
	private int id_buy = 0;
	
	public int getIDDetail() {
		return id_detail;
	}
	public void setIDDetail(int id_detail) {
		this.id_detail = id_detail;
	}
	
	public int getIDProduct() {
		return id_product;
	}
	public void setIDProduct(int id_product) {
		this.id_product = id_product;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(double actualPrice) {
		this.actualPrice = actualPrice;
	}
	
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public double getImporte() {
		return importe;
	}
	public void setImporte(double importe) {
		this.importe = importe;
	}
	
	public int getIDBuy() {
		return id_buy;
	}
	public void setIDBuy(int id_buy) {
		this.id_buy = id_buy;
	}
}
