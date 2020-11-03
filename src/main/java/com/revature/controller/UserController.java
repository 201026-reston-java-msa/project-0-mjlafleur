package com.revature.controller;

import java.util.Scanner;

import com.revature.model.User;
import com.revature.service.UserService;

public class UserController {
	
	public static UserService userServ = new UserService();
	
	//////////LOGIN///////
	public static void login() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please Enter Your Username.");
		String userName = scan.nextLine();
		System.out.println("Please Enter Your Password.");
		String passWord = scan.nextLine();
		
		System.out.println("Attempting to Login");
		User targetUser = userServ.login(userName,passWord);
	}
	//pushed from Driver//

}
