package com.barbershop.dao;

import java.sql.CallableStatement;
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
import com.barbershop.pojo.AppointmentInfo;
import com.barbershop.pojo.ManagerApptInfo;
import com.barbershop.util.ConnectionFactoryPostgres;

public class AppointmentDaoPostgres implements AppointmentDao<Appointment> {

	Logger log = Logger.getRootLogger();
	private static final String CLASS_NAME = "AppointmentDaoPostgres";

	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<ManagerApptInfo> getAllUsersAppointmentsDetails() { // Manager

		log.info(CLASS_NAME + ".getAllUsersAppointmentsDetails() -> An Attempt to get all appointments.");

//		String sql = "select * from appointment order by appointment_date, appointment_time desc";
		String sql = "select a.appointment_id, ua.user_id, ua.first_name, ua.last_name, ua.email_address, ua.phone_number, "
				+ "ua.user_role, ss.service_name, ss.duration , ss.price, a.appointment_date , a.appointment_time "
				+ "from salon_service ss inner join appointment a on ss.service_id = a.service_id "
				+ "inner join user_acc ua on ua.user_id = a.user_id "
				+ "order by ua.email_address, a.appointment_date , a.appointment_time desc";

		List<ManagerApptInfo> appointments = null;

		ManagerApptInfo appointment = null;

		try {

			appointments = new ArrayList<>();
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				java.sql.Date sqlDate = rs.getDate("appointment_date");
				java.sql.Time sqlTime = rs.getTime("appointment_time");

				LocalDate date = sqlDate.toLocalDate();
				LocalTime time = sqlTime.toLocalTime();
				appointment = new ManagerApptInfo(rs.getInt("appointment_id"), rs.getInt("user_id"),
						rs.getString("first_name"), rs.getString("last_name"), rs.getString("email_address"),
						rs.getString("phone_number"), rs.getString("user_role"), rs.getString("service_name"),
						rs.getString("duration"), rs.getFloat("price"), date, time);

				appointments.add(appointment);
			}

		} catch (SQLException e) {
			log.error(CLASS_NAME + ".getAllUsersAppointmentsDetails() -> Failure to get all appointments (SQLEXCEPTION)." + e.getMessage());
		}
		log.info(CLASS_NAME + ".getAllUsersAppointmentsDetails() -> A list of appointments returned successfully.");
		return appointments;
	}


	@Override
	public boolean create(Appointment appointment) {

		log.info(CLASS_NAME + ".create() -> An attempt to create an appointment on " + appointment.getAppointmentDate()
				+ " at " + appointment.getAppointmentTime());

		PreparedStatement stmt = null;

		String sql = "insert into appointment (appointment_date, appointment_time, user_id, service_id)"
				+ " values (? , ? , ? , ?)";

		try {

			stmt = connection.prepareStatement(sql);
			stmt.setDate(1, java.sql.Date.valueOf(appointment.getAppointmentDate()));
			stmt.setTime(2, java.sql.Time.valueOf(appointment.getAppointmentTime()));
			stmt.setInt(3, appointment.getUserId());
			stmt.setInt(4, appointment.getServiceId());
			stmt.execute();

			log.info(CLASS_NAME + ".create() -> Appointment created successfuly.");
			return true;
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".create() -> Failure to create a new appointment (SQLEXCEPTION)." + e.getMessage());
		}

		log.error(CLASS_NAME + ".create() -> Unable to create a new appointment.");
		return false;
	}

	@Override
	public boolean update(Appointment appointment) {

		log.info(CLASS_NAME + ".update() -> An attempt to update an appointment for user_id= "
				+ appointment.getUserId());

		CallableStatement stmt = null;
		
		// Using a function

		String sql = "select update_appointment(?,?,?,?);";
		

		try {
			stmt = connection.prepareCall(sql);
			stmt.setDate(1, java.sql.Date.valueOf(appointment.getAppointmentDate()));
			stmt.setTime(2, java.sql.Time.valueOf(appointment.getAppointmentTime()));
			stmt.setInt(3, appointment.getServiceId());
			stmt.setInt(4, appointment.getAppointmentId());
			stmt.execute();
			log.info(CLASS_NAME + ".update() -> Appointment updated successfully.");
			return true;
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".update() -> Failure to update an appointment (SQLEXCEPTION)." + e.getMessage());
		}

		log.error(CLASS_NAME + ".update() -> Unable to update an appointment.");
		return false;
		
	
	}

	@Override
	public boolean deleteById(int id) {

		log.info(CLASS_NAME + ".deleteById() -> An attempt to delete an appointment with id= " + id);

		PreparedStatement stmt = null;

		String sql = "delete from appointment where appointment_id = ?";

			try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.execute();
			log.info(CLASS_NAME + ".deleteById() -> Appointment deleted successfully.");
			return true;
		} catch (SQLException e) {
			log.error(
					CLASS_NAME + ".deleteById() -> Failure to delete an appointment (SQLEXCEPTION)." + e.getMessage());
		}

		log.error(CLASS_NAME + ".deleteById() -> Unable to delete an appointment.");
		return false;
	}

	@Override
	public List<AppointmentInfo> getAllAppointmentsByUserId(int id) {

		log.info(
				CLASS_NAME + ".getAllAppointmentsByUserId() -> An Attempt to get all appointments for user id = " + id);

		PreparedStatement stmt = null;

		String sql = "select a.appointment_id, ss.service_name, ss.duration , ss.price, a.appointment_date , a.appointment_time "
				+ "from salon_service ss inner join appointment a on ss.service_id = a.service_id "
				+ "where user_id = ?" + "order by a.appointment_date , a.appointment_time desc";

		List<AppointmentInfo> appointments = null;

		AppointmentInfo appointment = null;

			try  {

			appointments = new ArrayList<>();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				java.sql.Date sqlDate = rs.getDate("appointment_date");
				java.sql.Time sqlTime = rs.getTime("appointment_time");

				LocalDate date = sqlDate.toLocalDate();
				LocalTime time = sqlTime.toLocalTime();
				appointment = new AppointmentInfo(rs.getInt("appointment_id"), rs.getString("service_name"),
						rs.getString("duration"), rs.getFloat("price"), date, time);

				appointments.add(appointment);
			}

		} catch (SQLException e) {
			log.error(CLASS_NAME + ".getAllAppointmentsByUserId() -> Failure to connect to DB (SQLEXCEPTION)."
					+ e.getMessage());
		}
		log.info(CLASS_NAME + ".getAllAppointmentsByUserId() -> A list of appointments for user_id " + id
				+ " returned successfully.");
		return appointments;

	}

	@Override
	public List<Appointment> findAll() { // Delete later

		log.info(CLASS_NAME + ".findAll() -> An Attempt to get all appointments.");

		String sql = "select * from appointment order by appointment_date, appointment_time desc";

		List<Appointment> appointments = null;

		Appointment appointment = null;

		try {

			appointments = new ArrayList<>();
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				java.sql.Date sqlDate = rs.getDate("appointment_date");
				java.sql.Time sqlTime = rs.getTime("appointment_time");

				LocalDate date = sqlDate.toLocalDate();
				LocalTime time = sqlTime.toLocalTime();
				appointment = new Appointment(rs.getInt("appointment_id"), date, time, rs.getInt("user_id"),
						rs.getInt("service_id"));

				appointments.add(appointment);
			}

		} catch (SQLException e) {
			log.error(CLASS_NAME + ".findAll() -> Failure to get all appointments (SQLEXCEPTION)." + e.getMessage());
		}
		log.info(CLASS_NAME + ".findAll() -> A list of appointments returned successfully.");
		return appointments;
	}

	@Override
	public List<LocalTime> getAllAppointmentsTimeByDate(LocalDate date) {

		log.info(CLASS_NAME + ".getAllAppointmentsTimeByDate() -> An Attempt to get all appointments on " + date);

		PreparedStatement stmt = null;

		String sql = "select appointment_time from appointment where appointment_date = ?";

		List<LocalTime> appointmentsTime = null;

		LocalTime apptTime = null;

			try {

			appointmentsTime = new ArrayList<>();
			stmt = connection.prepareStatement(sql);
			stmt.setDate(1, java.sql.Date.valueOf(date));
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				java.sql.Time sqlTime = rs.getTime("appointment_time");
				apptTime = sqlTime.toLocalTime();

				appointmentsTime.add(apptTime);
			}

		} catch (SQLException e) {
			log.error(CLASS_NAME + ".getAllAppointmentsTimeByDate() -> Failure to connect to DB (SQLEXCEPTION)."
					+ e.getMessage());
		}
		log.info(CLASS_NAME + ".getAllAppointmentsTimeByDate() -> A list of appointments time for the date " + date
				+ " returned successfully.");
		return appointmentsTime;
	}

	// For testing purpose
	@Override
	public void deleteAll() {
		log.info(CLASS_NAME + ".deleteAll() -> An attempt to delete all appointments.");

		PreparedStatement stmt = null;

		String sql = "delete from appointment where appointment_id <> 22";

			try  {
			stmt = connection.prepareStatement(sql);
			stmt.execute();
			log.info(CLASS_NAME + ".deleteAll() -> Appointments deleted successfully.");
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".deleteById() -> Failure to delete all appointments (SQLEXCEPTION)."
					+ e.getMessage());
		}

	}

}





