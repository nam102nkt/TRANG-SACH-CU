package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;
import service.AdminServiceImpl;
import service.IAdminService;

import java.io.IOException;
import java.util.List;

import dao.BookDAOImpl;
import dao.IBookDAO;
import dao.IOrderDAO;
import dao.OrderDAOImpl;

/**
 * Servlet implementation class ManageOrdersServlet
 */
@WebServlet("/admin/orders")
public class ManageOrdersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IAdminService service = new AdminServiceImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    	List<Order> orders = service.getAllOrders();
    	System.out.println(" "+ req.getParameter("orders.size()"));
    	req.setAttribute("orders", orders);
        
		req.setAttribute("contentPage", "/WEB-INF/views/admin/orders.jsp");

		// 🔥 LUÔN forward về admin.jsp
		req.getRequestDispatcher("/WEB-INF/views/admin/admin.jsp").forward(req, resp);
        
//        req.getRequestDispatcher("/WEB-INF/views/admin/orders.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        service.updateOrderStatus(
            Integer.parseInt(req.getParameter("id")),
            req.getParameter("status")
        );
        resp.sendRedirect(req.getContextPath() + "/admin/orders");
    }
}