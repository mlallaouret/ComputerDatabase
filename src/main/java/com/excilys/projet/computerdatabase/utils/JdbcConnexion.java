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
	
	
	private static JdbcConnexion jdbcConnexion;
	
	public ThreadLocal<Connection> threadConnection;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			jdbcConnexion = new JdbcConnexion();
		} catch (ClassNotFoundException e) {
			logger.error("Impossible de charger le driver MySql");
			
		}
	}

	private JdbcConnexion() {
		this.threadConnection = new ThreadLocal<Connection>();
	}
	
	public static JdbcConnexion getInstance() {
		return jdbcConnexion;
	}
	
	public Connection getConnection(){
		
		if(threadConnection.get()==null ){
			try {
				Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				conn.setAutoCommit(false);
				threadConnection.set(conn);
				return threadConnection.get();
			} catch (SQLException e) {
				logger.error("Erreur de recuperation de la connexion");
				return null;
			}
		
		} else {
			return threadConnection.get();
		}
		
	}
	
	public void closeConnection(){
		try {
			threadConnection.get().close();
		} catch (SQLException e) {
			logger.error("Erreur lors de la fermeture de la connexion");
		}
		threadConnection.remove();
	}
		
}
