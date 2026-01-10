package service;

import dao.ICartDAO;
import dao.CartDAOImpl;
import model.Cart;
import model.CartItem;

import java.util.Map;

public class CartServiceImpl implements ICartService {

    private ICartDAO cartDAO = new CartDAOImpl();

    @Override
    public Cart getCart(Integer userId) {
        Cart cart = new Cart();
        Map<Integer, CartItem> map = cartDAO.getCartByUserId(userId);
        map.values().forEach(item -> cart.add(item.getBook(), item.getQuantity()));
        return cart;
    }

    @Override
    public void add(Integer userId, int bookId, int qty) {
        cartDAO.addItemToCart(userId, bookId, qty);
    }

    @Override
    public void update(Integer userId, int bookId, int qty) {
        if (qty <= 0) {
            cartDAO.removeItemFromCart(userId, bookId);
        } else {
            cartDAO.updateItemQuantity(userId, bookId, qty);
        }
    }

    
    @Override
    public void remove(Integer userId, int bookId) {
        cartDAO.removeItemFromCart(userId, bookId);
    }

	@Override
	public void increment(int userId, int bookId) {
		cartDAO.changeQuantity(userId, bookId, +1);
	}

	@Override
	public void decrement(int userId, int bookId) {
		cartDAO.changeQuantity(userId, bookId, -1);
	}
}
