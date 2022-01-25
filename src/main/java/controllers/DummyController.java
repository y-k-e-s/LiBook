/*package controllers;

import java.util.List;
import java.util.Set;
import constants.BookGenre;
import entites.Book;
import entites.User;
import entites.UserBook;
import model.dao.Dao;
import model.managers.BookManager;
import model.managers.UserBookManager;
import model.managers.UserManager;

public class DummyController{
	private static Set<Book> currentBookList;
	private static List<User> currentUserList;
	private static List<UserBook> currentBorrowedBooksList;
	private static DummyController instance;
	
	private DummyController() {}

	public static DummyController getInstance() {
		if(instance == null) {
			instance = new DummyController();
		}
		return instance;
	}
	
	//Observer Pattern
	/*
	public void notifyObservers() {
		registerObserver(new DummyController());
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
	
	public void registerObserver(Observer observer) {
		Dao.getInstance().registerObserver(observer);
	}
	
	//Client
	public void borrowBook(User user, Book book){
		BookManager.getInstance().borrowBook(user, book);
	}
	public void giveBack(User user, Book book) {
		UserBookManager.getInstance().giveBack(user, book);
	}
	
	//public void logIn() {}
	//public void register() {}
	
	//Admin	
	public void addBook(String title, String author, int publishYear, String imageUrl, String language, BookGenre genre) {
		BookManager.getInstance().addBook(title, author, publishYear, imageUrl, language, genre);
	}
	public void removeBook(Book book) {
		BookManager.getInstance().removeBook(book);
	}
	public void removeClient(User client) {
		UserManager.getInstance().removeClient(client);
	}
	
	public void addClient(String name, String email, String password) {
		UserManager.getInstance().addClient(name, email, password);
	}
	public void showBorrowedBooks() {
		UserBookManager.getInstance().showBorrowedBooks();
	}
	
	
	public void editBook() {}
	
	//on start(every time?)
	public void loadData() {
			UserBookManager.getInstance().loadData();
	}
	//search
	public List<Book> search(String title, String author, BookGenre genre) {
		return BookManager.getInstance().search(title, author, genre);
	}

	public static List<UserBook> getCurrentBorrowedBooksList() {
		return currentBorrowedBooksList;
	}

	public static void setCurrentBorrowedBooksList(List<UserBook> currentBorrowedBooksList) {
		DummyController.currentBorrowedBooksList = currentBorrowedBooksList;
	}

	public static Set<Book> getCurrentBookList() {
		return currentBookList;
	}

	public static List<User> getCurrentUserList() {
		return currentUserList;
	}
}
*/
