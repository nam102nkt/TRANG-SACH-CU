package service;

import model.Order;
import java.util.List;

public interface IOrderService {
    List<Order> getOrdersByUser(int userId);
    Order getOrderDetail(int orderId, int userId);
    Order checkout(int userId);
}
