package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Book;
import service.AdminServiceImpl;
import service.IAdminService;

import java.io.IOException;
import java.util.List;

import dao.BookDAOImpl;
import dao.IBookDAO;

/**
 * Servlet implementation class ManageBooksServlet
 */
@WebServlet("/admin/books")
public class ManageBooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IAdminService service = new AdminServiceImpl();
	private IBookDAO bookDAO = new BookDAOImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Book> pendingBooks = bookDAO.findPendingBooks();
        request.setAttribute("pendingBooks", pendingBooks);
        request.getRequestDispatcher("/WEB-INF/views/admin/manage_books.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        if ("delete".equals(action)) {
            service.deleteBook(Integer.parseInt(req.getParameter("id")));
        }
        resp.sendRedirect(req.getContextPath() + "/admin/books");
    }
}