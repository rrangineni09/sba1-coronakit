<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-Add New Product(Admin)</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<hr />
	<h3>Add New Product</h3>

	<form action="admin?action=insertproduct" method="POST">

		<div>
			<label>Product Id</label> <input type="number" name="productId"
				value="${product.id }" required />
		</div>
		<div>
			<label>Product Name</label> <input type="text" name="productName"
				value="${product.productName }" required />
		</div>
		<div>
			<label>Cost</label> <input type="text" name="cost"
				value="${product.cost }" required />
		</div>
		<div>
			<label>Product Details</label> <input type="text" name="productDescription"
				value="${product.productDescription }" required />
		</div>
		<button>ADD</button>
	</form>


	<hr />
	<jsp:include page="footer.jsp" />
</body>
</html>