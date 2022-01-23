<%@ page
	import="entites.Book, java.util.List, java.util.ArrayList,controllers.BrowseController, controllers.LoginController, java.util.TreeSet, java.lang.Comparable, java.io.*,java.util.*,java.sql.*"
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>LiBook</title>
</head>
<body style="font-family: Arial; font-size: 20px;">
	<div
		style="height: 65px; align: center; background: #27db4b; font-family: Arial; color: white;">
		<br> <b> <a href="<%=request.getContextPath()%>/Browse"
			style="font-family: garamond; font-size: 34px; margin: 0px 0px 0px 10px; color: white; text-decoration: none;">LiBook - login panel</a></b>
	</div>
	<br>
	<br>
	<div>
		<center>
			<form method="POST" action="<%=request.getContextPath()%>/auth/login">
				<fieldset>
					<legend>Log In</legend>
					<table>
						<tr>
							<td><label>Email:</label></td>
							<td><input type="text" name="email"><br></td>
						</tr>
						<tr>
							<td><label>Password:</label></td>
							<td><input type="password" name="password"><br>
							</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><input type="submit" name="submitLoginForm"
								value="Log In"></td>
						</tr>
					</table>
				</fieldset>
			</form>
			</form>
		</center>
	</div>
</body>
</html>