package com.excilys.projet.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.List;

import com.excilys.projet.computerdatabase.daoapi.GestionComputerDao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.utils.SqlRequestOptions;

@Repository
public class GestionComputerDaoImpl implements GestionComputerDao {
	
	/**
	 * Query
	 */
	private static final String SELECT_ALL_COMPUTERS_QUERY = "from Computer cpu";
	private static final String SELECT_ONE_COMPUTER_BY_ID_QUERY = "from Computer where id = :id";
	private static final String DELETE_COMPUTER = "delete from Computer where id=:id";
	private static final String COUNT_COMPUTER = "select count(id) from Computer cpu";
	private static final String ID_EXISTS_QUERY = "select count(id) from Computer where id = :id";
	private static final String SELECT_WHERE = " where cpu.name LIKE :filter" ;
	private static final String SELECT_ORDER_BY = " order by ISNULL (%1$s),%1$s %2$s";
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Computer> getComputers(final int debut, final int nombre, final SqlRequestOptions sqlRequestOptions){
		
		Formatter f = new Formatter();
		
		StringBuilder sb = new StringBuilder(SELECT_ALL_COMPUTERS_QUERY);
		if(sqlRequestOptions.getFilter()!=null && !sqlRequestOptions.getFilter().isEmpty()){
			sb.append(SELECT_WHERE);
		}
		f.format(SELECT_ORDER_BY, sqlRequestOptions.getSqlTri(), sqlRequestOptions.getSqlOrder());
		sb.append(f.toString());
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		if(sqlRequestOptions.getFilter()!=null && !sqlRequestOptions.getFilter().isEmpty()){
			query.setString("filter", sqlRequestOptions.getSqlFilter());
		}
		query.setFirstResult(debut);
		query.setMaxResults(nombre);
		if(f!=null){
			f.close();
		}
		return (List<Computer>) query.list();
	}
	
	@Override
	public Computer getComputer(final int id){
		return (Computer) sessionFactory.getCurrentSession().createQuery(SELECT_ONE_COMPUTER_BY_ID_QUERY).setParameter("id", id).uniqueResult();
	}
	
	@Override
	public Integer getComputerCount(final SqlRequestOptions sqlRequestOptions){
		
		StringBuilder sb = new StringBuilder(COUNT_COMPUTER);
		if(sqlRequestOptions.getFilter()!=null && !sqlRequestOptions.getFilter().isEmpty()){
			sb.append(SELECT_WHERE);
		}
		Query query = sessionFactory.getCurrentSession().createQuery(sb.toString());
		if(sqlRequestOptions.getFilter()!=null && !sqlRequestOptions.getFilter().isEmpty()){
			query.setString("filter", sqlRequestOptions.getSqlFilter());
		}
		return ((Long)query.uniqueResult()).intValue();
		
	}
	
	@Override
	public boolean deleteComputer(final int id){
		int res=0;
		res = jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement myPreparedStatement=null;
				myPreparedStatement = con.prepareStatement(DELETE_COMPUTER);
				myPreparedStatement.setInt(1, id);
				
				return myPreparedStatement;
			}
		});
		return res == 0 ? false : true;
	}
	
	@Override
	public boolean updateComputer(final Computer computer){	
		
		sessionFactory.getCurrentSession().update(computer);
		return true;
	}
	
	@Override
	public boolean insertComputer(final Computer computer){
		sessionFactory.getCurrentSession().save(computer);
		return true;
	}
	
	@Override
	public boolean ComputerExists(final int id) {
		return ((Long)sessionFactory.getCurrentSession().createQuery(ID_EXISTS_QUERY).setInteger("id", id).uniqueResult()).intValue() == 1 ? true : false;
		
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
}
