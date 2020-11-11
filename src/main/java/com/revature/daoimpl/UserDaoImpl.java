package com.revature.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.revature.dao.UserDao;
import com.revature.model.Account;
import com.revature.model.User;
import com.revature.util.ConnectionUtil;

public class UserDaoImpl implements UserDao {
	// YOU NEED THIS STATIC BLOCK FOR A JDBC DRIVER IN A WEB APPLICATION
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
			ps.setString(1, userName);
			ps.setString(2, passWord);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				targetUser = new User(rs.getInt("user_id"), rs.getString("user_name"), ""/* this is the password */,
						rs.getString("first_name"), rs.getString("last_name"), rs.getInt("rolenum"));
				log.info("Successful Login");
			} else {
				log.warn("Invalid Credentials");
				System.out.println("Invalid credentials");
			}
		} catch (SQLException sqle) {
			log.warn("Unable to retrieve information from database", sqle);
			sqle.printStackTrace();

		}

		return targetUser;
	}

	@Override
	public User register(String userName, String passWord, String firstName, String lastName) {
		User newUser = new User();

		try (Connection conn = ConnectionUtil.getConnection()) {

//check if duplicate account
			String check = "SELECT * FROM UserInformation WHERE user_name = ?";
			PreparedStatement p = conn.prepareStatement(check);
			p.setString(1, userName);

			ResultSet rs = p.executeQuery();
			if (!rs.next()) {

// if not then register.
				String addUser = "INSERT INTO userinformation (user_name,pass_word,first_name,last_name,rolenum)"
						+ " VALUES (?,?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(addUser);
				ps.setString(1, userName);
				ps.setString(2, passWord);
				ps.setString(3, firstName);
				ps.setString(4, lastName);
				ps.setInt(5, 3);
				ps.executeUpdate();

				String getUser = "SELECT * FROM UserInformation WHERE user_name = ?";
				PreparedStatement pullUser = conn.prepareStatement(getUser);
				pullUser.setString(1, userName);
				ResultSet getUserinfo = pullUser.executeQuery();
				if (getUserinfo.next())
					;
				newUser = new User(getUserinfo.getInt("user_id"), getUserinfo.getString("user_name"),
						""/* this is the password */, getUserinfo.getString("first_name"),
						getUserinfo.getString("last_name"), getUserinfo.getInt("rolenum"));
				log.info("User was created in database");
			} else {
				log.warn("That username is unavailable.");

			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return newUser;
	}
	
	@Override
	public User registerEmployee(String userName, String passWord, String firstName, String lastName) {
		User newUser = new User();

		try (Connection conn = ConnectionUtil.getConnection()) {

//check if duplicate account
			String check = "SELECT * FROM UserInformation WHERE user_name = ?";
			PreparedStatement p = conn.prepareStatement(check);
			p.setString(1, userName);

			ResultSet rs = p.executeQuery();
			if (!rs.next()) {

// if not then register.
				String addUser = "INSERT INTO userinformation (user_name,pass_word,first_name,last_name,rolenum)"
						+ " VALUES (?,?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(addUser);
				ps.setString(1, userName);
				ps.setString(2, passWord);
				ps.setString(3, firstName);
				ps.setString(4, lastName);
				ps.setInt(5, 2);
				ps.executeUpdate();

				String getUser = "SELECT * FROM UserInformation WHERE user_name = ?";
				PreparedStatement pullUser = conn.prepareStatement(getUser);
				pullUser.setString(1, userName);
				ResultSet getUserinfo = pullUser.executeQuery();
				if (getUserinfo.next())
					;
				newUser = new User(getUserinfo.getInt("user_id"), getUserinfo.getString("user_name"),
						""/* this is the password */, getUserinfo.getString("first_name"),
						getUserinfo.getString("last_name"), getUserinfo.getInt("rolenum"));
				log.info("User was created in database");
			} else {
				log.warn("That username is unavailable.");

			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return newUser;
	}

	@Override
	public User specificUser(int iD) {
		User targetUser = new User();
		try (Connection conn = ConnectionUtil.getConnection()) {
			String getUser = "SELECT * FROM userinformation WHERE user_id = ?";
			PreparedStatement pullUser = conn.prepareStatement(getUser);
			pullUser.setInt(1, iD);

			ResultSet postUser = pullUser.executeQuery();
			if (postUser.next()) {
				targetUser = new User(postUser.getInt("user_id"), postUser.getString("user_name"),
						""/* this is the password */, postUser.getString("first_name"), postUser.getString("last_name"),
						postUser.getInt("rolenum"));
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return targetUser;
	}

	@Override
	public User editUsername(String newUserName, int userId) {
		User editUser = new User();

		try (Connection conn = ConnectionUtil.getConnection()) {

			String check = "SELECT * FROM UserInformation WHERE user_name = ?";
			PreparedStatement p = conn.prepareStatement(check);
			p.setString(1, newUserName);

			ResultSet rs = p.executeQuery();
			if (!rs.next()) {

				String updateUserName = "UPDATE userinformation \r\n" + 
						"SET user_name = ?\r\n" + 
						"WHERE user_id = ?";

				PreparedStatement setUserName = conn.prepareStatement(updateUserName);
				setUserName.setString(1, newUserName);
				setUserName.setInt(2, userId);
				setUserName.executeUpdate();

				String getUser = "SELECT * FROM UserInformation WHERE user_name = ?";
				PreparedStatement pullUser = conn.prepareStatement(getUser);
				pullUser.setString(1, newUserName);
				ResultSet getUserinfo = pullUser.executeQuery();
				if (getUserinfo.next())
					
				editUser = new User(getUserinfo.getInt("user_id"), getUserinfo.getString("user_name"),
						""/* this is the password */, getUserinfo.getString("first_name"),
						getUserinfo.getString("last_name"), getUserinfo.getInt("rolenum"));
				log.info("Username was edited in database");
			} else {
				log.info("username already in use.");
				return editUser;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return editUser;
	}

	@Override
	public User editPassword(String newUserPass, int userId) {
		User editUser = new User();

		try(Connection conn = ConnectionUtil.getConnection()) {
			String updatePassword = "UPDATE userinformation SET pass_word = ? WHERE user_id = ?;";
			PreparedStatement setPassword = conn.prepareStatement(updatePassword);
			setPassword.setString(1, newUserPass);
			setPassword.setInt(2,userId);
			setPassword.executeUpdate();
			
			String getUser = "SELECT * FROM userinformation WHERE user_id = ?";
			PreparedStatement pullUser = conn.prepareStatement(getUser);
			pullUser.setInt(1, userId);
			
			ResultSet getUserinfo = pullUser.executeQuery();
			while(getUserinfo.next()) {
				editUser = new User(getUserinfo.getInt("user_id"), getUserinfo.getString("user_name"),
						getUserinfo.getString("pass_word"), getUserinfo.getString("first_name"),
						getUserinfo.getString("last_name"), getUserinfo.getInt("rolenum"));
			}
			} catch (SQLException sqle) {
				log.warn("An error with the database has occured.");
		
			}
	
		return editUser;
	}
	@Override
	public User editFirstname(String newUserFirst, int userId) {
		User editUser = new User();

		try(Connection conn = ConnectionUtil.getConnection()) {
			String updateFirstName = "UPDATE userinformation SET first_name = ? WHERE user_id = ?;";
			PreparedStatement setPassword = conn.prepareStatement(updateFirstName);
			setPassword.setString(1, newUserFirst);
			setPassword.setInt(2,userId);
			setPassword.executeUpdate();
			
			String getUser = "SELECT * FROM userinformation WHERE user_id = ?";
			PreparedStatement pullUser = conn.prepareStatement(getUser);
			pullUser.setInt(1, userId);
			
			ResultSet getUserinfo = pullUser.executeQuery();
			while(getUserinfo.next()) {
				editUser = new User(getUserinfo.getInt("user_id"), getUserinfo.getString("user_name"),
						"", getUserinfo.getString("first_name"),
						getUserinfo.getString("last_name"), getUserinfo.getInt("rolenum"));
			}
			} catch (SQLException sqle) {
				log.warn("An error with the database has occured.");
		
			}
	
		return editUser;
	}

	@Override
	public User editLastname(String newUserLast, int userId) {
		User editUser = new User();

		try(Connection conn = ConnectionUtil.getConnection()) {
			String updateLastName = "UPDATE userinformation SET last_name = ? WHERE user_id = ?;";
			PreparedStatement setPassword = conn.prepareStatement(updateLastName);
			setPassword.setString(1, newUserLast);
			setPassword.setInt(2,userId);
			setPassword.executeUpdate();
			
			String getUser = "SELECT * FROM userinformation WHERE user_id = ?";
			PreparedStatement pullUser = conn.prepareStatement(getUser);
			pullUser.setInt(1, userId);
			
			ResultSet getUserinfo = pullUser.executeQuery();
			while(getUserinfo.next()) {
				editUser = new User(getUserinfo.getInt("user_id"), getUserinfo.getString("user_name"),
						"", getUserinfo.getString("first_name"),
						getUserinfo.getString("last_name"), getUserinfo.getInt("rolenum"));
			}
			} catch (SQLException sqle) {
				log.warn("An error with the database has occured.");
		
			}
	
		return editUser;
	}

	

}
