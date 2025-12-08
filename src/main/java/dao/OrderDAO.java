
package dao;
import model.Order;

/** DAO xử lý đơn hàng */
public interface OrderDAO {
    int save(Order o);
}
