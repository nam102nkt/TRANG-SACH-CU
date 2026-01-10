package service;

import dao.*;
public class WishlistServiceImpl implements IWishlistService {

    private IWishlistDAO dao = new WishlistDAOImpl();

    public boolean isInWishlist(int userId, int bookId) {
        try {
            return dao.exists(userId, bookId);
        } catch (Exception e) {
            return false;
        }
    }
}