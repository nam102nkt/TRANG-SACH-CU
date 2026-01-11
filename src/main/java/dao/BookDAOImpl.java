package dao;

import model.Book;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;

public class BookDAOImpl implements IBookDAO {
	private static final String INSERT_PENDING = "INSERT INTO books(title, author, price, description, image_url, [condition], status, seller_id, category_id) "
			+ "VALUES (?, ?, ?, ?, ?, ?, 'PENDING', ?, ?)";

	private static final String FIND_PENDING = "SELECT b.*, u.fullname FROM books b JOIN users u ON b.seller_id = u.id WHERE b.status = 'PENDING'";

	private static final String UPDATE_STATUS = "UPDATE books SET status = ? WHERE id = ?";

	private static final String FIND_APPROVED = "SELECT * FROM books WHERE status = 'APPROVED'";

	private Book map(ResultSet rs) throws SQLException {
		Book b = new Book();
		b.setId(rs.getInt("id"));
		b.setTitle(rs.getString("title"));
		b.setAuthor(rs.getString("author"));
		b.setPrice(rs.getBigDecimal("price"));
		b.setDescription(rs.getString("description"));
		b.setImageUrl(rs.getString("image_url"));
		b.setStatus(rs.getString("status"));
		b.setSellerId(rs.getInt("seller_id"));
		b.setCondition(rs.getString("condition"));
		b.setCategoryId(rs.getInt("category_id"));

		Timestamp ts = rs.getTimestamp("created_at");
		if (ts != null)
			b.setCreatedAt(ts.toLocalDateTime());

		return b;
	}