// Update appointment not using function


// Using prepared statement --- working well

//log.info(CLASS_NAME + ".update() -> An attempt to update an appointment for user_id= "
//		+ appointment.getUserId());
//
//PreparedStatement stmt = null;
//
//String sql = "update appointment set appointment_date = ?, appointment_time = ?, "
//		+ "service_id = ? where appointment_id = ?";
//
//
////try (Connection conn = ConnectionFactoryPostgres.getConnection()) {
//try {
//	stmt = connection.prepareStatement(sql);
//	stmt.setDate(1, java.sql.Date.valueOf(appointment.getAppointmentDate()));
//	stmt.setTime(2, java.sql.Time.valueOf(appointment.getAppointmentTime()));
//	stmt.setInt(3, appointment.getServiceId());
//	stmt.setInt(4, appointment.getAppointmentId());
//	stmt.execute();
//	log.info(CLASS_NAME + ".update() -> Appointment updated successfully.");
//	return true;
//} catch (SQLException e) {
//	log.error(CLASS_NAME + ".update() -> Failure to update an appointment (SQLEXCEPTION)." + e.getMessage());
//}
//
//log.error(CLASS_NAME + ".update() -> Unable to update an appointment.");
//return false;



// 

// old
//log.info(CLASS_NAME + ".getAllAppointmentsByUserId() -> An Attempt to get all appointments for user id = " + id);
//
//PreparedStatement stmt = null;
//
//String sql = "select * from appointment where user_id = ? order by appointment_date, appointment_time desc";
//
//List<Appointment> appointments = null;
//
//Appointment appointment = null;
//
//try (Connection conn = ConnectionFactoryPostgres.getConnection(DATABASE_ENV)) {
//
//	appointments = new ArrayList<>();
//	stmt = conn.prepareStatement(sql);
//	stmt.setInt(1, id);
//	ResultSet rs = stmt.executeQuery();
//
//	while (rs.next()) {
//
//		java.sql.Date sqlDate = rs.getDate("appointment_date");
//		java.sql.Time sqlTime = rs.getTime("appointment_time");
//
//		LocalDate date = sqlDate.toLocalDate();
//		LocalTime time = sqlTime.toLocalTime();
//		appointment = new Appointment(rs.getInt("appointment_id"), date, time, rs.getInt("user_id"),
//				rs.getInt("service_id"));
//
//		appointments.add(appointment);
//	}
//
//} catch (SQLException e) {
//	log.error(CLASS_NAME + ".getAllAppointmentsByUserId() -> Failure to connect to DB (SQLEXCEPTION)."
//			+ e.getMessage());
//}
//log.info(CLASS_NAME + ".getAllAppointmentsByUserId() -> A list of appointments for user_id " + id
//		+ " returned successfully.");
//return appointments;