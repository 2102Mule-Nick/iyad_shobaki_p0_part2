package com.barbershop.ui;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.barbershop.pojo.User;
import com.barbershop.service.UserServiceImpl;

public class RegistrationMenu implements Menu {

	private User user;
	private UserServiceImpl userServiceImpl;
	private Menu loginMenu;
	private Menu nextMenu;

	// Constructor
	public RegistrationMenu(User user, UserServiceImpl userServiceImpl, Menu logiMenu) {
		super();
		this.user = user;
		this.userServiceImpl = userServiceImpl;
		this.loginMenu = logiMenu;
	}

	/*
	 * Direct the user to the next menu
	 */
	public Menu advance() {
		return nextMenu;
	}

	/*
	 * Display options to the user. Get the user input (username and password) and
	 * try to register as new user. Ask the user if he/she wants to proceed with the
	 * registration process or go back to the login menu
	 */

	public void displayOptions(Scanner scanner) {

		System.out.println("\t------------    -------------------------------------     ------------");
		System.out.println("\t------------       Welcome To The Registration Menu       ------------");
		System.out.println("\t------------    -------------------------------------     ------------");

		System.out.println("Please enter your first name:");
		String firstName = validateName(scanner);
		System.out.println("Please enter your last name:");
		String lastName = validateName(scanner);
		System.out.println("Please enter a valid username/emailAddress (example@example.com):");
		String username = validateUsername(scanner);
		System.out.println("Please enter a valid US phone number (333-444-5555):");
		String phoneNumber = validatePhoneNumber(scanner);
		System.out.println("Please enter a valid password"
				+ "\n(1 lowercase, 1 digit, 1 special character, 1 capital letter, 6 letters min - 16 letters max):");
		String password = validatePassword(scanner);

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmailAddress(username);
		user.setPhoneNumber(phoneNumber);
		user.setPassword(password);
		
		if (userServiceImpl.create(user)) {
			System.out.println("Your account created successfully!");
			nextMenu = loginMenu;
		} else {
			clearUserInfo(user);
			System.out.println(
					"Something went wrong! Please press Enter to try again or" + "'login' to go to the login menu");
			String answer = scanner.nextLine();

			if ("login".equals(answer)) {
				nextMenu = loginMenu;
			} else {
				nextMenu = this;
			}

		}
	}

	private void clearUserInfo(User user) {
		user.setFirstName("");
		user.setLastName("");
		user.setEmailAddress("");
		user.setPhoneNumber("");
		user.setPassword("");
	}

	/*
	 * ((?=.*[a-z])(?=.*d)(?=.*[@#$%])(?=.*[A-Z]).{6,16})
	 * 
	 * (?=.*[a-z]) : This matches the presence of at least one lowercase letter.
	 * (?=.*d) : This matches the presence of at least one digit i.e. 0-9.
	 * (?=.*[@#$%]) : This matches the presence of at least one special character.
	 * ((?=.*[A-Z]) : This matches the presence of at least one capital letter.
	 * {6,16} : This limits the length of password from minimum 6 letters to maximum
	 * 16 letters.
	 * 
	 * ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$
	 * 
	 * ^ # start-of-string (?=.*[0-9]) # a digit must occur at least once
	 * (?=.*[a-z]) # a lower case letter must occur at least once (?=.*[A-Z]) # an
	 * upper case letter must occur at least once (?=.*[@#$%^&+=]) # a special
	 * character must occur at least once (?=\S+$) # no whitespace allowed in the
	 * entire string .{8,} # anything, at least eight places though $ #
	 * end-of-string
	 * 
	 */

	private String validatePassword(Scanner scanner) {

		String password = null;
		do {

			password = scanner.nextLine();
//			String regex = "((?=.*[a-z])(?=.*d)(?=.*[@#$%])(?=.*[A-Z]).{6,16})";
			String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,16}$";
//			String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{6,16}$";
//			String regex = "(?=.*[a-z])(?=.*\\\\d)(?=.*[A-Z])(?=.*[@#$%!]).{6,16}";
			//String regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,16})";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(password);
			if (matcher.matches()) {
				System.out.println("Verify password:");
				String verifyPassword = scanner.nextLine();
				if (password.equals(verifyPassword)) {
					break;
				}
			} else {
				System.out.println("Invalid input. Please try again:");
			}

		} while (true);

		return password;
	}

	private String validatePhoneNumber(Scanner scanner) {

		String phoneNumber = null;
		do {

			phoneNumber = scanner.nextLine();

			String regex = "^\\(?([0-9]{3})\\)?[-.●]?([0-9]{3})[-.●]?([0-9]{4})$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(phoneNumber);
			if (matcher.matches()) {
				break;
			} else {
				System.out.println("Invalid input. Please try again:");
			}

		} while (true);

		return phoneNumber;

	}

	private String validateName(Scanner scanner) {
		String name = null;
		do {

			name = scanner.nextLine();

//			String regex = "/^[a-z ,.'-]+$/i";
			String regex = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(name);
			if (matcher.matches() && name.length() <= 40) {
				break;
			} else {
				System.out.println("Invalid input. Please try again:");
			}

		} while (true);

		return name;
	}

	private String validateUsername(Scanner scanner) {

		String username = null;
		do {

			username = scanner.nextLine();

			String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(username);
			if (matcher.matches() && !userServiceImpl.isExist(username)) {
				break;
			} else {
				System.out.println("Invalid input or username already exist.\nPlease try again (example@example.com):");
			}

		} while (true);

		return username;
	}
}
