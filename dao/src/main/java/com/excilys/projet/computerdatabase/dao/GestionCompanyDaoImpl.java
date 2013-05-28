package com.excilys.projet.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.projet.computerdatabase.daoapi.GestionCompanyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.projet.computerdatabase.model.Company;

@Repository
@Transactional(readOnly = true)
public class GestionCompanyDaoImpl implements GestionCompanyDao {
	
	/**
	 * Query
	 */
	private static final String SELECT_ALL_COMPANIES_QUERY = "select id, name from company order by name";
	private static final String SELECT_ONE_COMPANY_BY_ID_QUERY = "select id, name from company where id = ?";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public GestionCompanyDaoImpl(){
		
	}
	
	@Override
	public List<Company> getCompanies(){
		List<Company> liste = new ArrayList<Company>();
	
		liste = jdbcTemplate.query(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				return con.prepareStatement(SELECT_ALL_COMPANIES_QUERY);
			}
		}, new RowCompany());

		return liste;
	}
	
	@Override
	public Company getCompany(final int id){
		List<Company> liste = new ArrayList<Company>();
		
		liste = jdbcTemplate.query(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement myPreparedStatement = con.prepareStatement(SELECT_ONE_COMPANY_BY_ID_QUERY);
				myPreparedStatement.setInt(1, id);
				return myPreparedStatement;
			}
		}, new RowCompany());

		return liste.get(0);
	}
	
	private class RowCompany implements RowMapper<Company>{
		@Override
		public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
			Company c = new Company();
			c.setId(rs.getInt("id"));
			c.setName(rs.getString("name"));
			return c;
		}
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}