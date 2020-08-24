package com.iiht.evaluation.coronokit.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.iiht.evaluation.coronokit.exception.ContactException;
import com.iiht.evaluation.coronokit.model.CoronaKit;
import com.iiht.evaluation.coronokit.model.KitDetail;
import com.iiht.evaluation.coronokit.model.ProductMaster;



public class KitDao {

	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;

	public KitDao(String jdbcURL, String jdbcUsername, String jdbcPassword) {
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
	
	
	public static final String INS_USER_QRY = "INSERT INTO User(id,username,email,contactnum) VALUES(?,?,?,?)";
	public static final String INS_KIT_QRY = "INSERT INTO kit(id,coronakitId,productID,amount) VALUES(?,?,?,?)";
	public static final String UPD_KIT_QRY = "UPDATE productmaster set productname=?,cost=?,productdescription=? WHERE id=?";
	public static final String DEL_PROD_QRY = "DELETE FROM productmaster WHERE id=?";
	public static final String GET_PROD_BY_ID_QRY = "SELECT id,productname,cost,productdescription FROM productmaster WHERE id=?";
	public static final String GET_ALL_KIT_PROD_QRY = "SELECT id,coronakitId,productID,amount FROM kit WHERE id=? ORDER BY productID";

	
	public CoronaKit addUser(CoronaKit coronakit) throws ContactException, SQLException {

		if (coronakit != null) {
			connect();
			try (PreparedStatement pst = jdbcConnection.prepareStatement(INS_USER_QRY);) {
								
				pst.setInt(1, coronakit.getId());
				pst.setString(2, coronakit.getPersonName());
				pst.setString(3, coronakit.getEmail());
				pst.setString(4, coronakit.getContactNumber());

				pst.executeUpdate();
			} catch (SQLException exp) {
				throw new ContactException("Adding a user failed!");
			}
		}

		return coronakit;
	}
	
	public KitDetail additemtokit(KitDetail kitDetail) throws ContactException, SQLException {

		if (kitDetail != null) {
			connect();
			try (PreparedStatement pst = jdbcConnection.prepareStatement(INS_KIT_QRY);) {
								
				pst.setInt(1, kitDetail.getId());
				pst.setInt(2, kitDetail.getCoronaKitId());
				pst.setInt(3, kitDetail.getProductId());
				pst.setInt(4, kitDetail.getQuantity());

				pst.executeUpdate();
			} catch (SQLException exp) {
				throw new ContactException("Adding an item to Kit failed!");
			}
		}

		return kitDetail;
	}

	
	public ProductMaster Edit(ProductMaster product) throws ContactException, SQLException {
		if (product != null) {
			connect();
			try (PreparedStatement pst = jdbcConnection.prepareStatement(UPD_KIT_QRY);) {

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

	
	
	public List<KitDetail> getAll(int id) throws ContactException, SQLException {
		List<KitDetail> KitDetails = new ArrayList<>();
		connect();
		try (PreparedStatement pst = jdbcConnection.prepareStatement(GET_ALL_KIT_PROD_QRY);) {
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				KitDetail KitDetail = new KitDetail();
				
				KitDetail.setId(rs.getInt(1));
				KitDetail.setCoronaKitId(rs.getInt(2));
				KitDetail.setProductId(rs.getInt(3));
				KitDetail.setAmount(rs.getInt(4));
				KitDetails.add(KitDetail);
			}
			
			if(KitDetails.isEmpty()) {
				KitDetails=null;
			}

		} catch (SQLException exp) {
			throw new ContactException("Feteching Contact failed!");
		}
		
		return KitDetails;
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
	
	
	

	// add DAO methods as per requirements
}