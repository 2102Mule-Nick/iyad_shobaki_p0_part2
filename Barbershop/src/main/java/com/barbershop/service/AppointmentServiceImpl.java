package com.barbershop.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.barbershop.dao.AppointmentDaoPostgres;
import com.barbershop.pojo.Appointment;
import com.barbershop.pojo.User;

public class AppointmentServiceImpl implements AppointmentService<Appointment> {

	Logger log = Logger.getRootLogger();
	private AppointmentDaoPostgres AppointmentDao;
	private static final String CLASS_NAME = "UserServiceImpl";
	
	
	// Constructor 
	public AppointmentServiceImpl(AppointmentDaoPostgres appointmentDao) {
		super();
		AppointmentDao = appointmentDao;
	}

	@Override
	public List<Appointment> findAll() {
		
		try {			
			List<Appointment> appointments = AppointmentDao.findAll();
			System.out.println("All appointments info returned successfully.");
			return appointments;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".findAll() -> Failure to get all appointments." + e.getMessage());
		}
		return null;
	}

	@Override
	public boolean create(Appointment appointment) {
		
		
		try {			
			AppointmentDao.create(appointment);
			System.out.println("Appointment booked successfully.");
			return true;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".create() -> Failure to book an appointment." + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean update(Appointment appointment) {
		
		if(appointment == null || appointment.getAppointmentId() < 1) {
			log.error(CLASS_NAME + ".update() -> Failure to update an appointment with id = " + appointment.getAppointmentId());
			return false;
		}
		try {
			AppointmentDao.update(appointment);
			System.out.println("Appointment updated successfully.");
			return true;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".update() -> Failure to update an appointment." + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean deleteById(int id) {
		
		try {
			AppointmentDao.deleteById(id);
			System.out.println("Appointment with id = " + id + " deleted successfully.");
			return true;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".deleteById() -> Failure to delete an appointment." + e.getMessage());
		}
		return false;
	}

	@Override
	public void deleteAll() {

		try {
			AppointmentDao.deleteAll();
			System.out.println("All appointments deleted successfully.");
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".deleteAll() -> Failure to delete all appointments." + e.getMessage());
		}
		
	}

	@Override
	public List<LocalTime> getAllAppointmentsTimeByDate(LocalDate date) {
		
		List<LocalTime> apptsTimes = new ArrayList<>();
		try {
			apptsTimes = AppointmentDao.getAllAppointmentsTimeByDate(date);
			System.out.println("All appointments on " + date + " returned successfully.");
			return apptsTimes;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".getAllAppointmentsTimeByDate() -> Failure to get all appointments for a specific date." + e.getMessage());
		}
		return null;
	}

	@Override
	public List<Appointment> getAllAppointmentsByUserId(int id) {
		
		List<Appointment> apptsByUser = new ArrayList<>();
		try {
			apptsByUser = AppointmentDao.getAllAppointmentsByUserId(id);
			System.out.println("All appointments for user with id = " + id + " returned successfully.");
			return apptsByUser;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".getAllAppointmentsByUserId() -> Failure to get all appointments for a specific user." + e.getMessage());
		}
		return null;
	}

}
