package com.excilys.projet.computerdatabase.service;

import java.sql.Connection;
import java.util.List;

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
		Company c = companyDao.getCompany(conn, id);
		JdbcConnexion.closeConnection(conn);
		return c;
	}
	
	@Override
	public void insertOrUpdate(Computer computer){
		Connection conn = JdbcConnexion.getConnection();
		computerDao.insertOrUpdateComputer(conn, computer);
		JdbcConnexion.closeConnection(conn);
	}
	
	@Override
	public List<Computer> getComputers(int debut, int nombre, SqlRequestOptions sqlRequestOptions){
		Connection conn = JdbcConnexion.getConnection();
		List<Computer> computers = computerDao.getComputers(conn, debut, nombre, sqlRequestOptions);
		JdbcConnexion.closeConnection(conn);
		return computers;
	}

	@Override
	public Integer getComputerCount(SqlRequestOptions sqlRequestOptions){
		Connection conn = JdbcConnexion.getConnection();
		Integer i = computerDao.getComputerCount(conn, sqlRequestOptions);
		JdbcConnexion.closeConnection(conn);
		
		return i;
	}
	
	@Override
	public void updateComputer(Computer c){
		Connection conn = JdbcConnexion.getConnection();
		computerDao.updateComputer(conn, c);
		JdbcConnexion.closeConnection(conn);
	}
	
	@Override
	public void deleteComputer(int id){
		Connection conn = JdbcConnexion.getConnection();
		computerDao.deleteComputer(conn, id);
		JdbcConnexion.closeConnection(conn);
	}
	
	@Override
	public Computer getComputer(int id){
		Connection conn = JdbcConnexion.getConnection();
		Computer c = computerDao.getComputer(conn, id);
		JdbcConnexion.closeConnection(conn);
		return c;
	}
	
	@Override
	public List<Company> getCompanies(){
		Connection conn = JdbcConnexion.getConnection();
		List<Company> companies = companyDao.getCompanies(conn);
		JdbcConnexion.closeConnection(conn);
		return companies;
	}
	
	@Override
	public boolean isComputerExists(int id) {
		Connection conn = JdbcConnexion.getConnection();
		boolean b = computerDao.isComputerExists(conn, id);
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
