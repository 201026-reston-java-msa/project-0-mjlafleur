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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return targetAccounts;
	}
	@Override
	public Account deposit(String choice, String amount) {
		Double depo = Double.valueOf(amount);
		Account updatedAccount = new Account();
		
		String deposit = "";
		try(Connection conn = ConnectionUtil.getConnection()) {
			PreparedStatement postDeposit = conn.prepareStatement(deposit);
			postDeposit.setDouble(1, depo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return updatedAccount;
	}

}
