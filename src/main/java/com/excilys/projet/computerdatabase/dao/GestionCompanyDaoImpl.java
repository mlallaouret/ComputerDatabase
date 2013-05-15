package com.excilys.projet.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.projet.computerdatabase.model.Company;

public class GestionCompanyDaoImpl implements GestionCompanyDao {

	/**
	 * Logger
	 */
	final static Logger logger = LoggerFactory.getLogger(GestionCompanyDaoImpl.class);
	
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
	public List<Company> getCompanies(Connection conn){
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
		} catch (SQLException e) {
			logger.warn("Erreur lors de la récupération de la liste des sociétés");
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				logger.warn("Erreur lors de la récupération de la liste des sociétés (fermeture prepared statement)");
			}
		}
		
		
		
		return liste;
	}
	
	@Override
	public Company getCompany(Connection conn, int id){
		PreparedStatement myPreparedStatement=null;
		Company company = new Company();
		
		try {
			myPreparedStatement = conn.prepareStatement(SELECT_ONE_COMPANY_BY_ID_QUERY);
			myPreparedStatement.setInt(1, id);
			
			ResultSet rs = myPreparedStatement.executeQuery();
			
			rs.first();
			company.setId(rs.getInt("id"));
			company.setName(rs.getString("name"));
		} catch (SQLException e) {
			logger.warn("Erreur lors de la récupération d'une société");
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				logger.warn("Erreur lors de la récupération d'une société (fermeture prepared statement)");
			}
		}
		
		
		
		return company;
	}
	
}
