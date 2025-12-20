
package dao;

import model.Review;
import java.util.*;

/** Triển khai ReviewDAO */
public class ReviewDAOImpl implements ReviewDAO {

    /** Lưu đánh giá */
    public int add(Review r){ return 1; }

    /** Lấy danh sách đánh giá theo bookId */
    public List<Review> getByBook(int id){ return new ArrayList<>(); }
}
