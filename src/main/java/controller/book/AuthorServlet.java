package controller.book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.BookDAOImpl;

/**
 * Servlet implementation class AuthorServlet
 */
@WebServlet("/authors")
public class AuthorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookDAOImpl bookDAO = new BookDAOImpl();
       
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

            req.setAttribute("authors", bookDAO.getAllAuthors());
            req.getRequestDispatcher("/WEB-INF/views/book/author.jsp")
               .forward(req, resp);
        }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
