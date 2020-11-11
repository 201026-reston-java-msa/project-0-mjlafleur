package com.revature.service;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.controller.UserController;
import com.revature.dao.UserDao;
import com.revature.daoimpl.UserDaoImpl;
import com.revature.model.Account;
import com.revature.model.User;

public class UserService {
	Logger log = Logger.getLogger(UserService.class);
	public static AccountService accountServ = new AccountService();
	UserDao userDao = new UserDaoImpl();

	public User login(String userName, String passWord) {
		return userDao.login(userName, passWord);
	}

	public User register(String userName, String passWord, String firstName, String lastName) {
		return userDao.register(userName, passWord, firstName, lastName);
	}

	public void userInfo() {
		User targetUser = new User();
		Scanner uID = new Scanner(System.in);
		userIdentity: while (true) {
			System.out.println("What is your UserID?");
			String userID = uID.nextLine();
			if (Integer.valueOf(userID) >= 1) {
				int iD = Integer.valueOf(userID);
				targetUser = userDao.specificUser(iD);
				if (targetUser.getId() == 0) {
					System.out.println("Please use a valid UserID");
					log.warn("User ID not in database.");
					continue userIdentity;
				}
				break;
			} else {
				log.warn("Not a valid UserID");
				System.out.println("Please Enter a Valid UserID");
				continue userIdentity;
			}

			// TODO Auto-generated method stub
		}
		List<Account> targetAccounts = AccountService.getAccount(targetUser.getId());
		System.out.println(targetUser.toString());
		for(Account a: targetAccounts) {
			System.out.println(a.toString());
		}
	}

	public void editUserAccount() {
		Scanner edit = new Scanner(System.in);
		while (true) {
			userInfo();
					
			System.out.println("\nWhat User/Account information would you like to edit?\n" 
					+ "1 - Username\n"
					+ "2 - Password\n" 
					+ "3 - FirstName\n"
					+ "4 - LastName\n"
					+ "5 - Accounts\n"
					+ "Q - Back");
			String menu = edit.nextLine();
			if (menu.equalsIgnoreCase("1")) {
				editUsername();
			} else if (menu.equalsIgnoreCase("2")) {
				editPassword();
			} else if (menu.equalsIgnoreCase("3")) {
				editFirstname();
			} else if (menu.equalsIgnoreCase("4")) {
				editLastname();
			} else if (menu.equalsIgnoreCase("5")) {
				accountServ.editAccount();
			} else if(menu.equalsIgnoreCase("q")) {
					break;
				}
			}
		}

	private void editUsername() {
		User userEdit = new User();
		Scanner editUserName = new Scanner(System.in);
		System.out.println("Please Enter the new Username");
		String newUserName = editUserName.nextLine();
		
		userEdit = userDao.editUsername(newUserName);
		while(true) {
			if (userEdit.getRolenum() == 0) {
			System.out.println("The username is unavailable\n" + "Please enter a new username.");
			newUserName = editUserName.nextLine();
			userEdit = userDao.editUsername(newUserName);
			} else {
				break;
			}
			
		}
		System.out.println("Username updated on logout");
	}
		
	private User editPassword() {
		Scanner editPassWord = new Scanner(System.in);
		System.out.println("Please Enter the new Password");
		String newUserPass = editPassWord.nextLine();
		
		return userDao.editPassword(newUserPass);
	}
	
	private User editFirstname() {
		Scanner editFirstName = new Scanner(System.in);
		System.out.println("Please Enter the new First Name");
		String newUserFirst = editFirstName.nextLine();
		
		return userDao.editFirstname(newUserFirst);
	}
	
	private User editLastname() {
		Scanner editLastName = new Scanner(System.in);
		System.out.println("Please Enter the new Last Name");
		String newUserLast = editLastName.nextLine();
		
		return userDao.editLastname(newUserLast);
	}


	


}
