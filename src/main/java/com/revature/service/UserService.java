package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.daoimpl.UserDaoImpl;
import com.revature.model.User;

public class UserService {
	
	UserDao userDao = new UserDaoImpl();
	
	public User login(String userName, String passWord) {
		System.out.println("Service");
		return userDao.login(userName, passWord);
	}

	public User register(String userName, String passWord, String firstName, String lastName) {
		
		return userDao.register(userName, passWord, firstName, lastName);
	}

}
