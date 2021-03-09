package com.barbershop.ui;

import java.util.Scanner;

import com.barbershop.pojo.User;
import com.barbershop.service.UserServiceImpl;

public class LoginMenu implements Menu {

	private User user;
	private UserServiceImpl userServiceImpl;
	private Menu customerMenu;
	private Menu managerMenu;
	private Menu nextMenu;

	public LoginMenu(User user, UserServiceImpl userServiceImpl, Menu customerMenu, Menu managerMenu) {
		super();
		this.user = user;
		this.userServiceImpl = userServiceImpl;
		this.customerMenu = customerMenu;
		this.managerMenu = managerMenu;
	}

	public Menu advance() {
		return nextMenu;
	}

	public void displayOptions(Scanner scanner) {

		System.out.println("\t------------    -------------------------------------     ------------");
		System.out.println("\t------------         Welcome To The Login Menu            ------------");
		System.out.println("\t------------    -------------------------------------     ------------");

		// If admin want to login as customer he can
		System.out.println("Enter 'admin' to login as admin or press enter to continue.");
		String answer = scanner.nextLine();

		System.out.println("Please Enter Username");
		String username = scanner.nextLine();
		System.out.println("Please Enter Password");
		String password = scanner.nextLine();
		
		if (userServiceImpl.getUserInfo(username, password) != null) {
			this.user = userServiceImpl.getUserInfo(username, password);
			if (answer.equals("admin") && this.user.getRole().equals("Manager")) {
				nextMenu = managerMenu;
			} else if(answer.equals("admin") && this.user.getRole().equals("Customer")) {
				System.out.println("You are not an admin. Please try again");
				nextMenu = this;
			}else {				
				nextMenu = customerMenu;
			}
		}else {
			System.out.println("Invalid username or password. Pleas try again!");
			clearUserInfo(this.user);
			nextMenu = this;
		}

	}

	private void clearUserInfo(User user) {
		user.setFirstName("");
		user.setLastName("");
		user.setEmailAddress("");
		user.setPhoneNumber("");
		user.setPassword("");
	}

}
