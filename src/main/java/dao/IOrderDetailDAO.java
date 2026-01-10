package dao;

import java.sql.SQLException;
import java.util.List;

import model.OrderDetail;

public interface IOrderDetailDAO {

	void insert(OrderDetail d) throws SQLException;

	List<OrderDetail> getByOrderId(int orderId);

	void deleteByOrderId(int orderId);

}
