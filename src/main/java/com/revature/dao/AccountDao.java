package com.revature.dao;

import com.revature.model.Account;

public interface AccountDao {

	Account openAccount(int id, String type, double balance);

}
