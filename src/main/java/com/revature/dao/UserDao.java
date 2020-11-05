package com.revature.dao;

import com.revature.model.User;

public interface UserDao {

	User login(String userName, String passWord);
	
	User register(String userName, String passWord, String firstName, String lastName);
}
