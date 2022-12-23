package model.inventories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DBConnection;

public class DAODepartments {
	//Attributes
	private PreparedStatement query = null;
	private ResultSet result = null;
	private boolean status;
	private String sql = "";
	
	//Method that SAVES the Department
	public boolean saveDepartment(JBDepartments jbDepartment) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO departments_department(department) VALUES(?);";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbDepartment.getDepartment());
			query.executeUpdate();
			dbconn.closeConnection();
			sql = "";
			query.close();
			status = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
	//Method that RETRIVES all the Departments
	public ArrayList<JBDepartments> getDepartments() {
		ArrayList<JBDepartments> alDepartments = new ArrayList<JBDepartments>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_department, "
			       + "department "
			  + "FROM "
			       + "departments_department;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			result = query.executeQuery();
			while(result.next()) {
				JBDepartments jbDepartment = new JBDepartments();
				jbDepartment.setIDDepartment(result.getInt(1));
				jbDepartment.setDepartment(result.getString(2));
				alDepartments.add(jbDepartment);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alDepartments;
	}
	
	//Method that CHECKS if the Department already Exist
	public boolean checkDepartment(String department) {
		DBConnection dbconn = new DBConnection();
		sql = "SELECT COUNT(*) FROM "
			       + "departments_department "
			  + "WHERE "
			       + "department "
			  + "LIKE "
			       + "?;";
		int quantity = 0;
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, department);
			result = query.executeQuery();
			while(result.next()) {
				quantity = result.getInt(1);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
			if(quantity == 0)
				status = false;
			else
				status = true;
		} catch (SQLException e) {
			e.printStackTrace();
			status = false;
		}
		
		return status;
	}
	
	//Get Department by Name
	public JBDepartments getDepartmentByName(String department) {
		DBConnection dbconn = new DBConnection();
		JBDepartments jbDepartment = new JBDepartments();
		sql = "SELECT "
			       + "id_department, "
			       + "department "
			  + "FROM "
			       + "departments_department "
			  + "WHERE "
			       + "department "
			  + "LIKE "
			       + "?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, department);
			result = query.executeQuery();
			while(result.next()) {
				jbDepartment.setIDDepartment(result.getInt(1));
				jbDepartment.setDepartment(result.getString(2));
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jbDepartment;
	}
	
	//Method that UPDATES the Department
	public boolean updateDepartment(JBDepartments jbDepartment) {
		DBConnection dbconn = new DBConnection();
		sql = "UPDATE "
			       + "departments_department "
			  + "SET "
			       + "department = ? "
			  + "WHERE "
			       + "id_department = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbDepartment.getDepartment());
			query.setInt(2, jbDepartment.getIDDepartment());
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
	
	//Method that DELETES the Department
	public boolean deleteDepartment(int id_department) {
		DBConnection dbconn = new DBConnection();
		sql = "DELETE FROM "
			       + "departments_department "
			  + "WHERE "
			       + "id_department = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_department);
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
