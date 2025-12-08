package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Order model for storing order metadata and details.
 * Fields:
 *  - id, userId, totalPrice, status, details
 */
public class Order {
    private int id;
    private int userId;
    private double totalPrice;
    private String status;
    private List<OrderDetail> details = new ArrayList<>();

    public Order(){}

    public int getId(){ return id; }
    public void setId(int id){ this.id = id; }

    public int getUserId(){ return userId; }
    public void setUserId(int userId){ this.userId = userId; }

    public double getTotalPrice(){ return totalPrice; }
    public void setTotalPrice(double totalPrice){ this.totalPrice = totalPrice; }

    public String getStatus(){ return status; }
    public void setStatus(String status){ this.status = status; }

    public List<OrderDetail> getDetails(){ return details; }
    public void setDetails(List<OrderDetail> details){ this.details = details; }

    public void addDetail(OrderDetail d){ this.details.add(d); }
}
