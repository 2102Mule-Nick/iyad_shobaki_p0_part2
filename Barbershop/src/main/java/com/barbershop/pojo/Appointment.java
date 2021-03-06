package com.barbershop.pojo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
	private int appointmentId;
	private LocalDate appointmentDate;
	private LocalTime appointmentTime;
	private int userId;
	private int serviceId;

	// Default constructor
	public Appointment() {
		super();
	}
	
	// Constructor with parameters
	public Appointment(LocalDate appointmentDate, LocalTime appointmentTime, int userId,
			int serviceId) {
		super();
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
		this.userId = userId;
		this.serviceId = serviceId;
	}

	// Constructor with parameters
	public Appointment(int appointmentId, LocalDate appointmentDate, LocalTime appointmentTime, int userId,
			int serviceId) {
		super();
		this.appointmentId = appointmentId;
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
		this.userId = userId;
		this.serviceId = serviceId;
	}

	
	// Setters and getters
	public int getAppointmentId() {
		return appointmentId;
	}


	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public LocalTime getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(LocalTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	// Override methods
	@Override
	public String toString() {
		return "Appointment [Appointment Date= " + getAppointmentDate()
				+ ", Appointment Time= " + getAppointmentTime() + "]";
	}
	
}
