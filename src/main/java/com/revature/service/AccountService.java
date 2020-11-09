package com.revature.service;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.dao.AccountDao;
import com.revature.daoimpl.AccountDaoImpl;
import com.revature.model.Account;

public class AccountService {
	Logger log = Logger.getLogger(AccountService.class);
	AccountDao accountDao = new AccountDaoImpl();

	public Account openAccount(int id, String type, double balance) {
		return accountDao.openAccount(id, type, balance);
	}

	public List<Account> getAccount(int id) {
		return accountDao.getAccount(id);
	}

//////////////////////DEPOSIT///////////////////
	public List<Account> deposit(List<Account> targetAccounts) {
		// decide which account here and then push to accountdaoimpl
		Scanner depo = new Scanner(System.in);

		int index = -1;
		int accountID = -1;
		accountList: while (true) {
			//////////////////////// HANDLES MULTIPLE ACCOUNTS////////////////
			if (targetAccounts.size() > 1) {
				System.out.println("Which account would you like to deposit into? [account number]");
				// output all account.
				for (Account account : targetAccounts) {
					System.out.println(account.getType() + " Account: " + account.getId());
				}
				// take input and check if matches to account ID
				String accountChoice = depo.nextLine();
				for (Account a : targetAccounts) {
					Integer i = a.getId();
					String j = i.toString();
					if (j.equalsIgnoreCase(accountChoice)) {
						accountID = Integer.valueOf(accountChoice);
						index = targetAccounts.indexOf(a);
						break;
					}
				}
				// if account id doesn't match index with stay negative.
				if (index == -1) {
					System.out.println("Invalid Account ID");
					continue accountList;
				}
			}
			// get amount to deposit
			Account updatedAccount;
			while (true) {
				System.out.println("How much would you like to deposit?");
				String amount = depo.nextLine();
				if (Double.valueOf(amount) > 0) {
					Double newBalance = targetAccounts.get(index).getBalance() + Double.valueOf(amount);
					updatedAccount = accountDao.deposit(accountID, newBalance);
					break;
				} else {
					log.warn("System input was not numeric or it was not gerater than 0.");
					System.out.println("the amount entered was not more than $0.00");
				}
			}
			// send to accountDaoImpl
			// insert and update account
			targetAccounts.set(index, updatedAccount);
			// return new account balance
			break;
			// insert new account balance into targetAccount(account)
			// return to page with new accountInfo.

			// if (targetAccounts.contains(o))
//			
//i think all i need is AccountID and ammount.
			// start here and think about what you want to do. pseudo!!

		}

		return targetAccounts;
	}

///////////////////////WITHDRAW////////////////
	public List<Account> withdraw(List<Account> targetAccounts) {
		Scanner with = new Scanner(System.in);

		int index = -1;
		int accountID = -1;
		accountList: while (true) {
			//////////////////////// HANDLES MULTIPLE ACCOUNTS////////////////
			if (targetAccounts.size() > 1) {
				System.out.println("Which account would you like to withdraw from? [account number]");
				// output all account.
				for (Account account : targetAccounts) {
					System.out.println(account.getType() + " Account: " + account.getId());
				}
				// take input and check if matches to account ID
				String accountChoice = with.nextLine();
				for (Account a : targetAccounts) {
					Integer i = a.getId();
					String j = i.toString();
					if (j.equalsIgnoreCase(accountChoice)) {
						accountID = Integer.valueOf(accountChoice);
						index = targetAccounts.indexOf(a);
						break;
					}
				}
				// if account id doesn't match index with stay negative.
				if (index == -1) {
					System.out.println("Invalid Account ID");
					continue accountList;
				}
			}
			// get amount to withdraw
			Account updatedAccount;
			withdrawAmount: while (true) {
				System.out.println("How much would you like to withdraw?");
				String amount = with.nextLine();
				if (Double.valueOf(amount) > 0) {
					if (Double.valueOf(amount) >= targetAccounts.get(index).getBalance()) {
						System.out.println("Overdraft Protection");
						log.warn("withdraw was greater than balance.");
						continue withdrawAmount;
					}

					Double newBalance = targetAccounts.get(index).getBalance() - Double.valueOf(amount);
					updatedAccount = accountDao.withdraw(accountID, newBalance);
					break;
				} else {
					log.warn("System input was not numeric or it was not gerater than 0.");
					System.out.println("the amount entered was not more than $0.00");
				}
			}
			// send to accountDaoImpl
			// insert and update account
			targetAccounts.set(index, updatedAccount);
			// return new account balance
			break;
			// insert new account balance into targetAccount(account)
			// return to page with new accountInfo.

			// if (targetAccounts.contains(o))
//			
//i think all i need is AccountID and ammount.
			// start here and think about what you want to do. pseudo!!

		}

		return targetAccounts;

	}

//////////////////////TRAnSFER/////////////////
	public List<Account> transfer(List<Account> targetAccounts, int userId) {
		Scanner tran = new Scanner(System.in);

		
		
		withdrawAccount:
		// account to transfer from
		while (true) {
			String choice ="";
			int withdrawIndex = -1;
			int accountWithdrawID = -1;
			double withdrawAmount = -1;
			System.out.println("Which account would you like to withdraw from? [account number]");
			for (Account account : targetAccounts) {
				System.out.println(account.getType() + " Account: " + account.getId());
			}
			// take input and check if matches to account ID
			String withdrawChoice = tran.nextLine();
			for (Account a : targetAccounts) {
				Integer i = a.getId();
				String j = i.toString();
				if (j.equalsIgnoreCase(withdrawChoice)) {
					choice=withdrawChoice;
					accountWithdrawID = Integer.valueOf(withdrawChoice);
					withdrawIndex = targetAccounts.indexOf(a);
					break;
				}
			}
			// amount to transfer
		Account updatedAccount;
		Double newWithdrawBalance;
		withdrawAmount: while (true) {
			System.out.println("How much would you like to withdraw?");
			String amount = tran.nextLine();
			if (Double.valueOf(amount) > 0) {
				if (Double.valueOf(amount) >= targetAccounts.get(withdrawIndex).getBalance()) {
					System.out.println("Overdraft Protection");
					log.warn("withdraw was greater than current balance.");
					continue withdrawAmount;
				}
				withdrawAmount = Double.valueOf(amount);
				newWithdrawBalance = targetAccounts.get(withdrawIndex).getBalance() - withdrawAmount;
				
				break;
			} else {
				log.warn("System input was not numeric or it was not gerater than 0.");
				System.out.println("the amount entered was not more than $0.00");
			}
		}
		int depositIndex = -1;
		int accountDepositID = -1;
		// account to transfer to
		depositAccount:
		while(true) {
		if (targetAccounts.size() > 1) {
			System.out.println("Which account would you like to deposit into? [account number]");
			// output all account.
			for (Account account : targetAccounts) {
				System.out.println(account.getType() + " Account: " + account.getId());
			}
			// take input and check if matches to account ID
			String depositChoice = tran.nextLine();
			for (Account a : targetAccounts) {
				Integer i = a.getId();
				String j = i.toString();
				if (choice.equalsIgnoreCase(depositChoice)) {
					System.out.println("You can't deposit into the same account that your withdrawing from.");
					continue depositAccount;
				} else if (j.equalsIgnoreCase(depositChoice)) {
					accountDepositID = Integer.valueOf(depositChoice);
					depositIndex = targetAccounts.indexOf(a);
					break;
				}
			}
			// if account id doesn't match index with stay negative.
			if (depositIndex == -1) {
				System.out.println("Invalid Account ID");
				continue depositAccount;
			}
		}
		Double newDepositBalance = targetAccounts.get(depositIndex).getBalance()+withdrawAmount;
		List<Account> updatedTransfer = accountDao.transfer(accountWithdrawID,newWithdrawBalance,accountDepositID,newDepositBalance,userId);
		return updatedTransfer;
		
		}
		// send to daoimpl
		}
	}

	public void closeAccount(List<Account> targetAccounts) {
		// TODO Auto-generated method stub

	}

}
