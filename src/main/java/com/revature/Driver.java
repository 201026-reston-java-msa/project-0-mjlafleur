package com.revature;

import java.util.Scanner;

import com.revature.controller.UserController;

public class Driver {
	
	/*
	 * This is a placeholder file created simple to maintain the Maven file structure on GitHub. 
	 * You may immediately delete this file from your project, you will not require it. 
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String userInput;
		System.out.println("Welcome to BrutalBank!");
		while(true) {
				System.out.println("What would you like to do?"
					+ "\n1 - Login"
					+ "\n2 - Register"
					+ "\nQ - Quit"
					+ "\n");
		userInput = scan.nextLine().toLowerCase();
			if(userInput.equalsIgnoreCase("1")|| userInput.equalsIgnoreCase("login")) {
				
					UserController.login();
					
			} else if (userInput.equalsIgnoreCase("2")|| userInput.equalsIgnoreCase("register")) {
				System.out.println("Let's Register Your Account!");
				
			} else if (userInput.equalsIgnoreCase("Q")|| userInput.equalsIgnoreCase("quit")) {
				System.out.println("Goodbye!");
				break;
			}else {
				System.out.println("Invalid option please try again.");
				
			}
		}
		scan.close();
	}

		
	//Main page
	/*
	 * this is an infinite loop with switch case to move through all options
	 *  1 - login
	 *  2 - register
	 *  3 - employee login
	 *  
	 */
	//Employee login
		/*
		 * 1 - employee
		 *maybe secret admin login.
		 * 2 - admin
		 */
	
	
	
	
	
	//dao inteface
	
	
	//daoimplUSerementation
	/*
	 * Login
	 * This will establish connection for user.
	 * 
	 * Register
	 * this will allow registration
	 * 		and apply
	 * 
	 * Information
	 * 		pulls all user info
	 * 
	 */
	
	//daoImplAccount
	/*
	 * AccountInfo
	 * all accounts and balances
	 * 	
	 * withdraw
	 * 
	 * deposit
	 * 
	 * transfer
	 */
}
