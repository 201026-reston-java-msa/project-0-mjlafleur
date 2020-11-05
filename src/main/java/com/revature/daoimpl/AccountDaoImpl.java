package com.revature.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
			
			//create account 
			String addAccount = "INSERT INTO AccountInformation (account_balance,account_type,account_status)" 
					+" VALUES (?,?,?)";
			PreparedStatement ps = conn.prepareStatement(addAccount);
			ps.setDouble(1, balance);
			ps.setString(2, type);
			ps.setString(3, "Pending");
			ps.executeUpdate();
			//connect to userinfo with foreign key.
			
			
		} catch (SQLException sqle) {
			log.warn("Connection issues with database.", sqle);
			sqle.printStackTrace();
		}
		
		
		return newAccount;
	}

}
