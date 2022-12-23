package model.buys;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DBConnection;

public class DAOBuys {
	//Attributes
	private PreparedStatement query;
	private ResultSet result;
	private String sql = "";
	private boolean status;
	
	//Method that SAVES the Buy
	public boolean saveBuy(JBBuys jbBuy) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
			       + "buys_buy("
			            + "date, "
			            + "time, "
			            + "total, "
			            + "id_provider, "
			            + "id_cashier) "
			  + "VALUES(?, ?, ?, ?, ?);";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbBuy.getDate());
			query.setString(2, jbBuy.getTime());
			query.setDouble(3, jbBuy.getTotal());
			query.setInt(4, jbBuy.getIDProvider());
			query.setInt(5, jbBuy.getIDCashier());
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
	
	//Method that GETS the ID from a Buy according to the Date and Time
	public int getIDBuy(JBBuys jbBuy) {
		int id_buy = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_buy "
			  + "FROM "
			       + "buys_buy "
			  + "WHERE "
			       + "date = ? "
			  + "AND "
			       + "time = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbBuy.getDate());
			query.setString(2, jbBuy.getTime());
			result = query.executeQuery();
			while(result.next()) {
				id_buy = result.getInt("id_buy");
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id_buy;
	}
	
	//Method that RETRIVES the BUYS on a Specific Date
	public ArrayList<JBBuys> getBuysByDate(String date) {
		ArrayList<JBBuys> alBuys = new ArrayList<JBBuys>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "buys_buy.id_buy, "
			       + "buys_buy.date, "
			       + "buys_buy.total, "
			       + "providers_provider.name, "
			       + "cashiers_cashier.names, "
			       + "cashiers_cashier.first_name, "
			       + "cashiers_cashier.last_name "
			  + "FROM "
			       + "buys_buy "
			  + "INNER JOIN "
			       + "providers_provider "
			  + "ON "
			       + "buys_buy.id_provider = providers_provider.id_provider "
			  + "INNER JOIN "
			       + "cashiers_cashier "
			  + "ON "
			       + "buys_buy.id_cashier = cashiers_cashier.id_cashier "
			  + "WHERE "
			       + "buys_buy.date = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, date);
			result = query.executeQuery();
			while(result.next()) {
				JBBuys jbBuy = new JBBuys();
				jbBuy.setIDBuy(result.getInt(1));
				jbBuy.setDate(result.getString(2));
				jbBuy.setTotal(result.getDouble(3));
				jbBuy.setProvider(result.getString(4));
				jbBuy.setCashier(result.getString(5) + " " + result.getString(6) + " " + result.getString(7));
				alBuys.add(jbBuy);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alBuys;
	}
	
	//Method that RETRIVES the ID from the LAST BUY
	public int getLastBuyID() {
		int lastBuy = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "MAX(id_buy) "
			  + "FROM "
			       + "buys_buy;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			result = query.executeQuery();
			while(result.next()) {
				lastBuy = result.getInt(1);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lastBuy;
	}
	
	//Method that RETRIVES the DATE from a specific BUY
	public String getDateBuy(int id_buy) {
		String dateBuy = "";
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
	               + "date "
	          + "FROM "
	               + "buys_buy "
	          + "WHERE "
	               + "id_buy = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_buy);
			result = query.executeQuery();
			while(result.next()) {
				dateBuy = result.getString(1);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dateBuy;
	}
	
	//Method that RETRIVES the Total of Buys for the Cashier Court
	public double getTotalBuysByCashier(String date, String startTime, String endTime, int id_cashier) {
		double totalBuys = 0.0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "SUM(total) "
			  + "FROM "
			       + "buys_buy "
			  + "WHERE "
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
			query.setString(1, date);
			query.setString(2, startTime);
			query.setString(3, endTime);
			query.setInt(4, id_cashier);
			result = query.executeQuery();
			while(result.next()) {
				totalBuys = result.getDouble(1);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return totalBuys;
	}
	
	//Method that RETRIVES the TOTAL of Buys in the Day
	public double getTotalBuysByDay(String date) {
		double totalBuys = 0.0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "SUM(total) "
			  + "FROM "
			       + "buys_buy "
			  + "WHERE "
			       + "date = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, date);
			result = query.executeQuery();
			while(result.next()) {
				totalBuys = result.getDouble(1);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return totalBuys;
	}
	
	//Method that RETRIVES the MIN ID from the buys on a specific date
	public int retriveMinID(String date) {
		int min = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "MIN(id_buy) "
			  + "FROM "
			       + "buys_buy "
			  + "WHERE "
			       + "date = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, date);
			result = query.executeQuery();
			while(result.next()) {
				min = result.getInt(1);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return min;
	}
	
	//Method that RETRIVES the MAX ID from the Buys on a specific date
	public int retriveMaxID(String date) {
		int max = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "MAX(id_buy) "
			  + "FROM "
			       + "buys_buy "
			  + "WHERE "
			       + "date = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, date);
			result = query.executeQuery();
			while(result.next()) {
				max = result.getInt(1);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return max;
	}
	
	//Method that RETRIVES all the Buys in the Selected Range
	public ArrayList<JBBuys> retriveBuysRange(int minBuy, int maxBuy) {
		ArrayList<JBBuys> alBuys = new ArrayList<JBBuys>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "buys_buy.id_buy, "
			       + "buys_buy.date, "
			       + "buys_buy.total, "
			       + "providers_provider.name, "
			       + "cashiers_cashier.names, "
			       + "cashiers_cashier.first_name, "
			       + "cashiers_cashier.last_name "
			  + "FROM "
			       + "buys_buy "
			  + "INNER JOIN "
			       + "providers_provider "
			  + "ON "
			       + "buys_buy.id_provider = providers_provider.id_provider "
			  + "INNER JOIN "
			       + "cashiers_cashier "
			  + "ON "
			       + "buys_buy.id_cashier = cashiers_cashier.id_cashier "
			  + "WHERE "
			       + "id_buy "
			  + "BETWEEN "
			      + "? "
			  + "AND "
			       + "? "
			  + "ORDER BY "
			       + "id_buy "
			  + "ASC;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, minBuy);
			query.setInt(2, maxBuy);
			result = query.executeQuery();
			while(result.next()) {
				JBBuys jbBuy = new JBBuys();
				jbBuy.setIDBuy(result.getInt(1));
				jbBuy.setDate(result.getString(2));
				jbBuy.setTotal(result.getDouble(3));
				jbBuy.setProvider(result.getString(4));
				jbBuy.setCashier(result.getString(5) + " " + result.getString(6) + " " + result.getString(7));
				alBuys.add(jbBuy);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alBuys;
	}
}
