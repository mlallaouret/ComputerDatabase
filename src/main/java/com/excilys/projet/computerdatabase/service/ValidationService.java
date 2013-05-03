package com.excilys.projet.computerdatabase.service;

import com.excilys.projet.computerdatabase.dao.GestionComputerDao;
import com.excilys.projet.computerdatabase.model.Company;
import com.excilys.projet.computerdatabase.model.Computer;

public class ValidationService {

	private GestionComputerDao dao;
	private static ValidationService validationService = null;
	
	private ValidationService() {
		dao = GestionComputerDao.getInstance(); 
	}
	
	public static ValidationService getInstance() {
		
		if(validationService==null) {
			return new ValidationService();
		} else {
			return validationService;
		}
		
		
	}
	
	public Company getCompany(int id){
		return dao.getCompany(id);
	}
	
	public void insertOrUpdate(Computer computer){
		dao.insertOrUpdateComputer(computer);
	}
}
