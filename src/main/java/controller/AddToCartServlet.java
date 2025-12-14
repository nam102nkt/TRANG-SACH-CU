package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Book;
import model.CartItem;
import model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dao.BookDAOImpl;
import dao.CartDAOImpl;
import dao.IBookDAO;
import dao.ICartDAO;

/**
 * Servlet implementation class AddToCartServlet
 */
@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IBookDAO bookDAO = new BookDAOImpl();
	private ICartDAO cartDAO = new CartDAOImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			// lấy thông tin từ form(detail.jsp)
			int id = Integer.parseInt(request.getParameter("bookId"));
			// (thêm ô nhập số lượng, mặc định là 1)
			// Đọc SỐ LƯỢNG từ form (với giá trị mặc định là 1 nếu lỗi)
			int quantity = 1;
			try {
			    quantity = Integer.parseInt(request.getParameter("quantity"));
			    if (quantity < 1) { // Đảm bảo số lượng luôn > 0
			        quantity = 1;
			    }
			} catch (NumberFormatException e) {
			    quantity = 1; // Nếu ai đó cố tình sửa "quantity=abc"
			}

			// lấy session
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			// ===Kiến trúc Hybrid
			if(user ==null) {
				// kịch bản 1: Khách (chưa đăng nhập)
				// lấy giỏ hàng từ session
				// với giỏ hàng là một Map<Integer, Item>
				Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
				// nếu trog session chưa có giỏ hàng thì tạo mới
				if (cart == null) {
					cart = new HashMap<>();
				}
				// lấy sách từ csdl
				Book book = bookDAO.findBookId(id);
				if (book != null) {
					if (cart.containsKey(id)) {// lấy key(integer"id" so id)
						// nếu đã có -> chỉ tăng số lượng
						CartItem existingItem = cart.get(id);
						existingItem.setQuantity(existingItem.getQuantity() + quantity);
					} else {
						// nếu chưa có -> tạo món hàng mới và bỏ vào giỏ hàng
						CartItem newItem = new CartItem(book, quantity);
						cart.put(id, newItem);
					}
				}
				// Lưu/cập nhập giỏ hàng
				session.setAttribute("cart", cart);
			}else {
				// kịch bản 2: user đã đăng nhập
				// thêm vào csdl
				cartDAO.addItemToCart(user.getId(), id, quantity);
				//"đồng bộ": tải lại giỏ hàng từ CSDL lên session
				//(để Header counter và cart.jsp luôn đúng)
				Map<Integer, CartItem> dbCart = cartDAO.getCartByUserId(user.getId());
				session.setAttribute("cart", dbCart);
			}
			// chuyển tiếp đến trang giỏ hàng
			response.sendRedirect("cart.jsp");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendRedirect("index.jsp");// về trang chủ nếu có lỗi
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
