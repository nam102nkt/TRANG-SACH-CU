package model;
import java.math.BigDecimal;

public class OrderDetail {
    private int id;         // ID của dòng chi tiết (tương ứng DB)
    private int orderId;    // Cần thêm cái này để map với DB
    private int bookId;
    private String bookTitle;
    private int quantity;
    private BigDecimal price; // Đổi từ double sang BigDecimal

    public OrderDetail() {}

    // Constructor dùng khi lưu đơn hàng
    public OrderDetail(int bookId, int quantity, BigDecimal price) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    
    // Tính tổng tiền dòng này: price * quantity
    public BigDecimal getLineTotal() {
        return price.multiply(new BigDecimal(quantity));
    }
    
}