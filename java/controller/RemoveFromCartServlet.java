package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.CartItem;
import model.User;

import java.io.IOException;
import java.util.Map;

import dao.CartDAOImpl;
import dao.ICartDAO;

/**
 * Servlet implementation class RemoveFromCartServlet
 */
@WebServlet("/remove_from_cart")
public class RemoveFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ICartDAO cartDAO = new CartDAOImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int bookId = Integer.parseInt(request.getParameter("bookId"));
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			// ==Kiến trúc Hybrid==
			if (user == null) {
				// Kịch bản 1: Khách (guest) chưa đăng nhập
				System.out.println(">> xóa" + bookId + "khỏi gh tạm thời");
				Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");

				if (cart != null && cart.containsKey(bookId)) {
					cart.remove(bookId);
				}
				session.setAttribute("cart", cart);// lưu lại giỏ hàng
			} else {
				// Kịch bản 2: User Đã đăng nhập
				System.out.println(">>> DEBUG: user(id" + user.getId() + "xóa khỏi vv");
				// xóa trong CSDL
				cartDAO.removeItemFromCart(user.getId(), bookId);

				// đồng bộ: tải lại giỏ hàng từ CSDL lên Session
				Map<Integer, CartItem> dbCart = cartDAO.getCartByUserId(user.getId());
				session.setAttribute("cart", dbCart);
				
			}
			// quay lại trang giỏ hàng
			response.sendRedirect("cart.jsp");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendRedirect("cart.jsp");// lây về giỏ hàng nếu lỗi
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
