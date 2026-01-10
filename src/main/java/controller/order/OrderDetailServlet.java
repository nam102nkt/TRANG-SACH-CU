package controller.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import service.IOrderService;
import service.OrderServiceImpl;

import java.io.IOException;

/**
 * Servlet implementation class OrderDetailServlet
 */
@WebServlet("/order_detail")
public class OrderDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IOrderService orderService = new OrderServiceImpl();

	protected void doGet(HttpServletRequest rq, HttpServletResponse rs) throws IOException, ServletException {

		HttpSession session = rq.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			rs.sendRedirect(rq.getContextPath() + "/login");
			return;
		}

		int orderId = Integer.parseInt(rq.getParameter("id"));
		var order = orderService.getOrderDetail(orderId, user.getId());

		rq.setAttribute("order", order);
		rq.getRequestDispatcher("/WEB-INF/views/order/order_detail.jsp").forward(rq, rs);
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
