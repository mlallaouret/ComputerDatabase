package com.excilys.projet.computerdatabase.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.excilys.projet.computerdatabase.model.Company;

public interface GestionCompanyDao {

	List<Company> getCompanies(Connection conn) throws SQLException;

	Company getCompany(Connection conn, int id) throws SQLException;

}