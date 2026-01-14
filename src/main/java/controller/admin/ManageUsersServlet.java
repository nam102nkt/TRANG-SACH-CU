package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.AdminServiceImpl;
import service.IAdminService;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class ManageUsersServlet
 */
@WebServlet("/admin/users")
public class ManageUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IAdminService service = new AdminServiceImpl();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<User> users = service.getAllUsers();
		req.setAttribute("users", users);

		req.setAttribute("contentPage", "users.jsp");

		// 🔥 LUÔN forward về admin.jsp
		req.getRequestDispatcher("/WEB-INF/views/admin/admin.jsp").forward(req, resp);
		
//        req.getRequestDispatcher("/WEB-INF/views/admin/users.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		service.toggleUser(Integer.parseInt(req.getParameter("id")), Boolean.parseBoolean(req.getParameter("active")));
		resp.sendRedirect(req.getContextPath() + "/admin/users");
	}
}