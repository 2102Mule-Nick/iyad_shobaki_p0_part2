package com.barbershop.pojo;

import java.time.LocalDate;
import java.time.LocalTime;

public class ManagerApptInfo {

	private int appointmentId;
	private int userId;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String phoneNumber;
	private String role;
	private String serviceName;
	private String duration;
	private float price;
	private LocalDate appointmentDate;
	private LocalTime appointmentTime;
	
	
	public ManagerApptInfo(int appointmentId, int userId, String firstName, String lastName, String emailAddress,
			String phoneNumber, String role, String serviceName, String duration, float price,
			LocalDate appointmentDate, LocalTime appointmentTime) {
		super();
		this.appointmentId = appointmentId;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.serviceName = serviceName;
		this.duration = duration;
		this.price = price;
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
	}



	@Override
	public String toString() {
		return //"Appointment ID: " + appointmentId +
				//", User ID: "+ userId +
				", First Name: " + firstName+
				", Last Name: " + lastName+
				", Email Address: " + emailAddress +
				", Phone Number: " + phoneNumber +
				//", User Role: " + role +
				", Service Name: " + serviceName +
				", Duration: " + duration +
				", Price: " + price + 
				", Date: " + appointmentDate + 
				", Time: " + appointmentTime;
	}
	
	
}
