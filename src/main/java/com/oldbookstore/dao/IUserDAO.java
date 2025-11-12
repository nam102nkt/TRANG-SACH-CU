package com.oldbookstore.dao;

import com.oldbookstore.model.User;

public interface IUserDAO {
	public boolean register(User user);
	// có thể thêm các phương thức checkLogin, findByEmail...
	public User findByEmail(String email);
}
