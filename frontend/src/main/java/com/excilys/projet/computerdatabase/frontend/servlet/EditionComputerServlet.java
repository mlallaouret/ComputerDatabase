package com.excilys.projet.computerdatabase.frontend.servlet;

import com.excilys.projet.computerdatabase.frontend.utils.IdToCompanyConverter;
import com.excilys.projet.computerdatabase.model.Company;
import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.model.PageEdition;
import com.excilys.projet.computerdatabase.serviceapi.GestionComputerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EditionComputerServlet{

	private static final Logger logger = LoggerFactory.getLogger(EditionComputerServlet.class);

    @Autowired
    private GestionComputerService gestionComputerService;

    @ExceptionHandler(TypeMismatchException.class)
    public String handleTypeMismatchException(TypeMismatchException e) {
    	logger.warn(e.getMessage());
    	return "redirect:affichageComputers.html";
    }

    @RequestMapping(value="/editionComputer",  method= RequestMethod.GET)
	public ModelAndView doGet(@RequestParam("id") Integer idParam){
    	ModelAndView modelView;
		try{
			PageEdition pageEdition = gestionComputerService.createPageEdition(idParam);
			Computer computer = pageEdition.getComputer();
			modelView = new ModelAndView("editionComputer", "computer", computer);
			modelView.addObject("companies", pageEdition.getCompanies());
			
			return modelView;
		}catch(NumberFormatException e){
			return new ModelAndView("affichageComputers");
		} catch (DataAccessException e) {
			modelView = new ModelAndView("errorPage");
			modelView.addObject("error", "Erreur technique.");
			return modelView;
		} catch (IllegalArgumentException e) {
			logger.warn(e.getMessage());
			modelView = new ModelAndView("errorPage");
			modelView.addObject("error", e.getMessage());
			return modelView;
		}
	}
    
    @RequestMapping(value="/editionComputer",  method= RequestMethod.POST)
   	public String doPost(@ModelAttribute("computer")
       								Computer computer, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
   		
       	if(result.hasErrors()){
       		logger.debug(result.getAllErrors().toString());
       		model.addAttribute("companies", gestionComputerService.getCompanies());
       		model.addAttribute("result", result);
       		return "ajoutComputer";
       	} else {
       		try {
   				gestionComputerService.insertOrUpdate(computer);
   				StringBuilder sb = new StringBuilder("Computer ").append(computer.getName()).append(" has been ");
   				sb.append("updated");
   				redirectAttributes.addFlashAttribute("info", sb.toString());
   				return "redirect:affichageComputers.html";			
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

    public void setGestionComputerService(GestionComputerService gestionComputerService) {
        this.gestionComputerService = gestionComputerService;
    }
    
    @InitBinder
	public void initBinderUser(WebDataBinder binder) {
		binder.registerCustomEditor(Company.class, new IdToCompanyConverter(gestionComputerService));
	}

}
