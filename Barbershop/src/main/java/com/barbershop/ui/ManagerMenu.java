package com.barbershop.ui;

import java.util.Scanner;

import com.barbershop.pojo.User;
import com.barbershop.service.UserServiceImpl;

public class ManagerMenu implements Menu {

	private User user;
	private UserServiceImpl userServiceImpl;
	private Menu welcomeMenu;
	private Menu nextMenu;
	
	
	public ManagerMenu(User user, UserServiceImpl userServiceImpl, Menu welcomeMenu) {
		super();
		this.user = user;
		this.userServiceImpl = userServiceImpl;
		this.welcomeMenu = welcomeMenu;
	}

	public Menu advance() {
		// TODO Auto-generated method stub
		return nextMenu;
	}

	public void displayOptions(Scanner scanner) {
		
		System.out.println("\t------------    -------------------------------------     ------------");
		System.out.println("\t------------      Welcome, " + this.user.getFirstName() + " To The Manager Menu            ------------");
		System.out.println("\t------------    -------------------------------------     ------------");


	}

}
