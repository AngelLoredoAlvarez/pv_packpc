package model.invoices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DBConnection;

public class DAOInvoices {
	//Attributes
	private PreparedStatement query = null;
	private ResultSet result = null;
	private String sql = "";
	private boolean state;
	
	//Method that SAVES the Invoice
	public boolean saveInvoice(JBInvoices jbInvoice) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
			       + "invoices_invoice("
			            + "date, "
			            + "time, "
			            + "uuid, "
			            + "certificate_cfdi_number, "
			            + "id_business_information, "
			            + "id_client, "
			            + "payment_method, "
			            + "way_to_pay, "
			            + "sub_total, "
			            + "irs, "
			            + "total, "
			            + "cfdi_sail, "
			            + "sat_sail, "
			            + "original_cfdi_string, "
			            + "original_sat_string, "
			            + "certificate_sat_number) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbInvoice.getDate());
			query.setString(2, jbInvoice.getTime());
			query.setString(3, jbInvoice.getUUID());
			query.setString(4, jbInvoice.getCertificateCFDINumber());
			query.setInt(5, jbInvoice.getIDBusinessInformation());
			query.setInt(6, jbInvoice.getIDClient());
			query.setString(7, jbInvoice.getPaymentMethod());
			query.setString(8, jbInvoice.getWayToPay());
			query.setDouble(9, jbInvoice.getSubTotal());
			query.setDouble(10, jbInvoice.getIRS());
			query.setDouble(11, jbInvoice.getTotal());
			query.setString(12, jbInvoice.getCFDISail());
			query.setString(13, jbInvoice.getSATSail());
			query.setString(14, jbInvoice.getOriginalCFDIString());
			query.setString(15, jbInvoice.getOriginalSATString());
			query.setString(16, jbInvoice.getCertificateSATNumber());
			query.executeUpdate();
			state = true;
			dbconn.closeConnection();
			sql = "";
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
			state = false;
		}
				
		return state;
	}	
	
	//Method that RETRIVES the ID from an Invoice according it's UUID
	public int getIDInvoiceByUUID(String uuid) {
		int id_invoice = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_invoice "
			  + "FROM "
			       + "invoices_invoice "
			  + "WHERE "
			       + "uuid = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, uuid);
			result = query.executeQuery();
			while(result.next()) {
				id_invoice = result.getInt("id_invoice");
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id_invoice;
	}
}