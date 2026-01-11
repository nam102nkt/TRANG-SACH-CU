package controller.order;

import dao.IOrderDAO;
import dao.OrderDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/cancel-order")
public class CancelOrderServlet extends HttpServlet {
    private IOrderDAO orderDAO = new OrderDAOImpl();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        com.google.gson.JsonObject json = new com.google.gson.JsonObject();

        try {
            HttpSession session = req.getSession(false);
            User user = (User) session.getAttribute("user");
            if(user == null) throw new Exception("Chưa đăng nhập");

            String orderIdStr = req.getParameter("orderId");
            if(orderIdStr == null) throw new Exception("Order ID không hợp lệ");

            int orderId = Integer.parseInt(orderIdStr);
            boolean cancelled = orderDAO.cancelOrder(orderId, user.getId());

            if(cancelled) {
                json.addProperty("success", true);
                json.addProperty("message", "Đơn hàng đã hủy thành công!");
            } else {
                json.addProperty("success", false);
                json.addProperty("message", "Không thể hủy đơn hàng này.");
            }
        } catch(Exception e) {
            json.addProperty("success", false);
            json.addProperty("message", "Có lỗi xảy ra: " + e.getMessage());
            e.printStackTrace();
        }

        resp.getWriter().write(json.toString());
    }
}
