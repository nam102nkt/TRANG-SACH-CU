package service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import dao.AdminDAOImpl;
import dao.IAdminDAO;
import model.Book;
import model.Order;
import model.User;

public class AdminServiceImpl implements IAdminService {

	private IAdminDAO dao = new AdminDAOImpl();

	public int countUsers() {
		return dao.count("users");
	}

	public int countOrders() {
		return dao.count("orders");
	}

	public int countBooks() {
		return dao.count("books");
	}

	public BigDecimal totalRevenue() {
		return dao.totalRevenue();
	}

	public List<Book> getAllBooks() {
		return dao.getAllBooks();
	}

	public void deleteBook(int id) {
		dao.deleteBook(id);
	}

	public List<Order> getAllOrders() {
		return dao.getAllOrders();
	}

	public void updateOrderStatus(int orderId, String status) {
		dao.updateOrderStatus(orderId, status);
	}

	public List<User> getAllUsers() {
		return dao.getAllUsers();
	}

	public void toggleUser(int userId, boolean active) {
		dao.toggleUser(userId, active);
	}

	public void updateBookStatus(int id, String s) throws SQLException {
		dao.updateBookStatus(id, s);
	}
}
