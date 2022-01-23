package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import constants.BookGenre;
import entites.Book;
import entites.User;
import entites.UserBook;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.Dao;
import model.managers.BookManager;
import model.managers.UserBookManager;

/**
 * Servlet implementation class Browse
 */
@WebServlet(urlPatterns = { "/Browse", "/search", "/sort"})
public class BrowseController extends HttpServlet implements Observer{
	private static final long serialVersionUID = 1L;
	private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	private static Set<Book> currentBookList;
	private static List<User> currentUserList;
	private static List<UserBook> currentBorrowedBooksList;
	private static BrowseController instance;
	/**
	 * Default constructor.
	 */
	public BrowseController() {
	}
	public static BrowseController getInstance(){
		if(instance == null) {
			instance = new BrowseController();
		}
		return instance;
	}
	
	public void notifyObservers() {
		Dao.getInstance().registerObserver(new BrowseController());
		List<Observer> observers = Dao.getInstance().getObservers();
		for(Observer o : observers) {
			//to delete
			o.updateBookList();
			o.updateUserList();
			o.updateUserBookList();
		}
	}
	public void updateBookList() {
		currentBookList = Dao.getInstance().getBookList();
	}
	public void updateUserList() {
		currentUserList = Dao.getInstance().getUserList();
	}
	public void updateUserBookList() {
		currentBorrowedBooksList = Dao.getInstance().getBorrowedBooks();
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
		if(request.getServletPath().contains("search")) {
			String title = request.getParameter("title");
			String author = request.getParameter("author");
			String genre = request.getParameter("select");
			BookGenre bookGenre = null;
			for(BookGenre bg : BookGenre.values()) {
				if(bg.getName().contains(genre)) {
					bookGenre = bg;
				}
			}
			ArrayList<Book> list = (ArrayList<Book>)DummyController.getInstance().search(title, author, bookGenre);			
			request.setAttribute("list", list);
			RequestDispatcher dispatcher = request.getRequestDispatcher("main_page.jsp");
			dispatcher.forward(request, response);
		}else if(request.getServletPath().contains("sort")){
			String sortBy = request.getParameter("sort by");
			ArrayList<Book> list = new ArrayList<>();
			if(sortBy.contains("title")) {
				list.addAll(Dao.getInstance().getBookList());
				list.sort(new Book.Comparators.TitleComparator());
			}else if(sortBy.contains("author")) {
				list.addAll(Dao.getInstance().getBookList());
				list.sort(new Book.Comparators.AuthorComparator());
			}else {
				list.addAll(Dao.getInstance().getBookList());
				list.sort(new Book.Comparators.GenreComparator());
			}
			request.setAttribute("list", list);
			RequestDispatcher dispatcher = request.getRequestDispatcher("main_page.jsp");
			dispatcher.forward(request, response);
		}else{
		UserBookManager.getInstance().loadData();
		ArrayList<Book> list = new ArrayList<>();
		//before - Dao.getInstance().getBookList()
		list.addAll(currentBookList);
		int size = list.size();
		request.setAttribute("size", size);
		request.setAttribute("list", list);
		RequestDispatcher dispatcher = request.getRequestDispatcher("main_page.jsp");
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
			} catch(ClassNotFoundException cnfe){
		}
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	public boolean getWaitingForAdminDecision(Book book) {
		for(UserBook ub : currentBorrowedBooksList) {
			if(ub.getBook().getId() == book.getId()) {
				return ub.isWaitingForAdminDecision();
			}
		}
		return false;
	}

	public static Set<Book> getCurrentBookList() {
		return currentBookList;
	}
	public static void setCurrentBookList(Set<Book> currentBookList) {
		BrowseController.currentBookList = currentBookList;
	}
	public static List<User> getCurrentUserList() {
		return currentUserList;
	}
	public static void setCurrentUserList(List<User> currentUserList) {
		BrowseController.currentUserList = currentUserList;
	}
	public static List<UserBook> getCurrentBorrowedBooksList() {
		return currentBorrowedBooksList;
	}
	public static void setCurrentBorrowedBooksList(List<UserBook> currentBorrowedBooksList) {
		BrowseController.currentBorrowedBooksList = currentBorrowedBooksList;
	}
}
