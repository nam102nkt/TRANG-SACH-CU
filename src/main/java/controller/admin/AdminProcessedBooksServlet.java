package controller.admin;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import dao.BookDAOImpl;
import dao.IBookDAO;
import model.Book;

@WebServlet("/admin/books/processed")
public class AdminProcessedBooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private IBookDAO bookDAO = new BookDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Book> list = bookDAO.findProcessedBooks();
        req.setAttribute("books", list);

        req.setAttribute("contentPage", "/WEB-INF/views/admin/processed_books.jsp");
        req.getRequestDispatcher("/WEB-INF/views/admin/admin.jsp").forward(req, resp);
    }
}
