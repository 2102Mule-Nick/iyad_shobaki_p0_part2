package com.barbershop.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService<Appointment> extends BaseService<Appointment> {

		//public List<LocalDate> getAllAppointmentsDates();
		public List<LocalTime> getAllAppointmentsTimeByDate(LocalDate date);
		//public boolean isAppointmentExist(LocalDate date, LocalTime time);
		public List<Appointment> getAllAppointmentsByUserId(int id);
}
