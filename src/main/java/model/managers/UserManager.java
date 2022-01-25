package model.managers;

import java.util.ArrayList;

import controllers.BrowseController;
import entites.Admin;
import entites.Client;
import entites.User;
import entites.UserBook;
import model.Model;
import model.dao.Dao;

public class UserManager implements Model {

	private static UserManager instance;

	private UserManager() {
	}

	public static UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}

	@Override
	public void notifyObservers() {
		BrowseController.getInstance().notifyObservers();
	}

	public boolean checkUserBoCap(User user) {
		if (user.isBookBorrowElibigle()) {
			return true;
		} else
			return false;
	}


	public void createClient(int id, String name, String email, String password) {

		User client = new Client();
		client.setId(id);
		client.setName(name);
		client.setEmail(email);
		client.setPassword(password);
		Dao.getInstance().getUserList().add(client);
		Dao.getInstance().notifyObservers();
	}

	public void addClient(String name, String email, String password) {
		int value = 0;
		Dao.getInstance().notifyObservers();
		System.out.println("inside AddClient");
		System.out.println(name + " " + email);
		for (User user : Dao.getInstance().getUserList()) {
			if (user.getName().contains(name) && user.getEmail().contains(email)) {
				value++;
				System.out.println("inside AddClient for loop");
			}
		}
		if (value == 0) {
			User client = new Client();
			client.setName(name);
			client.setEmail(email);
			client.setPassword(password);
			Dao.getInstance().addClient(client);
		} else {
			System.out.println("client already int the DB!");
		}
	}

	public void createAdmin(int id, String name, String email, String password) {

		User admin = new Admin();
		admin.setId(id);
		admin.setName(name);
		admin.setEmail(email);
		admin.setPassword(password);
		Dao.getInstance().getUserList().add(admin);
		Dao.getInstance().notifyObservers();
	}

	public void removeClient(User client) {
		Dao.getInstance().removeUser(client);
		
	}

	public int authenticate(String email, String password) {
		
		for(User user : Dao.getInstance().getUserList()) {
			if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
				return user.getId();
			}
		}
		return -1;
	}

	public int getUserType(int userId) {
		for(User user : Dao.getInstance().getUserList()) {
			if(user.getId() == userId) {
				if(user instanceof Admin) {
					return 2;
				}else {
					return 1;
				}
			}
		}
		return 0;
	}

	public ArrayList<Integer> getBorrowedBooksId(int userId) {
		ArrayList<Integer> borroweBooks = new ArrayList<>();
		for(UserBook userBook : Dao.getInstance().getBorrowedBooks()) {
			if(userBook.getUser().getId() == userId) {
				borroweBooks.add(userBook.getBook().getId());
			}
		}
		return borroweBooks;
	}

	public String getUserName(int userId) {
		
		for(User user : Dao.getInstance().getUserList()) {
			if(user.getId() == userId) {
				return user.getName();
			}
		}
		return "";
	}
}
