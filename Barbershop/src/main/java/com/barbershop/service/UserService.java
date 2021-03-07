package com.barbershop.service;

public interface UserService<User> extends BaseService<User> {

	public boolean isExist(String username); // check before registration process
	public User getUserInfo(String username, String password); // check for login
}
