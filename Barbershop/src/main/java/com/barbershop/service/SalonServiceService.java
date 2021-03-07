package com.barbershop.service;


public interface SalonServiceService<SalonService> extends BaseService<SalonService> {

	public SalonService getSalonServiceByName(String name);
	public SalonService getSalonServiceById(int id);
}
