package dao;

import model.*;
import java.util.List;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;

public class OrderDAOImpl implements IOrderDAO {

	@Override
	public int save(Order o) {
		String sqlOrder = "INSERT INTO orders(user_id,total_price,status) VALUES(?,?,?)";
		String sqlDetail = "INSERT INTO order_details(order_id,book_id,quantity,price) VALUES(?,?,?,?)";

		Connection conn = null;

		try {
			conn = DBContext.getConnection();
			conn.setAutoCommit(false);

			int orderId;

			try (PreparedStatement ps = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {

				ps.setInt(1, o.getUserId());
				ps.setBigDecimal(2, o.getTotalPrice());
				ps.setString(3, o.getStatus());
				ps.executeUpdate();

				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (!rs.next())
						throw new SQLException("Không tạo được order_id");
					orderId = rs.getInt(1);
				}
			}

			try (PreparedStatement psd = conn.prepareStatement(sqlDetail)) {
				for (OrderDetail d : o.getDetails()) {
					psd.setInt(1, orderId);
					psd.setInt(2, d.getBookId());
					psd.setInt(3, d.getQuantity());
					psd.setBigDecimal(4, d.getPrice());
					psd.addBatch();
				}
				psd.executeBatch();
			}

			conn.commit();
			return orderId;

		} catch (Exception e) {
			try {
				if (conn != null)
					conn.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return -1;

		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Order> getOrdersByUserId(int userId) {
		List<Order> list = new java.util.ArrayList<>();
		String sql = "SELECT id, user_id, total_price, status, shipping_address, order_date FROM orders WHERE user_id = ? ORDER BY id DESC";
		try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Order o = new Order();
					o.setId(rs.getInt("id"));
					o.setUserId(rs.getInt("user_id"));
					o.setTotalPrice(rs.getBigDecimal("total_price"));
					o.setStatus(rs.getString("status"));
					o.setShippingAddress(rs.getString("shipping_address"));
					Timestamp ts = rs.getTimestamp("order_date");
					if (ts != null)
						o.setOrderDate(ts.toLocalDateTime());

					list.add(o);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<Order> getByUserId(int userId) {
		return getOrdersByUserId(userId);
	}

	@Override
	public Order getDetail(int orderId, int userId) {
		String sqlOrder = "SELECT * FROM orders WHERE id=? AND user_id=?";
		String sqlDetails = "SELECT od.*, b.title AS bookTitle " + "FROM order_details od "
				+ "JOIN books b ON od.book_id = b.id " + "WHERE od.order_id=?";

		try (Connection con = DBContext.getConnection(); PreparedStatement ps1 = con.prepareStatement(sqlOrder)) {

			ps1.setInt(1, orderId);
			ps1.setInt(2, userId);
			ResultSet rs = ps1.executeQuery();

			if (!rs.next())
				return null;

			Order o = new Order();
			o.setId(orderId);
			o.setUserId(userId);
			o.setTotalPrice(rs.getBigDecimal("total_price"));
			o.setStatus(rs.getString("status"));
			o.setShippingAddress(rs.getString("shipping_address"));
			Timestamp ts = rs.getTimestamp("order_date");
			if (ts != null)
				o.setOrderDate(ts.toLocalDateTime());

			try (PreparedStatement ps2 = con.prepareStatement(sqlDetails)) {
				ps2.setInt(1, orderId);
				ResultSet rd = ps2.executeQuery();
				while (rd.next()) {
					OrderDetail d = new OrderDetail();
					d.setId(rd.getInt("id"));
					d.setOrderId(orderId);
					d.setBookId(rd.getInt("book_id"));
					d.setQuantity(rd.getInt("quantity"));
					d.setPrice(rd.getBigDecimal("price"));
					d.setBookTitle(rd.getString("bookTitle")); // ← Set tên sách
					o.addDetail(d);
				}

			}
			return o;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Order createOrderFromCart(int userId, Cart cart, String address) {

		String insertOrder = "INSERT INTO orders(user_id,total_price,status,shipping_address,order_date) VALUES(?,?,?,?,GETDATE())";
		String insertDetail = "INSERT INTO order_details(order_id,book_id,quantity,price) VALUES(?,?,?,?)";

		Connection con = null;

		try {
			con = DBContext.getConnection();
			con.setAutoCommit(false);

			// Tính tổng tiền
			BigDecimal totalPrice = BigDecimal.ZERO;
			for (CartItem item : cart.values()) {
				totalPrice = totalPrice.add(item.getTotal());
			}

			int orderId;

			// INSERT ORDER
			try (PreparedStatement ps = con.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS)) {
				ps.setInt(1, userId);
				ps.setBigDecimal(2, totalPrice);
				ps.setString(3, "PENDING");
				ps.setString(4, address); // ← lưu địa chỉ
				ps.executeUpdate();

				try (ResultSet rs = ps.getGeneratedKeys()) {
					if (!rs.next())
						throw new SQLException("Cannot get order id");
					orderId = rs.getInt(1);
				}
			}

			// INSERT DETAILS
			try (PreparedStatement ps = con.prepareStatement(insertDetail)) {
				for (CartItem item : cart.values()) {
					ps.setInt(1, orderId);
					ps.setInt(2, item.getBook().getId());
					ps.setInt(3, item.getQuantity());
					ps.setBigDecimal(4, item.getBook().getPrice());
					ps.addBatch();
				}
				ps.executeBatch();
			}

			con.commit();

			// Build Order object
			Order order = new Order();
			order.setId(orderId);
			order.setUserId(userId);
			order.setTotalPrice(totalPrice);
			order.setStatus("PENDING");
			order.setOrderDate(LocalDateTime.now());
			order.setShippingAddress(address); // ← set địa chỉ vào object

			// Thêm chi tiết order
			for (CartItem item : cart.values()) {
				OrderDetail d = new OrderDetail();
				d.setOrderId(orderId);
				d.setBookId(item.getBook().getId());
				d.setPrice(item.getBook().getPrice());
				d.setQuantity(item.getQuantity());
				order.addDetail(d);
			}

			return order;

		} catch (Exception e) {
			if (con != null)
				try {
					con.rollback();
				} catch (SQLException ignored) {
				}
			e.printStackTrace();
			return null;
		} finally {
			if (con != null)
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException ignored) {
				}
		}
	}

	@Override
	public boolean cancelOrder(int orderId, int userId) {
		String sqlDetail = "DELETE FROM order_details WHERE order_id=?";
		String sqlOrder = "DELETE FROM orders WHERE id=? AND user_id=?";
		try (Connection con = DBContext.getConnection()) {
			con.setAutoCommit(false);
			try (PreparedStatement ps = con.prepareStatement(sqlDetail)) {
				ps.setInt(1, orderId);
				ps.executeUpdate();
			}
			try (PreparedStatement ps = con.prepareStatement(sqlOrder)) {
				ps.setInt(1, orderId);
				ps.setInt(2, userId);
				int res = ps.executeUpdate();
				con.commit();
				return res > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}