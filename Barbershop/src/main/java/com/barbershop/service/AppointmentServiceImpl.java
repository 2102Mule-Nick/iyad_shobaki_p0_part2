package com.barbershop.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.barbershop.pojo.Appointment;

public class AppointmentServiceImpl implements AppointmentService<Appointment> {

	@Override
	public List<Appointment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean create(Appointment t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Appointment t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<LocalTime> getAllAppointmentsTimeByDate(LocalDate date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Appointment> getAllAppointmentsByUserId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
