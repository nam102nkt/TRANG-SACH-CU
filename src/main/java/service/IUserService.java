package service;

import model.Cart;
import model.User;

public interface IUserService {

    User login(String email, String plainPassword, Cart sessionCart);

    boolean register(User user);

    void updateProfile(User user);
}
