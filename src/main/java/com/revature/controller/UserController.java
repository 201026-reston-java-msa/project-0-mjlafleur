package com.revature.controller;

import java.util.Scanner;

import com.revature.model.Account;
import com.revature.model.User;
import com.revature.service.AccountService;
import com.revature.service.UserService;
import com.revature.util.AccountInfo;
import com.revature.util.Welcome;

public class UserController {
	
	public static UserService userServ = new UserService();
	public static AccountService accountServ = new AccountService();
	
	//////////LOGIN///////
	public static void login() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please Enter Your Username.");
		String userName = scan.nextLine();
		System.out.println("Please Enter Your Password.");
		String passWord = scan.nextLine();
		
		System.out.println("Attempting to Login");
		User targetUser = userServ.login(userName,passWord);
		
		/////////////////return valid user from login. Go to Account.
		if(targetUser.getRolenum() != 0) {
			//some logic going to account
			AccountInfo.accountInfo(targetUser);
		}else if (targetUser.getRolenum()==0) {
			login();
		}else {
			Welcome.welcome();
		}
	}
	//pushed from Driver//
	
	//////////REGISTER/////
	public static void register() {
		// is the info correct. and back.
		Scanner scan = new Scanner(System.in);
		System.out.println("Brutal Bank will need some information from you.\n"
				+"Username:"); 
		String userName = scan.nextLine();
		System.out.println("Password");
		String passWord = scan.nextLine();
		
		System.out.println("First Name");
		String firstName = scan.nextLine();
		System.out.println("Last Name");
		String lastName = scan.nextLine();
		
		User newUser = userServ.register(userName,passWord,firstName,lastName);
		System.out.println(newUser.getRolenum());
		
//check if the username is valid
		if (newUser.getRolenum()==0) {
			System.out.println("The username is invalid\n"
					+ "Please enter a new username.");
			userName = scan.nextLine();
			newUser = userServ.register(userName,passWord,firstName,lastName);
			
//if user registration successful moves to account opening
		} else if (newUser.getRolenum()==3){
			String type;
			double balance;
			double savingBalance;
			while(true) {
		System.out.println("What type of Account would you like to open\n"
				+"1 - Checking\n"
				+"2 - Savings\n"
				+"3 - Both");
		type = scan.nextLine();
		if(type.equalsIgnoreCase("1") || type.equalsIgnoreCase("2")
				|| type.equalsIgnoreCase("Checking") || type.equalsIgnoreCase("Savings")) {
			if(type == "1") {
				type = "Checking";
			} else {
					type = "Savings";
			}
			System.out.println("How much would you like to deposit");
			balance = scan.nextDouble();
			Account newAccount = accountServ.openAccount(newUser.getId(),type,balance);
			break;
		} else if (type.equalsIgnoreCase("3")||type.equalsIgnoreCase("Both")) {
			System.out.println("How much would you like to deposit in your checking account?");
			balance = scan.nextDouble();
			System.out.println("How much would you like to deposit in your savings account?");
			savingBalance = scan.nextDouble();
			type = "Checking";
			Account newAccount = accountServ.openAccount(newUser.getId(),type,balance);
			String type2 = "Saving";
			Account newSavingAccount = accountServ.openAccount(newUser.getId(),type2,savingBalance);
			break;
		} else {
			System.out.println("Not a valid account type");
		}
			}
			
		
//something on forseen just restarts the application
		} else {
			Welcome.welcome();
		}
			
		
		
	}
	

}
