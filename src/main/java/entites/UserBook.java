package entites;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserBook {
	private Book book;
	private User user;
	private LocalDate creationTime;
	private boolean isWaitingForAdminDecision;
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public LocalDate getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(LocalDate creationTime) {
		this.creationTime = creationTime;
	}
	@Override
	public String toString() {
		return "UserBook [book=" + book + ", user=" + user + ", creationTime=" + creationTime + "]";
	}
	public boolean isWaitingForAdminDecision() {
		return isWaitingForAdminDecision;
	}
	public void setWaitingForAdminDecision(boolean isWaitingForAdminDecision) {
		this.isWaitingForAdminDecision = isWaitingForAdminDecision;
	}
	
}
