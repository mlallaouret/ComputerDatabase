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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/editionComputer")
public class EditionComputerController {

	private static final Logger logger = LoggerFactory
			.getLogger(EditionComputerController.class);

	@Autowired
	private GestionComputerService gestionComputerService;

	@ExceptionHandler(TypeMismatchException.class)
	public String handleTypeMismatchException(TypeMismatchException e) {
		logger.warn(e.getMessage());
		return "redirect:affichageComputers.html";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(Model model, @RequestParam("id") Integer idParam) {
		String view;
		try {
			PageEdition pageEdition = gestionComputerService
					.createPageEdition(idParam);
			Computer computer = pageEdition.getComputer();
			model.addAttribute("computer", computer);
			model.addAttribute("companies", pageEdition.getCompanies());
			view = "editionComputer";
		} catch (NumberFormatException e) {
			view = "affichageComputers";
		} catch (DataAccessException e) {
			view = "errorPage";
			model.addAttribute("error", "Erreur technique.");
		} catch (IllegalArgumentException e) {
			logger.warn(e.getMessage());
			view = "errorPage";
			model.addAttribute("error", e.getMessage());
		}
		return view;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(@ModelAttribute("computer") Computer computer,
			BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		String view;
		if (result.hasErrors()) {
			logger.debug(result.getAllErrors().toString());
			model.addAttribute("companies",
					gestionComputerService.getCompanies());
			model.addAttribute("result", result);
			view = "editionComputer";
		} else {
			try {
				
				StringBuilder sb = new StringBuilder("Computer ").append(
						computer.getName());
				if(gestionComputerService.insertOrUpdate(computer)){
					sb.append(" has been ");
				} else {
					sb.append(" has not been ");
				}
				sb.append("updated");
				redirectAttributes.addFlashAttribute("info", sb.toString());
				view = "redirect:affichageComputers.html";
			} catch (DataAccessException e) {
				model.addAttribute("error", "Erreur technique");
				view = "errorPage";
			} catch (IllegalArgumentException e) {
				logger.warn(e.getMessage());
				model.addAttribute("error", "L'ordinateur n'existe pas.");
				view = "errorPage";
			}
		}
		return view;
	}

	public void setGestionComputerService(
			GestionComputerService gestionComputerService) {
		this.gestionComputerService = gestionComputerService;
	}

	@InitBinder
	public void initBinderUser(WebDataBinder binder) {
		binder.registerCustomEditor(Company.class, new IdToCompanyConverter(
				gestionComputerService));
	}

}
