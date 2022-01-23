package Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeSet;

import constants.BookGenre;
import controllers.BrowseController;
import controllers.DummyController;
import entites.Book;
import entites.User;
import entites.UserBook;
import model.dao.Dao;
import model.managers.BookManager;
import model.managers.UserBookManager;

public class Test {
	public static void main(String[] args) {
		//BookManager.getInstance().addBook("title", "Myzio", 2000, "image", "language", BookGenre.NOT_SPECIFIED);
		
		while(true) {
			System.out.println("true");
		}
	}}
		/*
		UserBookManager.getInstance().loadData();
		User martynka = Dao.getInstance().getUserList().get(0);
		User blues = Dao.getInstance().getUserList().get(3);

		Book b1 = (Book)Dao.getInstance().getBookList().toArray()[0];
		Book b2 = (Book)Dao.getInstance().getBookList().toArray()[1];
		
		BookManager.getInstance().borrowBook(blues, b2);
		System.out.println(b2.getId());
		System.out.println(blues.getId());
		UserBookManager.getInstance().waitingForApproval(blues,b2, true);
		for(UserBook ub : Dao.getInstance().getBorrowedBooks()) {
			if(ub.getBook().getId() == b2.getId()) {
				System.out.println(ub.isWaitingForAdminDecision());
			}
		}
		System.out.println(BrowseController.getInstance().getWaitingForAdminDecision(b2));
		
	}}
		/*
		//DummyController.getInstance().addBook("dupa", "Jan", 2000, "lalal", "polish", BookGenre.IT);
		
		
		Book b3 = null;
				for(Book book : Dao.getInstance().getBookList()) {
					if(book.getAuthor().contains("Jan")) {
						DummyController.getInstance().removeBook(book);
					}
				}
		
		
		
	}}
		
		//Book b3 = null;
		for(Book book : Dao.getInstance().getBookList()) {
			if(book.getAuthor().contains("Martynka")) {
				b3 = book;
			}
		}
		DummyController.getInstance().removeBook(b3);
		
		
		//DummyController.getInstance().removeBook(b2);
		
		//DummyController.getInstance().giveBack(martynka, b1);
		
		//DummyController.getInstance().borrowBook(martynka, b1);
		//DummyController.getInstance().borrowBook(martynka, b2);
	}}


	/*
		System.out.println(Dao.getInstance().getBorrowedBooks().size());
		
		ArrayList<Book> bList = new ArrayList<>();
		for(UserBook ub : Dao.getInstance().getBorrowedBooks()) {
			bList.add(ub.getBook());
		}
		for(Book b : bList) {
			System.out.println(b.getTitle());
		}
		
		
		
	}}
		
		
		
		ArrayList<Book> bList = new ArrayList<>();
		for(UserBook ub : Dao.getInstance().getBorrowedBooks()) {
			bList.add(ub.getBook());
		
		}
	}}
		
		UserBookManager.getInstance().loadData();
		List<Book> list = (List<Book>)DummyController.getInstance().search("a", "e", null);
		System.out.println(list.size());
		
		
		//System.out.println(BookGenre.valueOf("MANAGEMENT"));

		
		
		
		
		
		UserBookManager.getInstance().loadData();
		//DummyController.getInstance().addBook("dupa", "Martynka", 2000, "lalal", "polish", BookGenre.IT);
		

		
		
		for(Book book : Dao.getInstance().getBookList()) {
			if(book.getId()>6) {
				DummyController.getInstance().removeBook(book);
			}
		}
		
		UserBookManager.getInstance().loadData();
		TreeSet<Book> books = (TreeSet<Book>)Dao.getInstance().getBookList();
		for(Book book : books) {
			System.out.println(book.getTitle());
		}
		*/

		/*
		DummyController.getInstance().loadData();
		User martynka = Dao.getInstance().getUserList().get(0);

		
		
		Book b1 = (Book)Dao.getInstance().getBookList().toArray()[0];
		//Book b2 = (Book)Dao.getInstance().getBookList().toArray()[1];
		
		DummyController.getInstance().borrowBook(martynka, b1);
		DummyController.getInstance().giveBack(martynka, b1);
		
	
		//DummyController.getInstance().addClient("Jan", "Jan@dupa.pl", "pass");
		
		/*
		String name = "Jan";
		String email = "Jan@dupa.pl";
		int value = 0;
		for (User user : Dao.getInstance().getUserList()) {
			if (user.getName().contains(name) && user.getEmail().contains(email)) {
				value++;
				System.out.println("inside AddClient for loop");
			}
		}
		*/
		
		
		
		/*
		DummyController.getInstance().loadData();
		
	for(Book book : Dao.getInstance().getBookiList()) {
			System.out.println(book.getAuthor() + " "+ book.getId() + " " + book.getPublishYear() + " " + book.getTitle() + " " + book.getBookGenre());
		}
	for(User user : Dao.getInstance().getUserList()) {
		System.out.println(user.getId() + " "+ user.getName() + " " + user.getEmail() + " " + user.getPassword());
		}
	*/

