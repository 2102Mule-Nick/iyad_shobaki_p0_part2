package com.barbershop.service;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.barbershop.dao.SalonServiceDaoPostgres;
import com.barbershop.pojo.SalonService;
import com.barbershop.util.ConnectionFactoryPostgres;

public class SalonServiceServiceImpl implements SalonServiceService<SalonService> {

	Logger log = Logger.getRootLogger();
	private SalonServiceDaoPostgres salonServiceDao;
	private static final String CLASS_NAME = "SalonServiceServiceImpl";
	private Connection connection;
	
	
	// Constructor
	public SalonServiceServiceImpl(SalonServiceDaoPostgres salonServiceDao, Connection connection) {
		super();
		this.connection = connection;
		this.salonServiceDao = salonServiceDao;
		this.salonServiceDao.setConnection(this.connection);
	}

	@Override
	public List<SalonService> findAll() {
		try {			
			List<SalonService> services = salonServiceDao.findAll();
			log.info("----------------------------------------------------------------------");
			return services;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".findAll() -> Failure to get all salon services." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		return null;
	}

	@Override
	public boolean create(SalonService service) {
		// Since the admin who want to add a new service. I will not check for a similar service
		// He has the ability to delete or update it by id later (for now)
		try {			
			salonServiceDao.create(service);
			log.info("----------------------------------------------------------------------");
			return true;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".create() -> Failure to create a new salon service." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		return false;
	}

	@Override
	public boolean update(SalonService service) {

		if(service == null || service.getServiceId() < 1) {
			log.error(CLASS_NAME + ".update() -> Failure to update salon service with id = " + service.getServiceId());
			log.info("----------------------------------------------------------------------");
			return false;
		}
		try {
			salonServiceDao.update(service);
			log.info("----------------------------------------------------------------------");
			return true;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".update() -> Failure to update salon service." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		return false;
	}

	@Override
	public boolean deleteById(int id) {
		
		try {
			salonServiceDao.deleteById(id);
			log.info("----------------------------------------------------------------------");
			return true;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".deleteById() -> Failure to delete salon service." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		return false;
	}

	@Override
	public void deleteAll() {

		try {
			salonServiceDao.deleteAll();
			System.out.println("All salon services deleted successfully.");
			log.info("----------------------------------------------------------------------");
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".deleteAll() -> Failure to delete all salon services." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		
	}

	@Override
	public SalonService getSalonServiceByName(String name) {
		
		try {
			SalonService service = salonServiceDao.getSalonServiceByName(name);
			System.out.println("Salon service with name = " + name 
					+ " returned successfully.");
			log.info("----------------------------------------------------------------------");
			return service;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".getSalonServiceByName() -> Failure to get salon service info." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		return null;
	}

	@Override
	public SalonService getSalonServiceById(int id) {
		
		try {
			SalonService service = salonServiceDao.getSalonServiceById(id);
			System.out.println("Salon service with id = " + id + " returned successfully.");
			log.info("----------------------------------------------------------------------");
			return service;
		} catch (Exception e) {
			System.out.println("Something went wrong. Please try again later!");
			log.error(CLASS_NAME + ".getSalonServiceById() -> Failure to get salon service info." + e.getMessage());
			log.info("----------------------------------------------------------------------");
		}
		return null;
	}

}
