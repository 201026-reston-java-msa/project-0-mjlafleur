package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class ConnectionUtil {
private static Logger log = Logger.getLogger(ConnectionUtil.class);
	
	public static Connection getConnection() {
		String url= "jdbc:postgresql://revdatabase.cwrli2cspmix.us-east-2.rds.amazonaws.com/project0";
		String user= "RevDataBase";
		String password= System.getenv("TRAINING_DB_PASSWORD");
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException sqle) {
			log.warn("Unable to obtain connection to database",sqle);
			sqle.printStackTrace();
		}
		return conn;
	}
}
