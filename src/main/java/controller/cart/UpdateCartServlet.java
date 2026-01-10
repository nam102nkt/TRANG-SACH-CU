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
 * Servlet implementation class UpdateCartServlet
 */
@WebServlet("/update_cart")
public class UpdateCartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ICartService cartService = new CartServiceImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		Cart cart = (Cart) session.getAttribute("cart");

		if (cart == null) {
			resp.sendRedirect("cart");
			return;
		}

		String action = req.getParameter("action");
		String bookIdRaw = req.getParameter("bookId");

		// 1️⃣ Trường hợp nhấn + hoặc -
		if (action != null && bookIdRaw != null) {
			int bookId = Integer.parseInt(bookIdRaw);

			if ("inc".equals(action)) {
				if (user == null)
					cart.increment(bookId);
				else
					cartService.increment(user.getId(), bookId);

			} else if ("dec".equals(action)) {
				if (user == null)
					cart.decrement(bookId);
				else
					cartService.decrement(user.getId(), bookId);
			}
		}

		// 2️⃣ Trường hợp update hàng loạt (qty_x)
		for (String p : req.getParameterMap().keySet()) {
			if (p.startsWith("qty_")) {
				int id = Integer.parseInt(p.substring(4));
				int qty = Integer.parseInt(req.getParameter(p));

				if (qty < 1)
					qty = 1;

				if (user == null) {
				    cart.update(id, qty);
				} else {
				    cartService.update(user.getId(), id, qty);
				    cart = cartService.getCart(user.getId()); // reload từ DB
				    session.setAttribute("cart", cart);
				}
			}
		}

		// 3️⃣ Đồng bộ lại cart từ DB nếu user đã login
		if (user != null) {
			cart = cartService.getCart(user.getId());
		}

		session.setAttribute("cart", cart);
		resp.sendRedirect("cart");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doPost(req, resp);
	}
}
