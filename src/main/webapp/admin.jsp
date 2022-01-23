<%@ page
	import="entites.Book, entites.UserBook, java.util.List, java.util.ArrayList,controllers.BrowseController, controllers.BorrowedBooksController , controllers.LoginController, model.managers.BookManager, java.util.TreeSet, java.lang.Comparable, java.io.*,java.util.*,java.sql.*"
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
			style="font-family: garamond; font-size: 34px; margin: 0px 0px 0px 10px; color: white; text-decoration: none;">LiBook - admin</a></b>
		<div
			style="height: 25px; background: #27db4b; font-family: Arial; color: white;">
			<b> <%
 if (LoginController.isSessionActive()) {
 %> <a href="<%=request.getContextPath()%>/logout"
				style="font-size: 16px; color: white; margin-left: 1150px; text-decoration: none;">Logout</a>
				
				<a href="<%=request.getContextPath()%>/bookManager" style="font-size:16px;color:white;margin-left:10px;text-decoration:none;">Book Manager</a>				
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
			TreeSet<Book> books = (TreeSet<Book>) request.getAttribute("adminView");
			for (Book book : books) {
			%>
			<tr>
				<td><img src=<%=book.getImageUrl()%> width="150" height="200">
				</td>

				<td style="color: black;">By: <span style="color: #27db4b;"><%=book.getAuthor()%></span>
					<br>
				<br> Title: <span style="color: #27db4b;"><%=book.getTitle()%></span>
					<br>
				<br> Publication Year: <span style="color: #27db4b;"><%=book.getPublishYear()%></span>
					<br>
				<br>
				</td>
				
				<td style="color: black;">Borrowed By: <span style="color: #27db4b;"><%=BookManager.getInstance().getBorrowedBy(book).getName()%></span>
					<br>
				<br> Borrowed to: <span style="color: #27db4b;"><%=BookManager.getInstance().getBorrowedTo(book.getId())%></span>
					<br>
				<br>
				</td>
				<%if(BrowseController.getInstance().getWaitingForAdminDecision(book)){%>
					<tr><td><a href="<%=request.getContextPath()%>/confirmGiveBack?gbid=<%=book.getId()%>"style="font-size: 18px; color: #27db4b; font-weight: bold; text-decoration: none">Confirm Give Back</a> </td></tr>	
				<% }%>
			
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