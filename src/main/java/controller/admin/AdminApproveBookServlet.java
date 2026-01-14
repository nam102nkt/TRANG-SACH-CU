package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.BookDAOImpl;
import dao.IBookDAO;

/**
 * Servlet implementation class AdminApproveBookServlet
 */
@WebServlet("/admin/books/approve")
public class AdminApproveBookServlet extends HttpServlet {

    private IBookDAO bookDAO = new BookDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int bookId = Integer.parseInt(request.getParameter("bookId"));
        String action = request.getParameter("action");

        if ("approve".equals(action)) {
            bookDAO.updateStatus(bookId, "APPROVED");
        } else if ("reject".equals(action)) {
            bookDAO.updateStatus(bookId, "REJECTED");
        }

        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doPost(request, response);
    }
}