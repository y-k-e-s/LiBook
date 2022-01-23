package model.managers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entites.Book;
import entites.User;
import entites.UserBook;
import model.dao.Dao;

public class UserBookManager {

	static private UserBookManager instance;

	public static UserBookManager getInstance() {
		if (instance == null) {
			instance = new UserBookManager();
		}
		return instance;
	}

	private UserBookManager() {
	}

	public void loadData() {
		Dao.getInstance().loadData();
	}

	public void createUserBook(User user, Book book, LocalDate creationTime) {
		UserBook userBook = new UserBook();
		userBook.setBook(book);
		userBook.setUser(user);
		userBook.setCreationTime(creationTime);
		book.setBorrowed(true);
		if(isOverdue(book, creationTime)) {
			book.setOverdue(true);
		}
		Dao.getInstance().getBorrowedBooks().add(userBook);
		Dao.getInstance().notifyObservers();

		int value = 0;
		for (UserBook ub : Dao.getInstance().getBorrowedBooks()) {
			if (ub.getUser() == user) {
				value++;
			}
			if (value == User.MAX_BOOKS_NO) {
				user.setBookBorrowElibigle(false);
			}
		}
	}

	private boolean isOverdue(Book book, LocalDate creationTime) {
		if(creationTime.plusMonths(1).isBefore(LocalDate.now())) {
			return true;
		}
		return false;
	}

	public void borrowBook(User user, Book book, LocalDate creationTime) {
		UserBook userBook = new UserBook();
		userBook.setBook(book);
		userBook.setUser(user);
		userBook.setCreationTime(creationTime);
		Dao.getInstance().getBorrowedBooks().add(userBook);
		Dao.getInstance().addUserBook(userBook);
		book.setBorrowed(true);
		Dao.getInstance().notifyObservers();

		int value = 0;
		for (UserBook ub : Dao.getInstance().getBorrowedBooks()) {
			if (ub.getUser() == user) {
				value++;
			}
			if (value == User.MAX_BOOKS_NO) {
				user.setBookBorrowElibigle(false);
			}
		}
	}

	public void giveBack(User user, Book book) {
		if (!book.isBorrowed()) {
			System.out.println("you didnt borrow that book!");
			return;
		} else {
			Iterator<UserBook> iterator = Dao.getInstance().getBorrowedBooks().iterator();
			while (iterator.hasNext()) {
				UserBook ub = iterator.next();
				if (ub.getBook().getId() == book.getId()) {
					iterator.remove();
				}
			}
			book.setBorrowed(false);
			Dao.getInstance().notifyObservers();
			Dao.getInstance().removeUserBook(user, book);
		}
	}

	public void showBorrowedBooks() {
		Dao.getInstance().getBorrowedBooks();
		
	}
	public void waitingForApproval(User user, Book book, boolean bool) {
		if(bool) {
			for(UserBook ub : Dao.getInstance().getBorrowedBooks()) {
				if(ub.getBook().getId() == book.getId()) {
					ub.setWaitingForAdminDecision(true);
					Dao.getInstance().setWaitingForApproval(book, true);
					Dao.getInstance().notifyObservers();
				}
			}
		}else {
			for(UserBook ub : Dao.getInstance().getBorrowedBooks()) {
				if(ub.getBook().getId() == book.getId()) {
					ub.setWaitingForAdminDecision(false);
					//Dao.getInstance().setWaitingForApproval(book, false);
					Dao.getInstance().notifyObservers();
				}
			}
		}
	}
}
