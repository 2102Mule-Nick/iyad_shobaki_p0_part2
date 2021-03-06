package com.barbershop.pojo;

public class User {
	private int userId;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String phoneNumber;
	private String password;
	private String role;
	
	
	// Default constructor
	public User() {
		super();
	}

	// Constructor with parameters
	public User(String firstName, String lastName, String phoneNumber, 
			String emailAddress,String role,  String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.role = role;
	}

	// Constructor with parameters
	public User(int userId,String firstName, String lastName, String phoneNumber, 
			String emailAddress,String role,  String password) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.role = role;
	}

	// Setters and getters
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}

	// Override methods
	@Override
	public String toString() {
		return "User [First Name= " + getFirstName() +
				", Last Name= " + getLastName() + 
				", Email Address= " + getEmailAddress() +
				", role= " + getRole() + "]";
	}
	
	
	
}
