package service;

import java.util.List;

import dao.IOrderDAO;
import dao.OrderDAOImpl;
import model.Cart;
import model.Order;
import model.User;

public class OrderServiceImpl implements IOrderService {

	private IOrderDAO orderDAO = new OrderDAOImpl();

	@Override
	public Order checkout(int userId, Cart cart, String address) {
		if (cart == null || cart.isEmpty())
			return null;
		return orderDAO.createOrderFromCart(userId, cart, address);
	}

	@Override
	public List<Order> getOrdersByUser(int userId) {
		return orderDAO.getByUserId(userId);
	}

	@Override
	public Order getOrderDetail(int orderId, int userId) {
		// TODO Auto-generated method stub
		return orderDAO.getDetail(orderId, userId);
	}
	public void confirmPayment(int orderId) {
	    orderDAO.markAsPaid(orderId);
	}

	public List<Order> getPendingOrders() {
	    return orderDAO.findPendingOrders();
	}

}
