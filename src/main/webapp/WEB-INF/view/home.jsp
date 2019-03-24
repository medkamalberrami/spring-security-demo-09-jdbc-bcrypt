<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<html>

<head>
<title>luv2code Company Home Page</title>
</head>

<body>
	<h2>luv2code Company Home Page</h2>

	<!-- display user name and role -->

	<p>
		User :
		<security:authentication property="principal.username" />
		<br> <br> Role(s) :
		<security:authentication property="principal.authorities" />


	</p>


	<hr>

	<security:authorize access="hasRole('MANAGER')">

		<!-- add a link to point to /leaders ... this is for managers -->
		<p>
			<a href="${pageContext.request.contextPath}/leaders">LeaderShip
				Meeting</a> (Only for Manager peeps)
		</p>

	</security:authorize>


	<security:authorize access="hasRole('ADMIN')">

	<!-- add a link to point to /Systems ... this is for admins -->
	<a href="${pageContext.request.contextPath}/systems">It Systems
		Meeting</a> (Only for Admin peeps)
	</security:authorize>


	<hr>

	Welcome to the luv2code company home page!


	<!-- Add a logout button -->

	<form:form action="${pageContext.request.contextPath}/logout"
		method="POST">
		<input type="submit" value="logout" />
	</form:form>

</body>

</html>