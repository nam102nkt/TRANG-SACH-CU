package dao;

import model.OrderDetail;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements IOrderDetailDAO {

	@Override
	public void insert(OrderDetail d) throws SQLException {
		String sql = "INSERT INTO order_details(order_id, book_id, quantity, price) VALUES (?, ?, ?, ?)";

		try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, d.getOrderId());
			ps.setInt(2, d.getBookId());
			ps.setInt(3, d.getQuantity());
			ps.setBigDecimal(4, d.getPrice());

			ps.executeUpdate();
		}
	}

	@Override
	public List<OrderDetail> getByOrderId(int orderId) {
		List<OrderDetail> list = new ArrayList<>();

		String sql = "SELECT od.*, b.title AS book_title " + "FROM order_details od "
				+ "JOIN books b ON od.book_id = b.id " + "WHERE od.order_id = ?";

		try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, orderId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					OrderDetail d = new OrderDetail();
					d.setId(rs.getInt("id"));
					d.setOrderId(rs.getInt("order_id"));
					d.setBookId(rs.getInt("book_id"));
					d.setQuantity(rs.getInt("quantity"));
					d.setPrice(rs.getBigDecimal("price"));
					d.setBookTitle(rs.getString("book_title")); // ✅ set tên sách
					list.add(d);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public void deleteByOrderId(int orderId) {
		String sql = "DELETE FROM order_details WHERE order_id = ?";

		try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, orderId);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}