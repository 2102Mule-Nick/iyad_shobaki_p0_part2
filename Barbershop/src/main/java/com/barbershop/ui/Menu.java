package com.barbershop.ui;

import java.util.Scanner;

public interface Menu {
	
//	public Menu advance(User user, UserServiceImpl userServiceImpl, Menu[] menus);
	public Menu advance();
	
	public void displayOptions(Scanner scanner);
}
