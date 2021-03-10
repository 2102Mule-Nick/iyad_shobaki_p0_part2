package com.barbershop.service;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.barbershop.dao.AppointmentDaoPostgres;
import com.barbershop.pojo.Appointment;
import com.barbershop.pojo.AppointmentInfo;
import com.barbershop.pojo.ManagerApptInfo;
import com.barbershop.pojo.User;
import com.barbershop.util.ConnectionFactoryPostgres;

public class AppointmentServiceImpl implements AppointmentService<Appointment> {

	Logger log = Logger.getRootLogger();
	private AppointmentDaoPostgres appointmentDaoPostgres;
	private static final String CLASS_NAME = "UserServiceImpl";
	private static final String DATABASE_ENV = "OriginalDb";
	
	Connection connection = ConnectionFactoryPostgres.getConnection(DATABASE_ENV);
	
	// Constructor 
	public AppointmentServiceImpl(AppointmentDaoPostgres appointmentDao) {
		super();
		this.appointmentDaoPostgres = appointmentDao;
		this.appointmentDaoPostgres.setConnection(connection);
	}
	

	@Override
	public List<ManagerApptInfo> getAllUsersAppointmentsDetails() { // Manager
		try {			
			List<ManagerApptInfo> appointments = appointmentDaoPostgres.getAllUsersAppointmentsDetails();
			log.info("----------------------------------------------------------------------");
			return appointments;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".findAll() -> Failure to get all appointments." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		return null;
	}

	@Override
	public List<Appointment> findAll() { // Manager role / delete later
		
		try {			
			List<Appointment> appointments = appointmentDaoPostgres.findAll();
			log.info("----------------------------------------------------------------------");
			return appointments;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".findAll() -> Failure to get all appointments." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		return null;
	}

	@Override
	public boolean create(Appointment appointment) { // All users
		
		
		try {			
			appointmentDaoPostgres.create(appointment);
			System.out.println("Appointment booked successfully.");
			log.info("----------------------------------------------------------------------");
			return true;
		} catch (Exception e) {
			log.error(CLASS_NAME + ".create() -> Failure to book an appointment." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		return false;
	}

	@Override
	public boolean update(Appointment appointment) { //All users
		
		if(appointment == null || appointment.getAppointmentId() < 1) {
			log.error(CLASS_NAME + ".update() -> Failure to update an appointment with id = " + appointment.getAppointmentId());
			return false;
		}
		try {
			appointmentDaoPostgres.update(appointment);
			System.out.println("Appointment updated successfully.");
			log.info("----------------------------------------------------------------------");
			return true;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".update() -> Failure to update an appointment." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		return false;
	}

	@Override
	public boolean deleteById(int id) { // All users
		
		try {
			appointmentDaoPostgres.deleteById(id);
			log.info("----------------------------------------------------------------------");
			return true;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".deleteById() -> Failure to delete an appointment." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		return false;
	}

	@Override
	public void deleteAll() { // Testing purpose

		try {
			appointmentDaoPostgres.deleteAll();
			System.out.println("All appointments deleted successfully.");
			log.info("----------------------------------------------------------------------");
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".deleteAll() -> Failure to delete all appointments." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		
	}

	@Override
	public List<LocalTime> getAllAppointmentsTimeByDate(LocalDate date) { // Users
		
		List<LocalTime> apptsTimes = new ArrayList<>();
		try {
			apptsTimes = appointmentDaoPostgres.getAllAppointmentsTimeByDate(date);
			log.info("----------------------------------------------------------------------");
			return apptsTimes;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".getAllAppointmentsTimeByDate() -> Failure to get all appointments for a specific date." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		return null;
	}

	@Override
	public List<AppointmentInfo> getAllAppointmentsByUserId(int id) { // Users
		
		List<AppointmentInfo> apptsByUser = new ArrayList<>();
		try {
			apptsByUser = appointmentDaoPostgres.getAllAppointmentsByUserId(id);
			log.info("----------------------------------------------------------------------");
			return apptsByUser;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".getAllAppointmentsByUserId() -> Failure to get all appointments for a specific user." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		return null;
	}


}
