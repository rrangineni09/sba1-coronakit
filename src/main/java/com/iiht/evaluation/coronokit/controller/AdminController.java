package com.iiht.evaluation.coronokit.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iiht.evaluation.coronokit.dao.ProductMasterDao;
import com.iiht.evaluation.coronokit.exception.ContactException;
import com.iiht.evaluation.coronokit.model.ProductMaster;


@WebServlet("/admin")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductMasterDao productMasterDao;
	
	
	public void setProductMasterDao(ProductMasterDao productMasterDao) {
		this.productMasterDao = productMasterDao;
	}

	public void init(ServletConfig config) {
		String jdbcURL = config.getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername = config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = config.getServletContext().getInitParameter("jdbcPassword");

		this.productMasterDao = new ProductMasterDao(jdbcURL, jdbcUsername, jdbcPassword);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action =  request.getParameter("action");
		String viewName = "";
		try {
			switch (action) {
			case "login" : 
				viewName = adminLogin(request, response);
				break;
			case "newproduct":
				viewName = showNewProductForm(request, response);
				break;
			case "insertproduct":
				viewName = insertProduct(request, response);
				break;
			case "deleteproduct":
				viewName = deleteProduct(request, response);
				break;
			case "editproduct":
				viewName = showEditProductForm(request, response);
				break;
			case "updateproduct":
				viewName = updateProduct(request, response);
				break;
			case "list":
				viewName = listAllProducts(request, response);
				break;	
			case "logout":
				viewName = adminLogout(request, response);
				break;	
			default : viewName = "notfound.jsp"; break;		
			}
		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
		RequestDispatcher dispatch = 
					request.getRequestDispatcher(viewName);
		dispatch.forward(request, response);
		
		
	}

	private String adminLogout(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return "";
	}

	private String listAllProducts(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {			
			List<ProductMaster> products = this.productMasterDao.getAll();
			request.setAttribute("products", products);
			return "listproducts.jsp";

		} catch (Exception exp) {
			request.setAttribute("errmsg", exp.getMessage());
			return "errorPage.jsp";
		}
		
	}

	private String updateProduct(HttpServletRequest request, HttpServletResponse response) throws ContactException, SQLException {
		ProductMaster product = new ProductMaster();
		product.setId(Integer.parseInt(request.getParameter("productId")));
		product.setProductName(request.getParameter("productName"));
		product.setCost(request.getParameter("cost"));
		product.setProductDescription(request.getParameter("productDescription"));
		if((String.valueOf(product.getId())!=null) && (product.getProductName()!=null) && (product.getCost()!=null) && (product.getProductDescription()!=null))
		{
			ProductMaster newproducts = this.productMasterDao.Edit(product);
			if (newproducts!=null) {
				List<ProductMaster> products = this.productMasterDao.getAll();
				request.setAttribute("products", products);
				return "listproducts.jsp";
			}
			else {request.setAttribute("errmsg", "Product not Updated");
			return "errorPage.jsp";
			}
		}
		else {request.setAttribute("errmsg", "Fields cannot be empty. Try again");
		return "errorPage.jsp";}
	}

	private String showEditProductForm(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		int pid = Integer.parseInt(request.getParameter("pid"));
		if(String.valueOf(pid)!=null) {
			try {
				ProductMaster prod = this.productMasterDao.getById(pid);
				request.setAttribute("product", prod);
				return "editproduct.jsp";
			} catch (ContactException e) {
				request.setAttribute("errmsg", e.getMessage());
				return "errorPage.jsp";
			} 
		}else {request.setAttribute("errmsg", "Fields cannot be empty. Try again");
		return "errorPage.jsp";}
	}

	private String deleteProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		int pId = Integer.parseInt(request.getParameter("pid"));
		try {
			boolean bln = this.productMasterDao.deleteById(pId);
			if(bln) {
				List<ProductMaster> products = this.productMasterDao.getAll();
				request.setAttribute("products", products);
				return "listproducts.jsp";
			}
			else {
				request.setAttribute("errmsg", "Product ID was not found : " + pId);
				return "errorPage.jsp";
			}
		} catch (ContactException e) {
			// TODO Auto-generated catch block
			request.setAttribute("errmsg", e.getMessage());
			return "errorPage.jsp";
		} 
		
		
	
	}

	private String insertProduct(HttpServletRequest request, HttpServletResponse response) throws ContactException, SQLException {
		ProductMaster product = new ProductMaster();
		product.setId(Integer.parseInt(request.getParameter("productId")));
		product.setProductName(request.getParameter("productName"));
		product.setCost(request.getParameter("cost"));
		product.setProductDescription(request.getParameter("productDescription"));
		if((String.valueOf(product.getId())!=null) && (product.getProductName()!=null) && (product.getCost()!=null) && (product.getProductDescription()!=null))
		{
			ProductMaster newproducts = this.productMasterDao.add(product);
			if (newproducts!=null) {
				List<ProductMaster> products = this.productMasterDao.getAll();
				request.setAttribute("products", products);
				return "listproducts.jsp";
			}
			else {request.setAttribute("errmsg", "Product not added");
			return "errorPage.jsp";
			}
		}
		else {request.setAttribute("errmsg", "Fields cannot be empty. Try again");
		return "errorPage.jsp";}
		

	}			
	

	private String showNewProductForm(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return "newproduct.jsp";
	}

	private String adminLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username= request.getParameter("loginid");
		String password= request.getParameter("password");
		if(username.matches("admin")&&password.matches("admin")) {
			List<ProductMaster> products = this.productMasterDao.getAll();
			request.setAttribute("products", products);
			return "listproducts.jsp";
		}
		else {
			return "notfound.jsp";
		}
	}

	
}