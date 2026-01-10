package dao;

import java.util.List;

public interface IWishlistDAO {
	public void add(int userId, int bookId) throws Exception;
	public void remove(int userId, int bookId) throws Exception;
	public boolean exists(int userId, int bookId) throws Exception;
	public List<Integer> getAll(int userId) throws Exception;
}
