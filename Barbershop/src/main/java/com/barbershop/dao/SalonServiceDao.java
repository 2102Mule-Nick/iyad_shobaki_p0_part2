package com.barbershop.dao;

public interface SalonServiceDao<SalonService> extends BaseDao<SalonService> {
	
	public int getSalonServiceByName(String name);
}
