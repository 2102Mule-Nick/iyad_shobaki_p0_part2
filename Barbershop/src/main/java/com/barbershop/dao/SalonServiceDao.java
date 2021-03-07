package com.barbershop.dao;

public interface SalonServiceDao<SalonService> extends BaseDao<SalonService> {

	public SalonService getSalonServiceByName(String name);
	public SalonService getSalonServiceById(int id);

}
