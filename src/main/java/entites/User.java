package entites;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
	private int id;
	private String name;
	private String email;
	private String password;
	private boolean isBookBorrowElibigle = true;
	public static final short MAX_BOOKS_NO = 3;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public static short getMaxBooksNo() {
		return MAX_BOOKS_NO;
	}
	public boolean isBookBorrowElibigle() {
		return isBookBorrowElibigle;
	}
	public void setBookBorrowElibigle(boolean isBookBorrowElibigle) {
		this.isBookBorrowElibigle = isBookBorrowElibigle;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}
}
