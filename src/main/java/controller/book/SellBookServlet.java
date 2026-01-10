package controller.book;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;

import model.Book;
import model.User;
import service.BookServiceImpl;
import service.IBookService;

import java.math.BigDecimal;
import java.nio.file.Paths;


/** Servlet xử lý đăng bán sách */
@WebServlet("/sell_book")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 5 * 1024 * 1024,
    maxRequestSize = 10 * 1024 * 1024
)
public class SellBookServlet extends HttpServlet {

    private IBookService bookService = new BookServiceImpl();

    // ✅ HIỂN THỊ FORM
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String role = user.getRole();
        if (!"SELLER".equals(role) && !"ADMIN".equals(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // forward tới JSP
        request.getRequestDispatcher("/WEB-INF/views/book/sell_book.jsp")
               .forward(request, response);
    }

    // ✅ XỬ LÝ SUBMIT
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User seller = (User) request.getSession().getAttribute("user");
        if (seller == null || !"SELLER".equals(seller.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        String uploadPath = getServletContext().getRealPath("") + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        Part imagePart = request.getPart("image");
        String fileName = System.currentTimeMillis() + "_" +
                Paths.get(imagePart.getSubmittedFileName()).getFileName();

        imagePart.write(uploadPath + File.separator + fileName);

        Book b = new Book();
        b.setTitle(request.getParameter("title"));
        b.setPrice(new BigDecimal(request.getParameter("price")));
        b.setImageUrl("uploads/" + fileName);

        bookService.requestSellBook(b, seller.getId());

        response.sendRedirect("sell-book.jsp?success=true");
    }
}