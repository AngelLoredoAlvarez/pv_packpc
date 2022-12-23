package model.business_information;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.DBConnection;

public class DAOBusinessInformation {
	//Attributes
	private PreparedStatement query;
	private ResultSet result;
	private String sql = "";
	private boolean status;
	
	//Method that SAVES the Business Information
	public boolean saveBusinessInformation(JBBusinessInformation jbBusinessInformation) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
			       + "business_information("
			            + "name_business, "
			            + "company_name, "
			            + "irs, "
			            + "street, "
			            + "ext_number, "
			            + "int_number, "
			            + "colony, "
			            + "city, "
			            + "post_code, "
			            + "email, "
			            + "certificate_route, "
			            + "key_route, "
			            + "pass_key) "
			  + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbBusinessInformation.getNameBusiness());
			query.setString(2, jbBusinessInformation.getCompanyName());
			query.setString(3, jbBusinessInformation.getIRS());
			query.setString(4, jbBusinessInformation.getStreet());
			query.setString(5, jbBusinessInformation.getExtNumber());
			query.setString(6, jbBusinessInformation.getIntNumber());
			query.setString(7, jbBusinessInformation.getColony());
			query.setString(8, jbBusinessInformation.getCity());
			query.setString(9, jbBusinessInformation.getPostCode());
			query.setString(10, jbBusinessInformation.getEmail());
			query.setString(11, jbBusinessInformation.getCertificateRoute());
			query.setString(12, jbBusinessInformation.getPrivateKeyRoute());
			query.setString(13, jbBusinessInformation.getPrivateKeyPass());
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
	
	//Method that GETS the Business Information
	public JBBusinessInformation retriveBusinessInformation() {
		DBConnection dbconn = new DBConnection();
		JBBusinessInformation jbBusinessInformation = new JBBusinessInformation();
		sql = "SELECT "
			       + "id_business_information, "
			       + "name_business, "
			       + "company_name, "
			       + "irs, "
			       + "street, "
			       + "ext_number, "
			       + "int_number, "
			       + "colony, "
			       + "city, "
			       + "post_code, "
			       + "email, "
			       + "certificate_route, "
			       + "key_route, "
			       + "pass_key "
			  + "FROM "
			       + "business_information;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			result = query.executeQuery();
			while(result.next()) {
				jbBusinessInformation.setIDBusinessInformation(result.getInt("id_business_information"));
				jbBusinessInformation.setNameBusiness(result.getString("name_business"));
				jbBusinessInformation.setCompanyName(result.getString("company_name"));
				jbBusinessInformation.setIRS(result.getString("irs"));
				jbBusinessInformation.setStreet(result.getString("street"));
				jbBusinessInformation.setExtNumber(result.getString("ext_number"));
				jbBusinessInformation.setIntNumber(result.getString("int_number"));
				jbBusinessInformation.setColony(result.getString("colony"));
				jbBusinessInformation.setCity(result.getString("city"));
				jbBusinessInformation.setPostCode(result.getString("post_code"));
				jbBusinessInformation.setEmail(result.getString("email"));
				jbBusinessInformation.setCertificateRoute(result.getString("certificate_route"));
				jbBusinessInformation.setPrivateKeyRoute(result.getString("key_route"));
				jbBusinessInformation.setPassKey(result.getString("pass_key"));
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jbBusinessInformation;
	}
	
	//Method that CHECKS if there's any info on the table business_information
	public int retriveQuantityRegisters() {
		DBConnection dbconn = new DBConnection();
		int quantityRegisters = 0;
		sql = "SELECT COUNT(id_business_information) FROM business_information;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			result = query.executeQuery();
			while(result.next()) {
				quantityRegisters = result.getInt(1);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return quantityRegisters;
	}
	
	//Method that UPDATES the Business Information
	public boolean updateBusinessInformation(int id_business_information, JBBusinessInformation jbBusinessInformation) {
		DBConnection dbconn = new DBConnection();
		sql = "UPDATE "
			       + "business_information "
			  + "SET "
			       + "name_business = ?, "
			       + "company_name = ?, "
			       + "irs = ?, "
			       + "street = ?, "
			       + "ext_number = ?, "
			       + "int_number = ?, "
			       + "colony = ?, "
			       + "city = ?, "
			       + "post_code = ?, "
			       + "email = ?, "
			       + "certificate_route = ?, "
			       + "key_route = ?, "
			       + "pass_key = ? "
			  + "WHERE "
			       + "id_business_information = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbBusinessInformation.getNameBusiness());
			query.setString(2, jbBusinessInformation.getCompanyName());
			query.setString(3, jbBusinessInformation.getIRS());
			query.setString(4, jbBusinessInformation.getStreet());
			query.setString(5, jbBusinessInformation.getExtNumber());
			query.setString(6, jbBusinessInformation.getIntNumber());
			query.setString(7, jbBusinessInformation.getColony());
			query.setString(8, jbBusinessInformation.getCity());
			query.setString(9, jbBusinessInformation.getPostCode());
			query.setString(10, jbBusinessInformation.getEmail());
			query.setString(11, jbBusinessInformation.getCertificateRoute());
			query.setString(12, jbBusinessInformation.getPrivateKeyRoute());
			query.setString(13, jbBusinessInformation.getPrivateKeyPass());
			query.setInt(14, id_business_information);
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
