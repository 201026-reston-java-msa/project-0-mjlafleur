package com.revature.util;

import java.util.Scanner;

import com.revature.controller.UserController;

public class Welcome {
	
	public static void welcome() {
		Scanner mainInput = new Scanner(System.in);
		String userInput;
		System.out.println("Welcome to BrutalBank!");
		while(true) {
				System.out.println("What would you like to do?"
					+ "\n1 - Login"
					+ "\n2 - Register"
					+ "\nQ - Quit"
					+ "\n");
				userInput = mainInput.next();
			if(userInput.equalsIgnoreCase("1")|| userInput.equalsIgnoreCase("login")) {
				
					UserController.login();
					
			} else if (userInput.equalsIgnoreCase("2")|| userInput.equalsIgnoreCase("register")) {
				System.out.println("Let's Register Your Account!");
				
					UserController.register();
					
			} else if (userInput.equalsIgnoreCase("Q")|| userInput.equalsIgnoreCase("quit")) {
				
				System.out.println("Goodbye!");
				break;
			}
		}
		mainInput.close();
	}





}
