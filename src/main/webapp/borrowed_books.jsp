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
			style="font-family: garamond; font-size: 34px; margin: 0px 0px 0px 10px; color: white; text-decoration: none;">LiBook - my borrowed books</a></b>
		<div
			style="height: 25px; background: #27db4b; font-family: Arial; color: white;">
			<b> <%
 if (LoginController.isSessionActive()) {
 %> <a href="<%=request.getContextPath()%>/logout"
				style="font-size: 16px; color: white; margin-left: 1150px; text-decoration: none;">Logout</a>
				<%
				} else {
				%> <a href="<%=request.getContextPath()%>/auth"
				style="font-size: 16px; color: white; margin-left: 1150px; text-decoration: none;">Login</a>
				<%
				}
				%>
			</b>
		</div>
	</div>
	<br>
	<br>
	<div>
		<table>
			<%
			List<Book> books = (List<Book>) request.getAttribute("borrowedBooks");
			for (Book book : books) {
			%>
			<tr>
				<td><img src=<%=book.getImageUrl()%> width="150" height="200">
				</td>

				<td style="color: black;">By <span style="color: #27db4b;"><%=book.getAuthor()%></span>
					<br>
				<br> Title: <span style="color: #27db4b;"><%=book.getTitle()%></span>
					<br>
				<br> Publication Year: <span style="color: #27db4b;"><%=book.getPublishYear()%></span>
					<br>
				<br> 
				<%if(BrowseController.getInstance().getWaitingForAdminDecision(book)){%>
				Waiting for Admin decision
				
				<%}else if(!BrowseController.getInstance().getWaitingForAdminDecision(book)){
				%>
				<a href="<%=request.getContextPath()%>/giveBack?bbid=<%=book.getId()%>"
					style="font-size: 18px; color: #b8b8b8; font-weight: bold; text-decoration: none">GiveBack</a>
				<%} %>
					
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<%
			}
			%>
		</table>
	</div>
</body>
</html>