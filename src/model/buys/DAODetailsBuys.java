package model.buys;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DBConnection;

public class DAODetailsBuys {
	//Attributes
	private PreparedStatement query;
	private ResultSet result;
	private String sql = "";
	private boolean status;
	
	//Method that SAVES the Detail from the Buy
	public boolean saveDetail(JBDetailsBuys jbDetailBuy) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
			       + "buys_details("
			            + "id_product, "
			            + "actual_price, "
			            + "quantity, "
			            + "import, "
			            + "id_buy) "
			  + "VALUES(?, ?, ?, ?, ?);";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, jbDetailBuy.getIDProduct());
			query.setDouble(2, jbDetailBuy.getActualPrice());
			query.setString(3, jbDetailBuy.getQuantity());
			query.setDouble(4, jbDetailBuy.getImporte());
			query.setInt(5, jbDetailBuy.getIDBuy());
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
	
	//Method that RETURNS the Details from the Buy
	public ArrayList<JBDetailsBuys> getDetails(int id_buy) {
		ArrayList<JBDetailsBuys> alDetailsBuy = new ArrayList<JBDetailsBuys>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "inventories_products_services.description, "
			       + "buys_details.quantity, "
			       + "buys_details.import "
			  + "FROM "
			       + "inventories_products_services "
			  + "INNER JOIN "
			       + "buys_details "
			  + "ON "
			       + "inventories_products_services.id_product_service = buys_details.id_product "
			  + "WHERE "
			       + "buys_details.id_buy = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_buy);
			result = query.executeQuery();
			while(result.next()) {
				JBDetailsBuys jbDetailBuy = new JBDetailsBuys();
				jbDetailBuy.setDescription(result.getString(1));
				jbDetailBuy.setQuantity(result.getString(2));
				jbDetailBuy.setImporte(result.getDouble(3));
				alDetailsBuy.add(jbDetailBuy);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alDetailsBuy;
	}
}
