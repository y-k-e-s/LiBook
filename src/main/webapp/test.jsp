<%@ page
	import="entites.Book, java.util.List, java.util.ArrayList,controllers.BrowseController, java.util.TreeSet, java.util.Set"
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Books</title>
</head>
<body>
	<div>
		<table>
			<%
			Set<Book> books = (TreeSet<Book>) request.getAttribute("books");
			for (Book book : books) {
			%>
			<tr>
				<td><img src=<%=book.getImageUrl()%> width="150" height="200">
				<td>By: <span><%=book.getAuthor()%></span> <br> <br>
					Title: <span><%=book.getTitle()%></span> <br> <br> <a
					href="">More</a>
				</td>
			</tr>
			<%
			}
			%>
		</table>
	</div>
</body>
</html>