package model.till;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.DBConnection;

public class DAOTillDetails {
	//Attributes
	private PreparedStatement query = null;
	private String sql = "";
	private boolean status;
	
	public boolean saveDetailTill(JBTillDetails jbTillDetail) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
		           + "tills_details("
		                + "date, "
		                + "time, "
		                + "id_till, "
		                + "id_cashier, "
		                + "operation) "
		       + "VALUES(?, ?, ?, ?, ?);";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbTillDetail.getDate());
			query.setString(2, jbTillDetail.getTime());
			query.setInt(3, jbTillDetail.getIDTill());
			query.setInt(4, jbTillDetail.getIDUser());
			query.setString(5, jbTillDetail.getOperation());
			query.executeUpdate();
			status = true;
		} catch (SQLException e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}
}
