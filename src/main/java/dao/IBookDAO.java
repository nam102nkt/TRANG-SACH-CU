package dao;

import java.math.BigDecimal;
import java.util.List;

import model.Book;
import model.Category;

public interface IBookDAO {
	// Lấy 10 cuốn sách mới nhất làm "sách nổi bật"
	List<Book> getFeaturedBooks();

	// tìm book theo id
	Book findBookId(int id);

	// Tìm sách theo từ khóa (title hoặc author)
	List<Book> search(String keyword);

	// Lọc sách theo giá (min/max) và tình trạng (new/used/null)
	List<Book> filter(BigDecimal min, BigDecimal max, Integer categoryId, String sort, String condition);

	// Thêm sách mới, trả về id vừa tạo
//    int insertBook(Book b);
	List<Book> getBooksByIds(List<Integer> ids);
	List<Category> findAll();

	// user
	void insertPending(Book book);

	// admin
	void insertActive(Book book);

	boolean approveBook(int bookId);

	boolean rejectBook(int bookId);
	
	List<Book> findProcessedBooks();

	// query
	List<Book> findPendingBooks();

	void updateStatus(int bookId, String status);

	List<Book> findApprovedBooks();

	List<Book> searchSuggest(String keyword);
}