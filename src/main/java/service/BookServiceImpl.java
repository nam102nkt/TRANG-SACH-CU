package service;

import dao.*;
import model.AuthorStats;
import model.Book;
import model.Category;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookServiceImpl implements IBookService {

	private IBookDAO bookDAO = new BookDAOImpl();

	public List<Book> getFeaturedBooks() {
		return bookDAO.getFeaturedBooks();
	}

	public Book getBookDetail(int id) {
		return bookDAO.findBookId(id);
	}

	public List<Book> search(String keyword) {
		return bookDAO.search(keyword);
	}

	@Override
	public List<Book> filter(BigDecimal min, BigDecimal max, Integer categoryId, String sort, String condition) {
		return bookDAO.filter(min, max, categoryId, sort, condition);
	}

	@Override
	public void requestSellBook(Book b, int sellerId) {

		if (b == null)
			return;
		if (sellerId <= 0)
			return;
		if (b.getPrice() == null || b.getPrice().compareTo(BigDecimal.ZERO) <= 0)
			return;

		b.setSellerId(sellerId);
		b.setStatus("PENDING"); // BẮT BUỘC

		bookDAO.insertPending(b);
	}

	@Override
	public void adminAddBook(Book b) {

		if (b == null)
			return;
		if (b.getPrice() == null || b.getPrice().compareTo(BigDecimal.ZERO) <= 0)
			return;

		b.setStatus("ACTIVE"); // ADMIN đăng bán trực tiếp

		bookDAO.insertActive(b);
	}
	@Override
	public List<AuthorStats> getTopAuthors(int limit) {
		 return bookDAO.findTopAuthors(limit);
	}
	@Override
	public Map<Category, List<Book>> getBooksGroupedByCategory(int limitPerCategory) {
	    return bookDAO.findGroupedByCategory(limitPerCategory);
	}
}
