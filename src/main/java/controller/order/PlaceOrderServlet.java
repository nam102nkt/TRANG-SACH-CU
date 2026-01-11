package controller.order;

import java.io.IOException;
import java.util.Map;

import dao.IOrderDAO;
import dao.OrderDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Cart;
import model.CartItem;
import model.Order;
import model.User;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {

	private IOrderDAO orderDAO = new OrderDAOImpl();

	// GET → success page
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String orderId = request.getParameter("orderId");
		if (orderId == null) {
			response.sendRedirect(request.getContextPath() + "/");
			return;
		}

		request.setAttribute("orderId", orderId);
		request.getRequestDispatcher("/WEB-INF/views/order/checkout_success.jsp").forward(request, response);
	}

	// POST → tạo đơn hàng
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		User user = (User) session.getAttribute("user");
		Cart cart = (Cart) session.getAttribute("cart");

		if (user == null || cart == null || cart.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/cart");
			return;
		}

		// tạo order từ MAP
		String address = request.getParameter("shippingAddress");
		Order order = orderDAO.createOrderFromCart(user.getId(), cart, address);

		if (order == null) {

			response.sendRedirect(request.getContextPath() + "/checkout?error=true");
			return;
		}
		order.setShippingAddress(address);
		// xoá cart
		session.removeAttribute("cart");

		response.sendRedirect(request.getContextPath() + "/place-order?orderId=" + order.getId());
	}
}
