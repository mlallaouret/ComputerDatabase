package com.excilys.projet.computerdatabase.dao;

import java.util.List;

import com.excilys.projet.computerdatabase.daoapi.GestionCompanyDao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.projet.computerdatabase.model.Company;

@Repository
public class GestionCompanyDaoImpl implements GestionCompanyDao {
	
	/**
	 * Query
	 */
	private static final String SELECT_ALL_COMPANIES_QUERY = "from Company order by name";
	private static final String SELECT_ONE_COMPANY_BY_ID_QUERY = "from Company where id = ?";
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getCompanies(){
		 return (List<Company>) sessionFactory.getCurrentSession().createQuery(SELECT_ALL_COMPANIES_QUERY).list();
	}
	
	@Override
	public Company getCompany(final int id){
		return (Company) sessionFactory.getCurrentSession().createQuery(SELECT_ONE_COMPANY_BY_ID_QUERY).setParameter(0, id).uniqueResult();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}