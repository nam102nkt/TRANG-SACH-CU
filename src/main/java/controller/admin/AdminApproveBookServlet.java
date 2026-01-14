package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;

import dao.BookDAOImpl;
import dao.IBookDAO;

/**
 * Servlet implementation class AdminApproveBookServlet
 */
@WebServlet("/admin/books/approve")
public class AdminApproveBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private IBookDAO bookDAO = new BookDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User admin = (User) session.getAttribute("user");
        if (admin == null || !"ADMIN".equals(admin.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(request.getParameter("bookId"));
        } catch (Exception e) {
            response.sendError(400, "Invalid book id");
            return;
        }

        String action = request.getParameter("action");

        boolean result;
        if ("approve".equals(action)) {
            result = bookDAO.approveBook(bookId);
        } else if ("reject".equals(action)) {
            result = bookDAO.rejectBook(bookId);
        } else {
            response.sendError(400, "Invalid action");
            return;
        }

        if (!result) {
            response.sendError(400, "Invalid state or already processed");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/admin/books");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doPost(request, response);
    }
}