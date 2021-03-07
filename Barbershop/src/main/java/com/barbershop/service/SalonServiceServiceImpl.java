package com.barbershop.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.barbershop.dao.SalonServiceDaoPostgres;
import com.barbershop.pojo.SalonService;
import com.barbershop.pojo.User;

public class SalonServiceServiceImpl implements SalonServiceService<SalonService> {

	Logger log = Logger.getRootLogger();
	private SalonServiceDaoPostgres salonServiceDao;
	private static final String CLASS_NAME = "SalonServiceServiceImpl";
	
	// Constructor
	public SalonServiceServiceImpl(SalonServiceDaoPostgres salonServiceDao) {
		super();
		this.salonServiceDao = salonServiceDao;
	}

	@Override
	public List<SalonService> findAll() {
		try {			
			List<SalonService> services = salonServiceDao.findAll();
			System.out.println("All salon services info returned successfully.");
			return services;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".findAll() -> Failure to get all salon services." + e.getMessage());
		}
		return null;
	}

	@Override
	public boolean create(SalonService service) {
		// Since the admin who want to add a new service. I will not check for a similar service
		// He has the ability to delete or update it by id later
		try {			
			salonServiceDao.create(service);
			System.out.println("Salon service created successfully.");
			return true;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".create() -> Failure to create a new salon service." + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean update(SalonService service) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SalonService getSalonServiceByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SalonService getSalonServiceById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
