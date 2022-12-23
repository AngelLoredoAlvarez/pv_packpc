package model.cashiers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DBConnection;

public class DAOCashiersPrivileges {
	//Attributes
	private PreparedStatement query = null;
	private ResultSet result = null;
	private String sql = "";
	private boolean status;
	
	//Get Cashier Privileges
	public ArrayList<String> getPrivileges(int id_login) {
		ArrayList<String> getPriviliges = new ArrayList<String>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
		           + "privilege "
		      + "FROM "
		           + "cashiers_privileges "
		      + "WHERE "
		           + "id_login = ? "
		      + "ORDER BY "
		           + "id_privilege "
		      + "ASC;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_login);
			result = query.executeQuery();
			while(result.next()) {
				String privilege = result.getString("privilege");
				getPriviliges.add(privilege);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return getPriviliges;
	}
	
	//Method that SAVES the Privileges
	public boolean savePrivilige(JBCashiersPrivileges jbCashierPrivilige) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
			       + "cashiers_privileges("
			            + "privilege, "
			            + "id_login) "
			  + "VALUES(?, ?);";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbCashierPrivilige.getPrivilige());
			query.setInt(2, jbCashierPrivilige.getIDCashierLogin());
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
	
	//Method that GETS a single Privilege
	public int getIDPrivilige(String privilige, int id_login) {
		int id_privilige = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_privilige "
			  + "FROM "
			       + "cashiers_privilige "
			  + "WHERE "
			       + "privilige = ? "
			  + "AND "
			       + "id_login = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, privilige);
			query.setInt(2, id_login);
			result = query.executeQuery();
			while(result.next()) {
				id_privilige = result.getInt("id_privilige");
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id_privilige;
	}
	
	//Method that CHECKS if the Cashier have some privilege
	public int checkPriviliges(int id_cashierlogin) {
		int cantPriviliges = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT COUNT("
			       + "privilige) "
			  + "FROM "
			       + "cashiers_privilige "
			  + "WHERE "
			       + "id_login = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_cashierlogin);
			result = query.executeQuery();
			cantPriviliges = result.getInt(1);
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cantPriviliges;
	}
	
	//Method that DELETES the Privileges
	public boolean deletePrivilige(JBCashiersPrivileges jbCashierPrivilege) {
		DBConnection dbconn = new DBConnection();
		sql = "DELETE FROM "
			       + "cashiers_privileges "
			  + "WHERE "
			       + "privilege = ? "
			  + "AND "
			       + "id_login = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbCashierPrivilege.getPrivilige());
			query.setInt(2, jbCashierPrivilege.getIDCashierLogin());
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
