package com.revature.dao;

import com.revature.model.User;

public interface UserDao {

	User login(String userName, String passWord);
	
	User register(String userName, String passWord, String firstName, String lastName);

	User registerEmployee(String userName, String passWord, String firstName, String lastName);

	User specificUser(int iD);

	
//////ADMIN USER EDIT/////
	User editUsername(String newUserName, int userId);

	User editPassword(String newUserPass, int userId);

	User editFirstname(String newUserFirst, int userId);

	User editLastname(String newUserLast, int userId);

}
