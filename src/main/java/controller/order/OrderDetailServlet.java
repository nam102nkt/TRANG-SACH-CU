package controller.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Order;
import model.User;
import service.IOrderService;
import service.OrderServiceImpl;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;

/**
 * Servlet xử lý hiển thị chi tiết đơn hàng
 */
@WebServlet("/order_detail")
public class OrderDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IOrderService orderService = new OrderServiceImpl();

	@Override
	protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws IOException, ServletException {

		HttpSession session = rq.getSession();
		User user = (User) session.getAttribute("user");

		// Kiểm tra user đã đăng nhập chưa
		if (user == null) {
			rs.sendRedirect(rq.getContextPath() + "/login");
			return;
		}

		// Lấy orderId từ parameter và kiểm tra hợp lệ
		String idStr = rq.getParameter("orderId"); // phải trùng với link JSP
		if (idStr == null || idStr.isEmpty()) {
			rs.sendRedirect(rq.getContextPath() + "/profile?tab=orders");
			return;
		}

		int orderId;
		try {
			orderId = Integer.parseInt(idStr);
		} catch (NumberFormatException e) {
			rs.sendRedirect(rq.getContextPath() + "/profile?tab=orders");
			return;
		}

		// Lấy chi tiết đơn hàng từ service
		Order order = orderService.getOrderDetail(orderId, user.getId());
		if (order == null) {
			rs.sendRedirect(rq.getContextPath() + "/profile?tab=orders");
			return;
		}
		order.setFullName(user.getFullName()); // gán từ user đăng nhập

		if (order != null && (order.getShippingAddress() == null || order.getShippingAddress().isEmpty())) {
			order.setShippingAddress(order.getShippingAddress()); // lấy địa chỉ từ user nếu chưa có
		}
		// Set attribute và forward sang JSP
		rq.setAttribute("order", order);
		rq.setAttribute("user", user); // truyền thông tin người đặt
		rq.getRequestDispatcher("/WEB-INF/views/order/order_detail.jsp").forward(rq, rs);
	}

	@Override
	protected void doPost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		doGet(rq, rs); // POST cũng xử lý giống GET
	}
}
