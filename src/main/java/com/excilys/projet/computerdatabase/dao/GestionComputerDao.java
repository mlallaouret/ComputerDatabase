package com.excilys.projet.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.excilys.projet.computerdatabase.model.Company;
import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.utils.JdbcConnexion;

public class GestionComputerDao {

	/**
	 * Query
	 */
	public static final String SELECT_ALL_COMPUTERS_QUERY = "select cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpy.id, cpy.name  from computer cpu left join company cpy on cpu.company_id=cpy.id";
	public static final String SELECT_ONE_COMPUTER_BY_ID_QUERY = "select cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpy.id, cpy.name  from computer cpu left join company cpy on cpu.company_id=cpy.id where cpu.id = ?";
	public static final String INSERT_COMPUTER = "insert into computer (name, introduced, discontinued, company_id) values (?,?,?,?)";
	public static final String DELETE_COMPUTER = "delete from computer where id=?";
	public static final String UPDATE_COMPUTER = "update computer set name = ?, introduced = ?, discontinued = ? company_id = ? where id =? ";
	
	static {
		gestionComputerDao = new GestionComputerDao();
	}
	
	private static GestionComputerDao gestionComputerDao;
	
	
	private GestionComputerDao(){
		
	}
	
	public static GestionComputerDao getInstance() {
		return gestionComputerDao;
	}
	
	public List<Computer> getComputers(){
		PreparedStatement myPreparedStatement=null;
		List<Computer> liste = new ArrayList<Computer>();
		
		Connection conn = JdbcConnexion.getConnection();
		try {
			myPreparedStatement = conn.prepareStatement(SELECT_ALL_COMPUTERS_QUERY);
			ResultSet rs = myPreparedStatement.executeQuery();
			
			while(rs.next()){
				Computer c = new Computer();
				c.setId(rs.getInt("cpu.id"));
				c.setName(rs.getString("cpu.name"));
				c.setIntroduced(rs.getDate("cpu.introduced"));
				c.setDiscontinued(rs.getDate("cpu.discontinued"));
				Company cpy = new Company();
				cpy.setId(rs.getInt("cpy.id"));
				cpy.setName(rs.getString("cpy.name"));
				c.setCompany(cpy);
				liste.add(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la récupération de la liste des ordinateurs");
			e.printStackTrace();
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la récupération de la liste des ordinateurs");
				e.printStackTrace();
			}
			JdbcConnexion.closeConnection(conn);
		}
		
		
		
		return liste;
	}
	
	public Computer getComputer(int id){
		
		Computer computer= new Computer();
		PreparedStatement myPreparedStatement=null;
		Connection conn = JdbcConnexion.getConnection();
		try {
			myPreparedStatement = conn.prepareStatement(SELECT_ONE_COMPUTER_BY_ID_QUERY);
			myPreparedStatement.setInt(0, id);
			ResultSet rs = myPreparedStatement.executeQuery();
			rs.first();
			Computer c = new Computer();
			c.setId(rs.getInt("cpu.id"));
			c.setName(rs.getString("cpu.name"));
			c.setIntroduced(rs.getDate("cpu.introduced"));
			c.setDiscontinued(rs.getDate("cpu.discontinued"));
			Company cpy = new Company();
			cpy.setId(rs.getInt("cpy.id"));
			cpy.setName(rs.getString("cpy.name"));
			c.setCompany(cpy);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la récupération d'un ordinateur");
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la récupération d'un ordinateur");
				e.printStackTrace();
			}
			JdbcConnexion.closeConnection(conn);
		}
		
		return computer;
	}
	
	public void insertComputer(Computer computer) {
		PreparedStatement myPreparedStatement=null;
		Connection conn = JdbcConnexion.getConnection();
		try {
			myPreparedStatement = conn.prepareStatement(INSERT_COMPUTER);
			myPreparedStatement.setString(0, computer.getName());
			myPreparedStatement.setDate(1, new java.sql.Date(computer.getIntroduced().getTime()));
			myPreparedStatement.setDate(2, new java.sql.Date(computer.getDiscontinued().getTime()));
			myPreparedStatement.setInt(3, computer.getCompany().getId());
			int result = myPreparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Logger.getLogger("main").log(Level.WARNING, "Erreur lors de l'insert d'un ordinateur");
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				Logger.getLogger("main").log(Level.WARNING, "Erreur lors de l'insert d'un ordinateur");
				e.printStackTrace();
			}
			JdbcConnexion.closeConnection(conn);
		}
	}
	
	public void deleteComputer(Computer computer){
		PreparedStatement myPreparedStatement=null;
		Connection conn = JdbcConnexion.getConnection();
		try {
			myPreparedStatement = conn.prepareStatement(UPDATE_COMPUTER);
			myPreparedStatement.setInt(0, computer.getId());
			int result = myPreparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la suppression d'un ordinateur");
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la suppression d'un ordinateur");
				e.printStackTrace();
			}
			JdbcConnexion.closeConnection(conn);
		}
	}
	
	public void updateComputer(Computer computer) {
		PreparedStatement myPreparedStatement=null;
		Connection conn = JdbcConnexion.getConnection();
		try {
			myPreparedStatement = conn.prepareStatement(UPDATE_COMPUTER);
			myPreparedStatement.setString(0, computer.getName());
			myPreparedStatement.setDate(1, new java.sql.Date(computer.getIntroduced().getTime()));
			myPreparedStatement.setDate(2, new java.sql.Date(computer.getDiscontinued().getTime()));
			myPreparedStatement.setInt(3, computer.getCompany().getId());
			int result = myPreparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Logger.getLogger("main").log(Level.WARNING, "Erreur lors de l'update d'un ordinateur");
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				Logger.getLogger("main").log(Level.WARNING, "Erreur lors de l'update d'un ordinateur");
				e.printStackTrace();
			}
			JdbcConnexion.closeConnection(conn);
		}
	}
	
}
