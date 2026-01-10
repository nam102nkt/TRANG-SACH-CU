package dao;

import java.util.List;

import model.Cart;
import model.Order;

public interface IOrderDAO {
	// lấy ds Order theo id user
	public List<Order> getOrdersByUserId(int userId);

	// Lấy danh sách đơn hàng theo userId (mua hàng)
	public List<model.Order> getByUserId(int userId);
	public int save(Order o);
	public Order getDetail(int orderId, int userId);
	public Order createOrderFromCart(int userId, Cart cart);

}
