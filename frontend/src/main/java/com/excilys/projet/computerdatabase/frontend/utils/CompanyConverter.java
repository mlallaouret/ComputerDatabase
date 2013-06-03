package com.excilys.projet.computerdatabase.frontend.utils;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.projet.computerdatabase.model.Company;
import com.excilys.projet.computerdatabase.serviceapi.GestionComputerService;

public class CompanyConverter extends PropertyEditorSupport {

	public CompanyConverter(GestionComputerService gestionComputerService) {
		this.gestionComputerService = gestionComputerService;
	}

	@Autowired
	private GestionComputerService gestionComputerService;
	
	@Override
	public String getAsText() {
		Company company = (Company) getValue();
		System.out.println(company);
		if(company == null) {
			return null;
		}
		return String.valueOf(company.getId());
	}
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(text !=null && !text.isEmpty()){
			try {
				int companyId = Integer.parseInt(text);
				setValue(gestionComputerService.getCompany(companyId));
			} catch (NumberFormatException e){}
			return;
		}
		setValue(null);
	}
	
	public void setGestionComputerService(
			GestionComputerService gestionComputerService) {
		this.gestionComputerService = gestionComputerService;
	}

}
