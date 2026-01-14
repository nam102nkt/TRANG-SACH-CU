package controller.book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.AuthorStats;
import model.Book;
import model.Category;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import service.BookServiceImpl;
import service.IBookService;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private IBookService bookService = new BookServiceImpl();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	List<Book> featuredBooks = bookService.getFeaturedBooks();
    	List<AuthorStats> topAuthors = bookService.getTopAuthors(6);
    	Map<Category, List<Book>> booksByCategory = bookService.getBooksGroupedByCategory(6);

        request.setAttribute("featuredBooks", featuredBooks);
        request.setAttribute("topAuthors", topAuthors);
        request.setAttribute("booksByCategory", booksByCategory);
        request.getRequestDispatcher("/WEB-INF/views/book/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
