<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-All Products(user)</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<span>
		<a href="user?action=showkit&uid=${userid }">Check your Kit</a>
	</span>
	<!-- form action="user?action=addnewitem&uid=${userid }" method="POST" -->
	<%-- Required View Template --%>
	<label>User Id : ${userid}</label><br><br>
	<c:choose>
		<c:when test="${products == null || products.isEmpty() }">
			<p>
				No product Available. Want to add a Product? <a href="">Add a
					product</a>
			</p>
		</c:when>
		<c:otherwise>
			<table border="1" cellspacing="5px" cellpadding="5px">
				<tr>
					<th>Product ID</th>
					<th>Product Name</th>
					<th>Cost</th>
					<th>Product Details</th>
				</tr>
				<c:forEach items="${products}" var="product">
					<tr>
						<td>${product.id }</td>
						<td>${product.productName}</td>
						<td>${product.cost}</td>
						<td>${product.productDescription}</td>
						<td><a href="user?action=addnewitem&uid=${userid }&pid=${product.id }&pcost=${product.cost }">Add to Kit</a>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
	<p>${message }</p>
<!-- /form -->
	<jsp:include page="footer.jsp" />
</body>
</html>