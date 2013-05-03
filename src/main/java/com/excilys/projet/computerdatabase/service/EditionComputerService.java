package com.excilys.projet.computerdatabase.service;

import java.util.List;

import com.excilys.projet.computerdatabase.dao.GestionComputerDao;
import com.excilys.projet.computerdatabase.model.Company;
import com.excilys.projet.computerdatabase.model.Computer;

public class EditionComputerService {

	
	private GestionComputerDao dao;
	private static EditionComputerService editionComputerService = null;
	
	private EditionComputerService() {
		dao = GestionComputerDao.getInstance(); 
	}
	
	public static EditionComputerService getInstance() {
		
		if(editionComputerService==null) {
			return new EditionComputerService();
		} else {
			return editionComputerService;
		}
		
		
	}
	
	public void updateComputer(Computer c){
		dao.updateComputer(c);
	}
	
	public void deleteComputer(Computer c){
		dao.deleteComputer(c);
	}
	
	public Computer getComputer(int id){
		return dao.getComputer(id);
	}
	
	public List<Company> getCompanies(){
		return dao.getCompanies();
	}

	
}