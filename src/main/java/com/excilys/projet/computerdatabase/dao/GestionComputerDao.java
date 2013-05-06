package com.excilys.projet.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.excilys.projet.computerdatabase.model.Company;
import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.utils.JdbcConnexion;
import com.excilys.projet.computerdatabase.utils.SqlRequestOptions;

public class GestionComputerDao {

	/**
	 * Query
	 */
	private static final String SELECT_ALL_COMPUTERS_QUERY = "select cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpy.id, cpy.name  from computer cpu left join company cpy on cpu.company_id=cpy.id where cpu.name LIKE ? order by %s limit ?, ? ";
	private static final String SELECT_ONE_COMPUTER_BY_ID_QUERY = "select cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpy.id, cpy.name  from computer cpu left join company cpy on cpu.company_id=cpy.id where cpu.id = ?";
	private static final String INSERT_COMPUTER = "insert into computer (name, introduced, discontinued, company_id) values (?,?,?,?)";
	private static final String DELETE_COMPUTER = "delete from computer where id=?";
	private static final String UPDATE_COMPUTER = "update computer set name = ?, introduced = ?, discontinued = ?, company_id = ? where id =? ";
	private static final String COUNT_COMPUTER = "select count(cpu.id) as count from computer cpu where cpu.name LIKE ?";
	private static final String SELECT_ALL_COMPANIES_QUERY = "select id, name from company";
	private static final String SELECT_ONE_COMPANY_BY_ID_QUERY = "select id, name from company where id = ?";
	private static final String ID_EXISTS_QUERY = "select count(id) as count from computer where id = ?";
	
	static {
		gestionComputerDao = new GestionComputerDao();
	}
	
	private static GestionComputerDao gestionComputerDao;
	
	
	private GestionComputerDao(){
		
	}
	
	public static GestionComputerDao getInstance() {
		return gestionComputerDao;
	}
	
