package com.barbershop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public final class ConnectionFactoryPostgres {

	private static final String CLASS_NAME = "ConnectionFactoryPostgres";

	Logger log = Logger.getRootLogger();

	public static String URL;

	public static String USERNAME;

	public static String PASSWORD;
	
	public static String DB_NAME = "barbershop_test"; // "barbershop_db";

	private static ConnectionFactoryPostgres connectionFactory = null;

	private ConnectionFactoryPostgres() {
		
//		URL = "jdbc:postgresql://" + System.getenv("BARBERSHOP_DB_URL") + ":5432/" + "barbershop_db" + "?";
		URL = "jdbc:postgresql://" + System.getenv("BARBERSHOP_DB_URL") + ":5432/" + DB_NAME + "?";

		USERNAME = System.getenv("BARBERSHOP_DB_USERNAME");

		PASSWORD = System.getenv("BARBERSHOP_DB_PASSWORD");
		
	}

	public Connection createConnection() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			log.error(CLASS_NAME + ".createConnection() -> Failed to load Driver", e);
			System.out.println("Failed to load Driver");
		}

		// log.info("URL : " + URL);

		try {
			Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			log.info(CLASS_NAME + ".createConnection() -> Connecting to DB succeedded!");
			log.info("----------------------------------------------------------------------");
			return conn;
		} catch (SQLException e) {
			log.error(CLASS_NAME + ".createConnection() -> Failed to connect to DB", e);
			log.info("----------------------------------------------------------------------");
		}
		return null;
	}

	public static synchronized Connection getConnection() {
//		if(environment.equals("TestingDb")) {
//			DB_NAME = "barbershop_test";
//		}else {
//			DB_NAME = "barbershop_db";
//		}
		if (connectionFactory == null) {
			connectionFactory = new ConnectionFactoryPostgres();
		}

		return connectionFactory.createConnection();

	}

}
