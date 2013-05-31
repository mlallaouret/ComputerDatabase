package com.excilys.projet.computerdatabase.frontend.servlet;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping(value="/delete")
public class DeleteController {

	private static final Logger logger = LoggerFactory.getLogger(DeleteController.class);
    @Autowired
    private GestionComputerService gestionComputerService;

    public void setGestionComputerService(GestionComputerService gestionComputerService) {
        this.gestionComputerService = gestionComputerService;
    }

    @RequestMapping(method= RequestMethod.POST)
    public String doPost(Model model, @RequestParam("id") Integer id, RedirectAttributes redirectAttributes){

		try {
			if(gestionComputerService.deleteComputer(id)){
				redirectAttributes.addFlashAttribute("info", "Computer has been deleted");
			} else {
				redirectAttributes.addFlashAttribute("info", "Computer has not been deleted");
			}
			return "redirect:affichageComputers.html";
		} catch (DataAccessException e) {
			logger.warn(e.getMessage());
			model.addAttribute("error", "Erreur technique.");
			return "errorPage";
		} catch (IllegalArgumentException e){
			logger.warn(e.getMessage());
			model.addAttribute("error", e.getMessage());
			return "errorPage";
		}
		
	}

}
