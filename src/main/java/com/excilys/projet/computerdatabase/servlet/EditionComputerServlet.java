package com.excilys.projet.computerdatabase.servlet;

import com.excilys.projet.computerdatabase.model.PageEdition;
import com.excilys.projet.computerdatabase.service.GestionComputerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EditionComputerServlet{

	private static final Logger logger = LoggerFactory.getLogger(EditionComputerServlet.class);

    @Autowired
    private GestionComputerService gestionComputerService;


    @RequestMapping(value="/editionComputer",  method= RequestMethod.GET)
	public String doGet(Model model, @RequestParam("id") String idParam){
		
		int id=0;
		
		try{
			id = Integer.parseInt(idParam);
			PageEdition pageEdition = gestionComputerService.createPageEdition(id);
			model.addAttribute("computer", pageEdition.getComputer());
			model.addAttribute("companies", pageEdition.getCompanies());
			
			return "editionComputer";
		}catch(NumberFormatException e){
			return "affichageComputers";
		} catch (DataAccessException e) {
			model.addAttribute("error", "Erreur technique.");
			return "errorPage";
		} catch (IllegalArgumentException e) {
			logger.warn(e.getMessage());
			model.addAttribute("error", e.getMessage());
			return "errorPage";
		}
	}

    public void setGestionComputerService(GestionComputerService gestionComputerService) {
        this.gestionComputerService = gestionComputerService;
    }

}
