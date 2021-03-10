package com.barbershop;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.barbershop.dao.AppointmentDaoPostgres;
import com.barbershop.dao.SalonServiceDaoPostgres;
import com.barbershop.dao.UserDaoPostgres;
import com.barbershop.pojo.User;
import com.barbershop.service.AppointmentServiceImpl;
import com.barbershop.service.SalonServiceServiceImpl;
import com.barbershop.service.UserServiceImpl;
import com.barbershop.ui.CustomerMenu;
import com.barbershop.ui.LoginMenu;
import com.barbershop.ui.ManagerMenu;
import com.barbershop.ui.Menu;
import com.barbershop.ui.RegistrationMenu;
import com.barbershop.ui.WelcomeMenu;

public class Driver {

	public static Scanner scanner = new Scanner(System.in);
	public static User user = new User();

	public static void main(String[] args) {

		System.out.println("\t------------    Welcome To The Barbershop Application     ------------");
		System.out.println("\t------------    -------------------------------------     ------------");
		System.out.println("\t------------    -------------------------------------     ------------");
	
		
		UserDaoPostgres userDao = new UserDaoPostgres();
		UserServiceImpl userServiceImpl = new UserServiceImpl(userDao);
		WelcomeMenu passingMenu = new WelcomeMenu(); // For later (logout functionality)
		AppointmentDaoPostgres appointmentDao = new AppointmentDaoPostgres();
		AppointmentServiceImpl appointmentServiceImpl = new AppointmentServiceImpl(appointmentDao);
		SalonServiceDaoPostgres salonServiceDao = new SalonServiceDaoPostgres();
		SalonServiceServiceImpl salonServiceServiceImpl = new SalonServiceServiceImpl(salonServiceDao);
		
		
		CustomerMenu customerMenu = new CustomerMenu(user,userServiceImpl, appointmentServiceImpl,
				salonServiceServiceImpl, passingMenu);
		ManagerMenu managerMenu = new ManagerMenu(user, userServiceImpl,appointmentServiceImpl,
				salonServiceServiceImpl, passingMenu);		
		LoginMenu loginMenu = new LoginMenu(user, userServiceImpl, customerMenu, managerMenu);
		RegistrationMenu registrationMenu = new RegistrationMenu(user, userServiceImpl, loginMenu);
		WelcomeMenu welcomeMenu = new WelcomeMenu(loginMenu, registrationMenu);

		//Menu[] menus = {loginMenu, registrationMenu};
		
		Menu nextMenu = welcomeMenu;

		do {
			nextMenu.displayOptions(scanner);

			nextMenu = nextMenu.advance();//user, userServiceImpl,menus);
		} while (nextMenu != null);

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
