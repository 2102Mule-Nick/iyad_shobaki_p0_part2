package com.barbershop.ui;

import java.util.List;
import java.util.Scanner;

import com.barbershop.pojo.ManagerApptInfo;
import com.barbershop.pojo.SalonService;
import com.barbershop.pojo.User;
import com.barbershop.service.AppointmentServiceImpl;
import com.barbershop.service.SalonServiceServiceImpl;
import com.barbershop.service.UserServiceImpl;

public class ManagerMenu implements Menu {

	private User user;
	private UserServiceImpl userServiceImpl;
	private AppointmentServiceImpl appointmentServiceImpl;
	private SalonServiceServiceImpl salonServiceServiceImpl;
	private Menu welcomeMenu;
	private Menu nextMenu;

	public ManagerMenu(User user, UserServiceImpl userServiceImpl, AppointmentServiceImpl appointmentServiceImpl,
			SalonServiceServiceImpl salonServiceServiceImpl, Menu welcomeMenu) {
		super();
		this.user = user;
		this.userServiceImpl = userServiceImpl;
		this.appointmentServiceImpl = appointmentServiceImpl;
		this.salonServiceServiceImpl = salonServiceServiceImpl;
		this.welcomeMenu = welcomeMenu;
	}

	public Menu advance() {
		// TODO Auto-generated method stub
		return nextMenu;
	}

