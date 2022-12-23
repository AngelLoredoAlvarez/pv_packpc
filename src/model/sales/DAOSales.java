package model.sales;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DBConnection;

public class DAOSales {
	//Attributes
	private PreparedStatement query;
	private ResultSet result;
	private String sql = "";
	private boolean status;

	//Method that SAVES the Sale
	public boolean saveSale(JBSales jbSale) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
			       + "sales_sale("
			            + "date, "
			            + "time, "
			            + "type_sale, "
			            + "total, "
			            + "status, "
			            + "id_client, "
			            + "id_cashier) "
			 + "VALUES(?, ?, ?, ?, ?, ?, ?);";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbSale.getDate());
			query.setString(2, jbSale.getTime());
			query.setString(3, jbSale.getTypeSell());
			query.setDouble(4, jbSale.getTotal());
			query.setString(5, jbSale.getStatus());
			query.setInt(6, jbSale.getIDClient());
			query.setInt(7, jbSale.getIDCashier());
			query.executeUpdate();
			dbconn.closeConnection();
			sql = "";
			status = true;
		} catch (SQLException e) {
			e.printStackTrace();
			status = false;
		}
		
		return status;
	}
	
	//Method that RETRIVES the ID from a Sale according the DATE and TIME
	public int getIDSale(JBSales jbSale) {
		int idSale = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_sale "
			  + "FROM "
			       + "sales_sale "
			  + "WHERE "
			       + "date = ? "
			  + "AND "
			       + "time = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbSale.getDate());
			query.setString(2, jbSale.getTime());
			result = query.executeQuery();
			while(result.next()) {
				idSale = result.getInt("id_sale");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return idSale;
	}
	
	//Method that RETRIVES the Sales on a Specific Date
	public ArrayList<JBSales> getSalesRange(String date) {
		ArrayList<JBSales> alSales = new ArrayList<JBSales>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "sales_sale.id_sale, "
			       + "sales_sale.date, "
			       + "sales_sale.time, "
			       + "sales_sale.status, "
			       + "clients_client.names, "
			       + "clients_client.first_name, "
			       + "clients_client.last_name "
			  + "FROM "
			       + "sales_sale "
			  + "INNER JOIN "
			       + "clients_client "
			  + "ON "
			       + "sales_sale.id_client = clients_client.id_client "
			  + "WHERE "
			       + "sales_sale.date = ? "
			  + "ORDER BY "
			       + "sales_sale.id_sale "
			  + "DESC;";
			  
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, date);
			result = query.executeQuery();
			while(result.next()) {
				JBSales jbSale = new JBSales();
				jbSale.setIDSell(result.getInt(1));
				jbSale.setDate(result.getString(2));
				jbSale.setTime(result.getString(3));
				jbSale.setStatus(result.getString(4));
				jbSale.setClient(result.getString(5) + " " + result.getString(6) + " " + result.getString(7));
				alSales.add(jbSale);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alSales;
	}
	
	//Method that RETRIVES the Sales from the SELECTED Cashier on a Specific Date
	public ArrayList<JBSales> getSalesFromClient(int id_client, String date) {
		ArrayList<JBSales> alSales = new ArrayList<JBSales>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			      + "id_sale, "
			      + "date "
			 + "FROM "
			      + "sales_sale "
			 + "WHERE "
			      + "date "
			 + "LIKE "
			      + "? "
			 + "AND "
			      + "id_client = ? " 
			 + "ORDER BY "
			      + "id_sale "
			 + "ASC;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, "%" + date + "%");
			query.setInt(2, id_client);
			result = query.executeQuery();
			while(result.next()) {
				JBSales jbSale = new JBSales();
				jbSale.setIDSell(result.getInt("id_sale"));
				jbSale.setDate(result.getString("date"));
				alSales.add(jbSale);
			}
			query.close();
			result.close();
			dbconn.closeConnection();
			sql = "";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alSales;
	}
	
	//Method that RETRIVES the TOTAL from an Specific Sale
	public double getTotalSale(int id_sale) {
		double totalSale = 0.0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			     + "total "
			+ "FROM "
			     + "sales_sale "
			+ "WHERE "
			     + "id_sale = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_sale);
			result = query.executeQuery();
			while(result.next()) {
				totalSale = result.getDouble("total");
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return totalSale;
	}
	
	//Method that RETRIVES the LAST SALE from an Specific Client
	public int getLastSaleFromClient(int id_client) {
		int lastSaleClient = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_sale "
			  + "FROM "
			       + "sales_sale "
			  + "WHERE "
			       + "type_sale = ? "
			  + "AND "
			       + "id_client = ? "
			  + "ORDER BY "
			       + "id_sale "
			  + "DESC "
			  + "LIMIT "
			       + "1;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, "Crédito");
			query.setInt(2, id_client);
			result = query.executeQuery();
			while(result.next()) {
				lastSaleClient = result.getInt("id_sale");
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lastSaleClient;
	}
	
	//Method that RETRIVES the DATE from an Specific Sale
	public String getSaleDate(int id_sale) {
		String saleDate = "";
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "date "
			  + "FROM "
			       + "sales_sale "
			  + "WHERE "
			       + "id_sale = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_sale);
			result = query.executeQuery();
			while(result.next()) {
				saleDate = result.getString("date");
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return saleDate;
	}
	
	//Method that GETS the LAST Sell
	public int getLastSale() {
		int last_sale = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT MAX("
			       + "id_sale) "
			  + "FROM "
			       + "sales_sale;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			result = query.executeQuery();
			while(result.next()) {
				last_sale = result.getInt(1);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return last_sale;
	}
	
	//Method that RETRIVES the TOTAL SALES for the CashierCourt
	public double getTotalSaleTypeSaleCashier(String startTime, String endTime, JBSales jbSale) {
		double totalSaleTypeSaleCashier = 0.0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "SUM(total) "
			  + "FROM "
			       + "sales_sale "
			  + "WHERE "
			       + "date = ? "
			  + "AND "
			       + "time "
			  + "BETWEEN "
			       + "? "
			  + "AND "
			       + "? "
			  + "AND "
			       + "type_sale = ? "
			  + "AND "
			       + "id_cashier = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbSale.getDate());
			query.setString(2, startTime);
			query.setString(3, endTime);
			query.setString(4, jbSale.getTypeSell());
			query.setInt(5, jbSale.getIDCashier());
			result = query.executeQuery();
			while(result.next()) {
				totalSaleTypeSaleCashier = result.getDouble(1);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return totalSaleTypeSaleCashier;
	}
	
	//Method that RETRIVES the ID's for the Sales made them by the Actual Cashier
	public ArrayList<Integer> getIDSalesByCashier(String startTime, String endTime, int id_cashier) {
		ArrayList<Integer> alIDsListSalesByCashier = new ArrayList<Integer>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_sale "
			  + "FROM "
			       + "sales_sale "
			  + "WHERE "
			       + "time  "
			  + "BETWEEN "
			       + "? "
			  + "AND "
			       + "? "
			  + "AND "
			       + "type_sale = ? "
			  + "AND "
			       + "id_cashier = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, startTime);
			query.setString(2, endTime);
			query.setString(3, "Efectivo");
			query.setInt(4, id_cashier);
			result = query.executeQuery();
			while(result.next()) {
				alIDsListSalesByCashier.add(result.getInt("id_sale"));
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alIDsListSalesByCashier;
	}
	
	//Method that RETRIVES the TOTAL SALES for the CashierCourt
	public double getTotalSalesDay(JBSales jbSale) {
		double totalSalesDay = 0.0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "SUM(total) "
			  + "FROM "
			       + "sales_sale "
			  + "WHERE "
			       + "date = ? "
			  + "AND "
			       + "type_sale = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbSale.getDate());
			query.setString(2, jbSale.getTypeSell());
			result = query.executeQuery();
			while(result.next()) {
				totalSalesDay = result.getDouble(1);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return totalSalesDay;
	}
	
	//Method that RETRIVES the ID's for the Sales made them in the DAY
	public ArrayList<Integer> getIDSalesInDay(String date) {
		ArrayList<Integer> alIDsListSalesInDay = new ArrayList<Integer>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_sale "
			  + "FROM "
			       + "sales_sale "
			  + "WHERE "
			       + "date = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, date);
			result = query.executeQuery();
			while(result.next()) {
				alIDsListSalesInDay.add(result.getInt("id_sale"));
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alIDsListSalesInDay;
	}
	
	//Method that RETRIVES the MIN ID from all the Sales on a Specific date
	public int retriveMinIDFromClient(String date, int id_client) {
		int min = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "MIN(id_sale) "
			  + "FROM "
			       + "sales_sale "
			  + "WHERE "
			       + "date = ? "
			  + "AND "
			       + "id_client = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, date);
			query.setInt(2, id_client);
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
	
	//Method that RETRIVES the MAX ID from all the Sales on a Specific date
	public int retriveMaxIDFromClient(String date, int id_client) {
		int max = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "MAX(id_sale) "
			  + "FROM "
			       + "sales_sale "
			  + "WHERE "
			       + "date = ? "
			  + "AND "
			       + "id_client = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, date);
			query.setInt(2, id_client);
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
	
	//Method that Retrieves all the Client Sales BETWEEN the Selected Range
	public ArrayList<JBSales> getSalesRangeFromClient(int minSale, int maxSale, int id_client) {
		ArrayList<JBSales> alSales = new ArrayList<JBSales>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_sale, "
			       + "date, "
			       + "time "
			  + "FROM "
			       + "sales_sale "
			  + "WHERE "
			       + "id_sale "
			  + "BETWEEN "
			       + "? "
			  + "AND "
			       + "? "
			  + "AND "
			       + "id_client = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, minSale);
			query.setInt(2, maxSale);
			query.setInt(3, id_client);
			result = query.executeQuery();
			while(result.next()) {
				JBSales jbSale = new JBSales();
				jbSale.setIDSell(result.getInt("id_sale"));
				jbSale.setDate(result.getString("date"));
				jbSale.setTime(result.getString("time"));
				alSales.add(jbSale);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alSales;
	}
	
	//Method that RETRIVES the MIN ID from the SALES on a specific date
	public int retriveMinID(String date) {
		int min = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "MIN(id_sale) "
			  + "FROM "
			       + "sales_sale "
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
			       + "MAX(id_sale) "
			  + "FROM "
			       + "sales_sale "
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
	
	//Method that Retrieves all the Sales on a Specific Range
	public ArrayList<JBSales> getSalesRange(int minSale, int maxSale) {
		ArrayList<JBSales> alSales = new ArrayList<JBSales>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "sales_sale.id_sale, "
			       + "sales_sale.date, "
			       + "sales_sale.time, "
			       + "sales_sale.status, "
			       + "clients_client.names, "
			       + "clients_client.first_name, "
			       + "clients_client.last_name "
			  + "FROM "
			       + "sales_sale "
			  + "INNER JOIN "
			       + "clients_client "
			  + "ON "
			       + "sales_sale.id_client = clients_client.id_client "
			  + "WHERE "
			       + "sales_sale.id_sale "
			  + "BETWEEN "
			       + "? "
			  + "AND "
			       + "? "
			  + "ORDER BY "
			       + "sales_sale.id_sale "
			  + "DESC;";
			  
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, minSale);
			query.setInt(2, maxSale);
			result = query.executeQuery();
			while(result.next()) {
				JBSales jbSale = new JBSales();
				jbSale.setIDSell(result.getInt(1));
				jbSale.setDate(result.getString(2));
				jbSale.setTime(result.getString(3));
				jbSale.setStatus(result.getString(4));
				jbSale.setClient(result.getString(5) + " " + result.getString(6) + " " + result.getString(7));
				alSales.add(jbSale);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alSales;
	}
}
