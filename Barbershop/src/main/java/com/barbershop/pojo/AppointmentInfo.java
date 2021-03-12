package com.barbershop.pojo;

import java.time.LocalDate;
import java.time.LocalTime;

// Hold the data that is coming from the inner join clause -- More info for the customer

public class AppointmentInfo {
	private int appointmentId;
	private String serviceName;
	private String duration;
	private float price;
	private LocalDate appointmentDate;
	private LocalTime appointmentTime;
	
	
	public AppointmentInfo(int appointmentId, String serviceName, String duration, float price,
			LocalDate appointmentDate, LocalTime appointmentTime) {
		super();
		this.appointmentId = appointmentId;
		this.serviceName = serviceName;
		this.duration = duration;
		this.price = price;
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
	}

	

	public int getAppointmentId() {
		return appointmentId;
	}
	
	


	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}



	public LocalTime getAppointmentTime() {
		return appointmentTime;
	}



	@Override
	public String toString() {
		return "Service Name: " + serviceName +
				", Duration: " + duration +
				", Price: " + price + 
				", Date: " + appointmentDate + 
				", Time: " + appointmentTime;
	}
	
	
}
