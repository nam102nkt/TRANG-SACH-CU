package controller.auth;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.User;
import service.CartServiceImpl;
import service.ICartService;
import service.IUserService;
import service.UserServiceImpl;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUserService userService ;
	private ICartService cartService;

	public LoginServlet() {
		super();
		this.userService= new UserServiceImpl(); 
		this.cartService= new CartServiceImpl();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		HttpSession session = request.getSession();
		Cart sessionCart = (Cart) session.getAttribute("cart");

		User user = userService.login(email, password, sessionCart);

		if (user != null) {
		    session.setAttribute("user", user);
		    Cart cart = cartService.getCart(user.getId());
		    session.setAttribute("cart", cart);

		    switch (user.getRole()) {
		        case "ADMIN":
		            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
		            break;
		        case "SELLER":
		        case "BUYER":
		            response.sendRedirect(request.getContextPath() + "/home");
		            break;
		        default:
		            response.sendRedirect(request.getContextPath()+"/login");
		    }
		} else {
			request.setAttribute("errorMessage", "Email hoặc mật khẩu không đúng!");
			request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
	}
}