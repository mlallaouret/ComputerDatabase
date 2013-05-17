package com.excilys.projet.computerdatabase.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.projet.computerdatabase.model.Company;
import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.model.Page;
import com.excilys.projet.computerdatabase.model.PageEdition;
import com.excilys.projet.computerdatabase.utils.SqlRequestOptions;

public interface GestionComputerService {

	Company getCompany(int id) throws SQLException;

	void insertOrUpdate(Computer computer) throws SQLException;

	List<Computer> getComputers(int debut, int nombre,
			SqlRequestOptions sqlRequestOptions) throws SQLException;

	Integer getComputerCount(SqlRequestOptions sqlRequestOptions) throws SQLException;

	void updateComputer(Computer c) throws SQLException;

	void deleteComputer(int id) throws SQLException;

	Computer getComputer(int id) throws SQLException;

	List<Company> getCompanies() throws SQLException;

	boolean isComputerExists(int id) throws SQLException;

	Page createPage(int page, int maxAffichage,
			SqlRequestOptions sqlRequestOptions) throws SQLException;
	
	PageEdition createPageEdition(int idComputer) throws SQLException;

}