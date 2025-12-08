
package dao;

import model.*;
import java.sql.*;

/** Triển khai OrderDAO */
public class OrderDAOImpl implements OrderDAO {

    /** Lưu đơn hàng vào CSDL */
    public int save(Order o){
        
    /** Lưu đơn hàng và chi tiết (sử dụng transaction).
     *  Trả về orderId mới tạo (hoặc -1 nếu lỗi).
     */
    public int save(Order o){
        String sqlOrder = "INSERT INTO orders(user_id,total_price,status) VALUES(?,?,?)";
        String sqlDetail = "INSERT INTO order_details(order_id,book_id,quantity,price) VALUES(?,?,?,?)";
        try (java.sql.Connection conn = database.DBContext.getConnection()) {
            conn.setAutoCommit(false);
            try (java.sql.PreparedStatement ps = conn.prepareStatement(sqlOrder, java.sql.Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, o.getUserId());
                ps.setDouble(2, o.getTotalPrice());
                ps.setString(3, o.getStatus());
                ps.executeUpdate();
                int orderId = -1;
                try (java.sql.ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) orderId = rs.getInt(1);
                }
                if (orderId == -1) throw new Exception("No order id generated");
                try (java.sql.PreparedStatement psd = conn.prepareStatement(sqlDetail)) {
                    for (OrderDetail d : o.getDetails()) {
                        psd.setInt(1, orderId);
                        psd.setInt(2, d.getBookId());
                        psd.setInt(3, d.getQuantity());
                        psd.setDouble(4, d.getPrice());
                        psd.addBatch();
                    }
                    psd.executeBatch();
                }
                conn.commit();
                return orderId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /** Lấy danh sách đơn hàng theo userId */
    public java.util.List<Order> getByUserId(int userId) {
        java.util.List<Order> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY id DESC";
        try (java.sql.Connection conn = database.DBContext.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order o = new Order();
                    o.setId(rs.getInt("id"));
                    o.setUserId(rs.getInt("user_id"));
                    o.setTotalPrice(rs.getDouble("total_price"));
                    o.setStatus(rs.getString("status"));
                    // details omitted for brevity
                    list.add(o);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

        return -1;
    }
}
