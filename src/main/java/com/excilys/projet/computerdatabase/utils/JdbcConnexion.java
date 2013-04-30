package com.excilys.projet.computerdatabase.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcConnexion {

	
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
			Logger.getLogger("main").log(Level.SEVERE, "Impossible de charger le driver MySql");
		}
	}

	
	public static Connection getConnection(){
		try {
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Erreur de recuperation de la connexion");
		}
		return null;
	}
	
	public static void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			Logger.getLogger("main").log(Level.SEVERE, "Erreur lors de la fermeture de la connexion");
		}
	}
		
}
