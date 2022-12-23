package model.cashiers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DBConnection;

public class DAOCashiers {
	//Attributes
	private PreparedStatement query = null;
	private ResultSet result = null;
	private String sql = "";
	private boolean status;
	
	//Method that GETS the Cashiers List
	public ArrayList<JBCashiers> getCashiers() {
		ArrayList<JBCashiers> cashiersList = new ArrayList<JBCashiers>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_cashier, "
			       + "names, "
			       + "first_name, "
			       + "last_name, "
			       + "land_line, "
			       + "cel_phone, "
			       + "email "
			  + "FROM "
			       + "cashiers_cashier;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			result = query.executeQuery();
			while(result.next()) {
				JBCashiers jbCashier = new JBCashiers();
				jbCashier.setIDCashier(result.getInt(1));
				jbCashier.setNames(result.getString(2));
				jbCashier.setFirstName(result.getString(3));
				jbCashier.setLastName(result.getString(4));
				jbCashier.setLandLine(result.getString(5));
				jbCashier.setCelPhone(result.getString(6));
				jbCashier.setEmail(result.getString(7));
				cashiersList.add(jbCashier);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cashiersList;
	}
	
	//Method that SAVES on the DB
	public boolean saveCashier(JBCashiers jbCashier) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
			       + "cashiers_cashier("
			            + "names, "
			            + "first_name, "
			            + "last_name, "
			            + "land_line, "
			            + "cel_phone, "
			            + "email,"
			            + "id_login) "
			  + "VALUES(?, ?, ?, ?, ?, ?, ?);";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbCashier.getNames());
			query.setString(2, jbCashier.getFirstName());
			query.setString(3, jbCashier.getLastName());
			query.setString(4, jbCashier.getLandLine());
			query.setString(5, jbCashier.getCelPhone());
			query.setString(6, jbCashier.getEmail());
			query.setInt(7, jbCashier.getIDCashierLogin());
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
	
	//Method that VERIFIES if the Cashier has been already registered
	public boolean verifyCashier(JBCashiers jbCashier) {
		DBConnection dbconn = new DBConnection();
		sql = "SELECT COUNT(id_cashier) FROM cashiers_cashier WHERE names = ? AND first_name = ? AND last_name = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbCashier.getNames());
			query.setString(2, jbCashier.getFirstName());
			query.setString(3, jbCashier.getLastName());
			result = query.executeQuery();
			while(result.next()) {
				if(result.getInt(1) != 0)
					status = true;
				else 
					status = false;
			}
			dbconn.closeConnection();
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return status;
	}
	
	//Method that GETS the Selected Cashier
	public JBCashiers getCashier(int id_cashier) {
		JBCashiers jbCashier = new JBCashiers();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "cashiers_cashier.id_cashier, "
			       + "cashiers_cashier.names, "
			       + "cashiers_cashier.first_name, "
			       + "cashiers_cashier.last_name, "
			       + "cashiers_cashier.land_line, "
			       + "cashiers_cashier.cel_phone, "
			       + "cashiers_cashier.email, "
			       + "cashiers_cashier.id_login, "
			       + "cashiers_login.username "
			  + "FROM "
			       + "cashiers_cashier "
			  + "INNER JOIN "
			       + "cashiers_login "
			  + "ON "
			       + "cashiers_cashier.id_login = cashiers_login.id_login "
			  + "WHERE "
			       + "cashiers_cashier.id_cashier = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_cashier);
			result = query.executeQuery();
			while(result.next()) {
				jbCashier.setIDCashier(result.getInt(1));
				jbCashier.setNames(result.getString(2));
				jbCashier.setFirstName(result.getString(3));
				jbCashier.setLastName(result.getString(4));
				jbCashier.setLandLine(result.getString(5));
				jbCashier.setCelPhone(result.getString(6));
				jbCashier.setEmail(result.getString(7));
				jbCashier.setIDLogin(result.getInt(8));
				jbCashier.setUsername(result.getString(9));
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jbCashier;
	}
	
	//Method that GETS the Cashier ID
	public int getCashierID(JBCashiers jbCashier) {
		int id_cashier = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_cashier "
			  + "FROM "
			       + "cashiers_cashier "
			  + "WHERE "
			       + "names = ? "
			  + "AND "
			       + "first_name = ? "
			  + "AND "
			       + "last_name = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbCashier.getNames());
			query.setString(2, jbCashier.getFirstName());
			query.setString(3, jbCashier.getLastName());
			result = query.executeQuery();
			while(result.next()) {
				id_cashier = result.getInt("id_cashier");
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id_cashier;
	}
	
	//Method that GETS the Cashier Name
	public JBCashiers getCashierName(int id_cashier) {
		JBCashiers jbCashier = new JBCashiers();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "names, "
			       + "first_name, "
			       + "last_name "
			  + "FROM "
			       + "cashiers_cashier "
			  + "WHERE "
			       + "id_cashier = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_cashier);
			result = query.executeQuery();
			while(result.next()) {
				jbCashier.setNames(result.getString("names"));
				jbCashier.setFirstName(result.getString("first_name"));
				jbCashier.setLastName(result.getString("last_name"));
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jbCashier;
	}
	
	//Method that UPDATES the Cashier DATA
	public boolean updateData(JBCashiers jbCashier) {
		DBConnection dbconn = new DBConnection();
		sql = "UPDATE "
			       + "cashiers_cashier "
			  + "SET "
			       + "names = ?, "
			       + "first_name = ?, "
			       + "last_name = ?, "
			       + "land_line = ?, "
			       + "cel_phone = ?, "
			       + "email = ? "
			  + "WHERE "
			       + "id_cashier = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbCashier.getNames());
			query.setString(2, jbCashier.getFirstName());
			query.setString(3, jbCashier.getLastName());
			query.setString(4, jbCashier.getLandLine());
			query.setString(5, jbCashier.getCelPhone());
			query.setString(6, jbCashier.getEmail());
			query.setInt(7, jbCashier.getIDCashier());
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
