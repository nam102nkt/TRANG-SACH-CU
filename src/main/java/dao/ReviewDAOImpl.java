package dao;

import model.Review;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOImpl implements IReviewDAO {

    @Override
    public int add(Review r) {
        String sql = "INSERT INTO reviews(user_id, book_id, rating, comment, created_at) VALUES (?, ?, ?, ?, GETDATE())";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, r.getUserId());
            ps.setInt(2, r.getBookId());
            ps.setInt(3, r.getRating());
            ps.setString(4, r.getComment());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public List<Review> getByBook(int bookId) {
        List<Review> list = new ArrayList<>();

        String sql = " SELECT r.id, r.user_id, r.book_id, r.rating, r.comment, r.created_at, u.fullname "
        		+ "FROM reviews r "
        		+ "JOIN users u ON r.user_id = u.id "
        		+ "WHERE r.book_id = ? "
        		+ "ORDER BY r.created_at DESC" ;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Review r = new Review();
                    r.setId(rs.getInt("id"));
                    r.setUserId(rs.getInt("user_id"));
                    r.setBookId(rs.getInt("book_id"));
                    r.setRating(rs.getInt("rating"));
                    r.setComment(rs.getString("comment"));
                    r.setCreatedAt(rs.getTimestamp("created_at"));

                    // Optional nếu Review có field userName
                    r.setUserName(rs.getString("fullname"));

                    list.add(r);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
