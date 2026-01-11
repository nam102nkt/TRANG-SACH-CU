package controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import model.Book;
import model.Cart;
import model.User;
import service.CartServiceImpl;
import service.ICartService;
import dao.BookDAOImpl;
import dao.IBookDAO;

/**
 * Servlet implementation class AddToCartServlet
 */
@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ICartService cartService = new CartServiceImpl();
	private IBookDAO bookDAO = new BookDAOImpl();

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int bookId = Integer.parseInt(req.getParameter("bookId"));
		int qty = Integer.parseInt(req.getParameter("quantity"));

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null)
			cart = new Cart();

		if (user == null) {
			Book b = bookDAO.findBookId(bookId);
			cart.add(b, qty);
		} else {
			cartService.add(user.getId(), bookId, qty);
			cart = cartService.getCart(user.getId());
		}

		session.setAttribute("cart", cart);
		resp.sendRedirect("cart");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

}
