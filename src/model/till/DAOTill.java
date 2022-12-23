package model.till;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DBConnection;

public class DAOTill {
	//Attributes
	private PreparedStatement query = null;
	private ResultSet result = null;
	private String sql = "";
	private boolean status;
	
	public boolean checkOpenTill() {
		DBConnection dbconn = new DBConnection();
		sql = "SELECT COUNT("
			       + "id_till) "
			  + "FROM "
			       + "tills_till "
			  + "WHERE "
			       + "status = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, "Abierta");
			result = query.executeQuery();
			while(result.next()) {
				if(result.getInt(1) != 0)
					status = true;
				else
					status = false;
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
			status = false;
		}
		
		return status;
	}
	
	public boolean saveTill(JBTill jbTill) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
			       + "tills_till("
			            + "beginning_balance, "
			            + "final_balance, "
			            + "status) "
			  + "VALUES(?, ?, ?);";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbTill.getBeginninBalance());
			query.setString(2, jbTill.getFinalBalance());
			query.setString(3, jbTill.getStatus());
			query.executeUpdate();
			dbconn.closeConnection();
			query.close();
			sql = "";
			status = true;
		} catch (SQLException e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	public int getIDTill(String status) {
		int id_till = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_till "
			  + "FROM "
			       + "tills_till "
			  + "WHERE "
			       + "status = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, status);
			result = query.executeQuery();
			while(result.next()) {
				id_till = result.getInt(1);
			}
			dbconn.closeConnection();
			query.close();
			result.close();
			sql = "";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id_till;
	}
	
	//Method that RETRIVES the Beginning Balance
	public double getBeginigBalance() {
		double beginingBalance = 0.0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "beginning_balance "
			  + "FROM "
			       + "tills_till "
			  + "WHERE "
			       + "status = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, "Abierta");
			result = query.executeQuery();
			while(result.next()) {
				beginingBalance = result.getDouble("beginning_balance");
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beginingBalance;
	}
	
	public double getActualBalance() {
		double actualBalance = 0.00;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "final_balance "
			  + "FROM "
			       + "tills_till "
			  + "WHERE "
			       + "status = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, "Abierta");
			result = query.executeQuery();
			while(result.next()) {
				actualBalance = result.getDouble("final_balance");
			}
			dbconn.closeConnection();
			query.close();
			result.close();
			sql = "";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actualBalance;
	}
	
	public boolean updateBalance(int id_till, double newBalance) {
		DBConnection dbconn = new DBConnection();
		sql = "UPDATE "
			       + "tills_till "
			  + "SET "
			       + "final_balance = ? "
			  + "WHERE "
			       + "id_till = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setDouble(1, newBalance);
			query.setInt(2, id_till);
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
	
	//Method that CLOSE the Till
	public boolean closeTill(String closeStatus) {
		DBConnection dbconn = new DBConnection();
		sql = "UPDATE "
		           + "tills_till "
		      + "SET "
		           + "status = ? "
		      + "WHERE "
		           + "status = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, closeStatus);
			query.setString(2, "Abierta");
			query.executeUpdate();
			dbconn.closeConnection();
			sql = "";
			query.close();
			status = true;
		} catch (SQLException e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}
}
