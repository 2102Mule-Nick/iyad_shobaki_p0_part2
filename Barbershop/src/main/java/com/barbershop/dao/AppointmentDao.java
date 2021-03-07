package com.barbershop.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentDao<Appointment> extends BaseDao<Appointment> {
	
	//public List<LocalDate> getAllAppointmentsDates();
	public List<LocalTime> getAllAppointmentsTimeByDate(LocalDate date);
	//public boolean isAppointmentExist(LocalDate date, LocalTime time);
	public List<Appointment> getAllAppointmentsByUserId(int id);
	
}
