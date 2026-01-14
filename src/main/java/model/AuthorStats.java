package model;

public class AuthorStats {
	private String author;
	private int totalBooks;

	public AuthorStats(String author, int totalBooks) {
		this.author = author;
		this.totalBooks = totalBooks;
	}
	public AuthorStats() {}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getTotalBooks() {
		return totalBooks;
	}

	public void setTotalBooks(int totalBooks) {
		this.totalBooks = totalBooks;
	}
}
