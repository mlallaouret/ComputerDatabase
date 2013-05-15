package com.excilys.projet.computerdatabase.dao;

import java.sql.Connection;
import java.util.List;

import com.excilys.projet.computerdatabase.model.Company;

public interface GestionCompanyDao {

	public abstract List<Company> getCompanies(Connection conn);

	public abstract Company getCompany(Connection conn, int id);

}