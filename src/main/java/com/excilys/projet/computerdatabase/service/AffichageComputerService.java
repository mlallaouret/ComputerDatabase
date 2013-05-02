package com.excilys.projet.computerdatabase.service;

import java.util.List;

import com.excilys.projet.computerdatabase.dao.GestionComputerDao;
import com.excilys.projet.computerdatabase.model.Computer;

public class AffichageComputerService {

	private GestionComputerDao dao;
	private final static int MAX_AFFICHAGE = 10;
	private static AffichageComputerService affichageComputerService = null;
	
	private AffichageComputerService() {
		dao = GestionComputerDao.getInstance(); 
	}
	
	public static AffichageComputerService getInstance() {
		if(affichageComputerService==null) {
			return new AffichageComputerService();
		} else {
			return affichageComputerService;
		}
	}
	
	public List<Computer> getComputers(int debut){
		return dao.getComputers(debut, MAX_AFFICHAGE);
	}
	
	public Integer getComputerCount(){
		return dao.getComputerCount();
	}
	
	
}
