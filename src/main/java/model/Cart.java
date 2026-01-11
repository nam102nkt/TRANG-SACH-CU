package model;

import java.math.BigDecimal;
import java.util.*;

public class Cart {
	private int cartId;
	private int userId;

	private Map<Integer, CartItem> items = new LinkedHashMap<>();

	public Map<Integer, CartItem> getItems() {
		return items;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setItems(Map<Integer, CartItem> items) {
		this.items = items;
	}

	public Collection<CartItem> values() {
		return items.values();
	}

	/*
	 * ========================= ADD / REMOVE / UPDATE =========================
	 */

	public void add(Book book, int qty) {
		CartItem item = items.get(book.getId());
		if (item == null) {
			items.put(book.getId(), new CartItem(book, qty));
		} else {
			item.setQuantity(item.getQuantity() + qty);
		}
	}

	public void remove(int bookId) {
		items.remove(bookId);
	}
	
    public void increment(int bookId) {
        CartItem i = items.get(bookId);
        if (i != null) {
            i.setQuantity(i.getQuantity() + 1);
        }
    }

    public void decrement(int bookId) {
        CartItem i = items.get(bookId);
        if (i != null && i.getQuantity() > 1) {
            i.setQuantity(i.getQuantity() - 1);
        }
    }

	public void update(int bookId, int qty) {
		if (qty <= 0) {
			items.remove(bookId);
		} else {
			CartItem item = items.get(bookId);
			if (item != null)
				item.setQuantity(qty);
		}
	}

	public int getSize() {
		int total = 0;
		for (CartItem item : items.values()) {
			total += item.getQuantity();
		}
		return total;
	}

	public BigDecimal getTotalPrice() {
		BigDecimal total = BigDecimal.ZERO;
		for (CartItem item : items.values()) {
			total = total.add(item.getTotal());
		}
		return total;
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public void clear() {
	    items.clear();
	}

}
