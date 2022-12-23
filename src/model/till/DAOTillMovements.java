package model.till;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DBConnection;

public class DAOTillMovements {
	//Attributes
	private PreparedStatement query = null;
	private ResultSet result = null;
	private String sql = "";
	private boolean status;
	
	public boolean saveMovement(JBTillMovements jbTillMovement) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
			       + "tills_movements("
			            + "type_movement, "
			            + "date, "
			            + "time, "
			            + "balance, "
			            + "commentary, "
			            + "id_cashier, "
			            + "id_till) "
			  + "VALUES(?, ?, ?, ?, ?, ?, ?);";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbTillMovement.getTypeMovement());
			query.setString(2, jbTillMovement.getDate());
			query.setString(3, jbTillMovement.getTime());
			query.setDouble(4, jbTillMovement.getBalance());
			query.setString(5, jbTillMovement.getComentCN());
			query.setInt(6, jbTillMovement.getIDCashier());
			query.setInt(7, jbTillMovement.getIDTill());
			query.executeUpdate();
			status = true;
			dbconn.closeConnection();
			query.close();
			sql = "";
		} catch (SQLException e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	//Method that RETRIVES the TOTAL OF THE MOVEMENT BY CASHIER
	public double getTotalMovementsByCashier(JBTillMovements jbTillMovement, String startTime, String endTime) {
		double totalCashByCashier = 0.0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "SUM(balance) "
			  + "FROM "
			       + "tills_movements "
			  + "WHERE "
			       + "type_movement = ? "
			  + "AND "
			       + "date = ? "
			  + "AND "
			       + "time "
			  + "BETWEEN "
			       + "? "
			  + "AND "
			       + "? "
			  + "AND "
			       + "id_cashier = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbTillMovement.getTypeMovement());
			query.setString(2, jbTillMovement.getDate());
			query.setString(3, startTime);
			query.setString(4, endTime);
			query.setInt(5, jbTillMovement.getIDCashier());
			result = query.executeQuery();
			while(result.next()) {
				totalCashByCashier = result.getDouble(1);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return totalCashByCashier;
	}
	
	//Method that RETRIVES the List of the Movements By Cashier
	public ArrayList<JBTillMovements> getListMovementsByCashier(JBTillMovements jbMovement, String startTime, String endTime) {
		ArrayList<JBTillMovements> alMovementsByCashier = new ArrayList<JBTillMovements>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "time, "
			       + "commentary, "
			       + "balance "
			  + "FROM "
			       + "tills_movements "
			  + "WHERE "
			       + "type_movement = ? "
			  + "AND "
			       + "date = ? "
			  + "AND "
			       + "time "
			  + "BETWEEN "
			       + "? "
			  + "AND "
			       + "? "
			  + "AND "
			       + "id_cashier = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbMovement.getTypeMovement());
			query.setString(2, jbMovement.getDate());
			query.setString(3, startTime);
			query.setString(4, endTime);
			query.setInt(5, jbMovement.getIDCashier());
			result = query.executeQuery();
			while(result.next()) {
				JBTillMovements jbTillMovement = new JBTillMovements();
				jbTillMovement.setTime(result.getString("time"));
				jbTillMovement.setComentCN(result.getString("commentary"));
				jbTillMovement.setBalance(result.getDouble("balance"));
				alMovementsByCashier.add(jbTillMovement);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alMovementsByCashier;
	}
	
	//Method that RETRIVES the TOTAL OF THE MOVEMENT PER DAY
	public double getTotalMovementsByDay(JBTillMovements jbTillMovement) {
		double totalMovementsByDay = 0.0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "SUM(balance) "
			  + "FROM "
			       + "tills_movements "
			  + "WHERE "
			       + "type_movement = ? "
			  + "AND "
			       + "date = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbTillMovement.getTypeMovement());
			query.setString(2, jbTillMovement.getDate());
			result = query.executeQuery();
			while(result.next()) {
				totalMovementsByDay = result.getDouble(1);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalMovementsByDay;
	}
	
	//Method that RETRIVES the List of the Movements By Day
	public ArrayList<JBTillMovements> getListMovementsByDay(JBTillMovements jbMovement) {
		ArrayList<JBTillMovements> alMovementsByDay = new ArrayList<JBTillMovements>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "time, "
			       + "commentary, "
			       + "balance "
			  + "FROM "
			       + "tills_movements "
			  + "WHERE "
			       + "type_movement = ? "
			  + "AND "
			       + "date = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbMovement.getTypeMovement());
			query.setString(2, jbMovement.getDate());
			result = query.executeQuery();
			while(result.next()) {
				JBTillMovements jbTillMovement = new JBTillMovements();
				jbTillMovement.setTime(result.getString("time"));
				jbTillMovement.setComentCN(result.getString("commentary"));
				jbTillMovement.setBalance(result.getDouble("balance"));
				alMovementsByDay.add(jbTillMovement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alMovementsByDay;
	}
}
