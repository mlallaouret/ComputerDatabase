package com.excilys.projet.computerdatabase.service;

import java.util.List;

import com.excilys.projet.computerdatabase.dao.GestionComputerDao;
import com.excilys.projet.computerdatabase.model.Company;
import com.excilys.projet.computerdatabase.model.Computer;

public class GestionComputerService {

	private GestionComputerDao dao;
	private static GestionComputerService gestionComputerService = null;
	
	private GestionComputerService() {
		dao = GestionComputerDao.getInstance(); 
	}
	
	public static GestionComputerService getInstance() {
		
		if(gestionComputerService==null) {
			return new GestionComputerService();
		} else {
			return gestionComputerService;
		}
		
	}
	
	public Company getCompany(int id){
		return dao.getCompany(id);
	}
	
	public void insertOrUpdate(Computer computer){
		dao.insertOrUpdateComputer(computer);
	}
	
	public List<Computer> getComputers(int debut, int nombre){
		return dao.getComputers(debut, nombre);
	}
	
	public Integer getComputerCount(){
		return dao.getComputerCount();
	}
	
	public void updateComputer(Computer c){
		dao.updateComputer(c);
	}
	
	public void deleteComputer(int id){
		dao.deleteComputer(id);
	}
	
	public Computer getComputer(int id){
		return dao.getComputer(id);
	}
	
	public List<Company> getCompanies(){
		return dao.getCompanies();
	}
	
	
	public boolean isIdExists(int id) {
		return dao.isIdExists(id);
	}
	
	
}
