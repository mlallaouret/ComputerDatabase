package com.excilys.projet.computerdatabase.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcConnexion {

	/**
	 * Logger
	 */
	final static Logger logger = LoggerFactory.getLogger(JdbcConnexion.class);
	
	/**
	 * Identifiants de connexion
	 */
	public static final String URL = "jdbc:mysql://localhost/computer_database";
	public static final String USER = "root";
	public static final String PASSWORD = "root";
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.error("Impossible de charger le driver MySql");
			
		}
	}

	
	public static Connection getConnection(){
		try {
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			logger.error("Erreur de recuperation de la connexion");
		}
		return null;
	}
	
	public static void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			logger.error("Erreur lors de la fermeture de la connexion");
		}
	}
		
}
