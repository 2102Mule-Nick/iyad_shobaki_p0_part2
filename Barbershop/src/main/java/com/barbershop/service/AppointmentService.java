package com.barbershop.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.barbershop.pojo.AppointmentInfo;
import com.barbershop.pojo.ManagerApptInfo;

public interface AppointmentService<Appointment> extends BaseService<Appointment> {

	public List<ManagerApptInfo> getAllUsersAppointmentsDetails();

	public List<LocalTime> getAllAppointmentsTimeByDate(LocalDate date);

	public List<AppointmentInfo> getAllAppointmentsByUserId(int id);
}
