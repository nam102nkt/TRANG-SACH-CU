package controller.book;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;

import model.Book;
import model.User;
import service.BookServiceImpl;
import service.IBookService;
import dao.BookDAOImpl;
import dao.IBookDAO;


/** Servlet xử lý đăng bán sách */
@WebServlet("/seller/book")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 5 * 1024 * 1024,
    maxRequestSize = 10 * 1024 * 1024
)
public class SellBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IBookService bookService = new BookServiceImpl();
	private BookDAOImpl bookDAO = new BookDAOImpl(); // dùng để lấy categories


    // ✅ HIỂN THỊ FORM
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null || !"SELLER".equals(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String success = req.getParameter("success");
        if ("true".equals(success)) {
            req.setAttribute("successMessage",
                "Đã gửi yêu cầu đăng bán thành công. Vui lòng chờ quản trị viên duyệt (tối đa 3 ngày).");
        }
        req.setAttribute("categories", bookDAO.findAll());
        req.getRequestDispatcher("/WEB-INF/views/book/sell_book.jsp").forward(req, resp);

    }


    // ✅ XỬ LÝ SUBMIT
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null || !"SELLER".equals(user.getRole())) {
            resp.sendError(403);
            return;
        }

        Book b = new Book();
        b.setTitle(req.getParameter("title"));
        b.setAuthor(req.getParameter("author"));
        b.setPrice(new BigDecimal(req.getParameter("price")));
        b.setCondition(req.getParameter("condition"));
        b.setDescription(req.getParameter("description"));
        b.setCategoryId(Integer.parseInt(req.getParameter("categoryId")));
        b.setSellerId(user.getId());
        b.setStatus("PENDING");

        // xử lý ảnh — chỉ lấy filename
        Part part = req.getPart("image");

        if (part != null && part.getSize() > 0) {

            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();

            String uploadDir = getServletContext().getRealPath("/uploads");

            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs(); // tạo folder nếu chưa có

            part.write(uploadDir + File.separator + fileName);

            b.setImageUrl(req.getContextPath() + "/uploads/" + fileName);
        }

        bookService.requestSellBook(b, user.getId());

        resp.sendRedirect(req.getContextPath() + "/seller/book?success=true");
    }

}