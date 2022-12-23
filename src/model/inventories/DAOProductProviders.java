package model.inventories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DBConnection;

public class DAOProductProviders {
	//Attributes
	private PreparedStatement query = null;
	private ResultSet result;
	private String sql = "";
	private boolean status;
	
	public boolean saveProductProvider(int id_product, int id_provider) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
			       + "product_providers("
			            + "id_product, "
			            + "id_provider) "
			  + "VALUES(?, ?);";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_product);
			query.setInt(2, id_provider);
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
	
	public boolean checkIfProviderExist(int id_product, int id_provider) {
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
				   + "COUNT(*) "
			  + "FROM "
			       + "product_providers "
			  + "WHERE "
			       + "id_product = ? "
			  + "AND "
			       + "id_provider = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_product);
			query.setInt(2, id_provider);
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
			status = false;
			e.printStackTrace();
		}
		return status;
	}
	
	public boolean deleteProvider(int id_product, int id_provider) {
		DBConnection dbconn = new DBConnection();
		sql = "DELETE FROM "
		           + "product_providers "
		      + "WHERE "
		           + "id_product = ? "
		      + "AND "
		           + "id_provider = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_product);
			query.setInt(2, id_provider);
			query.executeUpdate();
			dbconn.closeConnection();
			sql = "";
			query.close();
			status = true;
		} catch (SQLException e) {
			status = false;
			e.printStackTrace();
		}
		
		return status;
	}
	
	public boolean checkIfProductIsProviderByProvider(int id_product, int id_provider) {
		DBConnection dbconn = new DBConnection();
		sql = "SELECT COUNT(*) "
			+ "FROM "
			     + "product_providers "
			+ "WHERE "
			     + "id_product = ? "
			+ "AND "
			     + "id_provider = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_product);
			query.setInt(2, id_provider);
			result = query.executeQuery();
			while(result.next()) {
				if(result.getInt(1) != 0)
					status = true;
				else
					status = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}
}
