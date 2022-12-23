package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class DBConnection {
	//Attributes
	private static final String server = "jdbc:mysql://localhost/";
	private static final String user = "root";
	private static final String password = "";
	private static final String database = "pv_packpc";
	private Connection dbconn;
	//Constructor
	public DBConnection() {
		try {
			//Get MySQL Driver
			Class.forName("com.mysql.jdbc.Driver");
			//Establish the Connection
			dbconn = DriverManager.getConnection(server + database, user, password);
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	//Method that RETURNS the Connection
	public Connection getConnection() {
		return dbconn;
	}
	//Method that CLOSE the Connection
	public void closeConnection() {
		dbconn = null;
	}
}
