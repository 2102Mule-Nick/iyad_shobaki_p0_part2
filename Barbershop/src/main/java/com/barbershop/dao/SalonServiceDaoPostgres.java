package com.barbershop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.barbershop.pojo.SalonService;
import com.barbershop.util.ConnectionFactoryPostgres;

public class SalonServiceDaoPostgres implements SalonServiceDao<SalonService> {

	Logger log = Logger.getRootLogger();
	private static final String CLASS_NAME = "SalonServiceDaoPostgres";
	
	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<SalonService> findAll() {

		log.info(CLASS_NAME + ".findAll() -> An Attempt to get all salon services.");

		String sql = "select * from salon_service order by service_id asc";

		List<SalonService> salonServices = null;

		SalonService service = null;

			try {

			salonServices = new ArrayList<>();
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				service = new SalonService(rs.getInt("service_id"), rs.getString("service_name"),
						rs.getString("description"), rs.getString("duration"), rs.getFloat("price"));

				salonServices.add(service);
			}

		} catch (SQLException e) {
			log.error(CLASS_NAME + ".findAll() -> Failure to get all salon services (SQLEXCEPTION)." + e.getMessage());
		}
		log.info(CLASS_NAME + ".findAll() -> A list of salon services returned successfully.");
		return salonServices;
	}

	@Override
	public boolean create(SalonService service) {

		log.info(CLASS_NAME + ".create() -> An attempt to create a salon service with name= "
				+ service.getServiceName());

		PreparedStatement stmt = null;

		String sql = "insert into salon_service (service_name, description, duration, price)"
				+ " values (? , ? , ? , ?)";
		
			try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, service.getServiceName());
			stmt.setString(2, service.getDescription());
			stmt.setString(3, service.getDuration());
			stmt.setFloat(4, service.getPrice());
			stmt.execute();
			
			log.info(CLASS_NAME + ".create() -> Salon service created successfuly.");
			return true;
			
		} catch (SQLException e) {
			log.error(
					CLASS_NAME + ".create() -> Failure to create a new salon service (SQLEXCEPTION)." + e.getMessage());
		}

		log.error(CLASS_NAME + ".create() -> Unable to create a new salon service.");
		return false;
	}

	@Override
	public boolean update(SalonService service) {

		log.info(CLASS_NAME + ".update() -> An attempt to update a salon service with name= "
				+ service.getServiceName());

		PreparedStatement stmt = null;

		String sql = "update salon_service set service_name = ?, description = ?, duration = ?, "
				+ "price = ? where service_id = ?";

			try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, service.getServiceName());
			stmt.setString(2, service.getDescription());
			stmt.setString(3, service.getDuration());
			stmt.setFloat(4, service.getPrice());
			stmt.setInt(5, service.getServiceId());
			stmt.execute();
			log.info(CLASS_NAME + ".update() -> Salon service updated successfully.");
			return true;
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".update() -> Failure to update salon service (SQLEXCEPTION)." + e.getMessage());
		}

		log.error(CLASS_NAME + ".update() -> Unable to update salon service.");
		return false;
	}

	@Override
	public boolean deleteById(int id) {

		log.info(CLASS_NAME + ".deleteById() -> An attempt to delete a salon service with id= " + id);

		PreparedStatement stmt = null;

		String sql = "delete from salon_service where service_id = ?";

			try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
			log.info(CLASS_NAME + ".deleteById() -> Salon service deleted successfully.");
			return true;
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".deleteById() -> Failure to delete salon service (SQLEXCEPTION)." + e.getMessage());
		}

		return false;
	}

	@Override
	public SalonService getSalonServiceByName(String name) {

		log.info(CLASS_NAME + ".getSalonServiceByName() -> An attempt to get salon service with name= " + name);

		SalonService service = null;

		PreparedStatement stmt = null;

		String sql = "select * from salon_service where service_name = ?";

			try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				service = new SalonService(rs.getInt("service_id"), rs.getString("service_name"),
						rs.getString("description"), rs.getString("duration"), rs.getFloat("price"));

			}

		} catch (SQLException e) {
			log.error(CLASS_NAME + ".getSalonServiceByName() -> Failure to check for salon service (SQLEXCEPTION)."
					+ e.getMessage());
			// TODO - Add Exception --- Iyad
		}

		if (service != null) {
			log.info(CLASS_NAME + ".getSalonServiceByName() -> Salon service info returned successfully.");
			return service;
		}

		log.info(CLASS_NAME + ".getSalonServiceByName() -> Salon service is not exist.");
		return null;
	}

	@Override
	public SalonService getSalonServiceById(int id) {

		log.info(CLASS_NAME + ".getSalonServiceById() -> An Attempt to get salon services with id= " + id);

		String sql = "select * from salon_service where service_id = ?";

		SalonService service = null;

			try {

			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				service = new SalonService(rs.getInt("service_id"), rs.getString("service_name"),
						rs.getString("description"), rs.getString("duration"), rs.getFloat("price"));

			}
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".findAll() -> Failure to connect to DB (SQLEXCEPTION)." + e.getMessage());
		}
		log.info(CLASS_NAME + ".findAll() -> Salon service info returned successfully.");
		return service;
	}

	// For testing purpose
	@Override
	public void deleteAll() {
		log.info(CLASS_NAME + ".deleteAll() -> An attempt to delete all salon services.");

		PreparedStatement stmt = null;

		// Delete all except id # 1 to test appointment table Foreign key
		String sql = "delete from salon_service where service_id != 1";

			try {
			stmt = connection.prepareStatement(sql);
			stmt.execute();
			log.info(CLASS_NAME + ".deleteAll() -> Salon services deleted successfully.");
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".deleteById() -> Failure to delete all salon services (SQLEXCEPTION)."
					+ e.getMessage());
		}

	}

}
