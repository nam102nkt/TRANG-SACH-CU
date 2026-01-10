package service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import model.Book;
import model.Order;
import model.User;

public interface IAdminService {
    int countUsers();
    int countOrders();
    int countBooks();
    BigDecimal totalRevenue();

    List<Book> getAllBooks();
    void deleteBook(int id);

    List<Order> getAllOrders();
    void updateOrderStatus(int orderId, String status);

    List<User> getAllUsers();
    void toggleUser(int userId, boolean active);
    
    void updateBookStatus(int bookId, String status) throws SQLException;
}

