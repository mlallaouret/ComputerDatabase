package com.excilys.projet.computerdatabase.service;

import java.util.List;

import com.excilys.projet.computerdatabase.model.Company;
import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.model.Page;
import com.excilys.projet.computerdatabase.utils.SqlRequestOptions;

public interface GestionComputerService {

	public abstract Company getCompany(int id);

	public abstract void insertOrUpdate(Computer computer);

	public abstract List<Computer> getComputers(int debut, int nombre,
			SqlRequestOptions sqlRequestOptions);

	public abstract Integer getComputerCount(SqlRequestOptions sqlRequestOptions);

	public abstract void updateComputer(Computer c);

	public abstract void deleteComputer(int id);

	public abstract Computer getComputer(int id);

	public abstract List<Company> getCompanies();

	public abstract boolean isComputerExists(int id);

	public abstract Page createPage(int page, int maxAffichage,
			SqlRequestOptions sqlRequestOptions);

}