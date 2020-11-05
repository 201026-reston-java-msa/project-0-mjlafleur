package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.daoimpl.AccountDaoImpl;
import com.revature.model.Account;

public class AccountService {

	AccountDao accountDao = new AccountDaoImpl();
	
	public Account openAccount(int id, String type, double balance) {
		return accountDao.openAccount(id,type,balance);
	}

	

}
