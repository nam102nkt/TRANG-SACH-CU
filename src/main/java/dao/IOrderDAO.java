package dao;

import java.util.List;

import model.Order;

public interface IOrderDAO {
	// lấy ds Order theo id user
	public List<Order> getOrdersByUserId(int userId);
}
