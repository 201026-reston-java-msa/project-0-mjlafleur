package com.revature.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.dao.AccountDao;
import com.revature.model.Account;
import com.revature.util.ConnectionUtil;

public class AccountDaoImpl implements AccountDao{

	public static Logger log = Logger.getLogger(AccountDaoImpl.class);
	@Override
	public Account openAccount(int id, String type, double balance) {
		Account newAccount = new Account();
		//Userid, checking, amount;
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			//get userinfo with id
			//userid is id in openAccount
			
//create account 
			String addAccount = "INSERT INTO AccountInformation (account_balance,account_type,account_status)" 
					+" VALUES (?,?,?)";
			PreparedStatement createAccount = conn.prepareStatement(addAccount);
			createAccount.setDouble(1, balance);
			createAccount.setString(2, type);
			createAccount.setString(3, "Pending");
			createAccount.executeUpdate();
//get new account id.
			String accountId = "SELECT max(account_id) FROM accountinformation";
			PreparedStatement getAccountId = conn.prepareStatement(accountId);
			ResultSet resultAccountId = getAccountId.executeQuery();
			resultAccountId.next();
				int aid = resultAccountId.getInt(1);
//create foreign key.
			String fKConnect = "INSERT INTO user_account_junction VALUES (?,?)";
			PreparedStatement createFK = conn.prepareStatement(fKConnect);
			createFK.setInt(1,id);
			createFK.setInt(2,aid);
			createFK.executeUpdate();
//return new account id with old info.
			log.info("Account creation and user connection successful");
			newAccount = new Account(aid,type,balance);
			return newAccount;
		} catch (SQLException sqle) {
			log.warn("Connection issues with database.", sqle);
			sqle.printStackTrace();
		}
		
