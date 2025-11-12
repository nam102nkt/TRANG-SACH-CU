package dao;

import java.util.List;

import model.Book;

public interface IBookDAO {
    // Lấy 10 cuốn sách mới nhất làm "sách nổi bật"
    List<Book> getFeaturedBooks(); 
}