package controller;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CartItem;
import model.User;

import org.mindrot.jbcrypt.BCrypt; // <-- IMPORT BCrypt

import dao.CartDAOImpl;
import dao.ICartDAO;
import dao.IUserDAO;
import dao.UserDAOImpl;

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

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     * Khi người dùng truy cập /login, chỉ hiển thị form
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     * Khi người dùng nhấn nút "Đăng nhập"
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email").trim();
        String plainPassword = request.getParameter("password").trim(); // Mật khẩu gốc từ form

        // 1. Tìm user trong DB theo email
        User user = userDAO.findByEmail(email);

        // 2. Kiểm tra
        // if (user != null && user.getPassword().equals(plainPassword)) { // CÁCH CŨ, KHÔNG BẢO MẬT
        
        // CÁCH MỚI: Dùng BCrypt để so sánh mật khẩu gốc với chuỗi hash trong DB
        if (user != null && BCrypt.checkpw(plainPassword, user.getPassword())) {
            
            // === ĐĂNG NHẬP THÀNH CÔNG ===
            // Đây là lúc tạo ra Session (Stateful)
            
            // 1. Lấy (hoặc tạo mới) một HttpSession
            // "request.getSession()" có nghĩa là: 
            // "Server ơi, xem trình duyệt này đã có session nào chưa, 
            // nếu có thì đưa tôi, nếu chưa thì tạo một cái MỚI."
            HttpSession session = request.getSession();
            
            // 2. Lưu đối tượng User vào session
            // Nó hoạt động y hệt HashMap (Java Core - Quy tắc 2)
            // session.put("key", value)
            session.setAttribute("user", user); 
            // hợp nhất giỏ hàng tạm nếu có sản phẩm
            Map<Integer, CartItem> sessionCart = (Map<Integer, CartItem>)session.getAttribute("cart");
            if(sessionCart !=null && !sessionCart.isEmpty()) {
            	// gộp giỏ hàng tạm vào csdl 
            	cartDAO.mergeCart(sessionCart, user.getId());
            	// xóa giỏ hàng tạm sau khi gộp xong
            	session.removeAttribute("cart");
            }
            // tải lại giỏ hàng của user lên
            Map<Integer, CartItem> dbCart = cartDAO.getCartByUserId(user.getId());
            session.setAttribute("cart", dbCart);
            
            // 3. (Optional) Cài đặt thời gian "sống" của session (ví dụ: 30 phút)
            session.setMaxInactiveInterval(30 * 60); // 30 phút * 60 giây
            
            // 4. Chuyển hướng về trang chủ
            response.sendRedirect(request.getContextPath());

        } else {
            // === ĐĂNG NHẬP THẤT BẠI ===
            // Gửi thông báo lỗi quay lại trang login.jsp
            request.setAttribute("errorMessage", "Email hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}