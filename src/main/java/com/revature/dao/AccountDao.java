package com.revature.dao;

import java.util.List;

import com.revature.model.Account;

public interface AccountDao {

	Account openAccount(int id, String type, double balance);
	
	List<Account> getAccount(int id);

	Account deposit(String choice, String amount);


}
