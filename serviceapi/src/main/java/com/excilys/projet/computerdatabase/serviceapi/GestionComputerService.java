package com.excilys.projet.computerdatabase.serviceapi;

import java.util.List;

import com.excilys.projet.computerdatabase.model.Company;
import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.model.Page;
import com.excilys.projet.computerdatabase.model.PageEdition;
import com.excilys.projet.computerdatabase.utils.SqlRequestOptions;

public interface GestionComputerService {

	Company getCompany(int id);

	void insertOrUpdate(Computer computer);

	List<Computer> getComputers(int debut, int nombre,
			SqlRequestOptions sqlRequestOptions);

	Integer getComputerCount(SqlRequestOptions sqlRequestOptions);

	void updateComputer(Computer c);

	void deleteComputer(int id);

	Computer getComputer(int id);

	List<Company> getCompanies();

	boolean isComputerExists(int id);

	Page createPage(int page, int maxAffichage,
			SqlRequestOptions sqlRequestOptions);
	
	PageEdition createPageEdition(int idComputer);

}