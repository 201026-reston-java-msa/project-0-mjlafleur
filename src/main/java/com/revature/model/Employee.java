package com.revature.model;

public class Employee extends User{

	public Employee() {
		
		// TODO Auto-generated constructor stub
	}
	
	public Employee(User targetUser) {

	}

	public boolean approveAccounts() {
		
		return false;
	}
	
	public boolean denyAccounts() {
		
			return false;
	}

	public User specificAccount(User targetUser) {
		
		return targetUser;
	}
	
}
