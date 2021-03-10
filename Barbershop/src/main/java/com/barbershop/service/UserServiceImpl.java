package com.barbershop.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.barbershop.dao.UserDaoPostgres;
import com.barbershop.pojo.User;

public class UserServiceImpl implements UserService<User> {

	Logger log = Logger.getRootLogger();
	private UserDaoPostgres userDao;
	private static final String CLASS_NAME = "UserServiceImpl";

	// Constructor
	public UserServiceImpl(UserDaoPostgres userDao) {
		super();
		this.userDao = userDao;
	}

	@Override
	public List<User> findAll() {

		try {
			List<User> users = userDao.findAll();
			return users;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".findAll() -> Failure to get all users." + e.getMessage());
		}
		return null;
	}

	@Override
	public boolean create(User user) {

		if (isExist(user.getEmailAddress())) {
			System.out.println(user.getEmailAddress() + ": username is already exist.");
			log.info(CLASS_NAME + ".create() -> Username is already exist.");
			return false;
		}
		try {
			userDao.create(user);
			System.out.println("User created successfully.");
			return true;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".create() -> Failure to create a new user account." + e.getMessage());
		}
		return false;

	}

	@Override
	public boolean update(User user) {

		if (user == null || user.getUserId() < 1) {
			log.error(CLASS_NAME + ".update() -> Failure to update user account with id = " + user.getUserId());
			return false;
		}
		try {
			userDao.update(user);
			System.out.println("User updated successfully.");
			return true;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".update() -> Failure to update user account." + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean deleteById(int id) {

		try {
			userDao.deleteById(id);
			System.out.println("User with id = " + id + " deleted successfully.");
			return true;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".deleteById() -> Failure to delete user account." + e.getMessage());
		}
		return false;
	}

	@Override
	public void deleteAll() {

		try {
			userDao.deleteAll();
			System.out.println("All users deleted successfully.");
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".deleteAll() -> Failure to delete all users." + e.getMessage());
		}

	}

	@Override
	public boolean isExist(String username) {

		if (userDao.isExist(username)) {
			return true;
		}
		return false;
	}

	@Override
	public User getUserInfo(String username, String password) {

		if (isExist(username) == false) {
			System.out.println(username + ": username is not exist.");
			log.info(CLASS_NAME + ".getUserInfo() -> Username is not exist.");
			return null;
		}
		try {
			User outputUser = userDao.getUserInfo(username, password);
			return outputUser;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".getUserInfo() -> Failure to get user info." + e.getMessage());
		}
		return null;
	}

	@Override
	public boolean updateUerRole(int id, String role) {
		if (userDao.updateUerRole(id, role)) {
			return true;
		}
		return false;
	}

}
