package service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import model.AuthorStats;
import model.Book;
import model.Category;

public interface IBookService {
    List<Book> getFeaturedBooks();
    Book getBookDetail(int id);
    List<Book> search(String keyword);
    List<Book> filter(BigDecimal min, BigDecimal max, Integer categoryId, String sort, String condition);
    // SELLER
    void requestSellBook(Book b, int sellerId);
    // ADMIN
    void adminAddBook(Book b);
    List<AuthorStats> getTopAuthors(int limit);
    Map<Category, List<Book>> getBooksGroupedByCategory(int limitPerCategory);
}
