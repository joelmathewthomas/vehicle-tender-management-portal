package com.vtmp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 */
public class DbDao {

	private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/vtmp?useSSL=false&serverTimezone=UTC";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "password";

	static {
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates and returns a new database connection using the configured JDBC URL,
	 * username, and password.
	 *
	 * <p>
	 * This method does not reuse connections. Each call returns a fresh
	 * {@link Connection} object. The caller is responsible for closing the
	 * connection after use.
	 * </p>
	 *
	 * @return a new {@link Connection} to the configured database
	 * @throws SQLException if the connection attempt fails
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	}
}
