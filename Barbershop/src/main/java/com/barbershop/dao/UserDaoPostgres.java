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
	private static final String DATABASE_ENV = "OriginalDb";

	public List<User> findAll() {

		log.info(CLASS_NAME + ".findAll() -> An Attempt to get all users.");

		String sql = "select * from user_acc";

		List<User> users = null;

		User user = null;

		try (Connection conn = ConnectionFactoryPostgres.getConnection(DATABASE_ENV)) {

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
			log.error(CLASS_NAME + ".findAll() -> Failure to get all users (SQLEXCEPTION)." + e.getMessage());
		}
		log.info(CLASS_NAME + ".findAll() -> A list of users returned successfully.");
		return users;
	}

	public boolean create(User user) {

		log.info(CLASS_NAME + ".create() -> An attempt to create a user with username= " + user.getEmailAddress());

		PreparedStatement stmt = null;

		String sql = "insert into user_acc (first_name, last_name, phone_number, email_address, user_role, user_password)"
				+ " values (? , ? , ? , ?, ? , ?)";

		try (Connection conn = ConnectionFactoryPostgres.getConnection(DATABASE_ENV)) {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getPhoneNumber());
			stmt.setString(4, user.getEmailAddress());
			stmt.setString(5, user.getRole());
			stmt.setString(6, user.getPassword());
			stmt.execute();

			log.info(CLASS_NAME + ".create() -> User created successfuly.");
			return true;

		} catch (SQLException e) {
			log.error(
					CLASS_NAME + ".create() -> Failure to create a new user account (SQLEXCEPTION)." + e.getMessage());
		}

		log.error(CLASS_NAME + ".create() -> Unable to create a new user account.");
		return false;
	}

	public boolean update(User user) {

		log.info(CLASS_NAME + ".update() -> An attempt to update a user with username= " + user.getEmailAddress());

		PreparedStatement stmt = null;

		String sql = "update user_acc set first_name = ?, last_name = ?, phone_number = ?, "
				+ "email_address = ?, user_role = ?, user_password = ?  where user_id = ?";

		try (Connection conn = ConnectionFactoryPostgres.getConnection(DATABASE_ENV)) {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, user.getFirstName());
			stmt.setString(2, user.getLastName());
			stmt.setString(3, user.getPhoneNumber());
			stmt.setString(4, user.getEmailAddress());
			stmt.setString(5, user.getRole());
			stmt.setString(6, user.getPassword());
			stmt.setInt(7, user.getUserId());
			stmt.execute();
			log.info(CLASS_NAME + ".update() -> User updated successfully.");
			return true;
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".update() -> Failure to update user account (SQLEXCEPTION)." + e.getMessage());
		}

		log.error(CLASS_NAME + ".update() -> Unable to update user account.");
		return false;
	}

	public boolean deleteById(int id) {

		log.info(CLASS_NAME + ".deleteById() -> An attempt to delete a user with id= " + id);

		PreparedStatement stmt = null;

		String sql = "delete from user_acc where user_id = ?";

		try (Connection conn = ConnectionFactoryPostgres.getConnection(DATABASE_ENV)) {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
			log.info(CLASS_NAME + ".deleteById() -> User deleted successfully.");
			return true;
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".deleteById() -> Failure to delete user account (SQLEXCEPTION)." + e.getMessage());
		}

		log.error(CLASS_NAME + ".deleteById() -> Unable to delete user account.");
		return false;
	}

	public boolean isExist(String username) {

		log.info(CLASS_NAME + ".isExist() -> An attempt to check if a user with username= " + username
				+ " is exist or not.");

		PreparedStatement stmt = null;

		String sql = "select * from user_acc where email_address = ?";

		try (Connection conn = ConnectionFactoryPostgres.getConnection(DATABASE_ENV)) {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				log.info(CLASS_NAME + ".isExist() -> Username is already exist.");
				return true;
			} else {
				log.info(CLASS_NAME + ".isExist() -> Username is not exist.");
				return false;
			}

		} catch (SQLException e) {
			log.error(CLASS_NAME + ".isExist() -> Failure to check for user (SQLEXCEPTION)." + e.getMessage());
			// TODO - Add Exception --- Iyad
			return false;
		}

	}

	public User getUserInfo(String username, String password) {

		log.info(CLASS_NAME + ".getUserInfo() -> An attempt to get user with username= " + username);
		if (isExist(username) == false) {
			log.info(CLASS_NAME + ".getUserInfo() -> User with username= " + username + " is not exist.");
			return null;
		}

		User user = null;

		PreparedStatement stmt = null;

		String sql = "select * from user_acc where email_address = ? and user_password = ?";

		try (Connection conn = ConnectionFactoryPostgres.getConnection(DATABASE_ENV)) {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				user = new User(rs.getInt("user_id"), rs.getString("first_name"), rs.getString("last_name"),
						rs.getString("phone_number"), rs.getString("email_address"), rs.getString("user_role"),
						rs.getString("user_password"));

			}

		} catch (SQLException e) {
			log.error(CLASS_NAME + ".getUserInfo() -> Failure to check for user (SQLEXCEPTION)." + e.getMessage());
			// TODO - Add Exception --- Iyad
		}

		if (user != null && user.getEmailAddress().equals(username) && user.getPassword().equals(password)) {
			log.info(CLASS_NAME + ".getUserInfo() -> User info returned successfully. Valid username and password.");
			return user;
		}

		log.info(CLASS_NAME + ".getUserInfo() -> Invalid username or password.");
		return null;
	}

	public boolean updateUerRole(int id) {
		log.info(CLASS_NAME + ".updateUerRole() -> An attempt to update user role with user_id= " + id);

		PreparedStatement stmt = null;

		String sql = "update user_acc set user_role = 'Manager' where user_id = ?";

		try (Connection conn = ConnectionFactoryPostgres.getConnection(DATABASE_ENV)) {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
			log.info(CLASS_NAME + ".updateUerRole() -> User role updated successfully.");
			return true;
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".updateUerRole() -> Failure to update user role (SQLEXCEPTION)." + e.getMessage());
		}

		log.error(CLASS_NAME + ".updateUerRole() -> Unable to update user role.");
		return false;
	}

	// For testing purpose
	@Override
	public void deleteAll() {
		log.info(CLASS_NAME + ".deleteAll() -> An attempt to delete all users.");

		PreparedStatement stmt = null;

		// Delete all except id # 1 to test appointment table Foreign key
		String sql = "delete from user_acc where user_id != 1";

		try (Connection conn = ConnectionFactoryPostgres.getConnection(DATABASE_ENV)) {
			stmt = conn.prepareStatement(sql);
			stmt.execute();
			log.info(CLASS_NAME + ".deleteAll() -> Users deleted successfully.");
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".deleteById() -> Failure to delete all users (SQLEXCEPTION)." + e.getMessage());
		}

	}

}
