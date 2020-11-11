package com.revature.service;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revature.dao.AccountDao;
import com.revature.daoimpl.AccountDaoImpl;
import com.revature.model.Account;
import com.revature.model.User;

public class AccountService {
	Logger log = Logger.getLogger(AccountService.class);
	static AccountDao accountDao = new AccountDaoImpl();

	public Account openAccount(int id, String type, double balance) {
		return accountDao.openAccount(id, type, balance);
	}

	public static List<Account> getAccount(int id) {
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
			String choice = "";
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
					choice = withdrawChoice;
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
			depositAccount: while (true) {
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
				Double newDepositBalance = targetAccounts.get(depositIndex).getBalance() + withdrawAmount;
				List<Account> updatedTransfer = accountDao.transfer(accountWithdrawID, newWithdrawBalance,
						accountDepositID, newDepositBalance, userId);
				return updatedTransfer;

			}
			// send to daoimpl
		}
	}

	public void closeAccount(List<Account> targetAccounts) {
		// TODO Auto-generated method stub

	}

	public void pendingAccounts() {
		// pull all pending accounts and change to open.
		accountDao.pendingAccounts();
		System.out.println("All Accounts Open");
	}

	public void specificPendingAccounts() {
		Scanner uID = new Scanner(System.in);
		userIdentity: while (true) {
			String valid = "Invalid UserID";
			System.out.println("What is your UserID?");
			String userID = uID.nextLine();
			try {
				if (Integer.valueOf(userID) >= 1) {
					int iD = Integer.valueOf(userID);
					valid = accountDao.specificPendingAccounts(iD);

					if (valid.equalsIgnoreCase("Invalid UserID")) {
						System.out.println("Invalid UserID");
						continue userIdentity;
					}
					System.out.println(valid);
					break;
				} else {

					log.warn("Not a valid UserID");
					System.out.println("Please Enter a Valid UserID");
					continue userIdentity;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Invalid UserID");
				log.warn("The input was not an integer");
				continue userIdentity;
			}

		}

	}

	public void editAccount(int userId) {
		List<Account> Accounts =  getAccount(userId);
		for(Account a:Accounts) {
			System.out.println(a.toString());
		}
		Scanner accountChoice = new Scanner(System.in);
		System.out.println("Which account needs to be adjusted?");
		String accountId = accountChoice.nextLine();
		int accountID = Integer.valueOf(accountId);
		
		while (true) {
			System.out.println("What needs to be adjusted in this account?\n" 
					+ "1 - Account Balance\n"
					+ "2 - Account Type\n" 
					+ "3 - Account Status\n" 
					+ "Q - Back");
			String menu = accountChoice.nextLine();
			if (menu.equalsIgnoreCase("1")) {
				editBalance(accountID);
			} else if (menu.equalsIgnoreCase("2")) {
				editType(accountID);
			} else if (menu.equalsIgnoreCase("3")) {
				editStatus(accountID);
			} else if (menu.equalsIgnoreCase("q")) {
				break;
			}
		}

	}

	private void editStatus(int accountID) {
		Scanner editStatus = new Scanner(System.in);
		while (true) {
			System.out.println("What is the new status of the Account?\n" 
					+ "1 - OPEN\n"
					+ "2 - CLOSED\n" 
					+ "3 - Pending\n" 
					+ "4 - Denied\n"
					+ "Q - Back");
			String newStatus = editStatus.nextLine();
			if (newStatus.equalsIgnoreCase("1")) {
				newStatus = "OPEN";
				
			} else if (newStatus.equalsIgnoreCase("2")) {
				newStatus = "CLOSED";
				
			} else if (newStatus.equalsIgnoreCase("3")) {
				newStatus = "Pending";
				
			} else if (newStatus.equalsIgnoreCase("4")) {
				newStatus = "Denied";
				
			} else if (newStatus.equalsIgnoreCase("q")) {
				break;
			}
		
		Account editAccount = accountDao.editStatus(newStatus, accountID);
		System.out.println("Account Status updated on logout");
		log.info("Account Status updated for accountID: " + editAccount.getId());
		break;
		}
	}

	private void editType(int accountID) {
		Scanner editType = new Scanner(System.in);
		while (true) {
			System.out.println("What is the new Type of Account?\n" 
					+ "1 - Savings\n"
					+ "2 - Checking\n" 
					+ "Q - Back");
			String newType = editType.nextLine();
			if (newType.equalsIgnoreCase("1")) {
				newType = "Saving";
				
			} else if (newType.equalsIgnoreCase("2")) {
				newType = "Checking";
				
			} else if (newType.equalsIgnoreCase("q")) {
				break;
			}

		
		Account editAccount = accountDao.editType(newType, accountID);
		System.out.println("Account Type updated on logout");
		log.info("Account Type updated for accountID: " + editAccount.getId());
		break;
		}		
	}

	private void editBalance(int accountID) {
		Scanner editBalance = new Scanner(System.in);
		System.out.println("What is the correct balance of the Account?");
		String newBalance = editBalance.nextLine();
		double newAccountBalance = Double.valueOf(newBalance);
		Account editAccount = accountDao.editBalance(newAccountBalance,accountID);
		System.out.println("Account Balance updated on logout");
		log.info("Account Balance updated for accountID: " + editAccount.getId());

	}

}
