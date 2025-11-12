package com.oldbookstore.dao;

import com.oldbookstore.model.Book;
import java.util.List;

public interface IBookDAO {
    // Lấy 10 cuốn sách mới nhất làm "sách nổi bật"
    List<Book> getFeaturedBooks(); 
}