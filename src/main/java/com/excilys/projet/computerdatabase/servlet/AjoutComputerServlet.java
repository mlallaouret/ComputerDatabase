package com.excilys.projet.computerdatabase.servlet;


import com.excilys.projet.computerdatabase.service.GestionComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class AjoutComputerServlet{

    public void setGestionComputerService(GestionComputerService gestionComputerService) {
        this.gestionComputerService = gestionComputerService;
    }

    @Autowired
    private GestionComputerService gestionComputerService;

    @RequestMapping(value="/ajoutComputer",  method= RequestMethod.GET)
	public String doGet(Model model) {
		
		try {
			model.addAttribute("companies", gestionComputerService.getCompanies());
			return "ajoutComputer";
		} catch (DataAccessException e) {
			model.addAttribute("error", "Erreur technique.");
			return "errorPage";
		}
	}
}
