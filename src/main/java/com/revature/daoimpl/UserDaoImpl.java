package com.revature.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.dao.UserDao;
import com.revature.model.User;
import com.revature.util.ConnectionUtil;

public class UserDaoImpl implements UserDao{
	//YOU NEED THIS STATIC BLOCK FOR A JDBC DRIVER IN A WEB APPLICATION
//	static {
//		try {
//			Class.forName("org.postgresql.Driver");
//		}catch(ClassNotFoundException cnfe) {
//			cnfe.printStackTrace();
//			System.out.println("Static block has failed me");
//		}
//	}
	
////////////////////LOGINTODB/////////////////
///MOVED TO CONNECTIONUTIL
///////////////////////////////////////////
	public static Logger log = Logger.getLogger(UserDaoImpl.class);
	
	@Override
	public User login(String userName, String passWord) {
		User targetUser = new User();
		try (Connection conn = ConnectionUtil.getConnection()) {
			String getUserPass = "SELECT * FROM UserInformation WHERE user_name = ? AND pass_word =?";
			PreparedStatement ps = conn.prepareStatement(getUserPass);
			ps.setString(1,userName);
			ps.setString(2, passWord);
			
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				targetUser = new User(rs.getInt("user_id"),rs.getString("user_name"),""/*this is the password*/, 
						rs.getString("first_name"), rs.getString("last_name"), rs.getInt("rolenum"));
				log.info("Successful Login");
			}else {
				log.warn("Invalid Credentials");
				System.out.println("Invalid credentials");
			}
		} catch (SQLException sqle) {
			log.warn("Unable to retrieve information from database",sqle);
			sqle.printStackTrace();
			
		}
		
		return targetUser;
	}


	@Override
	public User register(String userName, String passWord, String firstName, String lastName) {
		User newUser = new User();
		
		
				
			try (Connection conn = ConnectionUtil.getConnection()) {
				
//check if duplicate account
				String check = "SELECT * FROM UserInformation WHERE user_name = ? AND pass_word =?";
				PreparedStatement p = conn.prepareStatement(check);
				p.setString(1,userName);
				p.setString(2, passWord);
				
				ResultSet rs = p.executeQuery();
				if (!rs.next()) {
				
// if not then register.
					String addUser = "INSERT INTO userinformation (user_name,pass_word,first_name,last_name,rolenum)" 
							+" VALUES (?,?,?,?,?)";
					PreparedStatement ps = conn.prepareStatement(addUser);
					ps.setString(1, userName);
					ps.setString(2, passWord);
					ps.setString(3, firstName);
					ps.setString(4, lastName);
					ps.setInt(5, 3);
					ps.executeUpdate();
					
					String getUser = "SELECT * FROM UserInformation WHERE user_name = ?";
					PreparedStatement pullUser = conn.prepareStatement(getUser);
					pullUser.setString(1,userName);
					ResultSet getUserinfo = pullUser.executeQuery();
					if(getUserinfo.next());
					 	newUser = new User(getUserinfo.getInt("user_id"),getUserinfo.getString("user_name"),""/*this is the password*/, 
								getUserinfo.getString("first_name"), getUserinfo.getString("last_name"), getUserinfo.getInt("rolenum"));
					log.info("User was created in database");
//					String accountType;
//					if (type == 1) {
//						accountType = "Checking";
//					} else {
//						accountType = "Savings";
//					}
//				
//					String addAccount = "INSERT INTO AccountInformation (Account_Type,Account_status) VALUES (?,?)";
//					PreparedStatement ps1 = conn.prepareStatement(addAccount);
//					ps1.setString(1,accountType);
//					ps1.setString(2,"OPEN");
//					ps1.executeUpdate();
				} else {
					log.warn("That username is unavailable.");
					
				}
					
			} catch (SQLException sqle){
				sqle.printStackTrace();
			}
			
			
			
		return newUser;
	}
	
}


