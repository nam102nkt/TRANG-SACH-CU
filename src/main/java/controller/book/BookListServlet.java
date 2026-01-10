package controller.book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import service.BookServiceImpl;
import service.IBookService;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/products")
public class BookListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IBookService bookService = new BookServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("q");
        String min = request.getParameter("min");
        String max = request.getParameter("max");
        String category = request.getParameter("category");
        String sort = request.getParameter("sort");
        String condition = request.getParameter("condition");

        BigDecimal minVal = (min != null && !min.isBlank()) ? new BigDecimal(min) : null;
        BigDecimal maxVal = (max != null && !max.isBlank()) ? new BigDecimal(max) : null;
        Integer catId = (category != null && !category.isBlank()) ? Integer.parseInt(category) : null;

        if (keyword != null && !keyword.isBlank()) {
            request.setAttribute("books", bookService.search(keyword));
        } else {
            request.setAttribute("books", bookService.filter(minVal, maxVal, catId, sort, condition));
        }

        request.getRequestDispatcher("/WEB-INF/views/book/book_list.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
