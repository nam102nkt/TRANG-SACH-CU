package service;

import org.mindrot.jbcrypt.BCrypt;
import dao.CartDAOImpl;
import dao.ICartDAO;
import dao.IUserDAO;
import dao.UserDAOImpl;
import model.Cart;
import model.User;

public class UserServiceImpl implements IUserService {

    private IUserDAO userDAO = new UserDAOImpl();
    private ICartDAO cartDAO = new CartDAOImpl();

    @Override
    public User login(String email, String plainPassword, Cart sessionCart) {

        User user = userDAO.findByEmail(email);
        if (user == null) return null;

        if (!BCrypt.checkpw(plainPassword, user.getPassword())) return null;

        if (sessionCart != null && !sessionCart.isEmpty()) {
            cartDAO.mergeCart(sessionCart.getItems(), user.getId());
        }

        return user;
    }

    @Override
    public boolean register(User user) {
        return userDAO.register(user);
    }

    @Override
    public void updateProfile(User user) {
        userDAO.updateUser(user);
    }
}