	@Override
	public List<Book> getFeaturedBooks() {
		String sql = "SELECT TOP 8 * FROM books WHERE status='ACTIVE' ORDER BY created_at DESC";
		List<Book> list = new ArrayList<>();

		try (Connection c = DBContext.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next())
				list.add(map(rs));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Book findBookId(int id) {
		String sql = "SELECT * FROM books WHERE id = ?";
		try (Connection c = DBContext.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return map(rs);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Book> search(String keyword) {
		String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?";
		List<Book> list = new ArrayList<>();

		try (Connection c = DBContext.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			String k = "%" + keyword + "%";
			ps.setString(1, k);
			ps.setString(2, k);

			ResultSet rs = ps.executeQuery();
			while (rs.next())
				list.add(map(rs));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Book> filter(BigDecimal min, BigDecimal max, Integer categoryId, String sort, String condition) {

		StringBuilder sql = new StringBuilder("SELECT * FROM books WHERE status = 'ACTIVE'");

		if (min != null)
			sql.append(" AND price >= ?");
		if (max != null)
			sql.append(" AND price <= ?");
		if (categoryId != null)
			sql.append(" AND category_id = ?");
		if (condition != null && !condition.isBlank())
			sql.append(" AND book_condition = ?");

		if ("asc".equalsIgnoreCase(sort))
			sql.append(" ORDER BY price ASC");
		else if ("desc".equalsIgnoreCase(sort))
			sql.append(" ORDER BY price DESC");
		else
			sql.append(" ORDER BY created_at DESC");

		List<Book> list = new ArrayList<>();

		try (Connection c = DBContext.getConnection(); PreparedStatement ps = c.prepareStatement(sql.toString())) {

			int i = 1;
			if (min != null)
				ps.setBigDecimal(i++, min);
			if (max != null)
				ps.setBigDecimal(i++, max);
			if (categoryId != null)
				ps.setInt(i++, categoryId);
			if (condition != null && !condition.isBlank())
				ps.setString(i++, condition.toUpperCase());

			ResultSet rs = ps.executeQuery();
			while (rs.next())
				list.add(map(rs));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

//    @Override
//    public int insertBook(Book b) {
//        String sql = "INSERT INTO books(title,author,price,description,image_url,status,"
//        		+ "seller_id,condition,category_id,created_at)"
//        		+ "VALUES(?,?,?,?,?,'PENDING',?,?,?,GETDATE())";
//
//        try (Connection c = DBContext.getConnection();
//             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//            ps.setString(1, b.getTitle());
//            ps.setString(2, b.getAuthor());
//            ps.setBigDecimal(3, b.getPrice());
//            ps.setString(4, b.getDescription());
//            ps.setString(5, b.getImageUrl());
//            ps.setInt(6, b.getSellerId());
//            ps.setString(7, b.getCondition());
//            ps.setInt(8, b.getCategoryId());
//
//            ps.executeUpdate();
//            ResultSet rs = ps.getGeneratedKeys();
//            if (rs.next()) return rs.getInt(1);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return -1;
//    }

	@Override
	public List<Book> getBooksByIds(List<Integer> ids) {
		if (ids == null || ids.isEmpty())
			return Collections.emptyList();

		String placeholders = String.join(",", Collections.nCopies(ids.size(), "?"));
		String sql = "SELECT * FROM books WHERE id IN (" + placeholders + ")";

		List<Book> list = new ArrayList<>();

		try (Connection c = DBContext.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

			for (int i = 0; i < ids.size(); i++)
				ps.setInt(i + 1, ids.get(i));

			ResultSet rs = ps.executeQuery();
			while (rs.next())
				list.add(map(rs));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void insertPending(Book book) {
		try (Connection conn = DBContext.getConnection();
				PreparedStatement ps = conn.prepareStatement(INSERT_PENDING)) {

			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setBigDecimal(3, book.getPrice());
			ps.setString(4, book.getDescription());
			ps.setString(5, book.getImageUrl());
			ps.setString(6, book.getCondition());
			ps.setInt(7, book.getSellerId());
			ps.setInt(8, book.getCategoryId());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Book> findPendingBooks() {
		List<Book> list = new ArrayList<>();
		try (Connection conn = DBContext.getConnection();
				PreparedStatement ps = conn.prepareStatement(FIND_PENDING);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Book b = new Book();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setAuthor(rs.getString("author"));
				b.setPrice(rs.getBigDecimal("price"));
				b.setStatus(rs.getString("status"));
				b.setSellerName(rs.getString("fullname"));
				list.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void updateStatus(int bookId, String status) {
		try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_STATUS)) {

			ps.setString(1, status);
			ps.setInt(2, bookId);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Book> findApprovedBooks() {
		List<Book> list = new ArrayList<>();
		try (Connection conn = DBContext.getConnection();
				PreparedStatement ps = conn.prepareStatement(FIND_APPROVED);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Book b = new Book();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				list.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void insertActive(Book book) {
		String sql = "INSERT INTO books(title, author, price, description, image_url,[condition], status, seller_id, category_id)"
				+ "VALUES (?, ?, ?, ?, ?, ?, 'ACTIVE', ?, ?)";

		try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setBigDecimal(3, book.getPrice());
			ps.setString(4, book.getDescription());
			ps.setString(5, book.getImageUrl());
			ps.setString(6, book.getCondition());
			ps.setInt(7, book.getSellerId());
			ps.setInt(8, book.getCategoryId());
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Book> searchSuggest(String keyword) {
		List<Book> list = new ArrayList<>();

		String sql = """
				    SELECT TOP 10 id, title, author, price, image_url
				    FROM books
				    WHERE status = 'ACTIVE'
				      AND title LIKE ?
				    ORDER BY
				      CASE
				        WHEN title LIKE ? THEN 0
				        ELSE 1
				      END,
				      title
				""";

		try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, "%" + keyword + "%"); // chứa
			ps.setString(2, keyword + "%"); // bắt đầu

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book b = new Book();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setAuthor(rs.getString("author"));
				b.setPrice(rs.getBigDecimal("price"));
				b.setImageUrl(rs.getString("image_url"));
				list.add(b);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public void approveBook(int bookId) {
		updateStatus(bookId, "ACTIVE");
	}

	@Override
	public void rejectBook(int bookId) {
		updateStatus(bookId, "INACTIVE");

	}
}