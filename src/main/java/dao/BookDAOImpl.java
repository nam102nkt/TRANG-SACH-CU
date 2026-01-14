package dao;

import model.Book;
import model.AuthorStats;
import model.Category;
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
	
	public List<Category> findAll() {
	    List<Category> list = new ArrayList<>();
	    String sql = "SELECT id, name FROM categories";

	    try (Connection c = DBContext.getConnection();
	         PreparedStatement ps = c.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            list.add(new Category(rs.getInt("id"), rs.getString("name")));
	        }
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

	private static final Set<String> VALID_STATUS = Set.of("PENDING","ACTIVE","INACTIVE");

	public void updateStatus(int bookId, String status) {
	    if (!VALID_STATUS.contains(status))
	        throw new IllegalArgumentException("Invalid status");

	    try (Connection conn = DBContext.getConnection();
	         PreparedStatement ps = conn.prepareStatement(UPDATE_STATUS)) {

	        ps.setString(1, status);
	        ps.setInt(2, bookId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
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

	@Deprecated
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

		String sql = "SELECT TOP 10 id, title, author, price, image_url FROM books WHERE status = 'ACTIVE' AND title LIKE ? ORDER BY CASE WHEN title LIKE ? THEN 0 ELSE 1 END, title";

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

	public boolean approveBook(int bookId) {
	    String sql = "UPDATE books SET status = 'ACTIVE' WHERE id = ? AND status = 'PENDING'";

	    try (Connection conn = DBContext.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, bookId);
	        int updated = ps.executeUpdate();
	        return updated > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public boolean rejectBook(int bookId) {
	    String sql = "UPDATE books SET status = 'INACTIVE' WHERE id = ? AND status = 'PENDING'";
	    try (Connection conn = DBContext.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, bookId);
	        return ps.executeUpdate() > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public List<Book> findProcessedBooks() {
	    String sql = "SELECT * FROM books WHERE status IN ('ACTIVE','INACTIVE')";
	    List<Book> list = new ArrayList<>();
	    try (Connection conn = DBContext.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) list.add(map(rs));
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return list;
	}
public List<AuthorStats> findTopAuthors(int limit) {
	    List<AuthorStats> list = new ArrayList<>();
	    String sql = "SELECT TOP (?) author, COUNT(*) AS total "
	    		+ "FROM books "
	    		+ "WHERE status = 'ACTIVE' "
	    		+ "GROUP BY author "
	    		+ "ORDER BY total DESC";

	    try (Connection c = DBContext.getConnection();
	         PreparedStatement ps = c.prepareStatement(sql)) {

	        ps.setInt(1, limit);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            AuthorStats a = new AuthorStats();
	            a.setAuthor(rs.getString("author"));
	            a.setTotalBooks(rs.getInt("total"));
	            list.add(a);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	public List<AuthorStats> getAllAuthors() {
	    String sql = "SELECT author, COUNT(*) AS cnt "
	    		+ "FROM books "
	    		+ "WHERE status = 'ACTIVE' "
	    		+ "GROUP BY author "
	    		+ "ORDER BY cnt DESC";

	    List<AuthorStats> list = new ArrayList<>();

	    try (Connection c = DBContext.getConnection();
	         PreparedStatement ps = c.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            list.add(new AuthorStats(
	                rs.getString("author"),
	                rs.getInt("cnt")
	            ));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	
	public Map<Category, List<Book>> findGroupedByCategory(int limitEach) {
	    Map<Integer, Category> catMap = new LinkedHashMap<>();
	    Map<Category, List<Book>> result = new LinkedHashMap<>();

	    String sql = "SELECT c.id AS cid, c.name AS cname, b.* "
	    		+ "FROM categories c "
	    		+ "JOIN books b ON b.category_id = c.id "
	    		+ "WHERE b.status = 'ACTIVE' "
	    		+ "ORDER BY c.name, b.created_at DESC";

	    try (Connection c = DBContext.getConnection();
	         PreparedStatement ps = c.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {

	            int cid = rs.getInt("cid");
	            Category cat = catMap.get(cid);

	            if (cat == null) {
	                cat = new Category();
	                cat.setId(cid);
	                cat.setName(rs.getString("cname"));
	                catMap.put(cid, cat);
	                result.put(cat, new ArrayList<>());
	            }

	            List<Book> books = result.get(cat);
	            if (books.size() < limitEach) {
	                books.add(map(rs));
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result;
	}

}
