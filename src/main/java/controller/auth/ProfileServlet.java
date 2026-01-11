package controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import model.User;
import service.IOrderService;
import service.IUserService;
import service.OrderServiceImpl;
import service.UserServiceImpl;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUserService userService = new UserServiceImpl();
	private IOrderService orderService = new OrderServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute("user");

		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		String tab = request.getParameter("tab");
		if (tab == null)
			tab = "info";

		if ("orders".equals(tab)) {
			request.setAttribute("orders", orderService.getOrdersByUser(user.getId()));
		}

		request.setAttribute("currentTab", tab);
		request.getRequestDispatcher("/WEB-INF/views/auth/profile.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user != null) {
			user.setFullName(request.getParameter("fullname"));
			user.setPhone(request.getParameter("phone"));

			userService.updateProfile(user);
			session.setAttribute("user", user);
		}

		response.sendRedirect(request.getContextPath() + "/profile?tab=info");
	}
}