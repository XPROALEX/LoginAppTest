<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<h1>Login</h1>
	<form action="/LoginAppTest/login" method="post">
		Username: <input type="text" name="emailOrUsername" required="required"><br>
		Password: <input type="password" name="password" required="required"><br> <input
			type="submit" value="Login">
	</form>
	<p style="color: red">${errorMessage} </p>
</body>
</html>