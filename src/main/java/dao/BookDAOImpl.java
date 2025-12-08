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
		// Dùng "TOP 10" của SQL Server để lấy 10 sách mới nhất
		String sql = "SELECT TOP 10 * FROM books ORDER BY id DESC";

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
}