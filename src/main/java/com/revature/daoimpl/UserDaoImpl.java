package com.revature.daoimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.dao.UserDao;
import com.revature.model.User;

public class UserDaoImpl implements UserDao{
	//YOU NEED THIS STATIC BLOCK FOR A JDBC DRIVER IN A WEB APPLICATION
	static {
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Static block has failed me");
		}
	}
	
////////////////////LOGINTODB/////////////////
	private static String url=
			"jdbc:postgresql://revdatabase.cwrli2cspmix.us-east-2.rds.amazonaws.com/project0";
	private static String un= "RevDataBase";
	private static String pw= System.getenv("TRAINING_DB_PASSWORD");
///////////////////////////////////////////
	
	
	@Override
	public User login(String username, String password) {
		User targetUser = new User();
		try (Connection conn = DriverManager.getConnection(url, un, pw)) {
			String sql = "SELECT * FROM UserInformation WHERE user_name = ? AND pass_word =?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,username);
			ps.setString(2, password);
			
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				targetUser =new User(rs.getInt("user_id"),rs.getString("user_name"),"******", rs.getString("first_name"), rs.getString("last_name"), rs.getString("email"), null);
				System.out.println("Successful Login");
				
			}else {
				System.out.println("Invalid credentials");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
		return targetUser;
	}
	
}


