package com.revature.dao;

import com.revature.model.User;

public interface UserDao {

	User login(String userName, String passWord);
	
	User register(String userName, String passWord, String firstName, String lastName);

	User specificUser(int iD);

	
//////ADMIN USER EDIT/////
	User editUsername(String newUserName);

	User editPassword(String newUserPass);

	User editFirstname(String newUserFirst);

	User editLastname(String newUserLast);
}
