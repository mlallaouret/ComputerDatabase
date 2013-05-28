package com.excilys.projet.computerdatabase.frontend.servlet;

import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.serviceapi.GestionComputerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class ValidationServlet{

	private static final Logger logger = LoggerFactory.getLogger(ValidationServlet.class);
    @Autowired
    private GestionComputerService gestionComputerService;

    public void setGestionComputerService(GestionComputerService gestionComputerService) {
        this.gestionComputerService = gestionComputerService;
    }

    @RequestMapping(value="/validation",  method= RequestMethod.POST)
    public String doPost(Model model, @RequestParam(value = "id", required=false) Integer id, @RequestParam("name") String name, @RequestParam("introduced") String introduced,
                         @RequestParam("discontinued") String discontinued, @RequestParam("company") String company){

		
		boolean error = false;
		Computer computer = new Computer();
		
		//Check de l'id
		if(id!=null){
			computer.setId(id);
		}
		
		//Check du nom de l'ordinateur
		if(name==null || name.trim().length()==0){
			error = true;
			model.addAttribute("nameError", "error");
		} else {
			computer.setName(name);
		}
		
		//Check des dates
		SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
		df.applyPattern("yyyy-MM-dd");
		df.setLenient(false);
		
		if(introduced.isEmpty()) {
			computer.setIntroduced(null);
		} else {
			try {
				computer.setIntroduced(df.parse(introduced));
			} catch (ParseException e) {
				error = true;
				model.addAttribute("introducedError", "error");
			}
		}
		
		if(discontinued.isEmpty()) {
			computer.setDiscontinued(null);
		} else {	
			try {
				computer.setDiscontinued(df.parse(discontinued));
			} catch (ParseException e) {
				error = true;
				model.addAttribute("discontinuedError", "error");
			}	
		}
		
		try {
			//Check de la compagnie
			if(company !=null && !company.isEmpty()){
				computer.setCompany(gestionComputerService.getCompany(Integer.parseInt(company)));
			}
			if(!error) {
				gestionComputerService.insertOrUpdate(computer);
				StringBuilder sb = new StringBuilder("Computer ").append(computer.getName()).append(" has been ");
				if(id!=null){
					sb.append("updated");
				} else {
					sb.append("created");
				}
				//req.getSession().setAttribute("info", sb.toString());
				return "redirect:affichageComputers.html";
			} else { 
				model.addAttribute("computer", computer);
				model.addAttribute("companies", gestionComputerService.getCompanies());
				if(id!=null) {
					
					return "editionComputer";
				} else {
					return "ajoutComputer";
				}
				
			}
		} catch(DataAccessException e){
			model.addAttribute("error", "Erreur technique");
			return "errorPage";
		} catch(IllegalArgumentException e) {
			logger.warn(e.getMessage());
			model.addAttribute("error", "L'ordinateur n'existe pas.");
			return "errorPage";
		}
		
		
	}

}
