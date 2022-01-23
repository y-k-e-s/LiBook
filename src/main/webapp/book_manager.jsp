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
			style="font-family: garamond; font-size: 34px; margin: 0px 0px 0px 10px; color: white; text-decoration: none;">LiBook - book manager</a></b>
	</div>
	<br>
	<br>
	<div>
		<center>
			<form method="POST" action="<%=request.getContextPath()%>/createBook">
				<fieldset>
					<legend>Create Book</legend>
					<table>
						<tr>
							<td><label>Title:</label></td>
							<td><input type="title" name="title"><br></td>
						</tr>
						<tr>
							<td><label>Author:</label></td>
							<td><input type="author" name="author"><br>
							</td>
						</tr>
						<tr>
							<td><label>Publish Year:</label></td>
							<td><input type="pubYear" name="pubYear"><br></td>
						</tr>
						<tr>
							<td><label>Language:</label></td>
							<td><input type="language" name="language"><br></td>
						</tr>
						<tr>
							<td><label>ImageUrl:</label></td>
							<td><input type="imageUrl" name="imageUrl"><br>
							</td>
						</tr>
						<tr>
							<td><label>Genre:</label></td>
						<td><select name="select" size="1">
								<option value="Not Specified">Not Specified</option>
								<option value="IT">IT</option>
								<option value="Self Help">Self Help</option>
								<option value="Management">Management</option>
								<option value="Language">Language</option>
								<option value="HR">HR</option>
						</select><br></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><input type="submit" name="createBookForm"
								value="Create Book"></td>
						</tr>
					</table>
				</fieldset>
			</form>
			</form>
		</center>
	</div>
	<div>
	<br><br>
	
	<table>
	   <%
			List<Book> books = (List<Book>) request.getAttribute("booksToEdit");
			for (Book book : books) {
			%>
	     <tr>
		   <td>
		     <img src=<%=book.getImageUrl()%> width="150" height="200">
		   </td>
			    
		   <td style="color:gray;">
			 By <span style="color: #27db4b;"><%=book.getAuthor()%></span>
			 <br><br>
			 Title: <span style="color: #27db4b;"><%=book.getTitle()%></span>
			 <br><br>
			 Publication Year: <span style="color: #27db4b;"><%=book.getPublishYear()%></span>
			 <br><br>
			 <a href = "<%=request.getContextPath()%>/delete?dbid=<%=book.getId()%>" style="color: red;" >Delete</a>
			 <br><br>
			 <a style="color: grey; href = "">Edit(not available yet)</a>
		   </td>
		  </tr>
		  <tr>
     	    <td>&nbsp;</td>
  		  </tr>
  		 
	   <%} %>
	   
	</table>
	</div>
</body>
</html>