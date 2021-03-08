package com.barbershop;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.barbershop.dao.AppointmentDaoPostgres;
import com.barbershop.dao.BaseDao;
import com.barbershop.dao.SalonServiceDaoPostgres;
import com.barbershop.dao.UserDaoPostgres;
import com.barbershop.pojo.Appointment;
import com.barbershop.pojo.SalonService;
import com.barbershop.pojo.User;

import junit.framework.Test;

public class Driver {

	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("Please enter a date in format mm/dd/yyyy and should be between today's date and 30 days from now: ");

		LocalDate date = null;
		boolean flag = true;
		do {
			
			while (!scanner.hasNext("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
				System.out.println("Invalid input. Please try again:");
				scanner.nextLine();
			}

			String dateString = scanner.nextLine();

			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			date = LocalDate.parse(dateString, dateFormatter);			
			if (!date.isBefore(LocalDate.now()) && !date.isAfter(LocalDate.now().plusMonths(1))) {
				flag = false;
			}else {
				System.out.println("Invalid input. Please try again (date should be between today's date and 30 days from now):");
			}
		} while (flag);

		System.out.println(date);

	}

}

/*
 * 
 * // UserDaoPostgres teBaseDao = new UserDaoPostgres(); //
 * System.out.println(teBaseDao.findAll());
 * 
 * // User testUser = new User("Mike", "Smith", "222-222-1111","mike@smith.com",
 * // "Customer", "1234"); // teBaseDao.create(testUser);
 * 
 * // User testUser = new User(3,"Sue", "Storm", "444-333-2222","sue@storm.com",
 * // "Customer", "1234"); // teBaseDao.update(testUser);
 * 
 * // teBaseDao.deleteById(3); //
 * System.out.println(teBaseDao.isExist("iyad@shobaki.com")); //
 * System.out.println(teBaseDao.isExist("iiiii@shobaki.com"));
 * 
 * // System.out.println(teBaseDao.getUserInfo("mike@smith.com", "1234")); //
 * System.out.println(teBaseDao.getUserInfo("mike@smith.net", "1234")); //
 * System.out.println(teBaseDao.getUserInfo("mike@smith.com", "1111"));
 * 
 * // Test Salon Service
 * 
 * // SalonServiceDaoPostgres teBaseDao = new SalonServiceDaoPostgres(); //
 * System.out.println(teBaseDao.findAll());
 * 
 * // SalonService service = new SalonService("MENS HAIRCUT", //
 * "Includes shampoo, scalp and neck message, line up and eye brow clean up.",
 * "45 min", 30.00f); // teBaseDao.create(service);
 * 
 * // SalonService service = new
 * SalonService("Straight Razor Head Or Face Shave", //
 * "Includes hot twoel, skin cleaner, top of the line after shave clean and eye brow clean up."
 * , // "45 min", 30.00f); // teBaseDao.create(service);
 * 
 * // SalonService service = new SalonService(1, "Mens Haircut", //
 * "Includes shampoo, scalp and neck message, Straight razor line up and eye brow clean up."
 * , "45 min", // 30.00f); // teBaseDao.update(service);
 * 
 * // SalonService service = new SalonService("Test", //
 * "Includes hot twoel, skin cleaner, Test.", "45 min", // 30.00f); //
 * teBaseDao.create(service);
 * 
 * // teBaseDao.deleteById(3); //
 * System.out.println(teBaseDao.getSalonServiceByName("Mens Haircut")); //
 * System.out.println(teBaseDao.getSalonServiceByName("test"));
 * 
 * 
 * 
 * // test Appointment
 * 
 * // AppointmentDaoPostgres teBaseDao = new AppointmentDaoPostgres(); //
 * //System.out.println(teBaseDao.findAll()); // // // Test create //
 * DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
 * // LocalDate date = LocalDate.parse("03-11-2021", dateFormatter); // //
 * DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a"); //
 * LocalTime time = LocalTime.parse("02:00 PM", timeFormatter); // //
 * System.out.println(date + "  " + time); // Appointment appointment = new
 * Appointment(date, time, 2, 2); // teBaseDao.create(appointment);
 * 
 * 
 * //Test update // DateTimeFormatter dateFormatter =
 * DateTimeFormatter.ofPattern("MM-dd-yyyy"); // LocalDate date =
 * LocalDate.parse("03-22-2021", dateFormatter); // // DateTimeFormatter
 * timeFormatter = DateTimeFormatter.ofPattern("h:mm a"); // LocalTime time =
 * LocalTime.parse("3:00 PM", timeFormatter); // // System.out.println(date +
 * "  " + time); // Appointment appointment = new Appointment(3,date, time, 2,
 * 2); // teBaseDao.update(appointment);
 * 
 * //teBaseDao.deleteById(3);
 * 
 * //System.out.println(teBaseDao.getAllAppointmentsByUserId(2));
 * 
 * // Get appointments by date // DateTimeFormatter dateFormatter =
 * DateTimeFormatter.ofPattern("MM-dd-yyyy"); // LocalDate date =
 * LocalDate.parse("03-12-2021", dateFormatter); // //
 * System.out.println(teBaseDao.getAllAppointmentsTimeByDate(date));
 * 
 */
