
package dao;

import model.Review;
import java.util.*;

/** DAO đánh giá */
public interface IReviewDAO {
    int add(Review r);
    List<Review> getByBook(int bookId);
}
