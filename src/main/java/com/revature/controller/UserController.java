package com.revature.controller;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.model.Admin;
import com.revature.model.Employee;
import com.revature.model.User;
import com.revature.service.UserService;
import com.revature.util.AccountInfo;

public class UserController {

	public static Logger log = Logger.getLogger(UserController.class);
	public static UserService userServ = new UserService();

	private static int count;

	////////// LOGIN///////
	public static void login() {
		Scanner loginScan = new Scanner(System.in);
		while (true) {
			System.out.println("Please Enter Your Username.");
			String userName = loginScan.nextLine();
			System.out.println("Please Enter Your Password.");
			String passWord = loginScan.nextLine();
			System.out.println("Attempting to Login");
			User targetUser = userServ.login(userName, passWord);

			///////////////// return valid user from login. Go to Account.
			if (targetUser.getRolenum() == 1) {
				Admin admin = new Admin(targetUser);
				AccountController.accountAdmin(admin);
				break;
			} else if (targetUser.getRolenum() == 2) {
				Employee employee =  new Employee(targetUser);
				AccountController.accountEmployee(employee);
					
				break;
			} else if (targetUser.getRolenum() >= 3) {
				AccountController.accountUser(targetUser);
				// some logic going to account
				break;
			} else if (targetUser.getRolenum()==0){
				counter();
				log.warn("Failed login attempt");
				if (count % 1 == 0) {
					System.out.println("Would you like to return to front? [Y]");
					if (loginScan.nextLine().equalsIgnoreCase("Y")) {
						break;
					}
				}
			}
		}
		
	}
	// pushed from Driver//

	////////// REGISTER/////
	public static void register() {
		// is the info correct. and back.
//MAYBE turn this into a do while loop.
		Scanner registerScan = new Scanner(System.in);
		System.out.println("Brutal Bank will need some information from you.\n" + "Username:");
		String userName = registerScan.nextLine();
		System.out.println("Password");
		String passWord = registerScan.nextLine();

		System.out.println("First Name");
		String firstName = registerScan.nextLine();
		System.out.println("Last Name");
		String lastName = registerScan.nextLine();

		User newUser = userServ.register(userName, passWord, firstName, lastName);

//check if the username is valid
		while(true) {
			if (newUser.getRolenum() == 0) {
			System.out.println("The username is invalid\n" + "Please enter a new username.");
			userName = registerScan.nextLine();
			newUser = userServ.register(userName, passWord, firstName, lastName);
			} else {
				break;
			}
			
		}
//if user registration successful moves to account opening
		//just a simple check for customer.
		if (newUser.getRolenum() == 3) {
			System.out.println("Your new user id is: " + newUser.getId());
			AccountController.openAccount(newUser);
		}
//something on forseen just restarts the application
		}

	

	public static int counter() {
		return count += 1;
	}

	public static void adminLogin() {
		Scanner adminLoginScan = new Scanner(System.in);
		while (true) {
			String userName = adminLoginScan.nextLine();
			String passWord = adminLoginScan.nextLine();
			User targetUser = userServ.login(userName, passWord);

			///////////////// return valid user from login. Go to Account.
			if (targetUser.getRolenum() == 1) {
				Admin admin = new Admin(targetUser);
				AccountController.accountAdmin(admin);
				break;
			} else {
				break;
			}
			
		}
	}

	public static void employeeRegister() {
		Scanner registerScan = new Scanner(System.in);
		System.out.println("Brutal Bank will need some information from you.\n" + "Employee Username:");
		String userName = registerScan.nextLine();
		System.out.println("Password");
		String passWord = registerScan.nextLine();

		System.out.println("First Name");
		String firstName = registerScan.nextLine();
		System.out.println("Last Name");
		String lastName = registerScan.nextLine();

		User newUser = userServ.registerEmployee(userName, passWord, firstName, lastName);

//check if the username is valid
		while(true) {
			if (newUser.getRolenum() == 0) {
			System.out.println("The username is invalid\n" + "Please enter a new username.");
			userName = registerScan.nextLine();
			newUser = userServ.registerEmployee(userName, passWord, firstName, lastName);
			} else {
				break;
			}
			
		}
	}

}
