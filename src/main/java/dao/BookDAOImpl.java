package dao;

import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBContext;

public class BookDAOImpl implements IBookDAO {

	@Override
	public List<Book> getFeaturedBooks() {
		List<Book> books = new ArrayList<>();
		// Dùng "TOP 8" của SQL Server để lấy 10 sách mới nhất để hiển thị 2 hàng trên web
		String sql = "SELECT TOP 8 * FROM books ORDER BY id DESC";

		// Kết nối Java Core (Quy tắc 2):
		// Đây là cách quản lý tài nguyên JDBC "sạch sẽ" (từ Java 7+)
		// Nó sẽ tự động đóng conn, ps, rs ngay cả khi có lỗi.
		try (Connection conn = DBContext.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			// Lặp qua kết quả CSDL
			while (rs.next()) {
				Book book = new Book();
				// Map dữ liệu từ CSDL vào đối tượng JavaBean
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setAuthor(rs.getString("author"));
				book.setPrice(rs.getBigDecimal("price"));
				book.setImageUrl(rs.getString("image_url"));
				book.setStatus(rs.getString("status"));

				// Thêm sách vào danh sách
				books.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace(); // In lỗi ra Console
		}
		return books; // Trả về danh sách (có thể rỗng)
	}

	@Override
	public Book findBookId(int id) {
		// TODO Auto-generated method stub
		Book book = null;
		// Kết nối Java Core (Quy tắc 2): Dùng '?'
		// Đây là PreparedStatement. Nó NGĂN CHẶN lỗi SQL Injection.
		// TUYỆT ĐỐI không dùng "WHERE id = " + id
		String sql = "SELECT * FROM books WHERE id = ?";
		try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			// nhét id vào dấu ? đầu tiên
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				// chúng ta tìm theo id(khóa chính), nên chỉ có 1 qk
				// dùng if thay vì while
				if (rs.next()) {
					book = new Book();
					book.setId(rs.getInt("id"));
					book.setTitle(rs.getString("title"));
					book.setAuthor(rs.getString("author"));
					book.setPrice(rs.getBigDecimal("price"));
					book.setImageUrl(rs.getString("image_url"));
					// lấy mô tả
					book.setDescription(rs.getString("description"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return book;
	}

    @Override
    public java.util.List<Book> search(String keyword) {
        java.util.List<Book> list = new java.util.ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?";
        try (java.sql.Connection conn = database.DBContext.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            String k = "%" + keyword + "%";
            ps.setString(1, k); ps.setString(2, k);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Book b = new Book();
                    b.setId(rs.getInt("id"));
                    b.setTitle(rs.getString("title"));
                    b.setAuthor(rs.getString("author"));
                    b.setPrice(rs.getBigDecimal("price"));
                    b.setImageUrl(rs.getString("image_url"));
                    b.setDescription(rs.getString("description"));
                    list.add(b);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public java.util.List<Book> filter(java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice, String condition) {
        java.util.List<Book> list = new java.util.ArrayList<>();
        StringBuilder sb = new StringBuilder("SELECT * FROM books WHERE 1=1");
        if (minPrice != null) sb.append(" AND price >= ?");
        if (maxPrice != null) sb.append(" AND price <= ?");
        if (condition != null && !condition.isEmpty()) sb.append(" AND condition = ?");
        try (java.sql.Connection conn = database.DBContext.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            int idx = 1;
            if (minPrice != null) ps.setBigDecimal(idx++, minPrice);
            if (maxPrice != null) ps.setBigDecimal(idx++, maxPrice);
            if (condition != null && !condition.isEmpty()) ps.setString(idx++, condition);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Book b = new Book();
                    b.setId(rs.getInt("id"));
                    b.setTitle(rs.getString("title"));
                    b.setAuthor(rs.getString("author"));
                    b.setPrice(rs.getBigDecimal("price"));
                    b.setImageUrl(rs.getString("image_url"));
                    b.setDescription(rs.getString("description"));
                    list.add(b);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public int insertBook(Book b) {
        String sql = "INSERT INTO books(title,author,price,description,image_url,condition,status,seller_id) VALUES(?,?,?,?,?,?,?)";
        try (java.sql.Connection conn = database.DBContext.getConnection();
             java.sql.PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setBigDecimal(3, b.getPrice());
            ps.setString(4, b.getDescription());
            ps.setString(5, b.getImageUrl());
            ps.setString(6, "Used"); // Ví dụ mặc định
            
            // QUAN TRỌNG: Mặc định là PENDING (Chờ duyệt)
            ps.setString(7, "PENDING"); 
            
            // QUAN TRỌNG: Phải lấy ID của người đang đăng nhập (từ Session)
            // Hiện tại tạm thời để b.getSellerId() (Bạn phải set cái này từ Servlet)
            ps.setInt(8, b.getSellerId());int affected = ps.executeUpdate();
            try (java.sql.ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }

}
