package model.managers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import constants.BookGenre;
import controllers.BrowseController;
import controllers.DummyController;
import entites.Book;
import entites.User;
import entites.UserBook;
import model.Model;
import model.dao.Dao;

public class BookManager implements Model {

	private static BookManager instance;

	private BookManager() {
	}

	public static BookManager getInstance() {
		if (instance == null) {
			instance = new BookManager();
		}
		return instance;
	}

	public void addBook(String title, String author, int publishYear, String imageUrl, String language,
			BookGenre genre) {
		Book book = new Book();
		book.setAuthor(author);
		book.setTitle(title);
		book.setPublishYear(publishYear);
		book.setImageUrl(imageUrl);
		book.setLanguage(language);
		book.setBookGenre(genre);
		Dao.getInstance().addBook(book);

		book.setId(Dao.getNextMaxBookId());
		Dao.getInstance().notifyObservers();
		Dao.getInstance().getBookList().add(book);

	}

	@Override
	public void notifyObservers() {
		BrowseController.getInstance().notifyObservers();
	}

	public void createBook(int id, String title, String author, int publishYear, String imageUrl, String language,
			BookGenre genre, int bookId) {
		Book book = new Book();
		book.setId(id);
		book.setAuthor(author);
		book.setTitle(title);
		book.setPublishYear(publishYear);
		book.setImageUrl(imageUrl);
		book.setLanguage(language);
		book.setBookGenre(genre);
		if (bookId > 0) {
			book.setBorrowed(true);
		}
		Dao.getInstance().getBookList().add(book);
		Dao.getInstance().notifyObservers();

	}

	public void borrowBook(User user, Book book) {
		//to do - its ok to have isEligible..
		Boolean value = UserManager.getInstance().checkUserBoCap(user);
		if (value && !book.isBorrowed()) {
			UserBookManager.getInstance().borrowBook(user, book, LocalDate.now());

			System.out.println("book borrowed!");
		} else {
			if (book.isBorrowed()) {
				System.out.println("Book is already borrowed!");
			} else {
				System.out.println("You reach your max borrowed books no!");
			}
		}
	}

	public void removeBook(Book book) {
		if (!book.isBorrowed()) {
			Dao.getInstance().removeBook(book);
		} else {
			for (UserBook ub : Dao.getInstance().getBorrowedBooks()) {
				if (ub.getBook() == book) {
					User user = ub.getUser();
					System.out.println("book " + book.getTitle() + "is borrowed by: " + user.getName());
				}
			}
		}
	}

	public <E extends Object> List<Book> search(E title, E author, E genre) {
		List<E> list = Arrays.asList(title, author, genre);
		int value = 0;
		for (E e : list) {
			if (e != null && !e.toString().isBlank()) {
				value++;
				System.out.println(value);
			}
		}
		List<Book> searchResult = new ArrayList<>();
		/*
		 * 3 -> search by title and author and genre 2 -> if(title == null) search by
		 * author and genre etc... 1 -> if(title != null) search by title etc...
		 */
		if (value == 3) {
			for (Book b : Dao.getInstance().getBookList()) {
				if (b.getAuthor().toLowerCase().contains(author.toString().toLowerCase())
						&& b.getTitle().toLowerCase().contains(title.toString().toLowerCase())
						&& b.getBookGenre().equals(genre)) {
					searchResult.add(b);
				}
			}
		} else if (value == 2) {
			if (title.toString().isBlank()) {
				for (Book b : Dao.getInstance().getBookList()) {
					if (b.getAuthor().toLowerCase().contains(author.toString().toLowerCase())
							&& b.getBookGenre().equals(genre)) {
						searchResult.add(b);
					}
				}
			} else if (author.toString().isBlank()) {
				for (Book b : Dao.getInstance().getBookList()) {
					if (b.getTitle().toLowerCase().contains(title.toString().toLowerCase())
							&& b.getBookGenre().equals(genre)) {
						searchResult.add(b);
					}
				}
			} else {
				for (Book b : Dao.getInstance().getBookList()) {
					if (b.getAuthor().toLowerCase().contains(author.toString().toLowerCase())
							&& b.getTitle().toLowerCase().contains(title.toString().toLowerCase())) {
						searchResult.add(b);
					}
				}
			}

		} else if (value == 1) {
			if (!title.toString().isBlank()) {
				for (Book b : Dao.getInstance().getBookList()) {
					if (b.getTitle().toLowerCase().contains(title.toString().toLowerCase())) {
						searchResult.add(b);
					}
				}
			} else if (!author.toString().isBlank()) {
				for (Book b : Dao.getInstance().getBookList()) {
					if (b.getAuthor().toLowerCase().contains(author.toString().toLowerCase())) {
						searchResult.add(b);
					}
				}
			} else {
				for (Book b : Dao.getInstance().getBookList()) {
					if (b.getBookGenre().equals(genre)) {
						searchResult.add(b);
					}
				}
			}
		} else {
			searchResult.addAll(Dao.getInstance().getBookList());
		}
		return searchResult;
	}

	public User getBorrowedBy(Book book) {
		for (UserBook ub : Dao.getInstance().getBorrowedBooks()) {
			if (ub.getBook().getId() == book.getId()) {
				return ub.getUser();
			}
		}
		return null;
	}
	
	public LocalDate getBorrowedTo(int bookId) {
		LocalDate borrowedTo;
		for(UserBook ub : Dao.getInstance().getBorrowedBooks()) {
			if(ub.getBook().getId() == bookId) {
				ub.getCreationTime();
				borrowedTo = ub.getCreationTime().plusMonths(1);
				return borrowedTo;
			}
		}
		return LocalDate.now();
	}
	public Book getBookBasedOnId(int bookId) {
		for(Book book : Dao.getInstance().getBookList()) {
			if(book.getId() == bookId) {
				return book;
			}
		}
		return null;
	}
}
