<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-My Kit(user)</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<nav>
		<a href="admin?action=login">Main Page</a><span>"   "</span>
		<a href="admin?action=newproduct">Add New Product</a>
	</nav>
	<%-- Required View Template --%>
	<c:choose>
		<c:when test="${KitDetails == null || KitDetails.isEmpty() }">
			<p>
				No product Available. Want to add a Product? <a href="">Add a
					product</a>
			</p>
		</c:when>
		<c:otherwise>
			<table border="1" cellspacing="5px" cellpadding="5px">
				<tr>
					<th>User ID</th>
					<th>Corona Kit ID</th>
					<th>Prod ID</th>
					<th>Price</th>
				</tr>
				<c:forEach items="${KitDetails}" var="kitdetail">
					<tr>
						<td>${kitdetail.id }</td>
						<td>${kitdetail.coronaKitId}</td>
						<td>${kitdetail.productId}</td>
						<td>${kitdetail.amount}</td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>

	<jsp:include page="footer.jsp" />
</body>
</html>