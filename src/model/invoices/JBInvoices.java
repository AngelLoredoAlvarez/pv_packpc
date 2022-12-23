package model.invoices;

public class JBInvoices {
	//Attributes
	private int id_invoice = 0;
	private String date = "";
	private String time = "";
	private String uuid = "";
	private String certificate_cfdi_number = "";
	private int id_business_information = 0;
	private int id_client = 0;
	private String payment_method = "";
	private String way_to_pay = "";
	private double subTotal = 0.0;
	private double irs = 0.0;
	private double total = 0.0;
	private String cfdi_sail = "";
	private String sat_sail = "";
	private String original_cfdi_string = "";
	private String original_sat_string = "";
	private String certificate_sat_number = "";
	
	public int getIDInvoice() {
		return id_invoice;
	}
	public void setIDInvoice(int id_invoice) {
		this.id_invoice = id_invoice;
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
	
	public String getUUID() {
		return uuid;
	}
	public void setUUID(String uuid) {
		this.uuid = uuid;
	}
	
	public String getCertificateCFDINumber() {
		return certificate_cfdi_number;
	}
	public void setCertificateCFDINumber(String certificate_cfdi_number) {
		this.certificate_cfdi_number = certificate_cfdi_number;
	}
	
	public int getIDBusinessInformation() {
		return id_business_information;
	}
	public void setIDBusinessInformation(int id_business_information) {
		this.id_business_information = id_business_information;
	}
	
	public int getIDClient() {
		return id_client;
	}
	public void setIDClient(int id_client) {
		this.id_client = id_client;
	}
	
	public String getPaymentMethod() {
		return payment_method;
	}
	public void setPaymentMethod(String payment_method) {
		this.payment_method = payment_method;
	}
	
	public String getWayToPay() {
		return way_to_pay;
	}
	public void setWayToPay(String way_to_pay) {
		this.way_to_pay = way_to_pay;
	}
	
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
	public double getIRS() {
		return irs;
	}
	public void setIRS(double irs) {
		this.irs = irs;
	}
	
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	public String getCFDISail() {
		return cfdi_sail;
	}
	public void setCFDISail(String cfdi_sail) {
		this.cfdi_sail = cfdi_sail;
	}
	
	public String getSATSail() {
		return sat_sail;
	}
	public void setSATSail(String sat_sail) {
		this.sat_sail = sat_sail;
	}
	
	public String getOriginalCFDIString() {
		return original_cfdi_string;
	}
	public void setOriginalCFDIString(String original_cfdi_string) {
		this.original_cfdi_string = original_cfdi_string;
	}
	
	public String getOriginalSATString() {
		return original_sat_string;
	}
	public void setOriginalSATString(String original_sat_string) {
		this.original_sat_string = original_sat_string;
	}
	
	public String getCertificateSATNumber() {
		return certificate_sat_number;
	}
	public void setCertificateSATNumber(String certificate_sat_number) {
		this.certificate_sat_number = certificate_sat_number;
	}
}