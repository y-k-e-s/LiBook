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
			style="font-family: garamond; font-size: 34px; margin: 0px 0px 0px 10px; color: white; text-decoration: none;">LiBook</a></b>

		<div
			style="height: 25px; background: #27db4b; font-family: Arial; color: white;">
			<b> <%
 if (LoginController.isSessionActive() && request.getSession().getAttribute("userType") != null) {
	 String userName = (String)request.getSession().getAttribute("userName");
	 int userType = (int)request.getSession().getAttribute("userType");

	 
	 // at line 22 after auto logout: "java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because the return value of "jakarta.servlet.http.HttpSession.getAttribute(String)" is null"
 %> 			
 				Hello <%=userName%>! 
 				
 				<% if(userType == 2){%>
 				<a
				href="<%=request.getContextPath()%>/admin"
				style="font-size:16px;color:white;margin-left:10px;text-decoration:none;">Admin Panel</a>
 				<%} %>
 				
 				<a
				href="<%=request.getContextPath()%>/logout"
				style="font-size:16px;color:white;margin-left:1000px;text-decoration:none;">Logout</a>
				<a
				href="<%=request.getContextPath()%>/myBorrowedBooks"
				style="font-size: 16px; color: white; margin-left: 10px; text-decoration: none;">My Borrowed Books</a>
				<%
				} else {
				%> <a href="<%=request.getContextPath()%>/auth"
				style="font-size: 16px; color: white; margin: 1150px; text-decoration: none;">Login</a>
				<%
				}
				%>
			</b>
		</div>
	</div>
	<br>
	<br>
	<div>
		<form method="POST" action="<%=request.getContextPath()%>/search">
			<fieldset>
				<legend>Search</legend>
				<table>
					<tr>
						<td><label>Title:</label></td>
						<td><input type="text" name="title"><br></td>
						<td><label>Author:</label></td>
						<td><input type="text" name="author"><br></td>
						<td><label>Genre:</label></td>
						<td><select name="select" size="1">
								<option value="any">any</option>
								<option value="IT">IT</option>
								<option value="Self Help">Self Help</option>
								<option value="Management">Management</option>
								<option value="Language">Language</option>
								<option value="HR">HR</option>
						</select><br></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td><input type="submit" name="submitSearchForm"
							value="Search"></td>
					</tr>
				</table>
			</fieldset>
		</form>
		<br></br>
		<form method="POST" action="<%=request.getContextPath()%>/sort">
			<select name="sort by" size="1">
				<option value="title">title</option>
				<option value="author">author</option>
				<option value="genre">genre</option>
			</select> <input type="submit" value="Sort">
		</form>
	</div>
	<div>
		<table>
			<%
			List<Book> books = (List<Book>) request.getAttribute("list");
			for (Book book : books) {
			%>
			<tr>
				<td><img src=<%=book.getImageUrl()%> width="150" height="200">
				</td>

				<td style="color: black;">By <span style="color: #27db4b;"><%=book.getAuthor()%></span>
					<br> <br> Title: <span style="color: #27db4b;"><%=book.getTitle()%></span>
					<br> <br> Publication Year: <span style="color: #27db4b;"><%=book.getPublishYear()%></span>
					<br> <br> <%
 					if (LoginController.isSessionActive()) {
 						
 						ArrayList<Integer> list = (ArrayList<Integer>)request.getSession().getAttribute("borrowedBooksId");
						if (list.contains(book.getId()) && book.isBorrowed() && !BrowseController.getInstance().getWaitingForAdminDecision(book)) {%>
					<a href="<%=request.getContextPath()%>/giveBack?bbid=<%=book.getId()%>"
					style="font-size: 18px; color: #27db4b; font-weight: bold; text-decoration: none">Give Back</a> 
					<%
 					} else if(book.isBorrowed() && !list.contains(book.getId()) && !BrowseController.getInstance().getWaitingForAdminDecision(book)){
 					%> 
 					Unavailable
						<%
 					} else if(BrowseController.getInstance().getWaitingForAdminDecision(book)){
 					%> 
 					Waiting for Admin decision
					<%
					} else {
					%> 
					<a
					href="<%=request.getContextPath()%>/myBorrowedBooks?bid=<%=book.getId()%>"
					style="font-size: 18px; color: #27db4b; font-weight: bold; text-decoration: none">Borrow</a>
					<%
 					}
 					%>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<%
			}}
			%>
		</table>
	</div>
</body>
</html>