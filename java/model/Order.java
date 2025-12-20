package model;
import java.math.BigDecimal;
import java.sql.Timestamp; // Dùng Timestamp cho ngày giờ đầy đủ
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private BigDecimal totalPrice; // Đổi từ double sang BigDecimal
    private String status;
    private Timestamp orderDate;   // Thêm ngày đặt hàng
    private List<OrderDetail> details = new ArrayList<>();

    public Order() {}

    // Getters Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

    public List<OrderDetail> getDetails() { return details; }
    public void setDetails(List<OrderDetail> details) { this.details = details; }

    public void addDetail(OrderDetail d) {
        this.details.add(d);
    }
}