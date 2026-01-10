package controller.order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.User;
import service.IOrderService;
import service.OrderServiceImpl;

import java.io.IOException;

/** Lịch sử mua hàng */
@WebServlet("/order_history")
public class OrderHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private IOrderService orderService = new OrderServiceImpl();

    protected void doGet(HttpServletRequest rq,HttpServletResponse rs)
            throws IOException,ServletException {

        HttpSession session = rq.getSession();
        User u = (User) session.getAttribute("user");
		if (u == null) {
			rs.sendRedirect(rq.getContextPath() + "/login"); return; }

        rq.setAttribute("orders", orderService.getOrdersByUser(u.getId()));
        rq.getRequestDispatcher("/WEB-INF/views/order/order_history.jsp").forward(rq,rs);
    }
}

