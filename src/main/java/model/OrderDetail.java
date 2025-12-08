package model;

/**
 * OrderDetail represents one line in an order:
 * bookId, quantity, price (price per book at time of purchase)
 */
public class OrderDetail {
    private int bookId;
    private int quantity;
    private double price;

    public OrderDetail(){}

    public OrderDetail(int bookId, int quantity, double price){
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getBookId(){ return bookId; }
    public void setBookId(int bookId){ this.bookId = bookId; }

    public int getQuantity(){ return quantity; }
    public void setQuantity(int quantity){ this.quantity = quantity; }

    public double getPrice(){ return price; }
    public void setPrice(double price){ this.price = price; }

    /** Total for the line = price * quantity */
    public double getLineTotal(){ return price * quantity; }
}
