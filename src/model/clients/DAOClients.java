package model.clients;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DBConnection;

public class DAOClients {
	//Attributes
	private PreparedStatement query = null;
	private ResultSet result = null;
	private String sql = "";
	private boolean state;
	
	public ArrayList<JBClients> getClients() {
		ArrayList<JBClients> retrivedClients = new ArrayList<JBClients>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
				   + "id_client, "
				   + "names, "
			       + "first_name, "
			       + "last_name, "
			       + "street, "
			       + "ext_number, "
			       + "colony, "
			       + "city, "
			       + "land_line, "
			       + "cel_phone, "
			       + "email, "
			       + "irs "
			  + "FROM "
			       + "clients_client "
			  + "ORDER BY "
			       + "id_client "
			  + "ASC;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			result = query.executeQuery();
			while(result.next()) {
				JBClients retrivedClient = new JBClients();
				retrivedClient.setIDClient(result.getInt("id_client"));
				retrivedClient.setNames(result.getString("names"));
				retrivedClient.setFirstName(result.getString("first_name"));
				retrivedClient.setLastName(result.getString("last_name"));
				retrivedClient.setStreet(result.getString("street"));
				retrivedClient.setExtNumber(result.getString("ext_number"));
				retrivedClient.setColony(result.getString("colony"));
				retrivedClient.setCity(result.getString("city"));
				retrivedClient.setLandLine(result.getString("land_line"));
				retrivedClient.setCelPhone(result.getString("cel_phone"));
				retrivedClient.setEmail(result.getString("email"));
				retrivedClient.setIRS(result.getString("irs"));
				retrivedClients.add(retrivedClient);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retrivedClients;
	}
	
	public boolean verifyClient(JBClients jbClient) {
		DBConnection dbconn = new DBConnection();
		sql = "SELECT COUNT(id_client) FROM clients_client WHERE names = ? AND first_name = ? AND last_name = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbClient.getNames());
			query.setString(2, jbClient.getFirstName());
			query.setString(3, jbClient.getLastName());
			result = query.executeQuery();
			while(result.next()) {
				if(result.getInt(1) != 0)
					state = true;
				else
					state = false;
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return state;
	}
	
	public boolean saveClient(JBClients jbClient) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
				+ "clients_client("
					+ "names, "
					+ "first_name, "
					+ "last_name, "
					+ "street, "
					+ "ext_number, "
					+ "int_number, "
					+ "colony, "
					+ "city, "
					+ "post_code, "
					+ "land_line, "
					+ "cel_phone, "
					+ "email, "
					+ "irs, "
					+ "comentary, "
					+ "id_credit) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbClient.getNames());
			query.setString(2, jbClient.getFirstName());
			query.setString(3, jbClient.getLastName());
			query.setString(4, jbClient.getStreet());
			query.setString(5, jbClient.getExtNumber());
			query.setString(6, jbClient.getIntNumber());
			query.setString(7, jbClient.getColony());
			query.setString(8, jbClient.getCity());
			query.setString(9, jbClient.getPostCode());
			query.setString(10, jbClient.getLandLine());
			query.setString(11, jbClient.getCelPhone());
			query.setString(12, jbClient.getEmail());
			query.setString(13, jbClient.getIRS());
			query.setString(14, jbClient.getComentary());
			query.setInt(15, jbClient.getIDCredit());
			query.executeUpdate();
			state = true;
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
			state = false;
		}
		
		return state;
	}
	
	public JBClients getClientByID(int id_client) {
		JBClients jbClient = new JBClients();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
				  + "id_client, "
			      + "names, "
			      + "first_name, "
			      + "last_name, "
			      + "street, "
			      + "ext_number, "
			      + "int_number, "
			      + "colony, "
			      + "city, "
			      + "post_code, "
			      + "land_line, "
			      + "cel_phone, "
			      + "email, "
			      + "irs, "
			      + "comentary "
			 + "FROM "
			      + "clients_client "
			 + "WHERE "
			      + "id_client = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_client);
			result = query.executeQuery();
			while(result.next()) {
				jbClient.setIDClient(result.getInt("id_client"));
				jbClient.setNames(result.getString("names"));
				jbClient.setFirstName(result.getString("first_name"));
				jbClient.setLastName(result.getString("last_name"));
				jbClient.setStreet(result.getString("street"));
				jbClient.setExtNumber(result.getString("ext_number"));
				jbClient.setIntNumber(result.getString("int_number"));
				jbClient.setColony(result.getString("colony"));
				jbClient.setCity(result.getString("city"));
				jbClient.setPostCode(result.getString("post_code"));
				jbClient.setLandLine(result.getString("land_line"));
				jbClient.setCelPhone(result.getString("cel_phone"));
				jbClient.setEmail(result.getString("email"));
				jbClient.setIRS(result.getString("irs"));
				jbClient.setComentary(result.getString("comentary"));
			}
			dbconn.closeConnection();
			query.close();
			result.close();
			sql = "";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jbClient;
	}
	
