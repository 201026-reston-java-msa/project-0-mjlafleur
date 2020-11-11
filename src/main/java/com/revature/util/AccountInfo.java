package com.revature.util;

import com.revature.model.User;

import java.util.List;

import com.revature.model.Account;
import com.revature.model.Admin;
import com.revature.model.Employee;

public class AccountInfo {
	
	public static void accountInfo(User targetUser) {	
	System.out.println("\nWelcome "+targetUser.getFirstName()+" "+targetUser.getLastName()+"\n"
			+"User ID: " + targetUser.getId()+"\n");
	}
	
	public static void accountInfo(Employee employee) {	
		System.out.println("Welcome "+   employee.getFirstName() +" "+ employee.getLastName()+"\n"
				+"Employee ID: " + employee.getId()+"\n");	
		}
	
	public static void accountInfo(Admin admin) {	
		System.out.println("Welcome "+admin.getFirstName() +" "+ admin.getLastName()+"\n"
				+"Admin: " + admin.getId()+"\n");		
		}
}
