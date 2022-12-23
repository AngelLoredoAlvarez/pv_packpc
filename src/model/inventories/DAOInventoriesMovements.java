package model.inventories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DBConnection;

public class DAOInventoriesMovements {
	//Attributes
	private PreparedStatement query = null;
	private ResultSet result = null;
	private boolean status;
	private String sql = "";
	
	//Method that SAVES the Movement
	public boolean saveInventoryMovement(JBInventoriesMovements jbInventoryMovement) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
			       + "inventories_movements("
			            + "date, "
			            + "time, "
			            + "id_product, "
			            + "existed, "
			            + "type_movement, "
			            + "quantity, "
			            + "id_cashier) "
			  + "VALUES(?, ?, ?, ?, ?, ?, ?);";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbInventoryMovement.getDate());
			query.setString(2, jbInventoryMovement.getTime());
			query.setInt(3, jbInventoryMovement.getIdProduct());
			query.setDouble(4, jbInventoryMovement.getExisted());
			query.setString(5, jbInventoryMovement.getMovement());
			query.setDouble(6, jbInventoryMovement.getQuantity());
			query.setInt(7, jbInventoryMovement.getIdCashier());
			query.executeUpdate();
			dbconn.closeConnection();
			query.close();
			sql = "";
			status = true;
		} catch (SQLException e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}
	
	//Method that RETRIVES all the Inventories Movements by Date
	public ArrayList<JBInventoriesMovements> getInventoriesMovementsByDate(String actualDate) {
		ArrayList<JBInventoriesMovements> alInventoriesMovements = new ArrayList<JBInventoriesMovements>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "inventories_movements.time, "
			       + "inventories_products_services.description, "
			       + "inventories_products_services.unit_measurement,"
			       + "inventories_movements.existed, "
			       + "inventories_movements.type_movement, "
			       + "inventories_movements.quantity, "
			       + "cashiers_cashier.names, "
			       + "cashiers_cashier.first_name, "
			       + "cashiers_cashier.last_name "
			  + "FROM "
			       + "inventories_movements "
			  + "INNER JOIN "
			       + "inventories_products_services "
			  + "ON "
			       + "inventories_movements.id_product = inventories_products_services.id_product_service "
			  + "INNER JOIN "
			       + "cashiers_cashier "
			  + "ON "
			       + "inventories_movements.id_cashier = cashiers_cashier.id_cashier "
			  + "WHERE "
			       + "date = ? "
			  + "ORDER BY "
			       + "inventories_movements.id_movement "
			  + "DESC;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, actualDate);
			result = query.executeQuery();
			while(result.next()) {
				JBInventoriesMovements jbInventoryMovement = new JBInventoriesMovements();
				jbInventoryMovement.setTime(result.getString(1));
				jbInventoryMovement.setDescription(result.getString(2));
				jbInventoryMovement.setUnitMeasurement(result.getString(3));
				jbInventoryMovement.setExisted(result.getDouble(4));
				jbInventoryMovement.setMovement(result.getString(5));
				jbInventoryMovement.setQuantity(result.getDouble(6));
				jbInventoryMovement.setCashier(result.getString(7) + " " + result.getString(8) + " " + result.getString(9));
				alInventoriesMovements.add(jbInventoryMovement);
			}
			dbconn.closeConnection();
			query.close();
			result.close();
			sql = "";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alInventoriesMovements;
	}
	
	//Method that FILTERS the Movements According the Selected DATE and the Selected MOVEMENT
	public ArrayList<JBInventoriesMovements> filterInventoriesMovements(JBInventoriesMovements jbInventoryMovementData) {
		ArrayList<JBInventoriesMovements> alInventoriesMovements = new ArrayList<JBInventoriesMovements>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "inventories_movements.date, "
			       + "inventories_movements.time, "
			       + "inventories_products_services.description, "
			       + "inventories_products_services.unit_measurement,"
			       + "inventories_movements.existed, "
			       + "inventories_movements.type_movement, "
			       + "inventories_movements.quantity, "
			       + "cashiers_cashier.names "
			  + "FROM "
			       + "inventories_movements "
			  + "INNER JOIN "
			       + "inventories_products_services "
			  + "ON "
			       + "inventories_movements.id_product = inventories_products_services.id_product_service "
			  + "INNER JOIN "
			       + "cashiers_cashier "
			  + "ON "
			       + "inventories_movements.id_cashier = cashiers_cashier.id_cashier "
			  + "WHERE "
			       + "date = ? "
			  + "AND "
			       + "type_movement = ? "
			  + "ORDER BY "
			       + "inventories_movements.id_movement "
			  + "DESC;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbInventoryMovementData.getDate());
			query.setString(2, jbInventoryMovementData.getMovement());
			result = query.executeQuery();
			while(result.next()){
				JBInventoriesMovements jbInventoryMovement = new JBInventoriesMovements();
				jbInventoryMovement.setDate(result.getString(1));
				jbInventoryMovement.setTime(result.getString(2));
				jbInventoryMovement.setDescription(result.getString(3));
				jbInventoryMovement.setUnitMeasurement(result.getString(4));
				jbInventoryMovement.setExisted(result.getDouble(5));
				jbInventoryMovement.setMovement(result.getString(6));
				jbInventoryMovement.setQuantity(result.getDouble(7));
				jbInventoryMovement.setCashier(result.getString(8));
				alInventoriesMovements.add(jbInventoryMovement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alInventoriesMovements;
	}
}
