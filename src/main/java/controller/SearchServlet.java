package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import dao.BookDAOImpl;
import java.io.IOException;

/** Tìm kiếm sách theo từ khóa */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private BookDAOImpl bookDAO = new BookDAOImpl();

    /** GET: nhận q và trả danh sách kết quả */
    protected void doGet(HttpServletRequest rq, HttpServletResponse rs)throws IOException,ServletException{
        String q = rq.getParameter("q");
        if(q==null) q = "";
        java.util.List<model.Book> results = bookDAO.search(q);
        rq.setAttribute("books", results);
        rq.setAttribute("q", q);
        rq.getRequestDispatcher("search.jsp").forward(rq,rs);
    }
}
