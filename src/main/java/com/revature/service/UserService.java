package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.daoimpl.UserDaoImpl;
import com.revature.model.User;

public class UserService {
	
UserDao userDao = new UserDaoImpl();
	
	public User login(String username, String password) {
		System.out.println("Service");
		return userDao.login(username, password);
	}

}
