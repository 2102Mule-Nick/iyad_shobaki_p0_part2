package com.barbershop.service;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.barbershop.dao.UserDaoPostgres;
import com.barbershop.pojo.User;
import com.barbershop.util.ConnectionFactoryPostgres;

public class UserServiceImpl implements UserService<User> {

	Logger log = Logger.getRootLogger();
	private UserDaoPostgres userDao;
	private static final String CLASS_NAME = "UserServiceImpl";
	private Connection connection;
	
	
	// Constructor
	public UserServiceImpl(UserDaoPostgres userDao,  Connection connection) {
		super();
		this.connection = connection;
		this.userDao = userDao;
		this.userDao.setConnection(this.connection);
	}

	@Override
	public List<User> findAll() {

		try {
			List<User> users = userDao.findAll();
			log.info("----------------------------------------------------------------------");
			return users;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".findAll() -> Failure to get all users." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		return null;
	}

	@Override
	public boolean create(User user) {

		if (isExist(user.getEmailAddress())) {
			System.out.println(user.getEmailAddress() + ": username is already exist.");
			log.info(CLASS_NAME + ".create() -> Username is already exist.");
			log.info("----------------------------------------------------------------------");
			return false;
		}
		try {
			userDao.create(user);
			System.out.println("User created successfully.");
			log.info("----------------------------------------------------------------------");
			return true;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".create() -> Failure to create a new user account." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		return false;

	}

	@Override
	public boolean update(User user) {

		if (user == null || user.getUserId() < 1) {
			log.error(CLASS_NAME + ".update() -> Failure to update user account with id = " + user.getUserId());
			log.info("----------------------------------------------------------------------");
			return false;
		}
		try {
			userDao.update(user);
			System.out.println("User updated successfully.");
			log.info("----------------------------------------------------------------------");
			return true;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".update() -> Failure to update user account." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		return false;
	}

	@Override
	public boolean deleteById(int id) {

		try {
			userDao.deleteById(id);
			System.out.println("User with id = " + id + " deleted successfully.");
			log.info("----------------------------------------------------------------------");
			return true;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".deleteById() -> Failure to delete user account." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		return false;
	}

	@Override
	public void deleteAll() {

		try {
			userDao.deleteAll();
			System.out.println("All users deleted successfully.");
			log.info("----------------------------------------------------------------------");
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".deleteAll() -> Failure to delete all users." + e.getMessage());
			log.info("----------------------------------------------------------------------");
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
			log.info("----------------------------------------------------------------------");
			return null;
		}
		try {
			User outputUser = userDao.getUserInfo(username, password);
			log.info("----------------------------------------------------------------------");
			return outputUser;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".getUserInfo() -> Failure to get user info." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		return null;
	}

	@Override
	public boolean updateUerRole(int id, String role) {
		if (userDao.updateUerRole(id, role)) {
			log.info("----------------------------------------------------------------------");
			return true;
		}
		return false;
	}

}
