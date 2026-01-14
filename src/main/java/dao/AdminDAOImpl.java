package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
	public int countBooks() {
		return count("books");
	}
	
	@Override
	public List<Book> getAllBooks() {
	    List<Book> list = new ArrayList<>();
	    String sql = "SELECT * FROM books WHERE status='PENDING'";

	    try (Connection c = DBContext.getConnection();
	         PreparedStatement ps = c.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Book b = new Book();
	            b.setId(rs.getInt("id"));
	            b.setTitle(rs.getString("title"));
	            b.setPrice(rs.getBigDecimal("price"));
	            list.add(b);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}


	@Override
	public void deleteBook(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int countOrders() {
		return count("orders");
	}

	public List<Order> getAllOrders() {
	    List<Order> list = new ArrayList<>();
	    String sql = "SELECT o.*, u.username"
	    		+ "FROM orders o"
	    		+ "JOIN users u ON o.user_id = u.id";

	    try (Connection con = DBContext.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Order o = new Order();
	            o.setId(rs.getInt("id"));
	            o.setFullName(rs.getString("username"));
	            o.setTotalPrice(rs.getBigDecimal("total_price"));
	            o.setStatus(rs.getString("status"));
	            list.add(o);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}

	public void updateOrderStatus(int id, String status) {
	    String sql = "UPDATE orders SET status=? WHERE id=?";
	    try (Connection con = DBContext.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setString(1, status);
	        ps.setInt(2, id);
	        ps.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public int countUsers() {
		return count("users");
	}
	
	public List<User> getAllUsers() {
	    List<User> list = new ArrayList<>();
	    String sql = "SELECT * FROM users";

	    try (Connection con = DBContext.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            User u = new User();
	            u.setId(rs.getInt("id"));
	            u.setFullName(rs.getString("username"));
	            u.setRole(rs.getString("role"));
	            u.setRole(rs.getString("status"));
	            list.add(u);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}

	public void updateUserStatus(int id, String status) {
	    String sql = "UPDATE users SET status=? WHERE id=?";
	    try (Connection con = DBContext.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setString(1, status);
	        ps.setInt(2, id);
	        ps.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void toggleUser(int userId, boolean active) {
		// TODO Auto-generated method stub
		
	}
	
}
