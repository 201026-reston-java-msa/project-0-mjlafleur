package com.revature.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.model.Account;
import com.revature.model.Admin;
import com.revature.model.Employee;
import com.revature.model.User;
import com.revature.service.AccountService;
import com.revature.service.UserService;
import com.revature.util.AccountInfo;

public class AccountController {

	public static AccountService accountServ = new AccountService();
	public static UserService userServ = new UserService();

	public static void openAccount(User newUser) {
		Scanner accountScan = new Scanner(System.in);
		String type;
		double balance;
		double savingBalance;
		while (true) {
			System.out.println(
					"What type of Account would you like to open\n" + "1 - Checking\n" + "2 - Savings\n" + "3 - Both");
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

	private static void closeAccount(User targetUser) {
		// TODO Auto-generated method stub

	}

	public static void accountUser(User targetUser) {
		// gets account info
		List<Account> targetAccounts = AccountService.getAccount(targetUser.getId());
		Scanner accountMenu = new Scanner(System.in);
		AccountInfo.accountInfo(targetUser);
		while (true) {
			if (targetAccounts.get(0).getStatus().equalsIgnoreCase("Pending")) {
				System.out.println("Employee needs to open account for UserID: " + targetUser.getId() + "\n");
				break;
			}
			System.out.println("Current Accounts");
			for (Account a : targetAccounts) {
				System.out.println(a.toString());
			}
			System.out.println("\nWhat would you like to do in your Accounts today?\n" 
			+ "1 - Account Deposit\n"
			+ "2 - Account Withdraw\n" 
			+ "3 - Account Transfer\n" 
			+ "4 - Open Account\n" 
			+ "Q - \"logout\"");
			
			String menu = accountMenu.nextLine();
			if (menu.equalsIgnoreCase("1")) {
				accountServ.deposit(targetAccounts);
			} else if (menu.equalsIgnoreCase("2")) {
				accountServ.withdraw(targetAccounts);
			} else if (menu.equalsIgnoreCase("3")) {
				if (targetAccounts.size() > 1)
					targetAccounts = accountServ.transfer(targetAccounts, targetUser.getId());
				else
					System.out.println("Please open another Account.");
			} else if (menu.equalsIgnoreCase("4")) {
				openAccount(targetUser);
				System.out.println("An Employee or Admin can open this account.");
			} else if (menu.equalsIgnoreCase("admin")) {
				UserController.adminLogin();
			} else if (menu.equalsIgnoreCase("q")) {
				break;
			}
		}
	}


	public static void accountEmployee(Employee employee) {
		AccountInfo.accountInfo(employee);
		Scanner employeeMenu = new Scanner(System.in);
		while (true) {
			
					
			System.out.println("\nWhat would you like to do?\n" 
					+ "1 - All Pending Accounts\n"
					+ "2 - Specific Account\n" 
					+ "3 - User Information\n" 
					+ "Q - \"logout\"");
			String menu = employeeMenu.nextLine();
			if (menu.equalsIgnoreCase("1")) {
				accountServ.pendingAccounts();
			} else if (menu.equalsIgnoreCase("2")) {
				accountServ.specificPendingAccounts();
			} else if (menu.equalsIgnoreCase("3")) {
				// UserInfo
				userServ.userInfo();
				// username,userid,accountid
			} else if (menu.equalsIgnoreCase("q")) {
				break;
			} else if (menu.equalsIgnoreCase("admin")) {
				UserController.adminLogin();
			}
		}

	}

	public static void accountAdmin(Admin admin) {
		AccountInfo.accountInfo(admin);
		Scanner adminMenu = new Scanner(System.in);
		while (true) {
			System.out.println("What would you like to do?\n" 
		+ "1 - All Pending Accounts\n"
		+ "2 - Specific Account\n" 
		+ "3 - User Information\n"
		+ "4 - Edit User/Account Information\n"
		+ "5 - New Employee\n"
		/*+ "6 - Change RoleID"*/
		+ "Q - logout");
			String menu = adminMenu.nextLine();
			if (menu.equalsIgnoreCase("1")) {
				accountServ.pendingAccounts();
			} else if (menu.equalsIgnoreCase("2")) {
				accountServ.specificPendingAccounts();
			} else if (menu.equalsIgnoreCase("3")) {
				userServ.userInfo();
			} else if (menu.equalsIgnoreCase("4")) {
				userServ.editUserAccount();
			} else if (menu.equalsIgnoreCase("5")) {
				UserController.employeeRegister();
			/*}else if (menu.equalsIgnoreCase("6")) {
				userServ.editRoleID();*/
			}else if (menu.equalsIgnoreCase("q")) {
				break;
			} 
		}
		
	}
}
