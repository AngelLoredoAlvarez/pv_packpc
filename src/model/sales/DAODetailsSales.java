package model.sales;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DBConnection;

public class DAODetailsSales {
	//Attributes
	private PreparedStatement query;
	private ResultSet result;
	private String sql = "";
	private boolean status;
	
	public boolean saveDetailsSale(JBDetailsSales jbDetailsSales) {
		DBConnection dbconn = new DBConnection();
		sql = "INSERT INTO "
		           + "sales_details("
		           + "id_product, "
		           + "quantity, "
		           + "import, "
		           + "id_sale) "
		     + "VALUES(?, ?, ?, ?);";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, jbDetailsSales.getIDProduct());
			query.setDouble(2, jbDetailsSales.getQuantity());
			query.setDouble(3, jbDetailsSales.getImportPrice());
			query.setInt(4, jbDetailsSales.getIDSell());
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
	
	public ArrayList<JBDetailsSales> getDetails(int id_sale) {
		ArrayList<JBDetailsSales> detailsSell = new ArrayList<JBDetailsSales>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "inventories_products_services.description, "
			       + "inventories_products_services.unit_measurement, "
			       + "inventories_products_services.sale_price, "
			       + "sales_details.quantity, "
			       + "sales_details.import "
			+ "FROM "
			     + "inventories_products_services "
			+ "INNER JOIN "
			     + "sales_details "
		    + "WHERE "
		         + "inventories_products_services.id_product_service = sales_details.id_product "
		    + "AND "
		         + "id_sale = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_sale);
			result = query.executeQuery();
			while(result.next()) {
				JBDetailsSales jbDetailsSales = new JBDetailsSales();
				jbDetailsSales.setDescription(result.getString(1));
				jbDetailsSales.setUnitMeasurement(result.getString(2));
				jbDetailsSales.setSellPrice(result.getDouble(3));
				jbDetailsSales.setQuantity(result.getDouble(4));
				jbDetailsSales.setImportPrice(result.getDouble(5));
				detailsSell.add(jbDetailsSales);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return detailsSell;
	}
	
	//Method that RETURNS the IDs, quantity and the import from the Sold Products
	public ArrayList<JBDetailsSales> getSoldProducts(int id_sale) {
		ArrayList<JBDetailsSales> alSoldProducts = new ArrayList<JBDetailsSales>();
		DBConnection dbconn = new DBConnection();
		sql = "SELECT "
			       + "id_product, "
			       + "quantity, "
			       + "import "
			  + "FROM "
			       + "sales_details "
			  + "WHERE "
			       + "id_sale = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_sale);
			result = query.executeQuery();
			while(result.next()) {
				JBDetailsSales jbDetailSale = new JBDetailsSales();
				jbDetailSale.setIDProduct(result.getInt("id_product"));
				jbDetailSale.setQuantity(result.getDouble("quantity"));
				jbDetailSale.setImportPrice(result.getDouble("import"));
				alSoldProducts.add(jbDetailSale);
			}
			dbconn.closeConnection();
			sql = "";
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alSoldProducts;
	}
}
