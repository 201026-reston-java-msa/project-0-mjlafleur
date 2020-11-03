package com.revature.dao;

import com.revature.model.User;

public interface UserDao {

	User login(String username, String password);

}
