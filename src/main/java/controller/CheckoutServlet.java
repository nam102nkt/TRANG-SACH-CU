package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import dao.OrderDAOImpl;
import model.Cart;
import model.CartItem;
import model.Order;
import model.OrderDetail;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CheckoutServlet processes checkout: creates Order and OrderDetails from session Cart.
 * POST:
 *  - requires user logged in (session "user")
 *  - reads cart from session
 *  - constructs Order and calls OrderDAOImpl.save()
 *  - clears cart on success and forwards to checkout_success.jsp with orderId
 */
@WebServlet("/checkout")
public class CheckoutServlet extends jakarta.servlet.http.HttpServlet {

    private OrderDAOImpl orderDAO = new OrderDAOImpl();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) {
            req.setAttribute("error", "Giỏ hàng trống.");
            req.getRequestDispatcher("cart.jsp").forward(req, resp);
            return;
        }

        Order order = new Order();
        order.setUserId(user.getId());
        order.setStatus("pending");

        List<OrderDetail> details = new ArrayList<>();
        double total = 0.0;
        for (CartItem ci : cart.getItems()) {
            OrderDetail d = new OrderDetail();
            d.setBookId(ci.getBook().getId());
            d.setQuantity(ci.getQuantity());
            d.setPrice(ci.getBook().getPrice().doubleValue());
            details.add(d);
            total += d.getLineTotal();
        }
        order.setDetails(details);
        order.setTotalPrice(total);

        int orderId = orderDAO.save(order);
        if (orderId > 0) {
            cart.clear();
            session.setAttribute("cart", cart);
            req.setAttribute("orderId", orderId);
            req.getRequestDispatcher("checkout_success.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Thanh toán thất bại, vui lòng thử lại.");
            req.getRequestDispatcher("cart.jsp").forward(req, resp);
        }
    }
}
