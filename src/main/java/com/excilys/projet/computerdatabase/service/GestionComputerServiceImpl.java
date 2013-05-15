package com.excilys.projet.computerdatabase.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.projet.computerdatabase.dao.GestionCompanyDao;
import com.excilys.projet.computerdatabase.dao.GestionCompanyDaoImpl;
import com.excilys.projet.computerdatabase.dao.GestionComputerDao;
import com.excilys.projet.computerdatabase.dao.GestionComputerDaoImpl;
import com.excilys.projet.computerdatabase.model.Company;
import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.model.Page;
import com.excilys.projet.computerdatabase.utils.JdbcConnexion;
import com.excilys.projet.computerdatabase.utils.SqlRequestOptions;

public class GestionComputerServiceImpl implements GestionComputerService {

	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(GestionComputerServiceImpl.class);
	
	private GestionComputerDao computerDao;
	private GestionCompanyDao companyDao;
	private static GestionComputerService gestionComputerService = null;
	
	private GestionComputerServiceImpl() {
		computerDao = GestionComputerDaoImpl.getInstance(); 
		companyDao = GestionCompanyDaoImpl.getInstance(); 
	}
	
	public static GestionComputerService getInstance() {
		
		if(gestionComputerService==null) {
			return new GestionComputerServiceImpl();
		} else {
			return gestionComputerService;
		}
		
	}
	
	@Override
	public Company getCompany(int id){
		Connection conn = JdbcConnexion.getConnection();
		Company c = null;
		try {
			c = companyDao.getCompany(conn, id);
		} catch(SQLException e) {
			
		}
		JdbcConnexion.closeConnection(conn);
		return c;
	}
	
	@Override
	public void insertOrUpdate(Computer computer){
		Connection conn = JdbcConnexion.getConnection();
		try {
			computerDao.insertOrUpdateComputer(conn, computer);
		} catch(SQLException e) {
			logger.warn("Erreur lors de l'inset/update d'un ordinateur");
		}
		JdbcConnexion.closeConnection(conn);
	}
	
	@Override
	public List<Computer> getComputers(int debut, int nombre, SqlRequestOptions sqlRequestOptions){
		Connection conn = JdbcConnexion.getConnection();
		List<Computer> computers = null;
		try{
			computers = computerDao.getComputers(conn, debut, nombre, sqlRequestOptions);
		} catch (SQLException e){
			logger.warn("Erreur lors de la récupération de la liste des ordinateurs");
		}
		JdbcConnexion.closeConnection(conn);
		return computers;
	}

	@Override
	public Integer getComputerCount(SqlRequestOptions sqlRequestOptions){
		Connection conn = JdbcConnexion.getConnection();
		Integer i = null;
		try {
			i = computerDao.getComputerCount(conn, sqlRequestOptions);
		} catch(SQLException e){
			logger.warn("Erreur lors de la récupération du compte des ordinateurs");
		}
		JdbcConnexion.closeConnection(conn);
		
		return i;
	}
	
	@Override
	public void updateComputer(Computer c){
		Connection conn = JdbcConnexion.getConnection();
		try {
			computerDao.updateComputer(conn, c);
		} catch(SQLException e) {
			logger.warn("Erreur lors de l'update d'un ordinateur");
		}
		JdbcConnexion.closeConnection(conn);
	}
	
	@Override
	public void deleteComputer(int id){
		Connection conn = JdbcConnexion.getConnection();
		try{
			computerDao.deleteComputer(conn, id);
		} catch(SQLException e){
			logger.warn("Erreur lors de la suppression d'un ordinateur");
		}
		JdbcConnexion.closeConnection(conn);
	}
	
	@Override
	public Computer getComputer(int id){
		Connection conn = JdbcConnexion.getConnection();
		Computer c = null;
		try {
			c = computerDao.getComputer(conn, id);
		} catch (SQLException e) {
			logger.warn("Erreur lors de la récupération d'un ordinateur");
		}
		JdbcConnexion.closeConnection(conn);
		return c;
	}
	
	@Override
	public List<Company> getCompanies(){
		Connection conn = JdbcConnexion.getConnection();
		List<Company> companies = null;
		try {
			companies = companyDao.getCompanies(conn);
		} catch (SQLException e){
			logger.warn("Erreur lors de la récupération de la liste des sociétés");
		}
		JdbcConnexion.closeConnection(conn);
		return companies;
	}
	
	@Override
	public boolean isComputerExists(int id) {
		Connection conn = JdbcConnexion.getConnection();
		
		boolean b = false;
		try {
			b = computerDao.isComputerExists(conn, id);
		} catch(SQLException e){
			logger.warn("Erreur lors de la récupération d'une société");
		}
		JdbcConnexion.closeConnection(conn);
		return b;
	}

	@Override
	public Page createPage(int page, int maxAffichage, SqlRequestOptions sqlRequestOptions){
		int total = getComputerCount(sqlRequestOptions);
		if(page<0 || (page>0 && (total - page*maxAffichage<0))){
			page=0;
		}
		return new Page(page, maxAffichage, total,
				getComputers(page*maxAffichage, maxAffichage, sqlRequestOptions));
	}
	
	
}
