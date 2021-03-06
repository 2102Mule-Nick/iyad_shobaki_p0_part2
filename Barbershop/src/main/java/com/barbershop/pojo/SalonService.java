package com.barbershop.pojo;

public class SalonService {
	
	private int serviceId;
	private String serviceName;
	private String description;
	private String duration;
	private float price;
	
	// Default constructor
	public SalonService() {
		super();
	}

	// Constructor with parameters
	public SalonService(String serviceName, String description, String duration, float price) {
		super();
		this.serviceName = serviceName;
		this.description = description;
		this.duration = duration;
		this.price = price;
	}
	
	// Constructor with parameters
	public SalonService(int serviceId, String serviceName, String description, String duration, float price) {
		super();
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.description = description;
		this.duration = duration;
		this.price = price;
	}

	// Setters and getters
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getServiceId() {
		return serviceId;
	}
	
	// Override methods
	@Override
	public String toString() {
		return "Salon Service [Service Name= " + getServiceName() +
				", Description=" + getDescription() + 
				", Duration= " + getDuration() +
				", Price= " + getPrice() + "]";
	}
	
}
