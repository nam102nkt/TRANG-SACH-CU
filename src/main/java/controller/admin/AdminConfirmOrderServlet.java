package controller.admin;

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
 * Servlet implementation class AdminConfirmOrderServlet
 */
@WebServlet("/admin/orders/confirm")
public class AdminConfirmOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private IOrderService service = new OrderServiceImpl();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession(false);
        User admin = (User) session.getAttribute("user");

        if (admin == null || !"ADMIN".equals(admin.getRole())) {
            resp.sendError(403);
            return;
        }

        int orderId = Integer.parseInt(req.getParameter("orderId"));
        service.confirmPayment(orderId);

        resp.sendRedirect(req.getContextPath() + "/admin/orders");
    }
}
