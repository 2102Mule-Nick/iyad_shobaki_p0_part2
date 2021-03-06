package com.barbershop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.barbershop.pojo.User;
import com.barbershop.util.ConnectionFactoryPostgres;

public class UserDaoPostgres implements UserDao<User> {

	Logger log = Logger.getRootLogger();
	private static final String CLASS_NAME = "UserDaoPostgres";

	public List<User> findAll() {

		log.info(CLASS_NAME + ".findAll() -> An Attempt to get all users.");
		Connection conn = ConnectionFactoryPostgres.getConnection();

		String sql = "select * from user_acc";

		List<User> users = null;

		User user = null;

		try {

			users = new ArrayList<>();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				user = new User(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("phone_number"), rs.getString("email_address"), rs.getString("user_role"),
						rs.getString("user_password"));

				users.add(user);
			}

		} catch (SQLException e) {
			log.error(CLASS_NAME + ".findAll() -> Failure to connect to DB.");
		}
		log.info(CLASS_NAME + ".findAll() -> A list of users returned successfully.");
		return users;
	}

	public boolean create(User user) {

		log.info(CLASS_NAME + ".create() -> An attempt to create a user with username= " + user.getEmailAddress());

		Connection conn = ConnectionFactoryPostgres.getConnection();

		PreparedStatement stmt = null;

		String sql = "insert into user_acc (first_name, last_name, phone_number, email_address, user_role, user_password)"
				+ " values (? , ? , ? , ?, ? , ?)";

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getPhoneNumber());
			stmt.setString(4, user.getEmailAddress());
			stmt.setString(5, user.getRole());
			stmt.setString(6, user.getPassword());
			stmt.execute();
			conn.close();
			log.info(CLASS_NAME + ".create() -> User created successfuly.");
			return true;
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".create() -> Failure to create a new user account.");
		}
		return false;
	}

	public boolean update(User user) {

		log.info(CLASS_NAME + ".update() -> An attempt to update a user with username= " + user.getEmailAddress());
		Connection conn = ConnectionFactoryPostgres.getConnection();

		PreparedStatement stmt = null;

		String sql = "update user_acc set first_name = ?, last_name = ?, phone_number = ?, "
				+ "email_address = ?, user_role = ?, user_password = ?  where user_id = ?";

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getPhoneNumber());
			stmt.setString(4, user.getEmailAddress());
			stmt.setString(5, user.getRole());
			stmt.setString(6, user.getPassword());
			stmt.setInt(7, user.getUserId());
			stmt.execute();
			conn.close();
			log.info(CLASS_NAME + ".update() -> User updated successfully.");
			return true;
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".update() -> Failure to update user.");
		}

		return false;
	}

	public boolean deleteById(int id) {

		log.info(CLASS_NAME + ".deleteById() -> An attempt to delete a user with id= " + id);

		Connection conn = ConnectionFactoryPostgres.getConnection();

		PreparedStatement stmt = null;

		String sql = "delete from user_acc where user_id = ?";

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
			conn.close();
			log.info(CLASS_NAME + ".deleteById() -> User deleted successfully.");
			return true;
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".deleteById() -> Failure to delete user.");
		}

		return false;
	}

	public boolean isExist(String username) {

		log.info(CLASS_NAME + ".isExist() -> An attempt to check if a user with username= " + username
				+ " is exist or not.");

		Connection conn = ConnectionFactoryPostgres.getConnection();

		PreparedStatement stmt = null;

		String sql = "select * from user_acc where email_address = ?";

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			conn.close();
			if(rs.next()) {
				log.info(CLASS_NAME + ".isExist() -> Username already exist.");
				return true;
			}else {
				log.info(CLASS_NAME + ".isExist() -> Username is not exist.");
				return false;
			}
			
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".isExist() -> Failure to check for user.");
			//TODO - Add Exception --- Iyad
			return false;
		}
	
	}

	public User getUserInfo(String username, String password) {
		
		log.info(CLASS_NAME + ".getUserInfo() -> An attempt to get user with username= " + username);
		if(isExist(username) == false) {
			log.info(CLASS_NAME + ".getUserInfo() -> User with username= " + username + " is not exist.");
			return null;
		}
		
		Connection conn = ConnectionFactoryPostgres.getConnection();
		User user =null;

		PreparedStatement stmt = null;

		String sql = "select * from user_acc where email_address = ? and user_password = ?";

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			conn.close();

			while(rs.next()) {

				user = new User(rs.getInt("user_id"), rs.getString("first_name"),
						rs.getString("last_name"),rs.getString("phone_number"),
						rs.getString("email_address"),rs.getString("user_role"),
						rs.getString("user_password"));
				
			}
			
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".isExist() -> Failure to check for user.");
			//TODO - Add Exception --- Iyad			
		}
		
		if(user != null && user.getEmailAddress().equals(username) && user.getPassword().equals(password)) {
			log.info(CLASS_NAME + ".getUserInfo() -> User info returned successfully. Valid username and password.");
			return user;
		}
		
		log.warn(CLASS_NAME + ".getUserInfo() -> Failure to return user info. Invalid username or password.");
		return null;
	}

}
