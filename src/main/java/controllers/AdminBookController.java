package controllers;

import java.io.IOException;
import java.util.ArrayList;

import constants.BookGenre;
import entites.Book;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.managers.BookManager;

/**
 * Servlet implementation class AdminBookController
 */
@WebServlet(urlPatterns = { "/bookManager", "/delete", "/createBook" })
public class AdminBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

	/**
	 * Default constructor.
	 */
	public AdminBookController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException cnfe) {
		}
		if (request.getServletPath().contains("delete")) {
			String dbid = request.getParameter("dbid");
			int bookId = Integer.parseInt(dbid);
			Book book = BookManager.getInstance().getBookBasedOnId(bookId);
			BookManager.getInstance().removeBook(book);
			RequestDispatcher dispatcher = request.getRequestDispatcher("bookManager");
			dispatcher.forward(request, response);

		} else if (request.getServletPath().contains("createBook")) {
			String title = request.getParameter("title");
			String author = request.getParameter("author");
			String imageUrl = request.getParameter("imageUrl");
			String pubYear = request.getParameter("pubYear");
			int publishYear = Integer.valueOf(pubYear);
			String language = request.getParameter("language");

			String genre = request.getParameter("select");
			BookGenre bookGenre = null;
			for (BookGenre bg : BookGenre.values()) {
				if (bg.getName().contains(genre)) {
					bookGenre = bg;
				}
			}
			BookManager.getInstance().addBook(title, author, publishYear, imageUrl, language, bookGenre);
			RequestDispatcher dispatcher = request.getRequestDispatcher("bookManager");
			dispatcher.forward(request, response);
			
		} else {
			ArrayList<Book> list = new ArrayList<>();
			list.addAll(BrowseController.getCurrentBookList());
			request.setAttribute("booksToEdit", list);
			RequestDispatcher dispatcher = request.getRequestDispatcher("book_manager.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException cnfe) {
		}
		doGet(request, response);
	}
}
