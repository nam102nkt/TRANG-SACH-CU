package controller.book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import model.Book;
import model.User;
import service.BookServiceImpl;
import service.IBookService;
import service.IWishlistService;
import service.WishlistServiceImpl;

@WebServlet("/book-detail")
public class BookDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private IBookService bookService = new BookServiceImpl();
    private IWishlistService wishlistService = new WishlistServiceImpl();
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Book book = bookService.getBookDetail(id);

            if (book == null) {
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            request.setAttribute("book", book);

            HttpSession session = request.getSession(false);
            User u = (User) (session != null ? session.getAttribute("user") : null);

            boolean isInWishlist = false;
            if (u != null) {
                isInWishlist = wishlistService.isInWishlist(u.getId(), id);
            }

            request.setAttribute("isInWishlist", isInWishlist);

            request.getRequestDispatcher("/WEB-INF/views/book/book_detail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}