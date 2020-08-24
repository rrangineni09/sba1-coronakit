<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Corona Kit-New User(user)</title>
</head>
<body>
<jsp:include page="header.jsp" />
	<hr />
	<h3>New User Form</h3>

	<form action="user?action=insertuser&uid=${userid}" method="POST">

		<div>
			<label>User Id : ${userid}</label>
		</div>
		<div>
			<label>User Name :</label> <input type="text" name="username"
				value="${User.username }" required />
		</div>
		<div>
			<label>Email :</label> <input type="text" name="email"
				value="${User.email }" required />
		</div>
		<div>
			<label>Contact Number :</label> <input type="text" name="contact"
				value="${User.contactnum }" required />
		</div>
		<button>Register</button>
	</form>


	<hr />
	<jsp:include page="footer.jsp" />
	</body>
</html>