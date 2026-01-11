package service;

import model.Order;
import model.Cart;
import model.CartItem;

import java.util.List;
import java.util.Map;

public interface IOrderService {

	List<Order> getOrdersByUser(int userId); // ✅ Thêm dòng này

	Order getOrderDetail(int orderId, int userId);

	Order checkout(int userId, Cart cart, String address);
}
