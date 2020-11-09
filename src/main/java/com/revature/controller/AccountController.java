package com.revature.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.model.Account;
import com.revature.model.User;
import com.revature.service.AccountService;
import com.revature.util.AccountInfo;

public class AccountController {
	
	
	
	public static AccountService accountServ = new AccountService();
	
	public static void openAccount(User newUser) {
		Scanner accountScan = new Scanner(System.in);
		 String type;
		 double balance;
		 double savingBalance;
		while (true) {
			System.out.println("What type of Account would you like to open\n" + "1 - Checking\n" + "2 - Savings\n"
					+ "3 - Both");
			type = accountScan.nextLine();
			if (type.equalsIgnoreCase("1") || type.equalsIgnoreCase("2") || type.equalsIgnoreCase("checking")
					|| type.equalsIgnoreCase("savings")) {
				if (type.equalsIgnoreCase("1") || type.equalsIgnoreCase("checking")) {
					type = "Checking";
				} else {
					type = "Savings";
				}
				System.out.println("How much would you like to deposit");
				balance = accountScan.nextDouble();
				Account newAccount = accountServ.openAccount(newUser.getId(), type, balance);
				break;
			} else if (type.equalsIgnoreCase("3") || type.equalsIgnoreCase("Both")) {
				System.out.println("How much would you like to deposit in your checking account?");
				balance = accountScan.nextDouble();
				System.out.println("How much would you like to deposit in your savings account?");
				savingBalance = accountScan.nextDouble();
				type = "Checking";
				Account newAccount = accountServ.openAccount(newUser.getId(), type, balance);
				newAccount.toString();
				String type2 = "Saving";
				Account newSavingAccount = accountServ.openAccount(newUser.getId(), type2, savingBalance);
				newSavingAccount.toString();
				break;
			} else {
				System.out.println("Not a valid account type");
			}
		}
		System.out.println("Accounts Pending Employee Approval. Please, Login to Check Status.");
	}

	public static void validateAccounts() {
		
	}

	public static void accountUser(User targetUser) {
		//gets account info
		List<Account> targetAccounts = accountServ.getAccount(targetUser.getId());
		Scanner accountMenu = new Scanner(System.in);
		AccountInfo.accountInfo(targetUser, targetAccounts);
		while(true) {
			for(Account a: targetAccounts) {
				System.out.println(a.toString());
			}
			System.out.println("\nWhat would you like to do in your Accounts today?\n"
					+ "1 - Account Deposit\n"
					+ "2 - Account Withdraw\n"
					+ "3 - Account Transfer\n"
					+ "4 - Open Account\n"
					+ "5 - Close Account\n"
					+ "Q - \"logout\"");
			String menu = accountMenu.nextLine();
			if(menu.equalsIgnoreCase("1")){
				accountServ.deposit(targetAccounts); 
			} else if (menu.equalsIgnoreCase("2")) {
				accountServ.withdraw(targetAccounts);
			} else if (menu.equalsIgnoreCase("3")) {
				accountServ.transfer(targetAccounts);
			} else if (menu.equalsIgnoreCase("4")) {
				accountServ.closeAccount(targetAccounts);
			} else if (menu.equalsIgnoreCase("5")) {
				accountServ.closeAccount(targetAccounts);
			}else if (menu.equalsIgnoreCase("q")) {
				break;
			}
		}
				
		
	}

}
