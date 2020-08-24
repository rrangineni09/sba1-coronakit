<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Order Summary(user)</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<nav>
		<a href="admin?action=login">Main Page</a><span>" "</span> <a
			href="admin?action=newproduct">Add New Product</a>
	</nav>
	<%-- Required View Template --%>
	<c:choose>
		<c:when test="${kitDetails == null || kitDetails.isEmpty() }">
			<p>
				No product Available. Want to add a Product? <a href="">Add a
					product</a>
			</p>
		</c:when>
		<c:otherwise>

			<table border="1" cellspacing="5px" cellpadding="5px">
				<tr>
					<th>User ID</th>
					<th>User Name</th>
					<th>Email ID</th>
					<th>contactNumber</th>
					<th>deliveryAddress</th>
					<th>orderDate</th>
				</tr>
				<tr>
					<td>${kitDetails.coronakitId }</td>
					<td>${kitDetails.productID}</td>
					<td>${kitDetails.quantity}</td>
					<td>${kitDetails.amount}</td>
					<td>${kitDetails.amount}* ${product.quantity}</td>
					<td>${kitDetails.amount}* ${product.quantity}</td>
				</tr>
			</table>
			<table border="1" cellspacing="5px" cellpadding="5px">
				<tr>
					<th>CoronaKit ID</th>
					<th>Product ID</th>
					<th>Quantity</th>
					<th>Amount</th>
					<th>Cumulative</th>
				</tr>
				<c:forEach items="${products}" var="product">
					<tr>
						<td>${kitDetails.coronakitId }</td>
						<td>${kitDetails.productID}</td>
						<td>${kitDetails.quantity}</td>
						<td>${kitDetails.amount}</td>
						<td>${kitDetails.amount}* ${kitDetails.quantity}</td>
						<td><a href="admin?action=deleteproduct&pid=${kitDetails.coronakitId }">Delete</a>
							<span></span> <a
							href="admin?action=editproduct&pid=${kitDetails.coronakitId }">Edit</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>

	<jsp:include page="footer.jsp" />
</body>
</html>