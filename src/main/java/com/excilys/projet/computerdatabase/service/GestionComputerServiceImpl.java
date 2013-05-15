package com.excilys.projet.computerdatabase.service;

import java.sql.SQLException;
import java.util.ArrayList;
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
import com.excilys.projet.computerdatabase.model.PageEdition;
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
		Company c = null;
		try {
			c = companyDao.getCompany(id);
			JdbcConnexion.getInstance().getConnection().commit();
		} catch(SQLException e) {
			logger.warn("Erreur lors de la récupération d'une compagnie");
			try {
				JdbcConnexion.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Erreur lors de l'annulation de la requete de récupération d'une compagnie");
			}
			
		}
		JdbcConnexion.getInstance().closeConnection();
		return c;
	}
	
	@Override
	public void insertOrUpdate(Computer computer){
		try {
			computerDao.insertOrUpdateComputer(computer);
			JdbcConnexion.getInstance().getConnection().commit();
		} catch(SQLException e) {
			logger.warn("Erreur lors de l'insert/update d'un ordinateur");
			try {
				JdbcConnexion.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Erreur lors de l'annulation de la requete de l'insert/update d'un ordinateur");
			}
			
		}
		JdbcConnexion.getInstance().closeConnection();
	}
	
	@Override
	public List<Computer> getComputers(int debut, int nombre, SqlRequestOptions sqlRequestOptions){
		List<Computer> computers = null;
		try{
			computers = computerDao.getComputers(debut, nombre, sqlRequestOptions);
			JdbcConnexion.getInstance().getConnection().commit();
		} catch (SQLException e){
			logger.warn("Erreur lors de la récupération de la liste des ordinateurs");
			try {
				JdbcConnexion.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Erreur lors de l'annulation de la requete de la récupération de la liste des ordinateurs");
			}
		}
		JdbcConnexion.getInstance().closeConnection();
		return computers;
	}

	@Override
	public Integer getComputerCount(SqlRequestOptions sqlRequestOptions){
		Integer i = null;
		try {
			i = computerDao.getComputerCount(sqlRequestOptions);
			JdbcConnexion.getInstance().getConnection().commit();
		} catch(SQLException e){
			logger.warn("Erreur lors de la récupération du compte des ordinateurs");
			try {
				JdbcConnexion.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Erreur lors de l'annulation de la requete de la récupération du compte des ordinateurs");
			}
		}
		JdbcConnexion.getInstance().closeConnection();
		
		return i;
	}
	
	@Override
	public void updateComputer(Computer c){
		try {
			computerDao.updateComputer(c);
			JdbcConnexion.getInstance().getConnection().commit();
		} catch(SQLException e) {
			logger.warn("Erreur lors de l'update d'un ordinateur");
			try {
				JdbcConnexion.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Erreur lors de l'annulation de la requete de l'update d'un ordinateur");
			}
		}
		JdbcConnexion.getInstance().closeConnection();
	}
	
	@Override
	public void deleteComputer(int id){
		try{
			computerDao.deleteComputer(id);
			JdbcConnexion.getInstance().getConnection().commit();
		} catch(SQLException e){
			logger.warn("Erreur lors de la suppression d'un ordinateur");
			try {
				JdbcConnexion.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Erreur lors de l'annulation de la suppression d'un ordinateur");
			}
		}
		JdbcConnexion.getInstance().closeConnection();
	}
	
	@Override
	public Computer getComputer(int id){
		Computer c = null;
		try {
			c = computerDao.getComputer(id);
			JdbcConnexion.getInstance().getConnection().commit();
		} catch (SQLException e) {
			logger.warn("Erreur lors de la récupération d'un ordinateur");
			try {
				JdbcConnexion.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Erreur lors de l'annulation de la récupération d'un ordinateur");
			}
		} 
		JdbcConnexion.getInstance().closeConnection();
		
		return c;
	}
	
	@Override
	public List<Company> getCompanies(){
		List<Company> companies = null;
		try {
			companies = companyDao.getCompanies();
			JdbcConnexion.getInstance().getConnection().commit();
		} catch (SQLException e){
			logger.warn("Erreur lors de la récupération de la liste des sociétés");
			try {
				JdbcConnexion.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Erreur lors de l'annulation de la récupération de la liste des sociétés");
			}
		}
		JdbcConnexion.getInstance().closeConnection();
		return companies;
	}
	
	@Override
	public boolean isComputerExists(int id) {		
		boolean b = false;
		try {
			b = computerDao.isComputerExists(id);
			JdbcConnexion.getInstance().getConnection().commit();
		} catch(SQLException e){
			logger.warn("Erreur lors de la récupération d'une société");
			try {
				JdbcConnexion.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Erreur lors de l'annulation de la récupération d'une société");
			}
		}
		JdbcConnexion.getInstance().closeConnection();
		return b;
	}

	@Override
	public Page createPage(int page, int maxAffichage, SqlRequestOptions sqlRequestOptions){
		int total = 0;
		List<Computer> computers = new ArrayList<Computer>();
		
		try {
			computers = computerDao.getComputers(page*maxAffichage, maxAffichage, sqlRequestOptions);
			total = computerDao.getComputerCount(sqlRequestOptions);
			JdbcConnexion.getInstance().getConnection().commit();
		} catch(SQLException e){
			logger.warn("Erreur lors de la création d'une page" + e.getMessage());
			try {
				JdbcConnexion.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Erreur lors de l'annulation de la création d'une page");
			}
		}
		JdbcConnexion.getInstance().closeConnection();
		if(page<0 || (page>0 && (total - page*maxAffichage<0))){
			page=0;
		}
		return new Page(page, maxAffichage, total,
				computers);
	}

	@Override
	public PageEdition createPageEdition(int idComputer) {
		Computer computer = null;
		List<Company> companies = new ArrayList<Company>();
		
		try {
			computer = computerDao.getComputer(idComputer);
			companies = companyDao.getCompanies();
			JdbcConnexion.getInstance().getConnection().commit();
		} catch(SQLException e){
			logger.warn("Erreur lors de la création de la page d'édition " + e.getMessage());
			try {
				JdbcConnexion.getInstance().getConnection().rollback();
			} catch (SQLException e1) {
				logger.error("Erreur lors de l'annulation de la page d'édition");
			}
		}
		JdbcConnexion.getInstance().closeConnection();
		return new PageEdition(computer, companies);
	}
	
	
}
