package model.invoices;

public class JBInvoicesDetails {
	//Attributes
	private int id_detail_invoice = 0;
	private int id_product_service = 0;
	private double quantity = 0.0;
	private double importe = 0.0;
	private int id_invoice = 0;
	
	public int getIDDetailInvoice() {
		return id_detail_invoice;
	}
	public void setIDDetailInvoice(int id_detail_invoice) {
		this.id_detail_invoice = id_detail_invoice;
	}
	
	public int getIDProductService() {
		return id_product_service;
	}
	public void setIDProductService(int id_product_service) {
		this.id_product_service = id_product_service;
	}
	
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public double getImporte() {
		return importe;
	}
	public void setImporte(double importe) {
		this.importe = importe;
	}
	
	public int getIDInvoice() {
		return id_invoice;
	}
	public void setIDInvoice(int id_invoice) {
		this.id_invoice = id_invoice;
	}		
}