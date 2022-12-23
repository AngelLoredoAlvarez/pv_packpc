package model.invoices;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DBConnection;

public class DAOInvoicesDetails {
	//Attributes
	private PreparedStatement query = null;
	private ResultSet result = null;
	private String sql = "";
	private boolean state;
	
	//Method that SAVES the Detail of the Invoice
	public boolean saveDetail(JBInvoicesDetails jbInvoiceDetails) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
			       + "invoices_details("
			       + "id_product_service, "
			       + "quantity, "
			       + "import, "
			       + "id_invoice) "
			+ "VALUES(?, ?, ?, ?);";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, jbInvoiceDetails.getIDProductService());
			query.setDouble(2, jbInvoiceDetails.getQuantity());
			query.setDouble(3, jbInvoiceDetails.getImporte());
			query.setInt(4, jbInvoiceDetails.getIDInvoice());
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
}
