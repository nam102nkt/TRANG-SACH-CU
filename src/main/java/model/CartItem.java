package model;

import java.math.BigDecimal;

/**
 * CartItem represents an item in the shopping cart.
 * It stores a Book and the selected quantity.
 * Methods:
 *  - getTotal(): return total price for this item (price * quantity)
 */
public class CartItem {
    private Book book;
    private int quantity;

    public CartItem() {}

    public CartItem(Book b, int q){ this.book=b; this.quantity=q; }

    public Book getBook(){ return book; }
    public void setBook(Book b){ this.book = b; }

    public int getQuantity(){ return quantity; }
    public void setQuantity(int q){ this.quantity = q; }

    /**
     * Return total price for this CartItem as BigDecimal.
     */
    public BigDecimal getTotal(){
        if (book == null || book.getPrice() == null) return BigDecimal.ZERO;
        return book.getPrice().multiply(new BigDecimal(quantity));
    }
}
