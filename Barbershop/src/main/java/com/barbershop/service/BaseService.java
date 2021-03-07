package com.barbershop.service;

import java.util.List;

public interface BaseService<T> {

	public List<T> findAll();
	public boolean create(T t);
	public boolean update(T t);
	public boolean deleteById(int id);
	public void deleteAll();
}
