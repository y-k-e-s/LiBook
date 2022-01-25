package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import constants.BookGenre;
import controllers.BrowseController;
import controllers.Observer;
import entites.Book;
import entites.User;
import entites.UserBook;
import model.Model;
import model.managers.BookManager;
import model.managers.UserBookManager;
import model.managers.UserManager;

public class Dao implements Model {
	private static Dao instance;
	private static Set<Book> bookList = new TreeSet<>();
	private static List<User> userList = new ArrayList<>();
	private static List<Observer> observers = new ArrayList<>();
	private static List<UserBook> borrowedBooks = new ArrayList<>();
	private static int nextMaxBookId;

	private Dao() {
	}

	public static Dao getInstance() {
		if (instance == null) {
			instance = new Dao();
		}
		return instance;
	}

	public void loadData() {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost: 3306/libook?useSSL=false", "root",
				"root"); Statement stm = con.createStatement()) {
			loadBooks(stm);
			loadUsers(stm);
			loadUserBook(stm);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Set<Book> getBookList() {
		return bookList;
	}

	public void addBook(Book book) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost: 3306/libook?useSSL=false", "root",
				"root"); Statement stm = con.createStatement(); Statement stm2 = con.createStatement()) {
			stm.executeUpdate("ALTER TABLE book AUTO_INCREMENT = 1;");
			stm.executeUpdate("ALTER TABLE book_author AUTO_INCREMENT = 1;");
			ResultSet rs = stm.executeQuery("select max(id) as id from book;");
			rs.next();
			int id = rs.getInt("id") + 1;
			setNextMaxBookId(id);
			String title = book.getTitle();
			String author = book.getAuthor();
			String imageUrl = book.getImageUrl();
			String language = book.getLanguage();
			int publishYear = book.getPublishYear();
			BookGenre bookGenre = book.getBookGenre();
			int genre = getIntBookGenre(bookGenre);
			
			int[] authorId = chceckAuthorId(author);
			int bookAuthorId;
			System.out.println("authorId[0]: " + authorId[0]);
			System.out.println("authorId[1]: " + authorId[1]);
			System.out.println("authorId[2]: " + authorId[2]);
			if (authorId[0] == -1) {
				addAuthor(author);
				bookAuthorId = (authorId[2] + 1);
			}else {
				bookAuthorId = authorId[1];
			}
			String querryBook = "insert into book (title, publish_year, genre, image_url, language) values ('" + title + "', "
					+ publishYear + ", '" + genre + "', '" + imageUrl + "', '" + language +"');";
			String querryBookAuthor = "insert into book_author (book_id, author_id) values (" + id + ", " + bookAuthorId + ");";
			stm.executeUpdate(querryBook);
			
			
			ResultSet rs1 = stm.executeQuery("select max(id) as id from book;");
			rs1.next();
			while(rs1.getInt("id") != id) {
				try {
					stm.wait(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			stm.executeUpdate(querryBookAuthor);

			System.out.println("bookAuthorId: " + bookAuthorId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private int getIntBookGenre(BookGenre bookGenre) {
		int intBookGenre = 0;
		if(bookGenre.getName().contains("IT")) {
			intBookGenre = 1;
		}else if(bookGenre.getName().contains("Self Help")) {
			intBookGenre = 2;
		}else if(bookGenre.getName().contains("Management")) {
			intBookGenre = 3;
		}else if(bookGenre.getName().contains("Language")) {
			intBookGenre = 4;
		}else {
			intBookGenre = 5;
		}
		return intBookGenre;
	}

	private void addAuthor(String author) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost: 3306/libook?useSSL=false", "root",
				"root"); Statement stm = con.createStatement()) {
			stm.executeUpdate("ALTER TABLE author AUTO_INCREMENT = 1;");
			String querry = "insert into author (author) values ('" + author + "');";
			stm.executeUpdate(querry);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private int[] chceckAuthorId(String author) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost: 3306/libook?useSSL=false", "root",
				"root"); Statement stm = con.createStatement()) {
			String querry = "SELECT id, author FROM libook.author;";
			ResultSet rs = stm.executeQuery(querry);

			int maxId = 0;
			int[] result = new int[3];
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("author");
				if (id > maxId) {
					maxId = id;
				}
				result[2] = maxId;
				if (author.contains(name)) {
					result[1] = id;
				}
			}
			if (result[1] == 0) {
				result[0] = -1;
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//is null ok here?
		return null;
	}

	public void notifyObservers() {
		// System.out.println("notification sent!");
		BrowseController.getInstance().notifyObservers();
	}

	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	public List<Observer> getObservers() {
		return observers;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void removeBook(Book book) {
		Iterator<Book> iterator = bookList.iterator();
		while(iterator.hasNext()) {
			if(iterator.next().equals(book)) {
				iterator.remove();
			}
		}
		
		bookList.remove(book);
		notifyObservers();

		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost: 3306/libook?useSSL=false", "root",
				"root"); Statement stm = con.createStatement()) {
			System.out.println("inside Dao.RemoveBook()");
			String bookQuerry = "delete from book where id = " + book.getId() + ";";
			String bookAuthorQuerry = "delete from book_author where book_id = " + book.getId() + ";";
			stm.executeUpdate(bookAuthorQuerry);
			stm.executeUpdate(bookQuerry);
			//Dao.getInstance().notifyObservers();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addClient(User client) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost: 3306/libook?useSSL=false", "root",
				"root"); Statement stm = con.createStatement()) {
			System.out.println("inside Dao.addClient()");
			String querry = "insert into user (name, email, password, is_book_borrow_eligible, user_type) "
					+ "values ('" + client.getName() + "', '" + client.getEmail() + "', '" + client.getPassword()
					+ "', true, 1);";
			stm.executeUpdate(querry);
			Dao.getInstance().notifyObservers();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateBookStatus(Book book) {
		// TODO Auto-generated method stub

	}

	public void updateClientStatus(User user) {
		// TODO Auto-generated method stub

	}

	public void addUserBook(UserBook userBook) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost: 3306/libook?useSSL=false", "root",
				"root"); Statement stm = con.createStatement()) {
			stm.executeUpdate("ALTER TABLE user_book AUTO_INCREMENT = 1;");
			System.out.println("inside addUserBook()");
			int book_id = userBook.getBook().getId();
			int user_id = userBook.getUser().getId();
			LocalDate ld = userBook.getCreationTime();
			Date date = Date.valueOf(ld);
			
			String querry = "insert into user_book (book_id, user_id, creation_time) values (" + book_id + ", "
					+ user_id + ", " + "'" + date + "'" + ");";
			System.out.println(date);
			stm.executeUpdate(querry);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<UserBook> getBorrowedBooks() {
		return borrowedBooks;
	}

	public void loadBooks(Statement stm) throws SQLException {

		String querry = "select b.id, b.title, b.publish_year, b.language, b.image_url, b.genre, a.author, ub.user_id, ub.book_id "
				+ "from author a, book_author ba, book b "
				+ "left join user_book ub on b.id = ub.book_id where ba.book_id = b.id and a.id = ba.author_id order by b.id;";
		ResultSet rs = stm.executeQuery(querry);

		while (rs.next()) {
			int id = rs.getInt("id");
			String title = rs.getString("title");
			String author = rs.getString("author");
			int publishYear = rs.getInt("publish_year");
			String imageUrl = rs.getString("image_url");
			String language = rs.getString("language");
			BookGenre bGenre = BookGenre.NOT_SPECIFIED;
			int bookId = rs.getInt("book_id");
			int intGenre = rs.getInt("genre");
			if (intGenre == 1) {
				bGenre = BookGenre.IT;
			}else if(intGenre == 2) {
				bGenre = BookGenre.SELF_HELP;
			}else if(intGenre == 3){
				bGenre = BookGenre.MANAGEMENT;
			}else if(intGenre == 4) {
				bGenre = BookGenre.LANGUAGE;
			}else {
				bGenre = BookGenre.HR;
			}
			BookManager.getInstance().createBook(id, title, author, publishYear, imageUrl, language, bGenre, bookId);
		}
	}

	public void loadUsers(Statement stm) throws SQLException {

		String querry = "select u.id, u.name, u.email, u.password, ut.title from user u, user_title ut"
				+ " where u.user_type = ut.id order by u.id asc;";
		ResultSet rs = stm.executeQuery(querry);

		while (rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String email = rs.getString("email");
			String password = rs.getString("password");
			String userType = rs.getString("title");

			if (userType.contains("admin")) {
				UserManager.getInstance().createAdmin(id, name, email, password);
			} else {
				UserManager.getInstance().createClient(id, name, email, password);
			}
		}
	}

	private void loadUserBook(Statement stm) throws SQLException {
		String querry = "select ub.book_id, ub.user_id, ub.creation_time from user_book ub;";
		ResultSet rs = stm.executeQuery(querry);

		while (rs.next()) {
			int bookId = rs.getInt("book_id");
			int userId = rs.getInt("user_id");
			Date date = rs.getDate("creation_time");
			LocalDate creationTime = date.toLocalDate();

			for (User user : Dao.getInstance().getUserList()) {
				if (user.getId() == userId) {
					for (Book book : Dao.getInstance().getBookList()) {
						if (book.getId() == bookId) {
							UserBookManager.getInstance().createUserBook(user, book, creationTime);
						}
					}
				}
			}

			Iterator<Book> bookIterator = Dao.getInstance().getBookList().iterator();
			while (bookIterator.hasNext()) {
				Book book = bookIterator.next();
				if (book.getId() == bookId) {
					book.setBorrowed(true);
				}
			}
		}
	}

	public void removeUser(User client) {
		userList.remove(client);
		notifyObservers();

		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost: 3306/libook?useSSL=false", "root",
				"root"); Statement stm = con.createStatement()) {
			System.out.println("inside Dao.RemoveClient()");
			String querry = "delete from user where id = " + client.getId() + ";";
			stm.executeUpdate(querry);
			Dao.getInstance().notifyObservers();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeUserBook(User user, Book book) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost: 3306/libook?useSSL=false", "root",
				"root"); Statement stm = con.createStatement()) {

			String querry = "delete from user_book where user_id = " + user.getId() + " and book_id = " + book.getId()
					+ ";";
			stm.executeUpdate(querry);
			System.out.println("book sucessfully returned!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	public int authenticate(String email, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/libook?useSSL=false", "root", "root");
				Statement stmt = conn.createStatement();) {	
			String query = "Select id from user where email = '" + email + "' and password = '" + password + "'";
			System.out.println("query: " + query);
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				return rs.getInt("id");				
	    	}			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return -1;
	}
	*/

	public void setWaitingForApproval(Book book, boolean bool) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost: 3306/libook?useSSL=false", "root",
				"root"); Statement stm = con.createStatement()) {
			String querry;
				querry = "update user_book set waiting_for_approval = 1 where book_id = " + book.getId() + ";";
				stm.executeUpdate(querry);
			/*
			 *  in erlier version if(bool){} else if(!bool)...
				if (!bool){
				querry = "update user_book set waiting_for_approval = 0 where book_id = " + book.getId() + ";";
				stm.executeUpdate(querry);
			}
			*/
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getNextMaxBookId() {
		return nextMaxBookId;
	}

	public static void setNextMaxBookId(int MaxBookId) {
		Dao.nextMaxBookId = MaxBookId;
	}
}
