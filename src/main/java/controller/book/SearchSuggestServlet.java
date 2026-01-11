package controller.book;

import com.google.gson.Gson;
import dao.BookDAOImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Book;
import model.dto.BookSuggestDTO;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/search-suggest")
public class SearchSuggestServlet extends HttpServlet {

	private BookDAOImpl dao = new BookDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String q = req.getParameter("q");
		resp.setContentType("application/json;charset=UTF-8");

		if (q == null || q.trim().isEmpty()) {
			resp.getWriter().print("[]");
			return;
		}

		List<Book> books = dao.searchSuggest(q);

		// 👉 CHỈ TRẢ ID + TITLE
		List<BookSuggestDTO> result = books.stream().map(b -> new BookSuggestDTO(b.getId(), b.getTitle()))
				.collect(Collectors.toList());

		resp.getWriter().print(new Gson().toJson(result));
	}
}
