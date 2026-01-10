package util;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        User user = (User) request.getSession().getAttribute("user");
        String uri = request.getRequestURI();

        // ADMIN chỉ được vào /admin/*
        if (uri.contains("/admin")) {
            if (user == null || !"ADMIN".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }
        }

        chain.doFilter(req, res);
    }
}