	public List<Computer> getComputers(int debut, int nombre, SqlRequestOptions sqlRequestOptions){
		PreparedStatement myPreparedStatement=null;
		List<Computer> liste = new ArrayList<Computer>();
		
		Connection conn = JdbcConnexion.getConnection();
		try {
			Formatter f = new Formatter();
			f.format(SELECT_ALL_COMPUTERS_QUERY, sqlRequestOptions.getSqlTri());
			
			myPreparedStatement = conn.prepareStatement(f.toString());
			f.close();
			myPreparedStatement.setString(1, sqlRequestOptions.getSqlFilter());
			myPreparedStatement.setInt(2, debut*10);
			myPreparedStatement.setInt(3, nombre);
			System.out.println(myPreparedStatement);
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
			myPreparedStatement.setInt(1, id);
			ResultSet rs = myPreparedStatement.executeQuery();
			rs.first();
			computer.setId(rs.getInt("cpu.id"));
			computer.setName(rs.getString("cpu.name"));
			computer.setIntroduced(rs.getDate("cpu.introduced"));
			computer.setDiscontinued(rs.getDate("cpu.discontinued"));
			Company cpy = new Company();
			cpy.setId(rs.getInt("cpy.id"));
			cpy.setName(rs.getString("cpy.name"));
			computer.setCompany(cpy);
		} catch (SQLException e) {
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
	
	public Integer getComputerCount(SqlRequestOptions sqlRequestOptions){
		
		Integer count = null;
		PreparedStatement myPreparedStatement=null;
		Connection conn = JdbcConnexion.getConnection();
		try {
			myPreparedStatement = conn.prepareStatement(COUNT_COMPUTER);
			myPreparedStatement.setString(1, sqlRequestOptions.getSqlFilter());
			ResultSet rs = myPreparedStatement.executeQuery();
			rs.first();
			count = rs.getInt("count");

		} catch (SQLException e) {
			Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la récupération du compte des ordinateurs");
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la récupération du compte des ordinateurs");
				e.printStackTrace();
			}
			JdbcConnexion.closeConnection(conn);
		}
		
		return count;
	}
	
	public void insertComputer(Computer computer) {
		PreparedStatement myPreparedStatement=null;
		Connection conn = JdbcConnexion.getConnection();
		try {
			myPreparedStatement = conn.prepareStatement(INSERT_COMPUTER);
			myPreparedStatement.setString(1, computer.getName());
			myPreparedStatement.setDate(2, new java.sql.Date(computer.getIntroduced().getTime()));
			myPreparedStatement.setDate(3, new java.sql.Date(computer.getDiscontinued().getTime()));
			myPreparedStatement.setInt(4, computer.getCompany().getId());
			int result = myPreparedStatement.executeUpdate();
			
		} catch (SQLException e) {
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
	
	public void deleteComputer(int id){
		PreparedStatement myPreparedStatement=null;
		Connection conn = JdbcConnexion.getConnection();
		try {
			myPreparedStatement = conn.prepareStatement(DELETE_COMPUTER);
			myPreparedStatement.setInt(1, id);
			int result = myPreparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la suppression d'un ordinateur");
			e.printStackTrace();
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
			myPreparedStatement.setString(1, computer.getName());
			myPreparedStatement.setDate(2, new java.sql.Date(computer.getIntroduced().getTime()));
			myPreparedStatement.setDate(3, new java.sql.Date(computer.getDiscontinued().getTime()));
			myPreparedStatement.setInt(4, computer.getCompany().getId());
			int result = myPreparedStatement.executeUpdate();
			
		} catch (SQLException e) {
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
	
	public void insertOrUpdateComputer(Computer computer) {
		PreparedStatement myPreparedStatement=null;
		Connection conn = JdbcConnexion.getConnection();
		try {
			if(computer.getId()!=0){
				myPreparedStatement = conn.prepareStatement(UPDATE_COMPUTER);
				myPreparedStatement.setInt(5, computer.getId());
			} else {
				myPreparedStatement = conn.prepareStatement(INSERT_COMPUTER);
			}
			myPreparedStatement.setString(1, computer.getName());
			if(computer.getIntroduced()!=null) {
				myPreparedStatement.setDate(2, new java.sql.Date(computer.getIntroduced().getTime()));
			} else {
				myPreparedStatement.setDate(2, null);
			}
			if(computer.getDiscontinued()!=null) {
				myPreparedStatement.setDate(3, new java.sql.Date(computer.getDiscontinued().getTime()));
			} else {
				myPreparedStatement.setDate(3, null);
			}
			myPreparedStatement.setInt(4, computer.getCompany().getId());
			int result = myPreparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			Logger.getLogger("main").log(Level.WARNING, "Erreur lors de l'inset/update d'un ordinateur");
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				Logger.getLogger("main").log(Level.WARNING, "Erreur lors de l'insert/update d'un ordinateur");
				e.printStackTrace();
			}
			JdbcConnexion.closeConnection(conn);
		}
	}
	
	public List<Company> getCompanies(){
		PreparedStatement myPreparedStatement=null;
		List<Company> liste = new ArrayList<Company>();
		
		Connection conn = JdbcConnexion.getConnection();
		try {
			myPreparedStatement = conn.prepareStatement(SELECT_ALL_COMPANIES_QUERY);
			
			ResultSet rs = myPreparedStatement.executeQuery();
			
			while(rs.next()){
				Company cpy = new Company();
				cpy.setId(rs.getInt("id"));
				cpy.setName(rs.getString("name"));
				liste.add(cpy);
			}
		} catch (SQLException e) {
			Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la récupération de la liste des sociétés");
			e.printStackTrace();
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la récupération de la liste des sociétés");
				e.printStackTrace();
			}
			JdbcConnexion.closeConnection(conn);
		}
		
		
		
		return liste;
	}
	
	public Company getCompany(int id){
		PreparedStatement myPreparedStatement=null;
		Company company = new Company();
		
		Connection conn = JdbcConnexion.getConnection();
		try {
			myPreparedStatement = conn.prepareStatement(SELECT_ONE_COMPANY_BY_ID_QUERY);
			myPreparedStatement.setInt(1, id);
			
			ResultSet rs = myPreparedStatement.executeQuery();
			
			rs.first();
			company.setId(rs.getInt("id"));
			company.setName(rs.getString("name"));
		} catch (SQLException e) {
			Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la récupération d'une société");
			e.printStackTrace();
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la récupération d'une société");
				e.printStackTrace();
			}
			JdbcConnexion.closeConnection(conn);
		}
		
		
		
		return company;
	}
	
	public boolean isIdExists(int id) {
		PreparedStatement myPreparedStatement=null;
		
		Connection conn = JdbcConnexion.getConnection();
		try {
			myPreparedStatement = conn.prepareStatement(ID_EXISTS_QUERY);
			myPreparedStatement.setInt(1, id);
			
			ResultSet rs = myPreparedStatement.executeQuery();
			
			rs.first();
			if(rs.getInt("count")==1) {
				return true;
			}
		} catch (SQLException e) {
			Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la récupération d'une société");
			e.printStackTrace();
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la récupération d'une société");
				e.printStackTrace();
			}
			JdbcConnexion.closeConnection(conn);
		}
		return false;
	}
	
}
