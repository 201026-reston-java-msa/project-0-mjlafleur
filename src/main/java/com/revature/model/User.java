package com.revature.model;

public class User {

	private int id;
	private String userName;
	private String passWord;
	private String firstName;
	private String lastName;
	private int rolenum;
	
	public User() {
	}
	


	public User(int id, String userName, String passWord, String firstName, String lastName, int rolenum) {
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		this.firstName = firstName;
		this.lastName = lastName;
		this.rolenum = rolenum;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return userName;
	}


	public String getPassword() {
		return passWord;
	}


	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getRolenum() {
		return rolenum;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + userName + ", password=" + passWord + ", firstName=" + firstName
				+ ", lastName=" + lastName  + ", rolenum=" + rolenum + "]";
	}



	
}
