package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.AdminServiceImpl;
import service.IAdminService;

import java.io.IOException;

/**
 * Servlet implementation class AdminDashboardServlet
 */
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IAdminService service = new AdminServiceImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userCount", service.countUsers());
        req.setAttribute("orderCount", service.countOrders());
        req.setAttribute("bookCount", service.countBooks());
        req.setAttribute("revenue", service.totalRevenue());

        req.getRequestDispatcher("/WEB-INF/views/admin/admin.jsp").forward(req, resp);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
