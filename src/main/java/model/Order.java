package model;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private String fullName;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime orderDate;
    private String shippingAddress; // ✅ Thêm địa chỉ giao hàng
    private List<OrderDetail> details = new ArrayList<>();

    public Order() {}

    // Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getFullName() {return fullName;}

	public void setFullName(String fullName) {this.fullName = fullName;}

	public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }



    public Date getOrderDateAsDate() {
        if (orderDate == null) return null;
        return Date.from(orderDate.atZone(ZoneId.systemDefault()).toInstant());
    }

    
    public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public List<OrderDetail> getDetails() { return details; }
    public void setDetails(List<OrderDetail> details) { this.details = details; }

    public void addDetail(OrderDetail d) {
        this.details.add(d);
    }
}
