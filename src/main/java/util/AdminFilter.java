package util;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.User;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        User u = (session != null) ? (User) session.getAttribute("user") : null;

        if (u == null || !"ADMIN".equals(u.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        chain.doFilter(req, res);
    }
}

