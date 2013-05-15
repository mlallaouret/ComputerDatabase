package com.excilys.projet.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.projet.computerdatabase.model.Company;

public class GestionCompanyDaoImpl implements GestionCompanyDao {
	
	/**
	 * Query
	 */
	private static final String SELECT_ALL_COMPANIES_QUERY = "select id, name from company order by name";
	private static final String SELECT_ONE_COMPANY_BY_ID_QUERY = "select id, name from company where id = ?";
	
	static {
		gestionCompanyDao = new GestionCompanyDaoImpl();
	}
	
	private static GestionCompanyDao gestionCompanyDao;
	
	
	private GestionCompanyDaoImpl(){
		
	}
	
	public static GestionCompanyDao getInstance() {
		return gestionCompanyDao;
	}
	
	@Override
	public List<Company> getCompanies(Connection conn) throws SQLException{
		PreparedStatement myPreparedStatement=null;
		List<Company> liste = new ArrayList<Company>();
		
		try {
			myPreparedStatement = conn.prepareStatement(SELECT_ALL_COMPANIES_QUERY);
			
			ResultSet rs = myPreparedStatement.executeQuery();
			
			while(rs.next()){
				Company cpy = new Company();
				cpy.setId(rs.getInt("id"));
				cpy.setName(rs.getString("name"));
				liste.add(cpy);
			}
		} finally {
			myPreparedStatement.close();
		}

		return liste;
	}
	
	@Override
	public Company getCompany(Connection conn, int id) throws SQLException{
		PreparedStatement myPreparedStatement=null;
		Company company = new Company();
		
		try {
			myPreparedStatement = conn.prepareStatement(SELECT_ONE_COMPANY_BY_ID_QUERY);
			myPreparedStatement.setInt(1, id);
			
			ResultSet rs = myPreparedStatement.executeQuery();
			
			rs.first();
			company.setId(rs.getInt("id"));
			company.setName(rs.getString("name"));
		} finally {
			myPreparedStatement.close();
		}
		
		
		
		return company;
	}
	
}
