package com.barbershop.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.barbershop.pojo.Appointment;
import com.barbershop.pojo.AppointmentInfo;
import com.barbershop.pojo.SalonService;
import com.barbershop.pojo.User;
import com.barbershop.service.AppointmentServiceImpl;
import com.barbershop.service.SalonServiceServiceImpl;
import com.barbershop.service.UserServiceImpl;

public class CustomerMenu implements Menu {

	private User user;
	private UserServiceImpl userServiceImpl;
	private AppointmentServiceImpl appointmentServiceImpl;
	private SalonServiceServiceImpl salonServiceServiceImpl;
	private Menu welcomeMenu;
	private Menu nextMenu;

	public CustomerMenu(User user, UserServiceImpl userServiceImpl, AppointmentServiceImpl appointmentServiceImpl,
			SalonServiceServiceImpl salonServiceServiceImpl, Menu welcomeMenu) {
		super();
		this.user = user;
		this.userServiceImpl = userServiceImpl;
		this.appointmentServiceImpl = appointmentServiceImpl;
		this.salonServiceServiceImpl = salonServiceServiceImpl;
		this.welcomeMenu = welcomeMenu;
	}

	public Menu advance() {
		return nextMenu;
	}

	public void displayOptions(Scanner scanner) {

		System.out.println("\t------------    -------------------------------------     ------------");
		System.out.println(
				"\t------------      Welcome, " + user.getFirstName() + " To The Customer Menu       ------------");
		System.out.println("\t------------    -------------------------------------     ------------");

		printMenu();

		List<AppointmentInfo> appointments = null;

		boolean logout = false;

		while (!logout) {
			int action = scanner.nextInt();
			scanner.nextLine();

			switch (action) {
			case 0:
				System.out.println("You are successfully logout. Thank you!");
//				clearUserInfo(user);
//				nextMenu = welcomeMenu;   // need to pass menus
				logout = true;
				break;
			case 1:
				
				System.out.println("***********************************************");
				Appointment appointment = createOrUpdateAppointment(scanner);

				if (appointmentServiceImpl.create(appointment)) {

					System.out.println("An appointment has been scheduled for " + this.user.getFirstName() + " on : "
							+ appointment.getAppointmentDate() + " at: " + appointment.getAppointmentTime());
				} else {
					System.out.println("Something went wrong! Please try again.");
				}
				System.out.println("***********************************************");
				break;
			case 2:
				System.out.println("***********************************************");
				appointments = appointmentServiceImpl.getAllAppointmentsByUserId(this.user.getUserId());
				System.out.println("All appointments:");
				if (appointments.size() < 1) {
					System.out.println("There are no appoinments found.");
				}
				for (AppointmentInfo appt : appointments) {
					System.out.println(appt);
				}
				System.out.println("***********************************************");
				break;
			case 3:
				System.out.println("***********************************************");
				appointments = appointmentServiceImpl.getAllAppointmentsByUserId(this.user.getUserId());
				if (appointments.size() < 1) {
					System.out.println("There are no appoinments found.");
				} else {

					for (int i = 0; i < appointments.size(); i++) {
						System.out.println((i + 1) + "- " + appointments.get(i));
					}
					System.out.println("Please choose appointment by number between 1 - " + appointments.size() + ":");
					boolean flag = true;

					int apptNumber = 0;
					do {

						if (scanner.hasNextInt()) {
							apptNumber = scanner.nextInt();
							if (apptNumber >= 1 && apptNumber <= appointments.size()) {
								flag = false;
							} else {
								System.out.println("Invalid input. Please pick an appointment by its number between 1 - "
										+ appointments.size());
							}
						} else {
							System.out.println("Invalid input. Please try again with a valid number:");
						}
					} while (flag);
					scanner.nextLine();

					Appointment appointmentUpdate = createOrUpdateAppointment(scanner);
					appointmentUpdate.setAppointmentId(appointments.get(apptNumber - 1).getAppointmentId());

					if (appointmentServiceImpl.update(appointmentUpdate)) {

						System.out.println("An appointment has been rescheduled for " + this.user.getFirstName()
								+ " on : " + appointmentUpdate.getAppointmentDate() + " at: "
								+ appointmentUpdate.getAppointmentTime());
					} else {
						System.out.println("Something went wrong! Please try again.");
					}
				}
				
				System.out.println("***********************************************");
				break;
			case 4:
				
				System.out.println("***********************************************");
				appointments = appointmentServiceImpl.getAllAppointmentsByUserId(this.user.getUserId());
				if (appointments.size() < 1) {
					System.out.println("There are no appoinments found.");
				} else {

					for (int i = 0; i < appointments.size(); i++) {
						System.out.println((i + 1) + "- " + appointments.get(i));
					}
					System.out.println("Please choose appointment by number between 1 - " + appointments.size() + ":");
					boolean flag = true;

					int apptNumber = 0;
					do {

						if (scanner.hasNextInt()) {
							apptNumber = scanner.nextInt();
							if (apptNumber >= 1 && apptNumber <= appointments.size()) {
								int timeValue = appointments.get(apptNumber - 1).getAppointmentTime()
										.compareTo(LocalTime.now().plusMinutes(30));
								int dateValue = appointments.get(apptNumber - 1).getAppointmentDate()
										.compareTo(LocalDate.now());
								if (dateValue >= 0 || timeValue >= 0) {
									flag = false;
								} else {
									System.out.println(
											"Appointment cannot be canceled at this time. \nPlease give us a call or choose a number to cancel different appointment:");
								}

							} else {
								System.out.println("Invalid input. Please pick a service by its number between 1 - "
										+ appointments.size());
							}
						} else {
							System.out.println("Invalid input. Please try again with a valid number:");
						}
					} while (flag);
					scanner.nextLine();

					if (appointmentServiceImpl.deleteById(appointments.get(apptNumber - 1).getAppointmentId())) {
						System.out.println("Appointment deleted successfully.");
					} else {
						System.out.println("Something went wrong! Please try again.");
					}
				}
				System.out.println("***********************************************");
				break;
			case 9:
				printMenu();
				break;
			}
		}
	}

