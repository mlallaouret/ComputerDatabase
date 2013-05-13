package com.excilys.projet.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.excilys.projet.computerdatabase.model.Company;
import com.excilys.projet.computerdatabase.utils.JdbcConnexion;

public class GestionCompanyDao {

	
	private static final String SELECT_ALL_COMPANIES_QUERY = "select id, name from company order by name";
	private static final String SELECT_ONE_COMPANY_BY_ID_QUERY = "select id, name from company where id = ?";
	
	static {
		gestionCompanyDao = new GestionCompanyDao();
	}
	
	private static GestionCompanyDao gestionCompanyDao;
	
	
	private GestionCompanyDao(){
		
	}
	
	public static GestionCompanyDao getInstance() {
		return gestionCompanyDao;
	}
	
	public List<Company> getCompanies(){
		PreparedStatement myPreparedStatement=null;
		List<Company> liste = new ArrayList<Company>();
		
		Connection conn = JdbcConnexion.getConnection();
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
			Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la récupération de la liste des sociétés");
			e.printStackTrace();
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la récupération de la liste des sociétés");
				e.printStackTrace();
			}
			JdbcConnexion.closeConnection(conn);
		}
		
		
		
		return liste;
	}
	
	public Company getCompany(int id){
		PreparedStatement myPreparedStatement=null;
		Company company = new Company();
		
		Connection conn = JdbcConnexion.getConnection();
		try {
			myPreparedStatement = conn.prepareStatement(SELECT_ONE_COMPANY_BY_ID_QUERY);
			myPreparedStatement.setInt(1, id);
			
			ResultSet rs = myPreparedStatement.executeQuery();
			
			rs.first();
			company.setId(rs.getInt("id"));
			company.setName(rs.getString("name"));
		} catch (SQLException e) {
			Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la récupération d'une société");
			e.printStackTrace();
		} finally{
			try {
				myPreparedStatement.close();
			} catch (SQLException e) {
				Logger.getLogger("main").log(Level.WARNING, "Erreur lors de la récupération d'une société");
				e.printStackTrace();
			}
			JdbcConnexion.closeConnection(conn);
		}
		
		
		
		return company;
	}
	
}
