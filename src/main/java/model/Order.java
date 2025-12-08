package model;

import java.math.BigDecimal;
import java.sql.Timestamp; // Dùng Timestamp cho ngày giờ

public class Order {
	private int id;
	private int userId;
	private BigDecimal totalMoney;
	private String status;
	private String fullName;
	private String phone;
	private String address;
	private Timestamp orderDate;

	// Constructor, Getters, Setters (Tự generate bằng Eclipse)
	public Order() {
	}
	// ... Generate Getters/Setters ...

	// Getter/Setter ví dụ:
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}
}