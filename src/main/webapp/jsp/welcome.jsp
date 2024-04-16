<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- ho inserito un jar esterno alla libreria per poter usare il metodo foreach -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Welcome ${username} !</h1>
	<br />
	<p>Here you can see the list of users on the platform</p>
	<table id="table-1">
		<thead>
			<tr>
				<th>id</th>
				<th>username</th>
				<th>password</th>
				<th>email</th>
			</tr>
		</thead>
		<tbody>
		<!-- il meotodo foreach crea righe e colonne popolandole di utenti -->
			<c:forEach items="${usersList}" var="user">
				<tr>
					<td>${user.id}</td>
					<td>${user.username}</td>
					<td>${user.password}</td>
					<td>${user.email}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</body>
</html>