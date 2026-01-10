package service;

import dao.OrderDAOImpl;
import dao.CartDAOImpl;
import dao.ICartDAO;
import dao.IOrderDAO;
import model.Cart;
import model.Order;
import service.IOrderService;

import java.util.List;

public class OrderServiceImpl implements IOrderService {

    private IOrderDAO orderDAO = new OrderDAOImpl();
    private ICartDAO cartDAO = new CartDAOImpl();

    @Override
    public List<Order> getOrdersByUser(int userId) {
        return orderDAO.getByUserId(userId);
    }

    @Override
    public Order getOrderDetail(int orderId, int userId) {
        return orderDAO.getDetail(orderId, userId);
    }

    @Override
    public Order checkout(int userId) {
        Cart cart = cartDAO.getCart(userId);
        if (cart == null || cart.getItems().isEmpty()) return null;

        Order order = orderDAO.createOrderFromCart(userId, cart);
        if (order != null) {
            cartDAO.clear(userId);
        }
        return order;
    }


}
