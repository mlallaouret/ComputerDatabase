package com.excilys.projet.computerdatabase.frontend.servlet;

import com.excilys.projet.computerdatabase.frontend.utils.CompanyConverter;
import com.excilys.projet.computerdatabase.model.Company;
import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.serviceapi.GestionComputerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/ajoutComputer")
public class AjoutComputerController {

	private static final Logger logger = LoggerFactory
			.getLogger(AjoutComputerController.class);

	public void setGestionComputerService(
			GestionComputerService gestionComputerService) {
		this.gestionComputerService = gestionComputerService;
	}

	@Autowired
	private GestionComputerService gestionComputerService;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(Model model) {
		model.addAttribute("computer", new Computer());
		model.addAttribute("companies", gestionComputerService.getCompanies());
		return "ajoutComputer";
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
			view = "ajoutComputer";
		} else {
			StringBuilder sb = new StringBuilder("Computer ").append(computer
					.getName());
			if (gestionComputerService.insertOrUpdate(computer)) {
				sb.append(" has been ");
			} else {
				sb.append(" has not been ");
			}
			sb.append("created");
			redirectAttributes.addFlashAttribute("info", sb.toString());
			view = "redirect:affichageComputers.html";
		}
		return view;
	}

	@InitBinder
	public void initBinderUser(WebDataBinder binder) {
		binder.registerCustomEditor(Company.class, new CompanyConverter(
				gestionComputerService));
	}
}
