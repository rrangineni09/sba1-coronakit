package com.iiht.evaluation.coronokit.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iiht.evaluation.coronokit.dao.KitDao;
import com.iiht.evaluation.coronokit.dao.ProductMasterDao;
import com.iiht.evaluation.coronokit.exception.ContactException;
import com.iiht.evaluation.coronokit.model.CoronaKit;
import com.iiht.evaluation.coronokit.model.KitDetail;
import com.iiht.evaluation.coronokit.model.ProductMaster;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private KitDao kitDAO;
	private ProductMasterDao productMasterDao;

	public void setKitDAO(KitDao kitDAO) {
		this.kitDAO = kitDAO;
	}

	public void setProductMasterDao(ProductMasterDao productMasterDao) {
		this.productMasterDao = productMasterDao;
	}

	public void init(ServletConfig config) {
		String jdbcURL = config.getServletContext().getInitParameter("jdbcUrl");
		String jdbcUsername = config.getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = config. getServletContext().getInitParameter("jdbcPassword");
		
		this.kitDAO = new KitDao(jdbcURL, jdbcUsername, jdbcPassword);
		this.productMasterDao = new ProductMasterDao(jdbcURL, jdbcUsername, jdbcPassword);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		
		String viewName = "";
		try {
			switch (action) {
			case "newuser":
				viewName = showNewUserForm(request, response);
				break;
			case "insertuser":
				viewName = insertNewUser(request, response);
				break;
			case "showproducts":
				viewName = showAllProducts(request, response);
				break;	
			case "addnewitem":
				viewName = addNewItemToKit(request, response);
				break;
			case "deleteitem":
				viewName = deleteItemFromKit(request, response);
				break;
			case "showkit":
				viewName = showKitDetails(request, response);
				break;
			case "placeorder":
				viewName = showPlaceOrderForm(request, response);
				break;
			case "saveorder":
				viewName = saveOrderForDelivery(request, response);
				break;	
			case "ordersummary":
				viewName = showOrderSummary(request, response);
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

	private String showOrderSummary(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return "";
	}

	private String saveOrderForDelivery(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return "";
	}

	private String showPlaceOrderForm(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return "";
	}

	private String showKitDetails(HttpServletRequest request, HttpServletResponse response) {
		try {
			KitDetail kd = new KitDetail();
			kd.setId(Integer.parseInt((request.getParameter("uid"))));
			List<KitDetail> KitDetails = this.kitDAO.getAll(kd.getId());
			request.setAttribute("userid", kd.getId());
			request.setAttribute("KitDetails", KitDetails);
			return "showkit.jsp";

		} catch (Exception exp) {
			request.setAttribute("errmsg", exp.getMessage());
			return "errorPage.jsp";
		}
	}

	private String deleteItemFromKit(HttpServletRequest request, HttpServletResponse response) {
		
			return "";
		 
	}

	private String addNewItemToKit(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		KitDetail kd = new KitDetail();
		kd.setId(Integer.parseInt((request.getParameter("uid"))));
		kd.setCoronaKitId(Integer.parseInt((request.getParameter("uid"))));
		kd.setProductId(Integer.parseInt(request.getParameter("pid")));
		kd.setAmount(Integer.parseInt((request.getParameter("pcost"))));
		
		try {
			KitDetail kit = this.kitDAO.additemtokit(kd);
			List<ProductMaster> products = this.productMasterDao.getAll();
			request.setAttribute("userid", kd.getId());
			request.setAttribute("products", products);
			request.setAttribute("message", "Item Added to your kit!");
			return "showproductstoadd.jsp";
		} catch (ContactException e) {
			request.setAttribute("errmsg", e.getMessage());
			return "errorPage.jsp";
		} 
	}

	private String showAllProducts(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return "";
	}

	private String insertNewUser(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		CoronaKit ck = new CoronaKit();
		ck.setId(Integer.parseInt((request.getParameter("uid"))));
		ck.setPersonName(request.getParameter("username"));
		ck.setEmail(request.getParameter("email"));
		ck.setContactNumber(request.getParameter("contact"));
		try {
			CoronaKit ckit = this.kitDAO.addUser(ck);
			List<ProductMaster> products = this.productMasterDao.getAll();
			request.setAttribute("userid", ck.getId());
			request.setAttribute("products", products);
			return "showproductstoadd.jsp";
		} catch (ContactException e) {
			request.setAttribute("errmsg", e.getMessage());
			return "errorPage.jsp";
		} 
		
	}

	private String showNewUserForm(HttpServletRequest request, HttpServletResponse response) {
		int max = 500000;
		int min = 1000;
		int userid = (int) ((Math.random() * (max-min) +1) + min);
		CoronaKit ck = new CoronaKit();
		ck.setId(userid);
		request.setAttribute("userid", userid);
		return "newuser.jsp";
	}
}