package model.providers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import model.DBConnection;

public class DAOProviders {
	//Attributes
	private PreparedStatement query;
	private ResultSet result;
	private String sql = "";
	private boolean status;
	
	public ArrayList<JBProviders> getProviders() {
		ArrayList<JBProviders> retrivedProvider = new ArrayList<JBProviders>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_provider, "
			       + "name, "
			       + "irs, "
			       + "street, "
			       + "ext_number, "
			       + "colony, "
			       + "city, "
			       + "land_line1, "
			       + "land_line2, "
			       + "email "
			  + "FROM "
			       + "providers_provider "
			  + "ORDER BY "
			       + "id_provider "
			  + "ASC";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			result = query.executeQuery();
			while(result.next()) {
				JBProviders jbProvider = new JBProviders();
				jbProvider.setIDProvider(result.getInt("id_provider"));
				jbProvider.setName(result.getString("name"));
				jbProvider.setIRS(result.getString("irs"));
				jbProvider.setStreet(result.getString("street"));
				jbProvider.setExtNumber(result.getString("ext_number"));
				jbProvider.setColony(result.getString("colony"));
				jbProvider.setCity(result.getString("city"));
				jbProvider.setLandLine1(result.getString("land_line1"));
				jbProvider.setLandLine2(result.getString("land_line2"));
				jbProvider.setEmail(result.getString("email"));
				retrivedProvider.add(jbProvider);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retrivedProvider;
	}
	
	public boolean verifyProvider(String irs) {
		DBConnection dbconn = new DBConnection();
		sql = "SELECT COUNT(id_provider) FROM providers_provider WHERE irs = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, irs);
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
			sql = "";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public boolean saveProvider(JBProviders jbProvider) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
			       + "providers_provider("
			            + "name, "
			            + "company_name, "
			            + "irs, "
			            + "street, "
			            + "ext_number, "
			            + "int_number, "
			            + "colony, "
			            + "city, "
			            + "post_code, "
			            + "land_line1, "
			            + "land_line2, "
			            + "email, "
			            + "comentary) "
			  + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbProvider.getName());
			query.setString(2, jbProvider.getCompanyName());
			query.setString(3, jbProvider.getIRS());
			query.setString(4, jbProvider.getStreet());
			query.setString(5, jbProvider.getIntNumber());
			query.setString(6, jbProvider.getExtNumber());
			query.setString(7, jbProvider.getColony());
			query.setString(8, jbProvider.getCity());
			query.setString(9, jbProvider.getPostCode());
			query.setString(10, jbProvider.getLandLine1());
			query.setString(11, jbProvider.getLandLine2());
			query.setString(12, jbProvider.getEmail());
			query.setString(13, jbProvider.getComentary());
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
	
	public JBProviders getProviderByID(String id_provider) {
		JBProviders jbProvider = new JBProviders();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "* "
			  + "FROM "
			       + "providers_provider "
			  + "WHERE "
			       + "id_provider = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, id_provider);
			result = query.executeQuery();
			while(result.next()) {
				jbProvider.setIDProvider(result.getInt("id_provider"));
				jbProvider.setName(result.getString("name"));
				jbProvider.setCompanyName(result.getString("company_name"));
				jbProvider.setIRS(result.getString("irs"));
				jbProvider.setStreet(result.getString("street"));
				jbProvider.setExtNumber(result.getString("ext_number"));
				jbProvider.setIntNumber(result.getString("int_number"));
				jbProvider.setColony(result.getString("colony"));
				jbProvider.setCity(result.getString("city"));
				jbProvider.setPostCode(result.getString("post_code"));
				jbProvider.setLandLine1(result.getString("land_line1"));
				jbProvider.setLandLine2(result.getString("land_line2"));
				jbProvider.setEmail(result.getString("email"));
				jbProvider.setComentary(result.getString("comentary"));
			}
			dbconn.closeConnection();
			query.close();
			result.close();
			sql = "";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jbProvider;
	}
	
	public JBProviders getProviderByIRS(String irs) {
		JBProviders jbProvider = new JBProviders();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "* "
			  + "FROM "
			       + "providers_provider "
			  + "WHERE "
			       + "irs = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, irs);
			result = query.executeQuery();
			while(result.next()) {
				jbProvider.setIDProvider(result.getInt("id_provider"));
				jbProvider.setName(result.getString("name"));
				jbProvider.setCompanyName(result.getString("company_name"));
				jbProvider.setIRS(result.getString("irs"));
				jbProvider.setStreet(result.getString("street"));
				jbProvider.setExtNumber(result.getString("ext_number"));
				jbProvider.setIntNumber(result.getString("int_number"));
				jbProvider.setColony(result.getString("colony"));
				jbProvider.setCity(result.getString("city"));
				jbProvider.setPostCode(result.getString("post_code"));
				jbProvider.setLandLine1(result.getString("land_line1"));
				jbProvider.setLandLine2(result.getString("land_line2"));
				jbProvider.setEmail(result.getString("email"));
				jbProvider.setComentary(result.getString("comentary"));
			}
			dbconn.closeConnection();
			query.close();
			result.close();
			sql = "";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jbProvider;
	}
	
	public HashMap<String, Integer> getProvidersProduct(int id_product) {
		HashMap<String, Integer> hmProvidersProduct = new HashMap<String, Integer>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "providers_provider.name, "
			       + "providers_provider.id_provider "
			  + "FROM "
			       + "providers_provider "
			  + "INNER JOIN "
			       + "product_providers "
			  + "ON "
			       + "providers_provider.id_provider = product_providers.id_provider "
			  + "WHERE "
			       + "product_providers.id_product = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_product);
			result = query.executeQuery();
			while(result.next()) {
				hmProvidersProduct.put(result.getString(1), result.getInt(2));
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hmProvidersProduct;
	}
	
	public boolean updateProvider(JBProviders jbProvider) {
		DBConnection dbconn = new DBConnection();
		sql = "UPDATE "
			       + "providers_provider "
			  + "SET "
			       + "name = ?, "
			       + "company_name = ?, "
			       + "irs = ?, "
			       + "street = ?, "
			       + "ext_number = ?, "
			       + "int_number = ?, "
			       + "colony = ?, "
			       + "city = ?, "
			       + "post_code = ?, "
			       + "land_line1 = ?, "
			       + "land_line2 = ?, "
			       + "email = ?, "
			       + "comentary = ? "
			  + "WHERE "
			       + "id_provider = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbProvider.getName());
			query.setString(2, jbProvider.getCompanyName());
			query.setString(3, jbProvider.getIRS());
			query.setString(4, jbProvider.getStreet());
			query.setString(5, jbProvider.getExtNumber());
			query.setString(6, jbProvider.getIntNumber());
			query.setString(7, jbProvider.getColony());
			query.setString(8, jbProvider.getCity());
			query.setString(9, jbProvider.getPostCode());
			query.setString(10, jbProvider.getLandLine1());
			query.setString(11, jbProvider.getLandLine2());
			query.setString(12, jbProvider.getEmail());
			query.setString(13, jbProvider.getComentary());
			query.setInt(14, jbProvider.getIDProvider());
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
	
	public boolean deleteProvider(String id_provider) {
		DBConnection dbconn = new DBConnection();
		sql = "DELETE FROM "
			       + "providers_provider "
			  + "WHERE "
			       + "id_provider = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, id_provider);
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
}
