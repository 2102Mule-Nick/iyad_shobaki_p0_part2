package com.barbershop.dao;

public interface UserDao<User> extends BaseDao<User> {
	
	public boolean isExist(String username); // check before registration process
	public User getUserInfo(String username, String password); // check for login
	public boolean updateUerRole(int id, String role);
}