	public void displayOptions(Scanner scanner) {

		System.out.println("\t------------    -------------------------------------     ------------");
		System.out.println(
				"\t------------      Welcome, " + user.getFirstName() + " To The Manager Menu       ------------");
		System.out.println("\t------------    -------------------------------------     ------------");

		printMenu();

		List<ManagerApptInfo> appointments = null;
		List<User> users = null;
		List<SalonService> services = null;

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
				appointments = appointmentServiceImpl.getAllUsersAppointmentsDetails();
				System.out.println("All users appointments details:");
				if (appointments.size() < 1) {
					System.out.println("There are no appoinments found.");
				}
				for (ManagerApptInfo appt : appointments) {
					System.out.println(appt);
					System.out.println("-----------------------------------------------");
				}
				System.out.println("***********************************************");
				break;
			case 2:
				System.out.println("***********************************************");
				users = userServiceImpl.findAll();
				System.out.println("All users details:");
				if (users.size() < 1) {
					System.out.println("There are no users found.");
				}
				for (User user : users) {
					System.out.println(user);
					System.out.println("-----------------------------------------------");
				}
				System.out.println("***********************************************");
				break;
			case 3:
				System.out.println("***********************************************");
				users = userServiceImpl.findAll();
				if (users.size() < 1) {
					System.out.println("There are no users found.");
				} else {

					for (int i = 0; i < users.size(); i++) {
						System.out.println((i + 1) + "- " + users.get(i));
					}
					System.out.println("Please choose user by number between 2 - " + users.size() + ":");
					boolean flag = true;

					int userNumber = 0;
					do {

						if (scanner.hasNextInt()) {
							userNumber = scanner.nextInt();
							if (userNumber >= 2 && userNumber <= users.size()) {
								flag = false;
							} else {
								System.out.println(
										"Invalid input. Please pick a user by his number between 1 - " + users.size());
							}
						} else {
							System.out.println("Invalid input. Please try again with a valid number:");
						}
					} while (flag);
					scanner.nextLine();

					System.out.println("Please write user new role: ");
					String newRole = scanner.nextLine();

					if (userServiceImpl.updateUerRole(users.get(userNumber - 1).getUserId(), newRole)) {
						System.out.println("User role updated successfully");
					} else {
						System.out.println("Something went wrong! Please try again.");
					}
				}

				System.out.println("***********************************************");
				break;
			case 4:
				System.out.println("***********************************************");
				services = salonServiceServiceImpl.findAll();
				System.out.println("All salon services details:");
				if (services.size() < 1) {
					System.out.println("There are no services found.");
				}
				int count = 0;
				for (SalonService service : services) {
					System.out.println((++count) + " - " + service);
					System.out.println("-----------------------------------------------");
				}
				System.out.println("***********************************************");
				break;
			case 5:
				System.out.println("***********************************************");
				System.out.println("Enter service name:");
				String serviceName = scanner.nextLine();
				System.out.println("Enter service description:");
				String description = scanner.nextLine();
				System.out.println("Enter service duration:");
				String duration = scanner.nextLine();
				System.out.println("Enter service price:");
				float price = scanner.nextFloat();
				SalonService service = new SalonService(serviceName, description, duration, price);
				
				if(salonServiceServiceImpl.create(service)) {
					System.out.println("Service added successfully");
				} else {
					System.out.println("Something went wrong! Please try again.");
				}
				System.out.println("***********************************************");
				break;
			case 6:
				System.out.println("***********************************************");
				services = salonServiceServiceImpl.findAll();
				if (services.size() < 1) {
					System.out.println("There are no services found.");
				} else {

					for (int i = 0; i < services.size(); i++) {
						System.out.println((i + 1) + "- " + services.get(i));
						System.out.println("-----------------------------------------------");
					}
					System.out.println("Please choose a service by number between 1 - " + services.size() + ":");
					boolean flag = true;

					int serviceNumber = 0;
					do {

						if (scanner.hasNextInt()) {
							serviceNumber = scanner.nextInt();
							if (serviceNumber >= 1 && serviceNumber <= services.size()) {
								flag = false;
							} else {
								System.out.println(
										"Invalid input. Please pick a user by his number between 1 - " + services.size());
							}
						} else {
							System.out.println("Invalid input. Please try again with a valid number:");
						}
					} while (flag);
					scanner.nextLine();

					System.out.println("Enter new service name:");
					String newServiceName = scanner.nextLine();
					System.out.println("Enter new service description:");
					String newDescription = scanner.nextLine();
					System.out.println("Enter new service duration:");
					String newDuration = scanner.nextLine();
					System.out.println("Enter new service price:");
					float newPrice = scanner.nextFloat();
					
					SalonService updatedService = new SalonService(services.get(serviceNumber-1).getServiceId() ,newServiceName, newDescription, newDuration, newPrice);
					
					if(salonServiceServiceImpl.update(updatedService)) {
						System.out.println("Salon service updated successfully.");
					} else {
						System.out.println("Something went wrong! Please try again.");
					}
				}

				System.out.println("***********************************************");
				break;
			case 7:
				System.out.println("***********************************************");
				services = salonServiceServiceImpl.findAll();
				if (services.size() < 1) {
					System.out.println("There are no services found.");
				} else {

					for (int i = 0; i < services.size(); i++) {
						System.out.println((i + 1) + "- " + services.get(i));
					}
					System.out.println("Please choose a service by number between 1 - " + services.size() + ":");
					boolean flag = true;

					int serviceNumber = 0;
					do {

						if (scanner.hasNextInt()) {
							serviceNumber = scanner.nextInt();
							if (serviceNumber >= 1 && serviceNumber <= services.size()) {
								flag = false;
							} else {
								System.out.println(
										"Invalid input. Please pick a user by his number between 1 - " + services.size());
							}
						} else {
							System.out.println("Invalid input. Please try again with a valid number:");
						}
					} while (flag);
					scanner.nextLine();

					if (salonServiceServiceImpl.deleteById(services.get(serviceNumber-1).getServiceId())) {
						System.out.println("Salon service deleted successfully");
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

	/*
	 * Display the menu options
	 */
	private static void printMenu() {
		System.out.println("Available actions:\npress");
		System.out.println("0 - to quit\n" + "1 - See all appointments\n" + "2 - See all users\n"
				+ "3 - Change user role\n" + "4 - See all salon services\n" +
				"5 - Add a new service\n" + 
				"6 - Update a service\n" +
				"7 - Delete a service\n" + "9 - Print available actions");

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
