package com.barbershop.dao;

public interface UserDao<User> extends BaseDao<User> {
	
	public boolean isExist(String username);
	public int getUserIdByUsernameAndPassword(String username, String password);
	
}
