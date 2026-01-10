package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import model.Book;
import model.Order;
import model.User;

public class AdminDAOImpl implements IAdminDAO {

    public int count(String table) {
        String sql = "SELECT COUNT(*) FROM " + table;
        try (Connection c = DBContext.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal totalRevenue() {
        String sql = "SELECT SUM(total_price) FROM orders WHERE status='PAID'";
        try (Connection c = DBContext.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getBigDecimal(1) : BigDecimal.ZERO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void updateBookStatus(int id, String status) throws SQLException {
        String sql = "UPDATE books SET status=? WHERE id=?";
        try(Connection c =DBContext.getConnection();
            PreparedStatement ps=c.prepareStatement(sql)){
            ps.setString(1,status);
            ps.setInt(2,id);
            ps.executeUpdate();
        }
    }


	@Override
	public List<Book> getAllBooks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBook(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Order> getAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateOrderStatus(int orderId, String status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void toggleUser(int userId, boolean active) {
		// TODO Auto-generated method stub
		
	}
	
}
