package com.excilys.projet.computerdatabase.service;

import java.util.List;

import com.excilys.projet.computerdatabase.dao.GestionCompanyDao;
import com.excilys.projet.computerdatabase.dao.GestionComputerDao;
import com.excilys.projet.computerdatabase.model.Company;
import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.model.Page;
import com.excilys.projet.computerdatabase.utils.SqlRequestOptions;

public class GestionComputerService {

	private GestionComputerDao computerDao;
	private GestionCompanyDao companyDao;
	private static GestionComputerService gestionComputerService = null;
	
	private GestionComputerService() {
		computerDao = GestionComputerDao.getInstance(); 
		companyDao = GestionCompanyDao.getInstance(); 
	}
	
	public static GestionComputerService getInstance() {
		
		if(gestionComputerService==null) {
			return new GestionComputerService();
		} else {
			return gestionComputerService;
		}
		
	}
	
	public Company getCompany(int id){
		return companyDao.getCompany(id);
	}
	
	public void insertOrUpdate(Computer computer){
		computerDao.insertOrUpdateComputer(computer);
	}
	
	public List<Computer> getComputers(int debut, int nombre, SqlRequestOptions sqlRequestOptions){
		return computerDao.getComputers(debut, nombre, sqlRequestOptions);
	}
	
	public Integer getComputerCount(SqlRequestOptions sqlRequestOptions){
		return computerDao.getComputerCount(sqlRequestOptions);
	}
	
	public void updateComputer(Computer c){
		computerDao.updateComputer(c);
	}
	
	public void deleteComputer(int id){
		computerDao.deleteComputer(id);
	}
	
	public Computer getComputer(int id){
		return computerDao.getComputer(id);
	}
	
	public List<Company> getCompanies(){
		return companyDao.getCompanies();
	}
	
	
	public boolean isComputerExists(int id) {
		return computerDao.isComputerExists(id);
	}
	
	public Page createPage(int page, int maxAffichage, SqlRequestOptions sqlRequestOptions){
		int total = getComputerCount(sqlRequestOptions);
		int pageNumber = 0;
		if(page<0 || (page>0 && (total - page*maxAffichage<0))){
			pageNumber=0;
		} else {
			pageNumber = page;
		}
		
		return new Page(pageNumber, maxAffichage, getComputerCount(sqlRequestOptions),
				getComputers(pageNumber, maxAffichage, sqlRequestOptions));
	}
	
	
}
