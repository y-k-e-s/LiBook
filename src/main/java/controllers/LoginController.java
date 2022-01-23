package controllers;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.managers.UserManager;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(urlPatterns = { "/auth/login", "/auth", "/logout" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	static private boolean isSessionActive = false;

	/**
	 * Default constructor.
	 */
	public LoginController() {
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
		if (request.getServletPath().contains("/login")) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			int userId = UserManager.getInstance().authenticate(email, password);
			if (userId != -1) {
				HttpSession session = request.getSession();
				setSessionActive(true);
				session.setAttribute("userId", userId);
				int userType = UserManager.getInstance().getUserType(userId);
				String userName = UserManager.getInstance().getUserName(userId);
				ArrayList<Integer> borrowedBooks = UserManager.getInstance().getBorrowedBooksId(userId);
				session.setAttribute("borrowedBooksId", borrowedBooks);
				session.setAttribute("userType", userType);
				session.setAttribute("userName", userName);
				if(request.getParameter("bid") != null) {
					request.getRequestDispatcher("myBorrowedBooks").forward(request, response);
				}
				request.getRequestDispatcher("/Browse").forward(request, response);
			} else {
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		} else if(request.getServletPath().contains("/logout")) {
			setSessionActive(false);
			request.getSession().invalidate();
			RequestDispatcher dispatcher = request.getRequestDispatcher("/Browse");
			dispatcher.forward(request, response);
		} else {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
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


	static public boolean isSessionActive() {
		return isSessionActive;
	}

	static public void setSessionActive(boolean isSessionActive) {
		LoginController.isSessionActive = isSessionActive;
	}

}
