package model.inventories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.DBConnection;

public class DAOInventoriesProductService {
	//To get and Individual Product by BarCode
	public JBInventoriesProductService getProductByBarCode(String barcode) {
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		ResultSet result = null;
		String sql = "SELECT "
		        + "inventories_products_services.id_product_service, "
				+ "inventories_products_services.barcode, "
				+ "inventories_products_services.description, "
				+ "inventories_products_services.unit_measurement, "
				+ "departments_department.id_department, "
				+ "departments_department.department, "
				+ "inventories_products_services.iva, "
				+ "inventories_products_services.buy_price, "
				+ "inventories_products_services.sale_price, "
				+ "inventories_products_services.stock, "
				+ "inventories_products_services.minimum_stock "
			+ "FROM "
				+ "inventories_products_services "
	        + "INNER JOIN "
	             + "departments_department "
	        + "ON "
	             + "inventories_products_services.id_department = departments_department.id_department "
			+ "WHERE "
				+ "inventories_products_services.barcode = ?;";
		
		JBInventoriesProductService jbProductService = new JBInventoriesProductService();
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, barcode);
			result = query.executeQuery();
			while(result.next()) {
				jbProductService.setIDProductService(result.getInt(1));
				jbProductService.setBarcode(result.getString(2));
				jbProductService.setDescription(result.getString(3));
				jbProductService.setUnitmeasurement(result.getString(4));
				jbProductService.setIDDepartment(result.getInt(5));
				jbProductService.setDepartment(result.getString(6));
				jbProductService.setIva(result.getDouble(7));
				jbProductService.setBuyprice(result.getDouble(8));
				jbProductService.setSaleprice(result.getDouble(9));
				jbProductService.setStock(result.getDouble(10));
				jbProductService.setMinstock(result.getDouble(11));
			}
			dbconn.closeConnection();
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jbProductService;
	}
	
	//To get a Product by Description
	public JBInventoriesProductService getProductByDescription(String description) {
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		ResultSet result = null;
		String sql = "SELECT "
		        + "inventories_products_services.id_product_service, "
				+ "inventories_products_services.barcode, "
				+ "inventories_products_services.description, "
				+ "inventories_products_services.unit_measurement, "
				+ "departments_department.id_department, "
				+ "departments_department.department, "
				+ "inventories_products_services.buy_price, "
				+ "inventories_products_services.sale_price, "
				+ "inventories_products_services.stock, "
				+ "inventories_products_services.minimum_stock "
			+ "FROM "
				+ "inventories_products_services "
	        + "INNER JOIN "
	             + "departments_department "
	        + "ON "
	             + "inventories_products_services.id_department = departments_department.id_department "
			+ "WHERE "
				+ "inventories_products_services.description = ?;";
		
		JBInventoriesProductService jbProductService = new JBInventoriesProductService();
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, description);
			result = query.executeQuery();
			while(result.next()) {
				jbProductService.setIDProductService(result.getInt(1));
				jbProductService.setBarcode(result.getString(2));
				jbProductService.setDescription(result.getString(3));
				jbProductService.setUnitmeasurement(result.getString(4));
				jbProductService.setIDDepartment(result.getInt(5));
				jbProductService.setDepartment(result.getString(6));
				jbProductService.setIva(result.getDouble(7));
				jbProductService.setBuyprice(result.getDouble(8));
				jbProductService.setSaleprice(result.getDouble(9));
				jbProductService.setStock(result.getDouble(10));
				jbProductService.setMinstock(result.getDouble(11));
			}
			dbconn.closeConnection();
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return jbProductService;
	}
	
	//To GET Everything
	public ArrayList<JBInventoriesProductService> getEverything() {
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		ResultSet result = null;
		ArrayList<JBInventoriesProductService> alEverything = new ArrayList<JBInventoriesProductService>();
		String sql = "SELECT "
				        + "inventories_products_services.id_product_service, "
						+ "inventories_products_services.barcode, "
						+ "inventories_products_services.description, "
						+ "inventories_products_services.unit_measurement, "
						+ "departments_department.department, "
						+ "inventories_products_services.buy_price, "
						+ "inventories_products_services.sale_price, "
						+ "inventories_products_services.minimum_stock, "
						+ "inventories_products_services.stock "
					+ "FROM "
					    + "inventories_products_services "
				    + "INNER JOIN "
				         + "departments_department "
				    + "ON "
				         + "inventories_products_services.id_department = departments_department.id_department;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			result = query.executeQuery();
			while(result.next()) {
				JBInventoriesProductService jbProduct = new JBInventoriesProductService();
				jbProduct.setIDProductService(result.getInt(1));
				jbProduct.setBarcode(result.getString(2));
				jbProduct.setDescription(result.getString(3));
				jbProduct.setUnitmeasurement(result.getString(4));
				jbProduct.setDepartment(result.getString(5));
				jbProduct.setBuyprice(result.getDouble(6));
				jbProduct.setSaleprice(result.getDouble(7));
				jbProduct.setMinstock(result.getDouble(8));
				jbProduct.setStock(result.getDouble(9));
				alEverything.add(jbProduct);
			}
			result.close();
			dbconn.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alEverything;
	}
	
	//To get All the Products according their Provider
	public ArrayList<JBInventoriesProductService> getProductsByProvider(int id_provider) {
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		ResultSet result = null;
		ArrayList<JBInventoriesProductService> alProductsByProvider = new ArrayList<JBInventoriesProductService>();
		String sql = "SELECT "
				        + "inventories_products_services.id_product_service, "
						+ "inventories_products_services.barcode, "
						+ "inventories_products_services.description, "
						+ "inventories_products_services.unit_measurement, "
						+ "departments_department.department, "
						+ "inventories_products_services.buy_price, "
						+ "inventories_products_services.sale_price, "
						+ "inventories_products_services.minimum_stock, "
						+ "inventories_products_services.stock "
					+ "FROM "
					    + "inventories_products_services "
				    + "INNER JOIN "
				         + "departments_department "
				    + "ON "
				         + "inventories_products_services.id_department = departments_department.id_department "
				    + "INNER JOIN "
				         + "product_providers "
				    + "ON "
				         + "inventories_products_services.id_product_service = product_providers.id_product "
				    + "WHERE "
				    	+ "product_providers.id_provider = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_provider);
			result = query.executeQuery();
			while(result.next()) {
				JBInventoriesProductService jbProduct = new JBInventoriesProductService();
				jbProduct.setIDProductService(result.getInt(1));
				jbProduct.setBarcode(result.getString(2));
				jbProduct.setDescription(result.getString(3));
				jbProduct.setUnitmeasurement(result.getString(4));
				jbProduct.setDepartment(result.getString(5));
				jbProduct.setBuyprice(result.getDouble(6));
				jbProduct.setSaleprice(result.getDouble(7));
				jbProduct.setMinstock(result.getDouble(8));
				jbProduct.setStock(result.getDouble(9));
				alProductsByProvider.add(jbProduct);
			}
			dbconn.closeConnection();
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alProductsByProvider;
	}
	
	//To SAVE a NEW Product or Service
	public boolean saveProductService(JBInventoriesProductService jbProductService) {
		boolean inserted = false;
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		String sql = "INSERT INTO "
					+ "inventories_products_services("
						+ "barcode, "
						+ "description, "
						+ "unit_measurement, "
						+ "id_department, "
						+ "iva, "
						+ "buy_price, "
						+ "sale_price, "
						+ "stock, "
						+ "minimum_stock)"
					+ "VALUES"
						+ "(?, ?, ?, ?, ?, ?, ?, ?, ?);";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbProductService.getBarcode());
			query.setString(2, jbProductService.getDescription());
			query.setString(3, jbProductService.getUnitmeasurement());
			query.setInt(4, jbProductService.getIDDepartment());
			query.setDouble(5, jbProductService.getIva());
			query.setDouble(6, jbProductService.getBuyprice());
			query.setDouble(7, jbProductService.getSellprice());
			query.setDouble(8, jbProductService.getStock());
			query.setDouble(9, jbProductService.getMinstock());
			query.executeUpdate();
			inserted = true;
			dbconn.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			inserted = false;
		}
		
		return inserted;
	}
	
	//Get the ID according the BarCode
	public int getIDProductService(String barcode) {
		int id_product = 0;
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		ResultSet result = null;
		String sql = "SELECT "
				          + "id_product_service "
				     + "FROM "
				          + "inventories_products_services "
				     + "WHERE "
				          + "barcode = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, barcode);
			result = query.executeQuery();
			while(result.next()) {
				id_product = result.getInt("id_product_service");
			}
			dbconn.closeConnection();
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id_product;
	}
	
	//To UPDATE the Info from a Product or Service
	public boolean updateProductService(JBInventoriesProductService jbProductService) {
		boolean updated = false;
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		String sql = "UPDATE "
						+ "inventories_products_services "
					+ "SET "
						+ "barcode = ?, "
						+ "description = ?, "
						+ "unit_measurement = ?, "
						+ "id_department = ?, "
						+ "iva = ?, "
						+ "buy_price = ?, "
						+ "sale_price = ?, "
						+ "stock = ?, "
						+ "minimum_stock = ? "
					+ "WHERE "
						+ "id_product_service = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, jbProductService.getBarcode());
			query.setString(2, jbProductService.getDescription());
			query.setString(3, jbProductService.getUnitmeasurement());
			query.setInt(4, jbProductService.getIDDepartment());
			query.setDouble(5, jbProductService.getIva());
			query.setDouble(6, jbProductService.getBuyprice());
			query.setDouble(7, jbProductService.getSellprice());
			query.setDouble(8, jbProductService.getStock());
			query.setDouble(9, jbProductService.getMinstock());
			query.setInt(10, jbProductService.getIDProductService());
			query.executeUpdate();
			dbconn.closeConnection();
			query.close();
			updated = true;
		} catch (SQLException e) {
			e.printStackTrace();
			updated = false;
		}
		
		return updated;
	}
	
	//To DELETE a Product
	public boolean deleteProductService(String barcode) {
		boolean deleted = false;
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		String sql = "DELETE FROM inventories_products_services WHERE barcode = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, barcode);
			query.executeUpdate();
			deleted = true;
			dbconn.closeConnection();
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
			deleted = false;
		}
		
		return deleted;
	}
	
	//To UPDATE the QUANTITY of the Product
	public double getActualStock(int id_product) {
		double actualStock = 0;
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		ResultSet result = null;
		String sql = "SELECT "
				          + "stock "
				     + "FROM "
				          + "inventories_products_services "
				     + "WHERE "
				          + "id_product_service = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_product);
			result = query.executeQuery();
			while(result.next()) {
				actualStock = result.getDouble("stock");
			}
			dbconn.closeConnection();
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return actualStock;
	}
	
	//Update the Stock
	public boolean updateStock(int id_product, double newStock) {
		boolean updateQuantity = false;
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		String sql = "UPDATE "
				          + "inventories_products_services "
				     + "SET "
				          + "stock = ? "
				     + "WHERE "
				          + "id_product_service = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setDouble(1, newStock);
			query.setInt(2, id_product);
			query.executeUpdate();
			updateQuantity = true;
			dbconn.closeConnection();
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
			updateQuantity = false;
		}
		
		return updateQuantity;
	}
	
	//Method that RETRIVES the Buy and Sale Price
	public double getBuyPrice(int id_product) {
		double buy_price = 0.0;
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		ResultSet result = null;
		String sql = "SELECT "
				          + "buy_price "
				     + "FROM "
				          + "inventories_products_services "
				     + "WHERE "
				          + "id_product_service = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_product);
			result = query.executeQuery();
			while(result.next()) {
				buy_price = result.getDouble("buy_price");
			}
			dbconn.closeConnection();
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return buy_price;
	}
	
	//Method that RETRIVES the Buy Price, and the Actual STOCK
	public ArrayList<JBInventoriesProductService> getInventoryCost() {
		ArrayList<JBInventoriesProductService> alInventoryCost = new ArrayList<JBInventoriesProductService>();
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		ResultSet result = null;
		String sql = "SELECT "
				          + "buy_price, "
				          + "stock "
				     + "FROM "
				          + "inventories_products_services "
				     + "WHERE("
				          + "unit_measurement = ? "
				     + "OR "
				          + "unit_measurement = ?);";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, "Pza");
			query.setString(2, "Granel");
			result = query.executeQuery();
			while(result.next()) {
				JBInventoriesProductService jbProduct = new JBInventoriesProductService();
				jbProduct.setBuyprice(result.getDouble("buy_price"));
				jbProduct.setStock(result.getDouble("stock"));
				alInventoryCost.add(jbProduct);
			}
			dbconn.closeConnection();
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alInventoryCost;
	}
	
	//Method that RETRIVES the total of Products
	public double getTotalProducts(String unit_measurement) {
		double totalProducts = 0.0;
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		ResultSet result = null;
		String sql = "SELECT "
				          + "SUM(stock) "
				     + "FROM "
				          + "inventories_products_services "
				     + "WHERE "
				          + "unit_measurement = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, unit_measurement);
			result = query.executeQuery();
			while(result.next()) {
				totalProducts = result.getDouble(1);
			}
			dbconn.closeConnection();
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return totalProducts;
	}
	
	//Method that RETRIVES all the Inventories that are about to get finish
	public ArrayList<JBInventoriesProductService> getInventoriesToFinish() {
		ArrayList<JBInventoriesProductService> alInventoriesToFinish = new ArrayList<JBInventoriesProductService>();
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		ResultSet result = null;
		String sql = "SELECT "
					      + "inventories_products_services.barcode, "
		                  + "inventories_products_services.description, "
		                  + "inventories_products_services.unit_measurement, "
		                  + "departments_department.department, "
		                  + "inventories_products_services.buy_price, "
		                  + "inventories_products_services.sale_price, "
		                  + "inventories_products_services.stock, "
		                  + "inventories_products_services.minimum_stock "
		             + "FROM "
		                  + "inventories_products_services "
		             + "INNER JOIN "
		                  + "departments_department "
		             + "ON "
		                  + "inventories_products_services.id_department = departments_department.id_department "
		             + "WHERE("
		                  + "inventories_products_services.unit_measurement = ? "
		             + "OR "
		                  + "inventories_products_services.unit_measurement = ?) "
		             + "AND "
		                  + "inventories_products_services.minimum_stock >= stock;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, "Pza");
			query.setString(2, "Granel");
			result = query.executeQuery();
			while(result.next()) {
				JBInventoriesProductService jbProduct = new JBInventoriesProductService();
				jbProduct.setBarcode(result.getString(1));
				jbProduct.setDescription(result.getString(2));
				jbProduct.setUnitmeasurement(result.getString(3));
				jbProduct.setDepartment(result.getString(4));
				jbProduct.setBuyprice(result.getDouble(5));
				jbProduct.setSaleprice(result.getDouble(6));
				jbProduct.setStock(result.getDouble(7));
				jbProduct.setMinstock(result.getDouble(8));
				alInventoriesToFinish.add(jbProduct);
			}
			dbconn.closeConnection();
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alInventoriesToFinish;
	}
	
	//Method that RETRIVES all the Inventories that are about to get finish by Provider
	public ArrayList<JBInventoriesProductService> getInventoriesToFinishByProvider(int id_provider) {
		ArrayList<JBInventoriesProductService> alInventoriesToFinish = new ArrayList<JBInventoriesProductService>();
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		ResultSet result = null;
		String sql = "SELECT "
					      + "inventories_products_services.barcode, "
		                  + "inventories_products_services.description, "
		                  + "inventories_products_services.unit_measurement, "
		                  + "departments_department.department, "
		                  + "inventories_products_services.buy_price, "
		                  + "inventories_products_services.sale_price, "
		                  + "inventories_products_services.stock, "
		                  + "inventories_products_services.minimum_stock "
		             + "FROM "
		                  + "inventories_products_services "
		             + "INNER JOIN "
		             	  + "departments_department "
		             + "ON "
		                  + "inventories_products_services.id_department = departments_department.id_department "
		             + "INNER JOIN "
		                  + "product_providers "
		             + "ON "
		                  + "inventories_products_services.id_product_service = product_providers.id_product "
		             + "WHERE("
		                  + "inventories_products_services.unit_measurement = ? "
		             + "OR "
		                  + "inventories_products_services.unit_measurement = ?) "
		             + "AND "
		                  + "inventories_products_services.stock <= inventories_products_services.minimum_stock "
		             + "AND "
		                  + "product_providers.id_provider = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, "Pza");
			query.setString(2, "Granel");
			query.setInt(3, id_provider);
			result = query.executeQuery();
			while(result.next()) {
				JBInventoriesProductService jbProduct = new JBInventoriesProductService();
				jbProduct.setBarcode(result.getString(1));
				jbProduct.setDescription(result.getString(2));
				jbProduct.setUnitmeasurement(result.getString(3));
				jbProduct.setDepartment(result.getString(4));
				jbProduct.setBuyprice(result.getDouble(5));
				jbProduct.setSaleprice(result.getDouble(6));
				jbProduct.setStock(result.getDouble(7));
				jbProduct.setMinstock(result.getDouble(8));
				alInventoriesToFinish.add(jbProduct);
			}
			dbconn.closeConnection();
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alInventoriesToFinish;
	}
	
	//Method that RETRIVES only the Products
	public ArrayList<JBInventoriesProductService> getProducts() {
		ArrayList<JBInventoriesProductService> alProducts = new ArrayList<JBInventoriesProductService>();
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		ResultSet result = null;
		String sql = "SELECT "
				        + "inventories_products_services.id_product_service, "
						+ "inventories_products_services.barcode, "
						+ "inventories_products_services.description, "
						+ "inventories_products_services.unit_measurement, "
						+ "departments_department.department, "
						+ "inventories_products_services.buy_price, "
						+ "inventories_products_services.sale_price, "
						+ "inventories_products_services.minimum_stock, "
						+ "inventories_products_services.stock "
					+ "FROM "
					    + "inventories_products_services "
					+ "INNER JOIN "
					     + "departments_department "
					+ "ON "
					     + "inventories_products_services.id_department = departments_department.id_department "
					+ "WHERE("
				        + "inventories_products_services.unit_measurement = ? "
				    + "OR "
				        + "inventories_products_services.unit_measurement = ?)";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, "Pza");
			query.setString(2, "Granel");
			result = query.executeQuery();
			while(result.next()) {
				JBInventoriesProductService jbProduct = new JBInventoriesProductService();
				jbProduct.setIDProductService(result.getInt(1));
				jbProduct.setBarcode(result.getString(2));
				jbProduct.setDescription(result.getString(3));
				jbProduct.setUnitmeasurement(result.getString(4));
				jbProduct.setDepartment(result.getString(5));
				jbProduct.setBuyprice(result.getDouble(6));
				jbProduct.setSaleprice(result.getDouble(7));
				jbProduct.setMinstock(result.getDouble(8));
				jbProduct.setStock(result.getDouble(9));
				alProducts.add(jbProduct);
			}
			dbconn.closeConnection();
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alProducts;
	}
	
	//Method that RETRIVES only the Services
	public ArrayList<JBInventoriesProductService> getServices() {
		ArrayList<JBInventoriesProductService> alServices = new ArrayList<JBInventoriesProductService>();
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		ResultSet result = null;
		String sql = "SELECT "
				        + "inventories_products_services.id_product_service, "
						+ "inventories_products_services.barcode, "
						+ "inventories_products_services.description, "
						+ "inventories_products_services.unit_measurement, "
						+ "departments_department.department, "
						+ "inventories_products_services.buy_price, "
						+ "inventories_products_services.sale_price, "
						+ "inventories_products_services.minimum_stock, "
						+ "inventories_products_services.stock "
					+ "FROM "
					    + "inventories_products_services "
					+ "INNER JOIN "
					     + "departments_department "
					+ "ON "
					     + "inventories_products_services.id_department = departments_department.id_department "
					+ "WHERE "
				        + "inventories_products_services.unit_measurement = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setString(1, "Servicio");
			result = query.executeQuery();
			while(result.next()) {
				JBInventoriesProductService jbProduct = new JBInventoriesProductService();
				jbProduct.setIDProductService(result.getInt(1));
				jbProduct.setBarcode(result.getString(2));
				jbProduct.setDescription(result.getString(3));
				jbProduct.setUnitmeasurement(result.getString(4));
				jbProduct.setDepartment(result.getString(5));
				jbProduct.setBuyprice(result.getDouble(6));
				jbProduct.setSaleprice(result.getDouble(7));
				jbProduct.setMinstock(result.getDouble(8));
				jbProduct.setStock(result.getDouble(9));
				alServices.add(jbProduct);
			}
			dbconn.closeConnection();
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alServices;
	}
	
	//Method that RETRIVES the Department of the Product
	public String getDepartmentFromProduct(int id_product) {
		String department = "";
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		ResultSet result = null;
		String sql = "SELECT "
			              + "departments_department.department "
			         + "FROM "
			              + "departments_department "
			         + "INNER JOIN "
			              + "inventories_products_services "
			         + "ON "
			              + "departments_department.id_department = inventories_products_services.id_department "
			         + "WHERE "
			              + "inventories_products_services.id_product_service = ?;";
		
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_product);
			result = query.executeQuery();
			while(result.next()) {
				department = result.getString(1);
			}
			dbconn.closeConnection();
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return department;
	}
	
	//Method that RETRIVES the IRS from and Specific Product or Service
	public double getProductServiceIRS(int id_product_service) {
		double irs = 0.0;
		DBConnection dbconn = new DBConnection();
		PreparedStatement query = null;
		ResultSet result = null;
		String sql = "SELECT "
		                  + "iva "
			         + "FROM "
			              + "inventories_products_services "
			         + "WHERE "
			              + "id_product_service = ?;";
		try {
			query = dbconn.getConnection().prepareStatement(sql);
			query.setInt(1, id_product_service);
			result = query.executeQuery();
			while(result.next()) {
				irs = result.getDouble("iva") + 1;
			}
			dbconn.closeConnection();
			query.close();
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return irs;
	}
}