	public JBClients getClientByName(JBClients jbClientRecived) {
		JBClients jbClient = new JBClients();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
				  + "id_client, "
			      + "names, "
			      + "first_name, "
			      + "last_name, "
			      + "street, "
			      + "ext_number, "
			      + "int_number, "
			      + "colony, "
			      + "city, "
			      + "post_code, "
			      + "land_line, "
			      + "cel_phone, "
			      + "email, "
			      + "irs, "
			      + "comentary "
			 + "FROM "
			      + "clients_client "
			 + "WHERE "
			      + "names = ? "
			 + "AND "
			      + "first_name = ? "
			 + "AND "
			      + "last_name = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbClientRecived.getNames());
			query.setString(2, jbClientRecived.getFirstName());
			query.setString(3, jbClientRecived.getLastName());
			result = query.executeQuery();
			while(result.next()) {
				jbClient.setIDClient(result.getInt("id_client"));
				jbClient.setNames(result.getString("names"));
				jbClient.setFirstName(result.getString("first_name"));
				jbClient.setLastName(result.getString("last_name"));
				jbClient.setStreet(result.getString("street"));
				jbClient.setExtNumber(result.getString("ext_number"));
				jbClient.setIntNumber(result.getString("int_number"));
				jbClient.setColony(result.getString("colony"));
				jbClient.setCity(result.getString("city"));
				jbClient.setPostCode(result.getString("post_code"));
				jbClient.setLandLine(result.getString("land_line"));
				jbClient.setCelPhone(result.getString("cel_phone"));
				jbClient.setEmail(result.getString("email"));
				jbClient.setIRS(result.getString("irs"));
				jbClient.setComentary(result.getString("comentary"));
			}
			dbconn.closeConnection();
			query.close();
			result.close();
			sql = "";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jbClient;
	}
	
	public boolean updateClient(JBClients jbDataClient) {
		DBConnection dbconn = new DBConnection();
		sql = "UPDATE "
			       + "clients_client "
			  + "SET "
			       + "names = ?, "
			       + "first_name = ?, "
			       + "last_name = ?, "
			       + "street = ?, "
			       + "ext_number = ?, "
			       + "int_number = ?, "
			       + "colony = ?, "
			       + "city = ?, "
			       + "post_code = ?, "
			       + "land_line = ?, "
			       + "cel_phone = ?, "
			       + "email = ?, "
			       + "irs = ?, "
			       + "comentary = ? "
			  + "WHERE "
			       + "id_client = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbDataClient.getNames());
			query.setString(2, jbDataClient.getFirstName());
			query.setString(3, jbDataClient.getLastName());
			query.setString(4, jbDataClient.getStreet());
			query.setString(5, jbDataClient.getExtNumber());
			query.setString(6, jbDataClient.getIntNumber());
			query.setString(7, jbDataClient.getColony());
			query.setString(8, jbDataClient.getCity());
			query.setString(9, jbDataClient.getPostCode());
			query.setString(10, jbDataClient.getLandLine());
			query.setString(11, jbDataClient.getCelPhone());
			query.setString(12, jbDataClient.getEmail());
			query.setString(13, jbDataClient.getIRS());
			query.setString(14, jbDataClient.getComentary());
			query.setInt(15, jbDataClient.getIDClient());
			query.executeUpdate();
			state = true;
			dbconn.closeConnection();
			query.close();
			result.close();
			sql = "";
		} catch (SQLException e) {
			state = false;
			e.printStackTrace();
		}
		
		return state;
	}
	
	public boolean deleteClient(String id_client) {
		DBConnection dbconn = new DBConnection();
		sql = "DELETE FROM "
			      + "clients_client "
			 + "WHERE "
			      + "id_client = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, id_client);
			query.executeUpdate();
			state = true;
		} catch (SQLException e) {
			state = false;
			e.printStackTrace();
		}
		
		return state;
	}
	
	public int getClientId(JBClients jbClient) {
		int id = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_client "
			  + "FROM "
			       + "clients_client "
			  + "WHERE "
			       + "names = ? "
			  + "AND "
			       + "first_name = ? "
			  + "AND "
			       + "last_name = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbClient.getNames());
			query.setString(2, jbClient.getFirstName());
			query.setString(3, jbClient.getLastName());
			result = query.executeQuery();
			while(result.next()) {
				id = result.getInt("id_client");
			}
			dbconn.closeConnection();
			query.close();
			result.close();
			sql = "";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
}
