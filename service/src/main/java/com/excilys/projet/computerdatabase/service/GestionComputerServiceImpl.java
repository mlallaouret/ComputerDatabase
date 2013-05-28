package com.excilys.projet.computerdatabase.service;

import java.util.ArrayList;
import java.util.List;

import com.excilys.projet.computerdatabase.daoapi.GestionCompanyDao;
import com.excilys.projet.computerdatabase.daoapi.GestionComputerDao;
import com.excilys.projet.computerdatabase.serviceapi.GestionComputerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.projet.computerdatabase.model.Company;
import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.model.Page;
import com.excilys.projet.computerdatabase.model.PageEdition;
import com.excilys.projet.computerdatabase.utils.SqlRequestOptions;

@Service
@Transactional(readOnly = true)
public class GestionComputerServiceImpl implements GestionComputerService {

	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(GestionComputerServiceImpl.class);
	
	@Autowired
	private GestionComputerDao computerDao;
	@Autowired
	private GestionCompanyDao companyDao;
	
	public GestionComputerServiceImpl() {

	}
	
	
	@Override
	public Company getCompany(int id){
		Company c = null;
		try {	
			if(computerDao.isComputerExists(id)){
				c = companyDao.getCompany(id);
			} else {
				throw new IllegalArgumentException("l'id de l'ordinateur n'existe pas.");
			}
		} catch(DataAccessException e) {
			logger.warn("Erreur lors de la récupération d'une compagnie" + e.getMessage());
			throw e;
		}
		return c;
	}
	
	@Override
	@Transactional(readOnly = false)
	public void insertOrUpdate(Computer computer){
		int result = 0;
		try {
			if(computer.getId()!=0){
				result = computerDao.updateComputer(computer);
			} else {
				result = computerDao.insertComputer(computer);
			}
			if (result == 0) {
				throw new IllegalArgumentException("Erreur lors de l'insert/update de l'ordinateur");
			}
		} catch(DataAccessException e) {
			logger.warn("Erreur lors de l'insert/update d'un ordinateur" + e.getMessage());
			throw e;
		}
	}
	
	@Override
	public List<Computer> getComputers(int debut, int nombre, SqlRequestOptions sqlRequestOptions){
		List<Computer> computers = null;
		try{
			computers = computerDao.getComputers(debut, nombre, sqlRequestOptions);
		} catch (DataAccessException e){
			logger.warn("Erreur lors de la récupération de la liste des ordinateurs" + e.getMessage());
			throw e;
		}
		return computers;
	}

	@Override
	public Integer getComputerCount(SqlRequestOptions sqlRequestOptions){
		Integer i = null;
		try {
			i = computerDao.getComputerCount(sqlRequestOptions);
		} catch(DataAccessException e){
			logger.warn("Erreur lors de la récupération du compte des ordinateurs" + e.getMessage());
			throw e;
		}
		
		return i;
	}
	
	@Override
	@Transactional(readOnly = false)
	public void updateComputer(Computer c){
		try {
			computerDao.updateComputer(c);
		} catch(DataAccessException e) {
			logger.warn("Erreur lors de l'update d'un ordinateur" + e.getMessage());
			throw e;
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deleteComputer(int id){
		try{
			if(computerDao.isComputerExists(id)) {
				computerDao.deleteComputer(id);
			} else {
				throw new IllegalArgumentException("L'id de l'ordinateur n'existe pas.");
			}
		} catch(DataAccessException e){
			logger.warn("Erreur lors de la suppression d'un ordinateur" + e.getMessage());
			throw e;
		}
	}
	
	@Override
	public Computer getComputer(int id){
		Computer c = null;
		try {
			c = computerDao.getComputer(id);
		} catch (DataAccessException e) {
			logger.warn("Erreur lors de la récupération d'un ordinateur" + e.getMessage());
			throw e;
		}
		
		return c;
	}
	
	@Override
	public List<Company> getCompanies(){
		List<Company> companies = null;
		try {
			companies = companyDao.getCompanies();
		} catch (DataAccessException e){
			logger.warn("Erreur lors de la récupération de la liste des sociétés" + e.getMessage());
			throw e;
		}
		return companies;
	}
	
	@Override
	public boolean isComputerExists(int id){		
		boolean b = false;
		try {
			b = computerDao.isComputerExists(id);
		} catch(DataAccessException e){
			logger.warn("Erreur lors de la récupération d'une société" + e.getMessage());
			throw e;
		}
		return b;
	}

	@Override
	public Page createPage(int page, int maxAffichage, SqlRequestOptions sqlRequestOptions){
		int total = 0;
		List<Computer> computers = new ArrayList<Computer>();
		
		try {
			total = computerDao.getComputerCount(sqlRequestOptions);
			if(page<0 || (page>0 && (total - page*maxAffichage<0))){
				page=0;
			}
			computers = computerDao.getComputers(page*maxAffichage, maxAffichage, sqlRequestOptions);
		} catch(DataAccessException e){
			logger.warn("Erreur lors de la création d'une page" + e.getMessage());
			throw e;
		}
		
		return new Page(page, maxAffichage, total,
				computers);
	}

	@Override
	public PageEdition createPageEdition(int idComputer){
		Computer computer = null;
		List<Company> companies = new ArrayList<Company>();
		
		try {
			if(computerDao.isComputerExists(idComputer)){
				computer = computerDao.getComputer(idComputer);
				companies = companyDao.getCompanies();
			} else {
				throw new IllegalArgumentException("l'id de l'ordinateur n'existe pas.");
			}
			
		} catch(DataAccessException e){
			logger.warn("Erreur lors de la création de la page d'édition " + e.getMessage());
			throw e;
		}
		return new PageEdition(computer, companies);
	}

	public GestionComputerDao getComputerDao() {
		return computerDao;
	}

	public void setComputerDao(GestionComputerDao computerDao) {
		this.computerDao = computerDao;
	}

	public GestionCompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(GestionCompanyDao companyDao) {
		this.companyDao = companyDao;
	}
	
}
