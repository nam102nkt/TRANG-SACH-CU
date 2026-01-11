package controller.order;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.Order;
import model.User;
import service.IOrderService;
import service.OrderServiceImpl;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

	private IOrderService orderService = new OrderServiceImpl();

	// GET → hiển thị trang xác nhận thanh toán
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		Cart cart = (Cart) session.getAttribute("cart");

		if (cart == null || cart.isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/cart");
			return;
		}

		req.getRequestDispatcher("/WEB-INF/views/order/checkout.jsp").forward(req, resp);
	}

	// POST → xử lý đặt hàng
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		Cart cart = (Cart) session.getAttribute("cart");
		String address = req.getParameter("shippingAddress");

		if (address == null || address.isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/checkout?error=address");
			return;
		}
		if (user == null || cart == null || cart.isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/cart");
			return;
		}

		Order order = orderService.checkout(user.getId(), cart, address);

		if (order == null) {
			resp.sendRedirect(req.getContextPath() + "/checkout?error=true");
			return;
		}

		req.setAttribute("orderId", order.getId());
		req.getRequestDispatcher("/WEB-INF/views/order/checkout_success.jsp").forward(req, resp);
	}
}
