package com.revature.daoimpl;

public class UserDaoImpl {
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
"jdbc:postgresql://revdatabase.cwrli2cspmix.us-east-2.rds.amazonaws.com/banking";
private static String un= "RevDataBase";
private static String pw= System.getenv("TRAINING_DB_PASSWORD");
///////////////////////////////////////////
}
