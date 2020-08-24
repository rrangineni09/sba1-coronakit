package com.iiht.evaluation.coronokit.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.iiht.evaluation.coronokit.exception.ContactException;
import com.iiht.evaluation.coronokit.model.ProductMaster;

public class ProductMasterDao {

	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;
	private static DataSource datasource;

	public ProductMasterDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}

	

	protected void connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
	}

	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}

	public Connection getConnection() throws SQLException {
		try {
			connect();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jdbcConnection;

	}

	
	public static final String INS_PROD_QRY = "INSERT INTO productmaster(id,productname,cost,productdescription) VALUES(?,?,?,?)";
	public static final String UPD_PROD_QRY = "UPDATE productmaster set productname=?,cost=?,productdescription=? WHERE id=?";
	public static final String DEL_PROD_QRY = "DELETE FROM productmaster WHERE id=?";
	public static final String GET_PROD_BY_ID_QRY = "SELECT id,productname,cost,productdescription FROM productmaster WHERE id=?";
	public static final String GET_ALL_PROD_QRY = "SELECT id,productname,cost,productdescription FROM productmaster ORDER BY id";

	
	public ProductMaster add(ProductMaster product) throws ContactException, SQLException {

		if (product != null) {
			connect();
			try (PreparedStatement pst = jdbcConnection.prepareStatement(INS_PROD_QRY);) {

				pst.setInt(1, product.getId());
				pst.setString(2, product.getProductName());
				pst.setString(3, product.getCost());
				pst.setString(4, product.getProductDescription());

				pst.executeUpdate();
			} catch (SQLException exp) {
				throw new ContactException("Adding a product failed!");
			}
		}

		return product;
	}

	
	public ProductMaster Edit(ProductMaster product) throws ContactException, SQLException {
		if (product != null) {
			connect();
			try (PreparedStatement pst = jdbcConnection.prepareStatement(UPD_PROD_QRY);) {

				pst.setInt(4, product.getId());
				pst.setString(1, String.valueOf(product.getProductName()));
				pst.setString(2, String.valueOf(product.getCost()));
				pst.setString(3, String.valueOf(product.getProductDescription()));

				pst.executeUpdate();
			} catch (SQLException exp) {
				throw new ContactException("Saving Contact failed!");
			}
		}

		return product;
	}

	
	public boolean deleteById(int pId) throws ContactException, SQLException {
		boolean isDeleted = false;
		connect();
		try (PreparedStatement pst = jdbcConnection.prepareStatement(DEL_PROD_QRY);) {
			pst.setInt(1, pId);
			int rowsCount = pst.executeUpdate();
			isDeleted = rowsCount > 0;

		} catch (SQLException exp) {
			throw new ContactException("Deleting Contact failed!");
		}
		return isDeleted;
	}

	
	
	public List<ProductMaster> getAll() throws ContactException, SQLException {
		List<ProductMaster> products = new ArrayList<>();
		connect();
		try (PreparedStatement pst = jdbcConnection.prepareStatement(GET_ALL_PROD_QRY);) {
			
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				ProductMaster product = new ProductMaster();
				product.setId(rs.getInt(1));
				product.setProductName(rs.getString(2));
				product.setCost(rs.getString(3));
				product.setProductDescription(rs.getString(4));
				products.add(product);
			}
			
			if(products.isEmpty()) {
				products=null;
			}

		} catch (SQLException exp) {
			throw new ContactException("Feteching Contact failed!");
		}
		
		return products;
	} 
	
	
	
	public ProductMaster getById(int id) throws ContactException, SQLException {
		ProductMaster product = null;
		connect();
		try (PreparedStatement pst = jdbcConnection.prepareStatement(GET_PROD_BY_ID_QRY);) {

			pst.setInt(1, id);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				product = new ProductMaster();
				product.setId(rs.getInt(1));
				product.setProductName(rs.getString(2));
				product.setCost(rs.getString(3));
				product.setProductDescription(rs.getString(4));
			}

		} catch (SQLException exp) {
			throw new ContactException("Feteching Contact failed!");
		}

		return product;
	}

}