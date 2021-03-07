package com.barbershop;

import com.barbershop.dao.AppointmentDaoPostgres;
import com.barbershop.dao.BaseDao;
import com.barbershop.dao.SalonServiceDaoPostgres;
import com.barbershop.dao.UserDaoPostgres;
import com.barbershop.pojo.SalonService;
import com.barbershop.pojo.User;

import junit.framework.Test;

public class Driver {

	public static void main(String[] args) {

		// UserDaoPostgres teBaseDao = new UserDaoPostgres();
		// System.out.println(teBaseDao.findAll());

		// User testUser = new User("Mike", "Smith", "222-222-1111","mike@smith.com",
		// "Customer", "1234");
		// teBaseDao.create(testUser);

		// User testUser = new User(3,"Sue", "Storm", "444-333-2222","sue@storm.com",
		// "Customer", "1234");
		// teBaseDao.update(testUser);

		// teBaseDao.deleteById(3);
		// System.out.println(teBaseDao.isExist("iyad@shobaki.com"));
		// System.out.println(teBaseDao.isExist("iiiii@shobaki.com"));

		// System.out.println(teBaseDao.getUserInfo("mike@smith.com", "1234"));
		// System.out.println(teBaseDao.getUserInfo("mike@smith.net", "1234"));
		// System.out.println(teBaseDao.getUserInfo("mike@smith.com", "1111"));

		// Test Salon Service

		// SalonServiceDaoPostgres teBaseDao = new SalonServiceDaoPostgres();
		// System.out.println(teBaseDao.findAll());

//		SalonService service = new SalonService("MENS HAIRCUT",
//				"Includes shampoo, scalp and neck message, line up and eye brow clean up.", "45 min", 30.00f);
//		teBaseDao.create(service);

//		 SalonService service = new SalonService("Straight Razor Head Or Face Shave", 
//				 "Includes hot twoel, skin cleaner, top of the line after shave clean and eye brow clean up.",
//				 "45 min", 30.00f);
//		 teBaseDao.create(service);

//		SalonService service = new SalonService(1, "Mens Haircut",
//				"Includes shampoo, scalp and neck message, Straight razor line up and eye brow clean up.", "45 min",
//				30.00f);
//		teBaseDao.update(service);

//		SalonService service = new SalonService("Test",
//				"Includes hot twoel, skin cleaner, Test.", "45 min",
//				30.00f);
//		teBaseDao.create(service);

//		 teBaseDao.deleteById(3);
//		 System.out.println(teBaseDao.getSalonServiceByName("Mens Haircut"));
//		 System.out.println(teBaseDao.getSalonServiceByName("test"));

		// test Appointment

		// AppointmentDaoPostgres teBaseDao = new AppointmentDaoPostgres();
		// System.out.println(teBaseDao.findAll());
		// User testUser = new User("Mike", "Smith", "222-222-1111","mike@smith.com",
		// "Customer", "1234");
		// User testUser = new User(3,"Sue", "Storm", "444-333-2222","sue@storm.com",
		// "Customer", "1234");
		// teBaseDao.update(testUser);
		// teBaseDao.create(testUser);
		// teBaseDao.deleteById(3);
		// System.out.println(teBaseDao.isExist("iyad@shobaki.com"));
		// System.out.println(teBaseDao.isExist("iiiii@shobaki.com"));

		// System.out.println(teBaseDao.getUserInfo("mike@smith.com", "1234"));
		// System.out.println(teBaseDao.getUserInfo("mike@smith.net", "1234"));
		// System.out.println(teBaseDao.getUserInfo("mike@smith.com", "1111"));
	}

}
