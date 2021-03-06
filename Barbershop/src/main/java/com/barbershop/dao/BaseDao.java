package com.barbershop.dao;

import java.util.List;

public interface BaseDao<T>{
	
	public List<T> findAll();
	public boolean create(T t);
	public boolean updateById(int id);
	public boolean deleteById(int id);
	
}
