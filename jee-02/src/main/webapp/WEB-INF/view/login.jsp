<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Area Login</title>
</head>
<body>
	<%if(request.getParameter("error") != null) { %>
	<p>Accesso Negato! Credenziali errate</p> <%} %>
	<form action="login" method="post">
		<select name="role">
			<option value="u">Utente</option>
			<option value="a">Amministratore</option>
		</select>
		<br>
		<input type="text" name="username" required>
		<br>
		<input type="password" name="password" required>
		<br>
		<input type="submit" value="Accedi">
	</form>
	<a href="/jee-02">Abbandona</a>
</body>
</html>