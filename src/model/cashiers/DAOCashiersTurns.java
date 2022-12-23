package model.cashiers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DBConnection;

public class DAOCashiersTurns {
	//Attributes
	private PreparedStatement query = null;
	private ResultSet result = null;
	private String sql = "";
	private boolean status;
	
	//Method that SAVES the TURN
	public void saveTurn(JBCashiersTurns jbCashierTurn) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
			        + "cashiers_turn("
				         + "start_date, "
				         + "start_time, "
				         + "end_date, "
				         + "end_time, "
				         + "status, "
				         + "id_cashier) "
			   + "VALUES(?, ?, ?, ?, ?, ?);";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbCashierTurn.getStartDate());
			query.setString(2, jbCashierTurn.getStartTime());
			query.setString(3, jbCashierTurn.getEndDate());
			query.setString(4, jbCashierTurn.getEndTime());
			query.setString(5, jbCashierTurn.getStatus());
			query.setInt(6, jbCashierTurn.getIDCashier());
			query.executeUpdate();
			dbconn.closeConnection();
			sql = "";
			query.close();
			status = true;
		} catch (SQLException e) {
			e.printStackTrace();
			status = false;
		}
	}
	
	//Method that CHECKS if there's any UNCLOSED TURN and RETRIVES the ID CASHIER from that TURN
	public int checkUnclosedTurn() {
		int id_cashier = 0;
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_cashier "
			  + "FROM "
			       + "cashiers_turn "
			  + "WHERE "
			       + "status = ? "
			  + "ORDER BY "
			       + "id_cashier "
			  + "DESC "
			  + "LIMIT "
			       + "1;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, "Abierto");
			result = query.executeQuery();
			while(result.next()) {
				id_cashier = result.getInt("id_cashier");
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
	
	//Method that RETURNS the Start Time
	public String getStartTime(JBCashiersTurns jbCashierTurn) {		
		String start_time = "";
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "start_time "
			  + "FROM "
			       + "cashiers_turn "
			  + "WHERE "
			       + "start_date = ? "
			  + "AND "
			       + "id_cashier = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbCashierTurn.getStartDate());
			query.setInt(2, jbCashierTurn.getIDCashier());
			result = query.executeQuery();
			while(result.next()) {
				start_time = result.getString("start_time");
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return start_time;
	}
	
	//Method that CLOSE the Actual TURN
	public boolean closeTurn(JBCashiersTurns jbCashierTurn) {
		DBConnection dbconn = new DBConnection();
		sql = "UPDATE "
			       + "cashiers_turn "
			  + "SET "
			       + "end_date = ?, "
			       + "end_time = ?, "
			       + "status = ? "
			  + "WHERE "
			       + "start_date = ? "
			  + "AND "
			       + "start_time = ? "
			  + "AND "
			       + "id_cashier = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbCashierTurn.getEndDate());
			query.setString(2, jbCashierTurn.getEndTime());
			query.setString(3, jbCashierTurn.getStatus());
			query.setString(4, jbCashierTurn.getStartDate());
			query.setString(5, jbCashierTurn.getStartTime());
			query.setInt(6, jbCashierTurn.getIDCashier());
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
	
	//Method that RETURNS the DATA needed to CLOSE the TURN
	public JBCashiersTurns getInitTurnInfo(String status, int id_cashier) {
		JBCashiersTurns jbDataCloseTurn = new JBCashiersTurns();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "start_date, "
			       + "start_time "
			  + "FROM "
			       + "cashiers_turn "
			  + "WHERE "
			       + "status = ? "
			  + "AND "
			       + "id_cashier = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, status);
			query.setInt(2, id_cashier);
			result = query.executeQuery();
			while(result.next()) {
				jbDataCloseTurn.setStartDate(result.getString("start_date"));
				jbDataCloseTurn.setStartTime(result.getString("start_time"));
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jbDataCloseTurn;
	}
	
	//Method that RETRIVES the Differents TURNS in a Specific Date
	public ArrayList<JBCashiersTurns> getTurnsCashier(JBCashiersTurns jbCashierTurn) {
		ArrayList<JBCashiersTurns> alCashierTurns = new ArrayList<JBCashiersTurns>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "start_time, "
			       + "end_time "
			  + "FROM "
			       + "cashiers_turn "
			  + "WHERE "
			       + "start_date = ? "
			  + "AND "
			       + "end_date = ? "
			  + "AND "
			       + "id_cashier = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbCashierTurn.getStartDate());
			query.setString(2, jbCashierTurn.getEndDate());
			query.setInt(3, jbCashierTurn.getIDCashier());
			result = query.executeQuery();
			while(result.next()) {
				JBCashiersTurns jbCashierTurnToSend = new JBCashiersTurns();
				jbCashierTurnToSend.setStartTime(result.getString("start_time"));
				jbCashierTurnToSend.setEndTime(result.getString("end_time"));
				alCashierTurns.add(jbCashierTurnToSend);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alCashierTurns;
	}
}
