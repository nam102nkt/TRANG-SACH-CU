package dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import model.Book;
import model.Order;
import model.User;

public interface IAdminDAO {

    int count(String table);

    BigDecimal totalRevenue();

    List<Book> getAllBooks();
    int countBooks();
    void deleteBook(int id);

    List<Order> getAllOrders();
    int countOrders();
    void updateOrderStatus(int orderId, String status);
    
    List<User> getAllUsers();
    int countUsers();
    void updateUserStatus(int bookId, String status) throws SQLException;
    void toggleUser(int userId, boolean active);
    
    void updateBookStatus(int bookId, String status) throws SQLException;
}
