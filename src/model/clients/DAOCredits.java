package model.clients;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DBConnection;

public class DAOCredits {
	//Attributes
	private PreparedStatement query;
	private ResultSet result;
	private String sql = "";
	private boolean status;
	
	public ArrayList<JBCredits> getClientsCredits() {
		ArrayList<JBCredits> clientsCredits = new ArrayList<JBCredits>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "clients_credit.credit_limit, "
			       + "clients_credit.current_balance, "
			       + "clients_client.id_credit, "
			       + "clients_client.id_client, "
			       + "clients_client.names, "
			       + "clients_client.first_name, "
			       + "clients_client.last_name "
			  + "FROM "
			       + "clients_credit "
			  + "INNER JOIN "
			       + "clients_client "
			  + "ON "
			       + "clients_credit.id_credit = clients_client.id_credit;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			result = query.executeQuery();
			while(result.next()) {
				JBCredits jbCredit = new JBCredits();
				jbCredit.setCreditLimit(result.getString(1));
				jbCredit.setCurrentBalance(result.getString(2));
				jbCredit.setIDCredit(result.getInt(3));
				jbCredit.setIDClient(result.getInt(4));
				jbCredit.setClient(result.getString(5) + " " + result.getString(6) + " " + result.getString(7));
				clientsCredits.add(jbCredit);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clientsCredits;
	}
	
	public JBCredits getCreditInfo(int id_client) {
		JBCredits creditInfo = new JBCredits();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
				   + "clients_credit.credit_limit, "
				   + "clients_credit.current_balance, "
			       + "clients_client.id_client, "
			       + "clients_client.names, "
			       + "clients_client.first_name, "
			       + "clients_client.last_name "
			  + "FROM "
			       + "clients_credit "
			  + "INNER JOIN "
			       + "clients_client "
			  + "ON "
			       + "clients_credit.id_credit = clients_client.id_credit "
			  + "WHERE "
			       + "clients_client.id_client = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_client);
			result = query.executeQuery();
			while(result.next()) {
				creditInfo.setCreditLimit(result.getString(1));
				creditInfo.setCurrentBalance(result.getString(2));
				creditInfo.setIDClient(result.getInt(3));
				creditInfo.setClient(result.getString(4) + " " + result.getString(5) + " " + result.getString(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return creditInfo;
	}
	
	public boolean saveCredit(JBCredits jbCredit) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
			       + "clients_credit("
			            + "opening_date, "
			            + "opening_time, "
			            + "credit_limit, "
			            + "current_balance) "
			  + "VALUES(?, ?, ?, ?);";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbCredit.getOpeningDate());
			query.setString(2, jbCredit.getOpeningTime());
			query.setString(3, jbCredit.getCreditLimit());
			query.setString(4, jbCredit.getCurrentBalance());
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
	
	public int getIDCredit(JBCredits jbCredit) {
		int id_credit = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_credit "
			  + "FROM "
			       + "clients_credit "
			  + "WHERE "
			       + "opening_date = ? "
			  + "AND "
			       + "opening_time = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbCredit.getOpeningDate());
			query.setString(2, jbCredit.getOpeningTime());
			result = query.executeQuery();
			while(result.next()) {
				id_credit = result.getInt("id_credit");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id_credit;
	}
	
	public String getCreditLimit(int id_client) {
		String creditLimit = "";
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "clients_credit.credit_limit "
			  + "FROM "
			       + "clients_credit "
			  + "INNER JOIN "
			       + "clients_client "
			  + "ON "
			       + "clients_credit.id_credit = clients_client.id_credit "
			  + "WHERE "
			       + "clients_client.id_client = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_client);
			result = query.executeQuery();
			while(result.next()) {
				creditLimit = result.getString("credit_limit");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return creditLimit;
	}
	
	public String getCurrentBalance(int id_client) {
		String currentBalance = "";
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "clients_credit.current_balance "
			  + "FROM "
			       + "clients_credit "
			  + "INNER JOIN "
			       + "clients_client "
			  + "ON "
			       + "clients_credit.id_credit = clients_client.id_credit "
			  + "WHERE "
			       + "clients_client.id_client = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_client);
			result = query.executeQuery();
			while(result.next()) {
				currentBalance = result.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return currentBalance;
	}
	
	public boolean updateCreditLimit(String newCreditLimit, int idClient) {
		DBConnection dbconn = new DBConnection();
		sql = "UPDATE "
			       + "clients_credit "
			  + "INNER JOIN "
			       + "clients_client "
			  + "ON "
			       + "clients_credit.id_credit = clients_client.id_credit "
			  + "SET "
			       + "clients_credit.credit_limit = ? "
			  + "WHERE "
			       + "clients_client.id_client = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, newCreditLimit);
			query.setInt(2, idClient);
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
	
	public boolean updateCurrentBalance(String newBalance, int id_client) {
		DBConnection dbconn = new DBConnection();
		sql = "UPDATE "
			       + "clients_credit "
			  + "INNER JOIN "
			       + "clients_client "
			  + "ON "
			       + "clients_credit.id_credit = clients_client.id_credit "
			  + "SET "
			       + "clients_credit.current_balance = ? "
			  + "WHERE "
			       + "clients_client.id_client = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, newBalance);
			query.setInt(2, id_client);
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
	
	public boolean liquidateBalance(int id_client) {
		DBConnection dbconn = new DBConnection();
		sql = "UPDATE "
			       + "clients_credit "
			  + "INNER JOIN "
			       + "clients_client "
			  + "ON "
			       + "clients_credit.id_credit = clients_client.id_credit "
			  + "SET "
			       + "clients_credit.current_balance = 0.00 "
			  + "WHERE "
			       + "clients_client.id_client = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_client);
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
}
