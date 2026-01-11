package controller.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Book;
import model.Cart;
import model.User;
import service.IBookService;
import service.BookServiceImpl;

import java.io.IOException;

@WebServlet("/buy-now")
public class BuyNowServlet extends HttpServlet {

	private IBookService bookService = new BookServiceImpl();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		try {
			int bookId = Integer.parseInt(request.getParameter("bookId"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));

			Book book = bookService.getBookDetail(bookId);
			if (book == null) {
				response.sendRedirect(request.getContextPath() + "/");
				return;
			}

			// Tạo cart tạm chỉ chứa sách này
			Cart tempCart = new Cart();
			tempCart.setUserId(user.getId());
			tempCart.add(book, quantity);

			// Lưu tạm vào session để checkout dùng lại
			session.setAttribute("cart", tempCart);

			// Redirect sang checkout (checkout.jsp + PlaceOrderServlet)
			response.sendRedirect(request.getContextPath() + "/checkout");

		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/");
		}
	}
}
