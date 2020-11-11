package com.revature.model;

public class Employee extends User{

	public Employee() {
		
	}
	
	
	public Employee(User targetUser) {
		super(targetUser.getId(),targetUser.getUsername(),targetUser.getPassword(),targetUser.getFirstName(),targetUser.getLastName(),targetUser.getRolenum());
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
