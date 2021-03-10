package com.barbershop.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.barbershop.pojo.AppointmentInfo;

public interface AppointmentService<Appointment> extends BaseService<Appointment> {

		//public List<LocalDate> getAllAppointmentsDates();
		public List<LocalTime> getAllAppointmentsTimeByDate(LocalDate date);
		//public boolean isAppointmentExist(LocalDate date, LocalTime time);
		public List<AppointmentInfo> getAllAppointmentsByUserId(int id);
}
