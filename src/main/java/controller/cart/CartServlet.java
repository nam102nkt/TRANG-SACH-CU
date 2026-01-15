package controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import dao.BookDAOImpl;
import model.Book;
import model.Cart;

import java.io.IOException;

/**
 * CartServlet xử lý việc thêm/xóa/cập nhật các mặt hàng trong giỏ hàng được lưu trong session.
 *
 * Các hành động được hỗ trợ (qua tham số yêu cầu "action"): - add : thêm sách vào giỏ hàng
 * (yêu cầu id và số lượng tùy chọn) - remove : xóa sách khỏi giỏ hàng (yêu cầu
 * id) - update : cập nhật số lượng từ các tham số form (POST)
 *
 * Tất cả các hoạt động sẽ chuyển hướng trở lại cart.jsp để hiển thị giỏ hàng hiện tại.
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookDAOImpl bookDAO = new BookDAOImpl();

	/** Xử lý GET cho thêm/xóa và hiển thị */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}

		if ("add".equals(action)) {
			try {
				int id = Integer.parseInt(req.getParameter("id"));
				int qty = 1;
				if (req.getParameter("qty") != null)
					qty = Integer.parseInt(req.getParameter("qty"));
				Book b = bookDAO.findBookId(id);
				if (b != null) {
					cart.add(b, qty);
				}
			} catch (Exception e) {
				e.getMessage();
			}
			resp.sendRedirect(req.getContextPath() + "/cart");
			return;
		} else if ("remove".equals(action)) {
			try {
				int id = Integer.parseInt(req.getParameter("id"));
				cart.remove(id);
			} catch (Exception e) {
				e.getMessage();
			}
			resp.sendRedirect(req.getContextPath() + "/cart");
			return;
		}

		req.getRequestDispatcher("/WEB-INF/views/cart/cart.jsp").forward(req, resp);
	}

	/** Xử lý POST để cập nhật hàng loạt số lượng từ form giỏ hàng */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			resp.sendRedirect(req.getContextPath() + "/cart");
			return;
		}

		// Expect parameter names like qty_<bookId>
		for (String name : req.getParameterMap().keySet()) {
			if (name.startsWith("qty_")) {
				try {
					int bookId = Integer.parseInt(name.substring(4));
					int qty = Integer.parseInt(req.getParameter(name));
					cart.update(bookId, qty);
				} catch (Exception e) {
					e.getMessage(); }
			}
		}
		resp.sendRedirect(req.getContextPath() + "/cart");
	}
}
