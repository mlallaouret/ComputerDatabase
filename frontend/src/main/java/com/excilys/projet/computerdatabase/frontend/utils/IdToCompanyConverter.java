package com.excilys.projet.computerdatabase.frontend.utils;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.projet.computerdatabase.serviceapi.GestionComputerService;

public class IdToCompanyConverter extends PropertyEditorSupport {

	public IdToCompanyConverter(GestionComputerService gestionComputerService) {
		this.gestionComputerService = gestionComputerService;
	}

	public IdToCompanyConverter() {
	}

	@Autowired
	private GestionComputerService gestionComputerService;
	
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
