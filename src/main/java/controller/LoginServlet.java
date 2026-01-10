package controller;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import dao.CartDAOImpl;
import dao.ICartDAO;
import dao.IUserDAO;
import dao.UserDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IUserDAO userDAO;
    private ICartDAO cartDAO;

    public LoginServlet() {
        super();
        this.userDAO = new UserDAOImpl();
        this.cartDAO = new CartDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email").trim();
        String plainPassword = request.getParameter("password").trim();

        User user = userDAO.findByEmail(email);

        if (user != null && BCrypt.checkpw(plainPassword, user.getPassword())) {

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // --- HỢP NHẤT GIỎ HÀNG TỪ SESSION VÀ DB ---
            Cart sessionCart = (Cart) session.getAttribute("cart"); // Cart class mới
            Cart dbCart = new Cart();

            // Lấy giỏ hàng từ DB
            var dbCartItems = cartDAO.getCartByUserId(user.getId()); // trả về Map<Integer, CartItem>
            if (dbCartItems != null) {
                dbCartItems.values().forEach(item -> dbCart.add(item.getBook(), item.getQuantity()));
            }

            if (sessionCart != null) {
                // Gộp giỏ hàng tạm vào DB và Cart
                sessionCart.getItems().forEach(item -> {
                    dbCart.add(item.getBook(), item.getQuantity());
                    cartDAO.addItemToCart(user.getId(), item.getBook().getId(), item.getQuantity());
                });
            }

            // Lưu lại Cart vào session
            session.setAttribute("cart", dbCart);

            // (Optional) Thời gian sống session
            session.setMaxInactiveInterval(30 * 60);

            // Chuyển hướng về trang chủ
            response.sendRedirect(request.getContextPath());

        } else {
            request.setAttribute("errorMessage", "Email hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
