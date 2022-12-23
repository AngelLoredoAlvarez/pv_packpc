package model.clients;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DBConnection;

public class DAOClientsMovements {
	//Attributes
	private PreparedStatement query = null;
	private ResultSet result = null;
	private String sql = "";
	private boolean status;
	
	//Method that SAVES the Payment
	public boolean saveMovement(JBClientsMovements jbClientMovement) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
				+ "clients_movements("
				     + "date, "
				     + "time, "
				     + "type_movement, "
				     + "quantity, "
				     + "id_client, "
				     + "id_cashier) "
		     + "VALUES(?, ?, ?, ?, ?, ?);";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbClientMovement.getDate());
			query.setString(2, jbClientMovement.getTime());
			query.setString(3, jbClientMovement.getTypeMovement());
			query.setDouble(4, jbClientMovement.getQuantity());
			query.setInt(5, jbClientMovement.getIDClient());
			query.setInt(6, jbClientMovement.getIDCashier());
			query.executeUpdate();
			status = true;
			dbconn.closeConnection();
			sql = "";
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
			status = false;
		}
		
		return status;
	}
	
	//Method that Retrieves the total of Registers
	public int getTotalRegisters(int id_client) {
		int totalRegisters = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "COUNT(*) "
			 + "FROM "
			      + "clients_movements "
			 + "WHERE "
			      + "id_client = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_client);
			result = query.executeQuery();
			while(result.next()) {
				totalRegisters = result.getInt(1);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalRegisters;
	}
	
	//Method that RETRIVES the List of Movements
	public ArrayList<JBClientsMovements> getMovements(int id_client) {
		ArrayList<JBClientsMovements> alPaymentList = new ArrayList<JBClientsMovements>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "date, "
			       + "time, "
			       + "type_movement, "
			       + "quantity "
			  + "FROM "
			       + "clients_movements "
			  + "WHERE "
			       + "id_client = ? "
			  + "ORDER BY "
			       + "id_movement "
			  + "DESC;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_client);
			result = query.executeQuery();
			while(result.next()) {
				JBClientsMovements jbClientMovement = new JBClientsMovements();
				jbClientMovement.setDate(result.getString("date"));
				jbClientMovement.setTime(result.getString("time"));
				jbClientMovement.setTypeMovement(result.getString("type_movement"));
				jbClientMovement.setQuantity(result.getDouble("quantity"));
				alPaymentList.add(jbClientMovement);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alPaymentList;
	}
	
	//Method that RETRIVES the TOTAL OF PAYMENTS by Cashier
	public double getTotalPaymentsByCashier(String date, String startTime, String endTime, int id_cashier) {
		double totalPaymentsByCashier = 0.0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "SUM(quantity) "
			  + "FROM "
			       + "clients_movements "
			  + "WHERE "
			       + "date = ? "
			  + "AND "
			       + "time "
			  + "BETWEEN "
			       + "? "
			  + "AND "
			       + "? "
			  + "AND "
			       + "type_movement = ? "
			  + "AND "
			       + "id_cashier = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, date);
			query.setString(2, startTime);
			query.setString(3, endTime);
			query.setString(4, "Abono");
			query.setInt(5, id_cashier);
			result = query.executeQuery();
			while(result.next()) {
				totalPaymentsByCashier = result.getDouble(1);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return totalPaymentsByCashier;
	}
	
	//Method that RETRIVES the Total of Payments in the Day
	public double getTotalPaymentsInDay(String date) {
		double totalPaymentsInDay = 0.0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "SUM(quantity) "
			  + "FROM "
			       + "clients_movements "
			  + "WHERE "
			       + "date = ? "
			  + "AND "
			       + "type_movement = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, date);
			query.setString(2, "Abono");
			result = query.executeQuery();
			while(result.next()) {
				totalPaymentsInDay = result.getDouble(1);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return totalPaymentsInDay;
	}
}
