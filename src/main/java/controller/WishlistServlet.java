package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * WishlistServlet manages user's wishlist stored in session.
 * Actions:
 *  - add?id=... : add book id to wishlist
 *  - remove?id=... : remove book id
 *  - view : forward to wishlist.jsp
 */
@WebServlet("/wishlist")
public class WishlistServlet extends jakarta.servlet.http.HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<Integer> wl = (List<Integer>) session.getAttribute("wl");
        if (wl == null) { wl = new ArrayList<>(); session.setAttribute("wl", wl); }

        String action = req.getParameter("action");
        if ("add".equals(action)) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                if (!wl.contains(id)) wl.add(id);
            } catch (Exception e) {}
            resp.sendRedirect("wishlist.jsp");
            return;
        } else if ("remove".equals(action)) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                wl.remove(Integer.valueOf(id));
            } catch (Exception e) {}
            resp.sendRedirect("wishlist.jsp");
            return;
        } else {
            req.getRequestDispatcher("wishlist.jsp").forward(req, resp);
            return;
        }
    }
}
