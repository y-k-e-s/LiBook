package entites;

import java.util.Comparator;
import java.util.Iterator;

import constants.BookGenre;

public class Book implements Comparable<Book> {
	private int id;
	private String title;
	private String author;
	private int publishYear;
	private String imageUrl;
	private String language;
	private BookGenre bookGenre;
	private boolean isBorrowed;
	private boolean isBooked;
	private boolean isOverdue;

	public static class Comparators {
		public static class AuthorComparator implements Comparator<Book> {

			public int compare(Book ob1, Book ob2) {
				return ob1.getAuthor().compareTo(ob2.getAuthor());
			}
		}

		public static class TitleComparator implements Comparator<Book> {

			public int compare(Book ob1, Book ob2) {
				return ob1.getTitle().compareTo(ob2.getTitle());
			}
		}

		public static class GenreComparator implements Comparator<Book> {

			public int compare(Book ob1, Book ob2) {
				return ob1.getBookGenre().getName().compareTo(ob2.getBookGenre().getName());
			}
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getPublishYear() {
		return publishYear;
	}

	public void setPublishYear(int publishYear) {
		this.publishYear = publishYear;
	}

	public boolean isBorrowed() {
		return isBorrowed;
	}

	public void setBorrowed(boolean isBorrowed) {
		this.isBorrowed = isBorrowed;
	}

	public boolean isBooked() {
		return isBooked;
	}

	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public BookGenre getBookGenre() {
		return bookGenre;
	}

	public void setBookGenre(BookGenre bookGenre) {
		this.bookGenre = bookGenre;
	}

	public boolean isOverdue() {
		return isOverdue;
	}

	public void setOverdue(boolean isOverdue) {
		this.isOverdue = isOverdue;
	}

	@Override
	public int compareTo(Book b) {
		if (title.equals(b.getTitle())) {
			return author.compareTo(b.getAuthor());
		} else
			return title.compareTo(b.getTitle());
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", isBorrowed=" + isBorrowed + "]";
	}

}
