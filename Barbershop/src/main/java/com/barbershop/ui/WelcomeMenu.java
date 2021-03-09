package com.barbershop.ui;

import java.util.Scanner;

public class WelcomeMenu implements Menu {

	private Menu loginMenu;
	private Menu registrationMenu;
	private Menu nextMenu;
	
	// Constructor
	public WelcomeMenu(Menu loginMenu, Menu registrationMenu) {
		super();
		this.loginMenu = loginMenu;
		this.registrationMenu = registrationMenu;
	}
	
	
	/*
	 * Direct the user to the next menu
	 */
	public Menu advance() {
		return nextMenu;
	}

	/*
	 * Display options to the user.
	 * Ask the user if he/she wants to create an account
	 * or login as an admin or a customer
	 */
	public void displayOptions(Scanner scanner) {

	
		System.out.println("Would you like to login or register?");
		String answer = scanner.nextLine();

		if ("login".equals(answer)) {
			nextMenu = loginMenu;
		} else if ("register".equals(answer)) {
			nextMenu = registrationMenu;
		} else {
			System.out.println("invalid input. Please try again!");
			nextMenu = this;
		}
	}

}
