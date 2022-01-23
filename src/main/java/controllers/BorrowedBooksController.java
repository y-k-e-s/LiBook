package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import entites.Book;
import entites.Client;
import entites.User;
import entites.UserBook;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.Dao;
import model.managers.BookManager;
import model.managers.UserBookManager;
import model.managers.UserManager;

/**
 * Servlet implementation class BorrowedBooksController
 */
@WebServlet(urlPatterns = { "/myBorrowedBooks", "/auth/myBorrowedBooks", "/admin", "/giveBack", "/confirmGiveBack" })
public class BorrowedBooksController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	static Set<Book> bSet = new TreeSet<>();

	/**
	 * Default constructor.
	 */
	public BorrowedBooksController() {
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
		if (request.getServletPath().contains("admin")) {
			if((int)request.getSession().getAttribute("userType") == 2) {
				for (UserBook ub : Dao.getInstance().getBorrowedBooks()) {
					bSet.add(ub.getBook());
				}
				request.setAttribute("adminView", bSet);

				request.getRequestDispatcher("/admin.jsp").forward(request, response);	
			}else {
				request.getRequestDispatcher("BorrowedBooksController").forward(request, response);	
			}
			
		} else if (request.getServletPath().contains("giveBack")) {
			String bid = request.getParameter("bbid");
			int bbid = Integer.parseInt(bid);
			Book b = null;
			User u = null;
			for (Book book : Dao.getInstance().getBookList()) {
				if (book.getId() == bbid) {
					b = book;
					for (UserBook ub : Dao.getInstance().getBorrowedBooks()) {
						if (ub.getBook().equals(book)) {
							u = ub.getUser();
						}
					}
				}
			}
			
			if(b != null && u != null) {
				if((int)request.getSession().getAttribute("userType") == 2) {
					UserBookManager.getInstance().giveBack(u, b);
				}else {
					UserBookManager.getInstance().waitingForApproval(u,b, true);
					}
			}
			request.getRequestDispatcher("myBorrowedBooks").forward(request, response);
		
		} else if(request.getServletPath().contains("confirmGiveBack")) {
			String gbId = request.getParameter("gbid");
			int bookId = Integer.parseInt(gbId);
			
			User client = new Client();
			Book book = new Book();
			
			for (UserBook ub : Dao.getInstance().getBorrowedBooks()) {
				if (ub.getBook().getId() == bookId) {
					client = (Client) ub.getUser();
					book = ub.getBook();
					ub.setWaitingForAdminDecision(false);
					//UserBookManager.getInstance().waitingForApproval(client, book, false);
				}
			}
			
			UserBookManager.getInstance().giveBack(client, book);
			bSet.clear();
			for (UserBook ub : BrowseController.getCurrentBorrowedBooksList()) {
				bSet.add(ub.getBook());
			}
			request.setAttribute("adminView", bSet);
			request.getRequestDispatcher("admin.jsp").forward(request, response);
	
			
			
			/*
			Iterator<UserBook> iterator = Dao.getInstance().getBorrowedBooks().iterator();
			while(iterator.hasNext()) {
				UserBook ub = iterator.next();
				if (ub.getBook().getId() == bookId) {
					UserBookManager.getInstance().waitingForApproval(ub.getUser(), ub.getBook(), false);
					client = (Client) ub.getUser();
					book = ub.getBook();
				}
			}
			
			UserBookManager.getInstance().giveBack(client, book);
			/*
			for (UserBook ub : Dao.getInstance().getBorrowedBooks()) {
				if (ub.getBook().getId() == bookId) {
					UserBookManager.getInstance().waitingForApproval(ub.getUser(), ub.getBook(), false);
					u = ub.getUser();
					b = ub.getBook();
					UserBookManager.getInstance().giveBack(u, b);
				}
			}
			*/
		}

		int userId = (Integer) request.getSession().getAttribute("userId");

		if (request.getParameter("bid") != null) {
			String bId = request.getParameter("bid");
			int bookId = Integer.parseInt(bId);

			Book book = null;
			User user = null;
			for (Book b : Dao.getInstance().getBookList()) {
				if (b.getId() == bookId) {
					book = b;
				}
			}
			for (User u : Dao.getInstance().getUserList()) {
				if (u.getId() == userId) {
					user = u;
				}
			}
			if (user != null && book != null) {
				BookManager.getInstance().borrowBook(user, book);
			}
		}

		ArrayList<Integer> borrowedBooksId = UserManager.getInstance().getBorrowedBooksId(userId);
		
		ArrayList<Book> borrowedBooks = new ArrayList<>();
		for (Book potentialyBorrowedBook : Dao.getInstance().getBookList()) {
			if (borrowedBooksId.contains(potentialyBorrowedBook.getId())) {
				borrowedBooks.add(potentialyBorrowedBook);
			}
		}
		
		request.setAttribute("borrowedBooks", borrowedBooks);
		request.getSession().setAttribute("borrowedBooks", borrowedBooksId);

		request.getRequestDispatcher("/borrowed_books.jsp").forward(request, response);

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
