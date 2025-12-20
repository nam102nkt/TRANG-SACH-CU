package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.Book;
import model.User;
import dao.BookDAOImpl;
import dao.IBookDAO;
import dao.WishlistDAO;

import java.io.IOException;

@WebServlet("/product-detail")
public class ProductDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IBookDAO bookDAO;

    public ProductDetailServlet() {
        this.bookDAO = new BookDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Book book = bookDAO.findBookId(id);

            if (book == null) {
                response.sendRedirect("index.jsp");
                return;
            }

            request.setAttribute("book", book);

            // === KIỂM TRA TRONG WISHLIST ===
            HttpSession session = request.getSession();
            User u = (User) session.getAttribute("user");
            Integer userId = null;

            if (u != null) {
                userId = u.getId();
            }

            boolean isInWishlist = false;

            if (userId != null) {
                WishlistDAO wdao = new WishlistDAO();
                try {
                    isInWishlist = wdao.exists(userId, id);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            request.setAttribute("isInWishlist", isInWishlist);


            request.getRequestDispatcher("product_detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("index.jsp");
        }
    }
}