	private Appointment createOrUpdateAppointment(Scanner scanner) {

		System.out.println("Please enter a date (mm/dd/yyyy format). Between today's date and 30 days from now: ");
		LocalDate appointmentDate = validateDate(scanner);
		System.out.println();
		System.out.println("***********************************************");
		System.out.println("Available time on " + appointmentDate + ":");
		System.out.println("***********************************************");
		
		LocalTime appointmentTime = validateTime(scanner, appointmentDate);

		System.out.println();
		System.out.println("***********************************************");
		System.out.println("Available Salon serices are:");
		System.out.println("***********************************************");
		
		int service_id = pickSalonService(scanner);

		Appointment appointment = new Appointment(appointmentDate, appointmentTime, this.user.getUserId(), service_id);

		return appointment;
	}

	private int pickSalonService(Scanner scanner) {
		List<SalonService> services = salonServiceServiceImpl.findAll();
		for (int i = 0; i < services.size(); i++) {
			System.out.println((i + 1) + "- " + services.get(i));
		}

		boolean flag = true;

		int serviceNumber = 0;
		do {

			if (scanner.hasNextInt()) {
				serviceNumber = scanner.nextInt();
				if (serviceNumber >= 1 && serviceNumber <= services.size()) {
					flag = false;
				} else {
					System.out.println(
							"Invalid input. Please pick a service by its number between 1 - " + services.size());
				}
			} else {
				System.out.println("Invalid input. Please try again with a valid number:");
			}
		} while (flag);

		return serviceNumber;
	}

