
package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import dao.*;
import model.*;
import java.io.IOException;

/** Nhận đánh giá từ người dùng */
@WebServlet("/review")
public class ReviewServlet extends HttpServlet {

    /** POST lưu đánh giá */
    protected void doPost(HttpServletRequest rq,HttpServletResponse rs)throws ServletException,IOException{
        rs.sendRedirect("book?id="+rq.getParameter("bookId"));
    }
}
