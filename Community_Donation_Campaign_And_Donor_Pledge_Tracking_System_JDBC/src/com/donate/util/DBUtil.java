package com.donate.util;

import java.sql.*;

public class DBUtil {
	private static final String url = "jdbc:mysql://localhost:3306/donationdb";
	private static final String user = "root";
	private static final String pass = "omen";
	private static Connection connection = null;
	
	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(url, user, pass);
				System.out.println("DB connected...");
			} catch (ClassNotFoundException e) {
				System.out.println("Driver not found: " + e.getMessage());
			}
		}
		
		return connection;
	}

}
 