	private LocalTime validateTime(Scanner scanner, LocalDate appointmentDate) {

		List<LocalTime> availabeAppointmentsTime = new ArrayList<>();
		List<LocalTime> bookedAppointmentsTime = appointmentServiceImpl.getAllAppointmentsTimeByDate(appointmentDate);

		for (TimeOfDay timeOfDay : TimeOfDay.values()) {
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
			LocalTime time = LocalTime.parse(timeOfDay.toString(), timeFormatter);
			int value = time.compareTo(LocalTime.now().plusMinutes(30));

			if (bookedAppointmentsTime.contains(time) || (value < 0 && appointmentDate.equals(LocalDate.now()))) {
				continue;
			} else {
				availabeAppointmentsTime.add(time);
			}
		}

		for (int i = 0; i < availabeAppointmentsTime.size(); i++) {
			System.out.println((i + 1) + "- " + availabeAppointmentsTime.get(i));

		}
		System.out.println("\nPlease pick a time by its number between 1 - " + availabeAppointmentsTime.size());
		boolean flag = true;

		int timeInt = 0;
		do {

			while (!scanner.hasNextInt()) {
				System.out.println("Invalid input. Please try again with valid number:");
				scanner.next();
			}
			
			timeInt = scanner.nextInt();
			if (timeInt >= 1 && timeInt <= availabeAppointmentsTime.size()) {
				flag = false;
			} else {
				System.out.println("Invalid input. Please pick a time by its number between 1 - "
						+ availabeAppointmentsTime.size());
			}
		} while (flag);

		return availabeAppointmentsTime.get(timeInt - 1);
//		
//		List<LocalTime> availabeAppointmentsTime = new ArrayList<>();
//		List<LocalTime> bookedAppointmentsTime = appointmentServiceImpl.getAllAppointmentsTimeByDate(appointmentDate);
//		
//		for(float i = 10f; i< 20f; i+=0.45f) {
//			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm"); // LocalTime time =
//			LocalTime time = LocalTime.parse(String.format("%.2f", i), timeFormatter);
//			availabeAppointmentsTime.add(time);
//		}
//		
//		System.out.println(appointmentServiceImpl.getAllAppointmentsTimeByDate(appointmentDate));				
//		System.out.println("Please pick a time :");
	}

	private enum TimeOfDay {
		ONE("10:00 AM"), TWO("10:45 AM"), THREE("11:30 AM"), FOUR("12:15 PM"), FIVE("1:00 PM"), SIX("1:45 PM"),
		SEVEN("2:30 PM"), EIGHT("3:15 PM"), NINE("4:00 PM"), TEN("4:45 PM"), ELEVEN("5:30 PM"), TWELVE("6:15 PM"),
		THIRTEEN("7:00 PM"), FOURTEEN("7:45 PM"), FIFTEEN("8:30 PM"), SIXTEEN("9:15 PM"), SEVENTEEN("10:00 PM");

		private final String name;

		private TimeOfDay(String s) {
			name = s;
		}

		@Override
		public String toString() {
			return this.name;
		}

	}

	private LocalDate validateDate(Scanner scanner) {

		LocalDate date = null;
		boolean flag = true;
		do {

			while (!scanner.hasNext("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
				System.out.println("Invalid input. Please follow mm/dd/yyyy format:");
				scanner.nextLine();
			}

			String dateString = scanner.nextLine();

			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			date = LocalDate.parse(dateString, dateFormatter);
			if (!date.isBefore(LocalDate.now()) && !date.isAfter(LocalDate.now().plusMonths(1))) {
				flag = false;
			} else {
				System.out.println(
						"Invalid input. Please try again (date should be between today's date and 30 days from now):");
			}
		} while (flag);

		return date;
	}

	/*
	 * Display the menu options
	 */
	private static void printMenu() {
		System.out.println("Available actions:\npress");
		System.out.println("0 - to quit\n" + "1 - Book an appointment\n" + "2 - See all appointments\n"
				+ "3 - Update an appointment\n" + "4 - Delete an appointment\n" + "9 - Print available actions");

	}

//	private void clearUserInfo(User user) { // for later (logout functionality)
//		user.setUserId(0);
//		user.setFirstName("");
//		user.setLastName("");
//		user.setEmailAddress("");
//		user.setPhoneNumber("");
//		user.setPassword("");
//	}

}
