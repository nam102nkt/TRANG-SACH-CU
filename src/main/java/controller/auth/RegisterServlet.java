package controller.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;// Annotation này cũng là 1 cách thay cho web.xml
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import model.User;
import service.IUserService;
import service.UserServiceImpl;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IUserService userService = new UserServiceImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response) Khi người dùng truy cập /register (gõ URL), chúng ta chỉ CẦN
	 *      HIỂN THỊ trang JSP.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Chuyển tiếp (forward) yêu cầu đến trang register.jsp
		// "Forward" là một hành động phía server, URL trên trình duyệt không đổi.
		request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * Khi người dùng NHẤN NÚT SUBMIT (method="POST") trên form.
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String fullName = request.getParameter("fullname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        String role = request.getParameter("role");
        if (role == null || !role.equals("SELLER")) role = "BUYER";

        User u = new User();
        u.setFullName(fullName);
        u.setEmail(email);
        u.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(12)));
        u.setPhone(phone);
        u.setRole(role);

        boolean success = userService.register(u);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            request.setAttribute("errorMessage", "Đăng ký thất bại! Email có thể đã tồn tại.");
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
        }
    }
}
