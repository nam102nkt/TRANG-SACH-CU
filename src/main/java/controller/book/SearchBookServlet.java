package controller.book;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import service.BookServiceImpl;
import service.IBookService;
import jakarta.servlet.annotation.*;
import java.io.IOException;

/** Tìm kiếm sách theo từ khóa */
@WebServlet("/search")
public class SearchBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private IBookService bookService = new BookServiceImpl();

    protected void doGet(HttpServletRequest rq, HttpServletResponse rs)
            throws IOException, ServletException {

        String q = rq.getParameter("q");
        if (q == null) q = "";

        rq.setAttribute("books", bookService.search(q.trim()));
        rq.setAttribute("q", escapeHtml(q));

        rq.getRequestDispatcher("/WEB-INF/views/book/search_result.jsp").forward(rq, rs);
    }

    private String escapeHtml(String s) {
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;");
    }
}