		return newAccount;
	}
	@Override
	public List<Account> getAccount(int id) {
		List<Account> targetAccounts = new ArrayList<Account>();
		Account bankAccount = new Account();
		try(Connection conn = ConnectionUtil.getConnection()) {
			String accounts = "SELECT C.account_id, C.account_balance, C.account_type, C.account_status \r\n" + 
					"FROM UserInformation A \r\n" + 
					"INNER JOIN user_account_junction B \r\n" + 
					"ON A.user_id  = B.user_fk \r\n" + 
					"INNER JOIN AccountInformation C \r\n" + 
					"ON C.account_id  = B.account_fk \r\n" +
					"WHERE A.user_id=?";
			PreparedStatement getAccounts = conn.prepareStatement(accounts);
			getAccounts.setInt(1,id);
			
			ResultSet rs = getAccounts.executeQuery();
				
			while(rs.next()) {
				bankAccount = new Account(rs.getInt("account_id"),rs.getString("account_type"),rs.getDouble("account_balance")
						,rs.getString("account_status"));
				targetAccounts.add(bankAccount);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return targetAccounts;
	}
	@Override
	public Account deposit(int accountID, Double newBalance) {
		Account updatedAccount = new Account();
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String deposit = "UPDATE accountinformation SET account_balance = ? WHERE account_id = ?;";
			PreparedStatement postDeposit = conn.prepareStatement(deposit);
			postDeposit.setDouble(1, newBalance);
			postDeposit.setInt(2,accountID);
			postDeposit.executeUpdate();
			
			String balance = "SELECT * FROM accountinformation WHERE account_id = ?";
			PreparedStatement getBalance = conn.prepareStatement(balance);
			getBalance.setInt(1, accountID);
			
			ResultSet setBalance = getBalance.executeQuery();
			while(setBalance.next()) {
				updatedAccount = new Account(setBalance.getInt("account_id"),setBalance.getString("account_type"),setBalance.getDouble("account_balance")
						,setBalance.getString("account_status"));
				
			}
			
		} catch (SQLException e) {
			log.warn("An error with the database has occured.");
			e.printStackTrace();
		}
		
		
		return updatedAccount;
	}
	@Override
	public Account withdraw(int accountID, Double newBalance) {
Account updatedAccount = new Account();
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String withdraw = "UPDATE accountinformation SET account_balance = ? WHERE account_id = ?;";
			PreparedStatement postWithdraw = conn.prepareStatement(withdraw);
			postWithdraw.setDouble(1, newBalance);
			postWithdraw.setInt(2,accountID);
			postWithdraw.executeUpdate();
			
			String balance = "SELECT * FROM accountinformation WHERE account_id = ?";
			PreparedStatement getBalance = conn.prepareStatement(balance);
			getBalance.setInt(1, accountID);
			
			ResultSet setBalance = getBalance.executeQuery();
			while(setBalance.next()) {
				updatedAccount = new Account(setBalance.getInt("account_id"),setBalance.getString("account_type"),setBalance.getDouble("account_balance")
						,setBalance.getString("account_status"));
				
			}
			
		} catch (SQLException e) {
			log.warn("An error with the database has occured.");
			
		}
		
		
		return updatedAccount;
	}
	@Override
	public List<Account> transfer(int accountWithdrawID, Double newWithdrawBalance, int accountDepositID,
			Double newDepositBalance,int userId) {
		List<Account> targetAccounts = new ArrayList<Account>();
		Account updatedAccount; 
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			String transfer = "UPDATE accountinformation\r\n" + 
					"	SET account_balance =   \r\n" + 
					"	CASE  account_id \r\n" + 
					"	WHEN ? THEN ?\r\n" + 
					"	WHEN ? THEN ?\r\n" + 
					"	END \r\n" + 
					"	WHERE account_id IN (?,?);";
			PreparedStatement postTransfer = conn.prepareStatement(transfer);
			postTransfer.setInt(1,accountWithdrawID);
			postTransfer.setDouble(2, newWithdrawBalance);
			postTransfer.setInt(3,accountDepositID);
			postTransfer.setDouble(4, newDepositBalance);
			postTransfer.setInt(5,accountWithdrawID);
			postTransfer.setInt(6,accountDepositID);
			postTransfer.executeUpdate();
			
			
			String balance = "SELECT C.account_id, C.account_balance, C.account_type, C.account_status\r\n" + 
					"FROM UserInformation A  \r\n" + 
					"INNER JOIN user_account_junction B \r\n" + 
					"ON A.user_id  = B.user_fk  \r\n" + 
					"INNER JOIN AccountInformation C \r\n" + 
					"ON C.account_id  = B.account_fk\r\n" + 
					"WHERE A.user_id=?";
			PreparedStatement getBalance = conn.prepareStatement(balance);
			getBalance.setInt(1, userId);
			
			ResultSet setBalance = getBalance.executeQuery();
			while(setBalance.next()) {
				updatedAccount = new Account(setBalance.getInt("account_id"),setBalance.getString("account_type"),setBalance.getDouble("account_balance")
						,setBalance.getString("account_status"));
				targetAccounts.add(updatedAccount);
				
				
			}
			
		} catch (SQLException e) {
			log.warn("An error with the database has occured.");
			e.printStackTrace();
		}
		
		return targetAccounts;
	}
	@Override
	public void pendingAccounts() {
		try(Connection conn = ConnectionUtil.getConnection()) {
			String openAll = "UPDATE accountinformation \r\n" + 
					"	SET account_status = 'OPEN'\r\n" + 
					"	WHERE account_status = 'Pending'";
			PreparedStatement openAccounts = conn.prepareStatement(openAll);
			openAccounts.executeUpdate();
			log.info("All Pending accounts now Open.");

		} catch (SQLException e) {
			log.info("lost communication with database");
			e.printStackTrace();
		}
	}
	@Override
	public String specificPendingAccounts(int iD) {
		try(Connection conn = ConnectionUtil.getConnection()) {
			String iDCheck = "SELECT max(user_id) FROM userinformation";
			PreparedStatement userIDCheck = conn.prepareStatement(iDCheck);
			ResultSet confirmID = userIDCheck.executeQuery();
			if(confirmID.next()) {
				if(confirmID.getInt("max")<iD) {
					return "Invalid UserID";
				}
			}
			
			String open = "UPDATE accountinformation A\r\n" + 
					"SET account_status = 'OPEN'\r\n" + 
					"FROM user_account_junction uaj\r\n" + 
					"WHERE uaj.account_fk = A.account_id \r\n" + 
					"AND uaj.user_fk =? AND A.account_status ='Pending';";
			PreparedStatement openAccounts = conn.prepareStatement(open);
			openAccounts.setInt(1, iD);
			int validate = openAccounts.executeUpdate();
			if (validate == 0) {
				log.info("No Pending Accounts");
				return "No Pending Accounts";
			}
			else {
			log.info("Pending accounts now Open.");
			}
		} catch (SQLException e) {
			log.info("lost communication with database");
			e.printStackTrace();
		}
		return "All Accounts for UserID: "+iD+ " are open.";
	}
	
	@Override
	public Account editStatus(String newStatus, int accountID) {
		Account editAccount = new Account();

		try(Connection conn = ConnectionUtil.getConnection()) {
			String updateStatus = "UPDATE accountinformation SET account_status = ? WHERE account_id = ?;";
			PreparedStatement setStatus = conn.prepareStatement(updateStatus);
			setStatus.setString(1, newStatus);
			setStatus.setInt(2,accountID);
			setStatus.executeUpdate();
			
			String getAccount = "SELECT * FROM accountinformation WHERE account_id = ?";
			PreparedStatement pullAccount = conn.prepareStatement(getAccount);
			pullAccount.setInt(1, accountID);
			
			ResultSet getAccountinfo = pullAccount.executeQuery();
			while(getAccountinfo.next()) {
				editAccount = new Account(getAccountinfo.getInt("account_id"), getAccountinfo.getString("account_type"),getAccountinfo.getDouble("account_balance"),
						getAccountinfo.getString("account_status"));
			}
			} catch (SQLException sqle) {
				log.warn("An error with the database has occured.");
		
			}
		return editAccount;
	}
	@Override
	public Account editType(String newType, int accountID) {
		Account editAccount = new Account();

		try(Connection conn = ConnectionUtil.getConnection()) {
			String updateType = "UPDATE accountinformation SET account_type = ? WHERE account_id = ?;";
			PreparedStatement setType = conn.prepareStatement(updateType);
			setType.setString(1, newType);
			setType.setInt(2,accountID);
			setType.executeUpdate();
			
			String getAccount = "SELECT * FROM accountinformation WHERE account_id = ?";
			PreparedStatement pullAccount = conn.prepareStatement(getAccount);
			pullAccount.setInt(1, accountID);
			
			ResultSet getAccountinfo = pullAccount.executeQuery();
			while(getAccountinfo.next()) {
				editAccount = new Account(getAccountinfo.getInt("account_id"), getAccountinfo.getString("account_type"),getAccountinfo.getDouble("account_balance"),
						getAccountinfo.getString("account_status"));
			}
			} catch (SQLException sqle) {
				log.warn("An error with the database has occured.");
		
			}
		return editAccount;
	}
	@Override
	public Account editBalance(double newAccountBalance, int accountID) {
		Account editAccount = new Account();

		try(Connection conn = ConnectionUtil.getConnection()) {
			String updateBalance = "UPDATE accountinformation SET account_balance = ? WHERE account_id = ?;";
			PreparedStatement setType = conn.prepareStatement(updateBalance);
			setType.setDouble(1, newAccountBalance);
			setType.setInt(2,accountID);
			setType.executeUpdate();
			
			String getAccount = "SELECT * FROM accountinformation WHERE account_id = ?";
			PreparedStatement pullAccount = conn.prepareStatement(getAccount);
			pullAccount.setInt(1, accountID);
			
			ResultSet getAccountinfo = pullAccount.executeQuery();
			while(getAccountinfo.next()) {
				editAccount = new Account(getAccountinfo.getInt("account_id"), getAccountinfo.getString("account_type"),getAccountinfo.getDouble("account_balance"),
						getAccountinfo.getString("account_status"));
			}
			} catch (SQLException sqle) {
				log.warn("An error with the database has occured.");
		
			}
		return editAccount;
	}

}
