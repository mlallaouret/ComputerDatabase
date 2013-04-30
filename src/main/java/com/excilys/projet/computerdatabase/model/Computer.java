package com.excilys.projet.computerdatabase.model;

import java.util.Calendar;

public class Computer {

	private int id;
	private String name;
	private Calendar introduced;
	private Calendar discontinued;
	private Company company;
	
	
	
	public Computer(int id, String name, Calendar introduced,
			Calendar discontinued, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Calendar getIntroduced() {
		return introduced;
	}
	public void setIntroduced(Calendar introduced) {
		this.introduced = introduced;
	}
	public Calendar getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(Calendar discontinued) {
		this.discontinued = discontinued;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
}
