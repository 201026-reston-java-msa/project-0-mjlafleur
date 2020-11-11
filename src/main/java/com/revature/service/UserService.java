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
	
	public User registerEmployee(String userName, String passWord, String firstName, String lastName) {
		return userDao.registerEmployee(userName, passWord, firstName, lastName);
	}

	public User userInfo() {
		User targetUser = new User();
		Scanner uID = new Scanner(System.in);
		userIdentity: while (true) {
			System.out.println("What is the UserID?");
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

		}
		List<Account> targetAccounts = AccountService.getAccount(targetUser.getId());
		System.out.println(targetUser.toString());
		for(Account a: targetAccounts) {
			System.out.println(a.toString());
		}
	return targetUser;
	}
	

	public void editUserAccount() {
		Scanner edit = new Scanner(System.in);
		User targetUser = userInfo();
		while (true) {
					
			System.out.println("\nWhat User/Account information would you like to edit?\n" 
					+ "1 - Username\n"
					+ "2 - Password\n" 
					+ "3 - FirstName\n"
					+ "4 - LastName\n"
					+ "5 - Accounts\n"
					+ "Q - Back");
			String menu = edit.nextLine();
			if (menu.equalsIgnoreCase("1")) {
				editUsername(targetUser.getId());
			} else if (menu.equalsIgnoreCase("2")) {
				editPassword(targetUser.getId());
			} else if (menu.equalsIgnoreCase("3")) {
				editFirstname(targetUser.getId());
			} else if (menu.equalsIgnoreCase("4")) {
				editLastname(targetUser.getId());
			} else if (menu.equalsIgnoreCase("5")) {
				accountServ.editAccount(targetUser.getId());
			} else if(menu.equalsIgnoreCase("q")) {
					break;
				}
			}
		}

	private void editUsername(int userId) {
		User userEdit = new User();
		Scanner editUserName = new Scanner(System.in);
		System.out.println("Please Enter the new Username");
		String newUserName = editUserName.nextLine();
		
		userEdit = userDao.editUsername(newUserName,userId);
		while(true) {
			if (userEdit.getRolenum() == 0) {
			System.out.println("The username is unavailable\n" + "Please enter a new username.");
			newUserName = editUserName.nextLine();
			userEdit = userDao.editUsername(newUserName, userId);
			} else {
				break;
			}
			
		}
		System.out.println("Username updated on logout");
		log.info("Username updated for userID: " +userEdit.getId()+" to "+ userEdit.getUsername());
	}
		
	private void editPassword(int userId) {
		Scanner editPassWord = new Scanner(System.in);
		System.out.println("Please Enter the new Password");
		String newUserPass = editPassWord.nextLine();
		
		User userEdit = userDao.editPassword(newUserPass, userId);
		System.out.println("Password updated on logout");
		log.info("Password updated for userID: " +userEdit.getId()+" to "+ userEdit.getPassword());
	}
	
	private void editFirstname(int userId) {
		Scanner editFirstName = new Scanner(System.in);
		System.out.println("Please Enter the new First Name");
		String newUserFirst = editFirstName.nextLine();
		
		User userEdit = userDao.editFirstname(newUserFirst, userId);
		System.out.println("FirstName updated on logout");
		log.info("FirstName updated for userID: " +userEdit.getId() +" to "+ userEdit.getFirstName());
		
	}
	
	private void editLastname(int userId) {
		Scanner editLastName = new Scanner(System.in);
		System.out.println("Please Enter the new Last Name");
		String newUserLast = editLastName.nextLine();
		
		User userEdit = userDao.editLastname(newUserLast, userId);
		System.out.println("LastName updated on logout");
		log.info("LastName updated for userID: " +userEdit.getId()+" to "+ userEdit.getLastName());
	}

	


	


}
