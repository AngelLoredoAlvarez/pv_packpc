package model.cashiers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DBConnection;

public class DAOCashiersLogin {
	//Attributes
	private PreparedStatement query = null;
	private ResultSet result = null;
	private String sql = "";
	private boolean status;
	
	//Method that GETS all the UserNames
	public ArrayList<String> getUserNames() {
		ArrayList<String> alUserNames = new ArrayList<String>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "username "
			  + "FROM "
			       + "cashiers_login;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			result = query.executeQuery();
			alUserNames.add("");
			while(result.next()) {
				alUserNames.add(result.getString("username"));
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alUserNames;
	}
	
	//Method that SAVES the Login
	public boolean saveLogin(JBCashiersLogin jbCashierLogin) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
			       + "cashiers_login("
			            + "username, "
			            + "password, "
			            + "status) "
			  + "VALUES(?, ?, ?);";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbCashierLogin.getUserName());
			query.setString(2, jbCashierLogin.getPassword());
			query.setString(3, jbCashierLogin.getStatus());
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
	
	//Method that GETS the Login ID with the UserName
	public int getLoginIDByUN(String username) {
		int id_login = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_login "
			  + "FROM "
			       + "cashiers_login "
			  + "WHERE "
			       + "username "
			  + "LIKE "
			       + "?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, username);
			result = query.executeQuery();
			while(result.next()) {
				id_login = result.getInt("id_login");
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id_login;
	}
	
	//Method that GETS the Login ID with the UserName and Pass
	public int getLoginIDByUP(JBCashiersLogin jbCashierLogin) {
		int id_login = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_login "
			  + "FROM "
			       + "cashiers_login "
			  + "WHERE "
			       + "username "
			  + "LIKE "
			       + "? "
			  + "AND "
			       + "password "
			  + "LIKE "
			       + "?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbCashierLogin.getUserName());
			query.setString(2, jbCashierLogin.getPassword());
			result = query.executeQuery();
			while(result.next()) {
				id_login = result.getInt("id_login");
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id_login;
	}
	
	//Method that RETURNS the STATUS from the LOGIN
	public String checkLoginStatus(String userName) {
		String loginStatus = "";
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
		           + "status "
		      + "FROM "
		           + "cashiers_login "
		      + "WHERE "
		           + "username = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, userName);
			result = query.executeQuery();
			while(result.next()) {
				loginStatus = result.getString("status");
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return loginStatus;
	}
	
	//Method that CHECKS if the USER EXIST
	public boolean checkLogin(JBCashiersLogin jbCashierLogin) {
		int countUser = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT COUNT("
			       + "id_login) "
			  + "AS "
			       + "user "
			  + "FROM "
			       + "cashiers_login "
			  + "WHERE "
			       + "username = ? "
			  + "COLLATE "
			       + "utf8_bin "
			  + "AND "
			       + "password = ? "
			  + "COLLATE "
			       + "utf8_bin;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbCashierLogin.getUserName());
			query.setString(2, jbCashierLogin.getPassword());
			result = query.executeQuery();
			result.next();
			countUser = result.getInt("user");
			if(countUser != 0)
				status = true;
			else 
				status = false;
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	//Method that UPDATES the USERNAME
	public boolean updateUserName(JBCashiersLogin jbCashierLogin) {
		DBConnection dbconn = new DBConnection();
		sql = "UPDATE "
			       + "cashiers_login "
			  + "SET "
			       + "username = ? "
			  + "WHERE "
			       + "id_login = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbCashierLogin.getUserName());
			query.setInt(2, jbCashierLogin.getIDLogin());
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
	
	//Method that UPDATES or SETS the PASSWORD
	public boolean passwordUpdateSet(int id_login, String newPass, String action) {
		DBConnection dbconn = new DBConnection();
		sql = "UPDATE "
			       + "cashiers_login "
			  + "SET "
			       + "password = ?, "
			       + "status = ? "
			  + "WHERE "
			       + "id_login = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, newPass);
			query.setString(2, action);
			query.setInt(3, id_login);
			query.executeUpdate();
			status = true;
			dbconn.closeConnection();
			sql = "";
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	//Method that GETS the ID from the Cashier
	public int getCashierID(JBCashiersLogin jbCashierLogin) {
		int id_cashier = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "cashiers_cashier.id_cashier "
			  + "FROM "
			       + "cashiers_cashier "
			  + "INNER JOIN "
			       + "cashiers_login "
			  + "ON "
			       + "cashiers_login.id_login = cashiers_cashier.id_login "
			  + "WHERE "
			       + "cashiers_login.username = ? "
			  + "AND "
			       + "cashiers_login.password = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbCashierLogin.getUserName());
			query.setString(2, jbCashierLogin.getPassword());
			result = query.executeQuery();
			while(result.next()) {
				id_cashier = result.getInt(1);
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
	
	//Method that GETS the Name from the Cashier
	public String getCashierName(int id_cashier) {
		String name = "";
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "cashiers_cashier.names "
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
				name = result.getString(1);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return name;
	}
	
	//Method that RETRIVES only the USERNAME
	public String getUserName(int id_cashier) {
		String userName = "";
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "cashiers_login.username "
			  + "FROM "
			       + "cashiers_login "
			  + "INNER JOIN "
			       + "cashiers_cashier "
			  + "ON "
			       + "cashiers_login.id_login = cashiers_cashier.id_login "
			  + "WHERE "
			       + "cashiers_cashier.id_cashier = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_cashier);
			result = query.executeQuery();
			while(result.next()) {
				userName = result.getString("username");
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userName;
	}
	
	//Method that RERIVES the id_login from the SELECTED Cashier by id_cashier
	public int getIDLoginByIDCashier(int id_cashier) {
		int id_login = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "cashiers_login.id_login "
			  + "FROM "
			       + "cashiers_login "
			  + "INNER JOIN "
			       + "cashiers_cashier "
			  + "ON "
			       + "cashiers_login.id_login = cashiers_cashier.id_login "
			  + "WHERE "
			       + "cashiers_cashier.id_cashier = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_cashier);
			result = query.executeQuery();
			while(result.next()) {
				id_login = result.getInt(1);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id_login;
	}
	
	//DELETES Cashier Login
	public boolean deleteLogin(int id_login) {
		DBConnection dbconn = new DBConnection();
		sql = "DELETE FROM "
			       + "cashiers_login "
			  + "WHERE "
			       + "id_login = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_login);
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
