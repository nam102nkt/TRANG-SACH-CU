package controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import model.Cart;
import model.User;
import service.CartServiceImpl;
import service.ICartService;
/**
 * Servlet implementation class RemoveFromCartServlet
 */
@WebServlet("/remove_from_cart")
public class RemoveFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ICartService cartService = new CartServiceImpl();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int bookId = Integer.parseInt(req.getParameter("bookId"));
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) return;

		if (user == null) {
		    cart.remove(bookId);
		} else {
		    cartService.remove(user.getId(), bookId);
		    cart = cartService.getCart(user.getId());
		}

		session.setAttribute("cart", cart);

		resp.sendRedirect("cart");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
