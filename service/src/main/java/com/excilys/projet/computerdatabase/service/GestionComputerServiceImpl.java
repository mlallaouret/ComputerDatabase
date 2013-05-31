package com.excilys.projet.computerdatabase.service;

import java.util.ArrayList;
import java.util.List;

import com.excilys.projet.computerdatabase.daoapi.GestionCompanyDao;
import com.excilys.projet.computerdatabase.daoapi.GestionComputerDao;
import com.excilys.projet.computerdatabase.serviceapi.GestionComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.excilys.projet.computerdatabase.model.Company;
import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.model.Page;
import com.excilys.projet.computerdatabase.model.PageEdition;
import com.excilys.projet.computerdatabase.utils.SqlRequestOptions;

@Service
@Transactional(readOnly = true)
public class GestionComputerServiceImpl implements GestionComputerService {
	
	@Autowired
	private GestionComputerDao computerDao;
	@Autowired
	private GestionCompanyDao companyDao;	
	
	@Override
	public Company getCompany(int id){
		Assert.isTrue(computerDao.ComputerExists(id),"l'id de l'ordinateur n'existe pas.");
		return companyDao.getCompany(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public boolean insertOrUpdate(Computer computer){
		boolean result;
		if(computer.getId()!=0){
			result = computerDao.updateComputer(computer);
		} else {
			result = computerDao.insertComputer(computer);
		}
		return result;
	}
	
	@Override
	public List<Computer> getComputers(int debut, int nombre, SqlRequestOptions sqlRequestOptions){
		List<Computer> computers = null;
		computers = computerDao.getComputers(debut, nombre, sqlRequestOptions);
		return computers;
	}

	@Override
	public Integer getComputerCount(SqlRequestOptions sqlRequestOptions){
		return computerDao.getComputerCount(sqlRequestOptions);	
	}
	
	@Override
	@Transactional(readOnly = false)
	public boolean updateComputer(Computer c){
		return computerDao.updateComputer(c);
	}
	
	@Override
	@Transactional(readOnly = false)
	public boolean deleteComputer(int id){
		Assert.isTrue(computerDao.ComputerExists(id), "L'id de l'ordinateur n'existe pas.");
		return computerDao.deleteComputer(id);
	}
	
	@Override
	public Computer getComputer(int id){
		Computer c = computerDao.getComputer(id);
		Assert.notNull(c, "L'ordinateur n esxiste pas !");
		return c;
	}
	
	@Override
	public List<Company> getCompanies(){
		return companyDao.getCompanies();
	}
	
	@Override
	public boolean ComputerExists(int id){		
		return computerDao.ComputerExists(id);
	}

	@Override
	public Page createPage(int page, int maxAffichage, SqlRequestOptions sqlRequestOptions){
		int total = 0;
		List<Computer> computers = new ArrayList<Computer>();
		total = computerDao.getComputerCount(sqlRequestOptions);
		if(page<0 || (page>0 && (total - page*maxAffichage<0))){
			page=0;
		}
		computers = computerDao.getComputers(page*maxAffichage, maxAffichage, sqlRequestOptions);
		return new Page(page, maxAffichage, total,
				computers);
	}

	@Override
	public PageEdition createPageEdition(int idComputer){
		Assert.isTrue(computerDao.ComputerExists(idComputer), "l'id de l'ordinateur n'existe pas.");
		return new PageEdition(computerDao.getComputer(idComputer), companyDao.getCompanies());
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
