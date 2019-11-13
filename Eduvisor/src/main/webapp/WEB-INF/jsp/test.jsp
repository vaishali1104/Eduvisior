<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="forms" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<forms:form method="POST" modelAttribute="register" action="register">
	<forms:errors path="*" cssStyle="color: black; font-size: 92px;" />

		<p>
			Name
			<forms:input type="text" path="name" />
		</p>
		<p>
			E-Mail
			<forms:input type="email" path="email" />
		</p>
		<p>
			Contact Number
			<forms:input type="text" path="contactNumber" />
		</p>
		<p>
			Current Profession
			<forms:select path="profession">
				<forms:option value="Select Profession" />
				<forms:option value="Student" />
				<forms:option value="Professor" />
			</forms:select>
		</p>
		<p>
			Password
			<forms:input type="password" path="password" />
		</p>
		<p>
			<input type="submit" value="Signup">
		</p>
	</forms:form>
</body>
</html>