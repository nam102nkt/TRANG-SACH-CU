package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;

/**
 * Servlet Filter implementation class RoleFilter
 */
@WebFilter("/*")
public class RoleFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();
        String ctx = request.getContextPath();

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // public resources
        if (path.startsWith(ctx + "/assets")
                || path.endsWith(".css")
                || path.endsWith(".js")
                || path.endsWith(".png")
                || path.endsWith(".jpg")
                || path.equals(ctx + "/login")
                || path.equals(ctx + "/home")
                || path.equals(ctx + "/")
                || path.startsWith(ctx + "/book")
                || path.startsWith(ctx + "/search")) {

            chain.doFilter(req, res);
            return;
        }

        // seller area
        if (path.startsWith(ctx + "/seller")) {
            if (user == null) {
                response.sendRedirect(ctx + "/login");
                return;
            }
            if (!"SELLER".equals(user.getRole())) {
                response.sendError(403);
                return;
            }
        }

        // admin area
        if (path.startsWith(ctx + "/admin")) {
            if (user == null) {
                response.sendRedirect(ctx + "/login");
                return;
            }
            if (!"ADMIN".equals(user.getRole())) {
                response.sendError(403);
                return;
            }
        }

        // checkout requires login
        if (path.startsWith(ctx + "/checkout")) {
            if (user == null) {
                response.sendRedirect(ctx + "/login");
                return;
            }
        }

        chain.doFilter(req, res);
    }
}