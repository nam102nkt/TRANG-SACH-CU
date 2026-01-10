package service;

import model.Cart;

public interface ICartService {
    Cart getCart(Integer userId);
    void add(Integer userId, int bookId, int qty);
    void increment(int userId, int bookId);
    void decrement(int userId, int bookId);
    void update(Integer userId, int bookId, int qty);
    void remove(Integer userId, int bookId);
}