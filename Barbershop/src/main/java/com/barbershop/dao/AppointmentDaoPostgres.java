package com.barbershop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.barbershop.pojo.Appointment;
import com.barbershop.util.ConnectionFactoryPostgres;

public class AppointmentDaoPostgres implements AppointmentDao<Appointment> {

	Logger log = Logger.getRootLogger();
	private static final String CLASS_NAME = "AppointmentDaoPostgres";

	@Override
	public List<Appointment> findAll() {

		log.info(CLASS_NAME + ".findAll() -> An Attempt to get all appointments.");

		String sql = "select * from appointment";

		List<Appointment> appointments = null;

		Appointment appointment = null;

		try (Connection conn = ConnectionFactoryPostgres.getConnection()) {

			appointments = new ArrayList<>();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				java.sql.Date sqlDate = rs.getDate("appointment_date");
				java.sql.Time sqlTime = rs.getTime("appointment_time");
//				java.util.Date date = new java.util.Date(sqlDate.getTime());
//				java.util.Date time = new java.util.Date(sqlTime.getTime());

				LocalDate date = sqlDate.toLocalDate();
				LocalTime time = sqlTime.toLocalTime();
				appointment = new Appointment(rs.getInt("appointment_id"), date, time, rs.getInt("user_id"),
						rs.getInt("service_id"));

				appointments.add(appointment);
			}

		} catch (SQLException e) {
			log.error(CLASS_NAME + ".findAll() -> Failure to connect to DB.");
		}
		log.info(CLASS_NAME + ".findAll() -> A list of appointments returned successfully.");
		return appointments;
	}

	@Override
	public boolean create(Appointment appointment) {

		log.info(CLASS_NAME + ".create() -> An attempt to create an appointment on " + appointment.getAppointmentDate()
				+ " at " + appointment.getAppointmentTime());

		PreparedStatement stmt = null;

		String sql = "insert into appointment (appointment_date, appointment_time, user_id, service_id)"
				+ " values (? , ? , ? , ?)";

		try (Connection conn = ConnectionFactoryPostgres.getConnection()) {
			stmt = conn.prepareStatement(sql);
			stmt.setDate(1, java.sql.Date.valueOf(appointment.getAppointmentDate()));
			stmt.setTime(2, java.sql.Time.valueOf(appointment.getAppointmentTime()));
			stmt.setInt(3, appointment.getUserId());
			stmt.setInt(4, appointment.getServiceId());
			stmt.execute();
			log.info(CLASS_NAME + ".create() -> Appointment created successfuly.");
			return true;
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".create() -> Failure to create a new appointment (SQLEXCEPTION).");
		}

		log.error(CLASS_NAME + ".create() -> Unable to create a new appointment.");
		return false;
	}

	@Override
	public boolean update(Appointment appointment) {

		log.info(CLASS_NAME + ".update() -> An attempt to update an appointment for user_id= "
				+ appointment.getUserId());

		PreparedStatement stmt = null;

		String sql = "update appointment set appointment_date = ?, appointment_time = ?, "
				+ "service_id = ? where user_id = ?";

		try (Connection conn = ConnectionFactoryPostgres.getConnection()) {
			stmt = conn.prepareStatement(sql);
			stmt.setDate(1, java.sql.Date.valueOf(appointment.getAppointmentDate()));
			stmt.setTime(2, java.sql.Time.valueOf(appointment.getAppointmentTime()));
			stmt.setInt(3, appointment.getServiceId());
			stmt.setInt(4, appointment.getUserId());
			stmt.execute();
			log.info(CLASS_NAME + ".update() -> Appointment updated successfully.");
			return true;
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".update() -> Failure to update user account (SQLEXCEPTION).");
		}

		log.error(CLASS_NAME + ".update() -> Unable to update user account.");
		return false;
	}

	@Override
	public boolean deleteById(int id) {

		log.info(CLASS_NAME + ".deleteById() -> An attempt to delete an appointment with id= " + id);

		PreparedStatement stmt = null;

		String sql = "delete from appointment where appointment_id = ?";

		try (Connection conn = ConnectionFactoryPostgres.getConnection()) {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
			log.info(CLASS_NAME + ".deleteById() -> Appointment deleted successfully.");
			return true;
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".deleteById() -> Failure to delete an appointment (SQLEXCEPTION).");
		}

		log.error(CLASS_NAME + ".deleteById() -> Unable to delete an appointment.");
		return false;
	}

//	@Override // Check if I need it --- Iyad
//	public boolean isAppointmentExist(LocalDate date, LocalTime time) {
//		// TODO Auto-generated method stub
//		return false;
//	}

	@Override
	public List<Appointment> getAllAppointmentsByUserId(int id) {

		log.info(CLASS_NAME + ".getAllAppointmentsByUserId() -> An Attempt to get all appointments for user id = " + id);

		PreparedStatement stmt = null;

		String sql = "select * from appointment where user_id = ?";

		List<Appointment> appointments = null;

		Appointment appointment = null;

		try (Connection conn = ConnectionFactoryPostgres.getConnection()) {

			appointments = new ArrayList<>();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				java.sql.Date sqlDate = rs.getDate("appointment_date");
				java.sql.Time sqlTime = rs.getTime("appointment_time");
//				java.util.Date date = new java.util.Date(sqlDate.getTime());
//				java.util.Date time = new java.util.Date(sqlTime.getTime());

				LocalDate date = sqlDate.toLocalDate();
				LocalTime time = sqlTime.toLocalTime();
				appointment = new Appointment(rs.getInt("appointment_id"), date, time, rs.getInt("user_id"),
						rs.getInt("service_id"));

				appointments.add(appointment);
			}

		} catch (SQLException e) {
			log.error(CLASS_NAME + ".getAllAppointmentsByUserId() -> Failure to connect to DB (SQLEXCEPTION).");
		}
		log.info(CLASS_NAME + ".getAllAppointmentsByUserId() -> A list of appointments for user_id " + id + " returned successfully.");
		return appointments;
	}

	@Override
	public List<LocalTime> getAllAppointmentsTimeByDate(LocalDate date) {
		
		log.info(CLASS_NAME + ".getAllAppointmentsTimeByDate() -> An Attempt to get all appointments on " + 
				date);

		PreparedStatement stmt = null;

		String sql = "select appointment_time from appointment where appointment_date = ?";

		List<LocalTime> appointmentsTime = null;

		LocalTime apptTime = null;

		try (Connection conn = ConnectionFactoryPostgres.getConnection()) {

			appointmentsTime = new ArrayList<>();
			stmt = conn.prepareStatement(sql);
			stmt.setDate(1, java.sql.Date.valueOf(date));
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				
				java.sql.Time sqlTime = rs.getTime("appointment_time");
				apptTime = sqlTime.toLocalTime();

				appointmentsTime.add(apptTime);
			}

		} catch (SQLException e) {
			log.error(CLASS_NAME + ".getAllAppointmentsTimeByDate() -> Failure to connect to DB (SQLEXCEPTION).");
		}
		log.info(CLASS_NAME + ".getAllAppointmentsTimeByDate() -> A list of appointments time for the date " + date + " returned successfully.");
		return appointmentsTime;
	}

}
