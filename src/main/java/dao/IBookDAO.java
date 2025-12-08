package dao;

import java.util.List;

import model.Book;

public interface IBookDAO {
    // Lấy 10 cuốn sách mới nhất làm "sách nổi bật"
    List<Book> getFeaturedBooks(); 
    // tìm book theo id
    Book findBookId(int id);
    // Tìm sách theo từ khóa (title hoặc author)
    java.util.List<model.Book> search(String keyword);
    // Lọc sách theo giá (min/max) và tình trạng (new/used/null)
    java.util.List<model.Book> filter(java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice, String condition);
    // Thêm sách mới, trả về id vừa tạo
    int insertBook(model.Book b);

}