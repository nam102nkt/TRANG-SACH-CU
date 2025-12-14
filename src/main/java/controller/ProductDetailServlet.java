package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Book;

import java.io.IOException;

import dao.BookDAOImpl;
import dao.IBookDAO;

/**
 * Servlet implementation class ProductDetailServlet
 */
@WebServlet("/product-detail")
public class ProductDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IBookDAO bookDAO;   
    /**
     * @see HttpServlet#HttpServlet()
     */
	public ProductDetailServlet() {
		this.bookDAO = new BookDAOImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			// đọc id từ ỦL (vd: ?id=5)
			int id = Integer.parseInt(request.getParameter("id"));
			// gọi DAO để lấy book
			Book book = bookDAO.findBookId(id);
			if (book != null) {
				// 'đóng gói' sách vào request
				request.setAttribute("book", book);
				// chuyển tiếp sang trang (view) detail.jsp
				request.getRequestDispatcher("product_detail.jsp").forward(request, response);
			} else {
				// không tìm thấy sách
				response.sendRedirect("index.jsp");// tạm quay lại trang chủ
			}
		} catch (NumberFormatException e) {
			// nếu id không phải là số
			response.sendRedirect("index.jsp");
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
