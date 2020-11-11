package com.revature.dao;

import java.util.List;

import com.revature.model.Account;
import com.revature.model.User;

public interface AccountDao {

	Account openAccount(int id, String type, double balance);
	
	List<Account> getAccount(int id);

	Account deposit(int accountID, Double newBalance);

	Account withdraw(int accountID, Double newBalance);

	List<Account> transfer(int accountWithdrawID, Double newWithdrawBalance, int accountDepositID, Double newDepositBalance, int userId);

	void pendingAccounts();

	String specificPendingAccounts(int iD);

	
	//////ADMIN ACCOUNT EDIT//////
	Account editStatus(String newStatus, int accountID);

	Account editType(String newType, int accountID);

	Account editBalance(double newAccountBalance, int accountID);


}
