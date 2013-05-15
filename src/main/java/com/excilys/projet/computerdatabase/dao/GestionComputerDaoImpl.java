package com.excilys.projet.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.projet.computerdatabase.model.Company;
import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.utils.SqlRequestOptions;

public class GestionComputerDaoImpl implements GestionComputerDao {

	/**
	 * Logger
	 */
		final static Logger logger = LoggerFactory.getLogger(GestionComputerDaoImpl.class);
	
	/**
	 * Query
	 */
	private static final String SELECT_ALL_COMPUTERS_QUERY = "select cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpy.id, cpy.name  from computer cpu left join company cpy on cpu.company_id=cpy.id";
	private static final String SELECT_ONE_COMPUTER_BY_ID_QUERY = "select cpu.id, cpu.name, cpu.introduced, cpu.discontinued, cpy.id, cpy.name  from computer cpu left join company cpy on cpu.company_id=cpy.id where cpu.id = ?";
	private static final String INSERT_COMPUTER = "insert into computer (name, introduced, discontinued, company_id) values (?,?,?,?)";
	private static final String DELETE_COMPUTER = "delete from computer where id=?";
	private static final String UPDATE_COMPUTER = "update computer set name = ?, introduced = ?, discontinued = ?, company_id = ? where id =? ";
	private static final String COUNT_COMPUTER = "select count(cpu.id) as count from computer cpu";
	private static final String ID_EXISTS_QUERY = "select count(id) as count from computer where id = ?";
	private static final String SELECT_WHERE = " where cpu.name LIKE ?" ;
	private static final String SELECT_ORDER_BY = " order by ISNULL (%1$s),%1$s %2$s limit %3$s, %4$s";
	
	
	static {
		gestionComputerDao = new GestionComputerDaoImpl();
	}
	
	private static GestionComputerDao gestionComputerDao;
	
	
	private GestionComputerDaoImpl(){
		
	}
	
	public static GestionComputerDao getInstance() {
		return gestionComputerDao;
	}
	
	@Override
	public List<Computer> getComputers(Connection conn, int debut, int nombre, SqlRequestOptions sqlRequestOptions){
		PreparedStatement myPreparedStatement=null;
		List<Computer> liste = new ArrayList<Computer>();
		Formatter f = new Formatter();
		try {
			
			StringBuilder sb = new StringBuilder(SELECT_ALL_COMPUTERS_QUERY);
			if(sqlRequestOptions.getFilter()!=null && !sqlRequestOptions.getFilter().isEmpty()){
				sb.append(SELECT_WHERE);
			}
			f.format(SELECT_ORDER_BY, sqlRequestOptions.getSqlTri(), sqlRequestOptions.getSqlOrder(), debut, nombre);
			sb.append(f.toString());
			myPreparedStatement = conn.prepareStatement(sb.toString());
			if(sqlRequestOptions.getFilter()!=null && !sqlRequestOptions.getFilter().isEmpty()){
				myPreparedStatement.setString(1, sqlRequestOptions.getSqlFilter());
			}
			
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
			logger.warn("Erreur lors de la récupération de la liste des ordinateurs");
		} finally{
			f.close();
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				logger.warn("Erreur lors de la récupération de la liste des ordinateurs (fermeture prepared statement)");
			}
		}
		return liste;
	}
	
	@Override
	public Computer getComputer(Connection conn, int id){
		
		Computer computer= new Computer();
		PreparedStatement myPreparedStatement=null;
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
			logger.warn("Erreur lors de la récupération d'un ordinateur");
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				logger.warn("Erreur lors de la récupération d'un ordinateur (fermeture prepared statement)");
			}
		}
		
		return computer;
	}
	
	@Override
	public Integer getComputerCount(Connection conn, SqlRequestOptions sqlRequestOptions){
		
		Integer count = null;
		PreparedStatement myPreparedStatement=null;
		Formatter f = new Formatter();
		try {
			
			StringBuilder sb = new StringBuilder(COUNT_COMPUTER);
			if(sqlRequestOptions.getFilter()!=null && !sqlRequestOptions.getFilter().isEmpty()){
				sb.append(SELECT_WHERE);
			}
			myPreparedStatement = conn.prepareStatement(sb.toString());
			if(sqlRequestOptions.getFilter()!=null && !sqlRequestOptions.getFilter().isEmpty()){
				myPreparedStatement.setString(1, sqlRequestOptions.getSqlFilter());
			}
			ResultSet rs = myPreparedStatement.executeQuery();
			rs.first();
			count = rs.getInt("count");

		} catch (SQLException e) {
			logger.warn("Erreur lors de la récupération du compte des ordinateurs");
		} finally{
			f.close();
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				logger.warn("Erreur lors de la récupération du compte des ordinateurs (fermeture prepared statement)");
			}
		}
		
		return count;
	}
	
	@Override
	public void deleteComputer(Connection conn, int id){
		PreparedStatement myPreparedStatement=null;
		try {
			myPreparedStatement = conn.prepareStatement(DELETE_COMPUTER);
			myPreparedStatement.setInt(1, id);
			myPreparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			logger.warn("Erreur lors de la suppression d'un ordinateur");
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				logger.warn("Erreur lors de la suppression d'un ordinateur (fermeture prepared statement)");
			}
		}
	}
	
	@Override
	public void updateComputer(Connection conn, Computer computer) {
		PreparedStatement myPreparedStatement=null;
		try {
			myPreparedStatement = conn.prepareStatement(UPDATE_COMPUTER);
			myPreparedStatement.setString(1, computer.getName());
			myPreparedStatement.setDate(2, new java.sql.Date(computer.getIntroduced().getTime()));
			myPreparedStatement.setDate(3, new java.sql.Date(computer.getDiscontinued().getTime()));
			myPreparedStatement.setInt(4, computer.getCompany().getId());
			myPreparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			logger.warn("Erreur lors de l'update d'un ordinateur");
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				logger.warn("Erreur lors de l'update d'un ordinateur (fermeture prepared statement)");
			}
		}
	}
	
	@Override
	public void insertOrUpdateComputer(Connection conn, Computer computer) {
		PreparedStatement myPreparedStatement=null;
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
			if(computer.getCompany()==null){
				myPreparedStatement.setNull(4, Types.NULL);
			} else {
				myPreparedStatement.setInt(4, computer.getCompany().getId());
			}
			myPreparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			logger.warn("Erreur lors de l'inset/update d'un ordinateur");
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				logger.warn("Erreur lors de l'inset/update d'un ordinateur (fermeture prepared statement)");
			}
		}
	}
	
	@Override
	public boolean isComputerExists(Connection conn, int id) {
		PreparedStatement myPreparedStatement=null;
		
		try {
			myPreparedStatement = conn.prepareStatement(ID_EXISTS_QUERY);
			myPreparedStatement.setInt(1, id);
			
			ResultSet rs = myPreparedStatement.executeQuery();
			
			rs.first();
			if(rs.getInt("count")==1) {
				return true;
			}
		} catch (SQLException e) {
			logger.warn("Erreur lors de la récupération d'une société");
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				logger.warn("Erreur lors de la récupération d'une société (fermeture prepared statement)");
			}
		}
		return false;
	}
	
}
