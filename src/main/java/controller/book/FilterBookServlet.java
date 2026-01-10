package controller.book;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import service.BookServiceImpl;
import service.IBookService;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;

/** Lọc sách theo giá, tình trạng */
@WebServlet("/filter")
public class FilterBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private IBookService bookService = new BookServiceImpl();

    protected void doGet(HttpServletRequest rq, HttpServletResponse rs)
            throws IOException, ServletException {

        BigDecimal minP = parseDecimal(rq.getParameter("min"));
        BigDecimal maxP = parseDecimal(rq.getParameter("max"));
        String category_id = rq.getParameter("categoryId");
        String sort = rq.getParameter("sort");
        String cond = rq.getParameter("condition");

        rq.setAttribute("books", bookService.filter(minP, maxP,Integer.parseInt(category_id),sort, cond));
        rq.getRequestDispatcher("/WEB-INF/views/book/filter_result.jsp").forward(rq, rs);
    }

    private BigDecimal parseDecimal(String v) {
        try { return (v != null && !v.isEmpty()) ? new BigDecimal(v) : null; }
        catch (Exception e) { return null; }
    }
}