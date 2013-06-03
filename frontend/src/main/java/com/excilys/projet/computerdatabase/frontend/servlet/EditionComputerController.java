package com.excilys.projet.computerdatabase.frontend.servlet;

import com.excilys.projet.computerdatabase.frontend.utils.CompanyConverter;
import com.excilys.projet.computerdatabase.model.Company;
import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.model.PageEdition;
import com.excilys.projet.computerdatabase.serviceapi.GestionComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/editionComputer")
public class EditionComputerController {

	@Autowired
	private GestionComputerService gestionComputerService;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(Model model, @RequestParam("id") Integer idParam) {
		PageEdition pageEdition = gestionComputerService
				.createPageEdition(idParam);
		model.addAttribute("computer", pageEdition.getComputer());
		model.addAttribute("companies", pageEdition.getCompanies());
		return "editionComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(@ModelAttribute("computer") Computer computer,
			BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		String view;
		if (result.hasErrors()) {
			model.addAttribute("companies",
					gestionComputerService.getCompanies());
			model.addAttribute("result", result);
			view = "editionComputer";
		} else {
			StringBuilder sb = new StringBuilder("Computer ").append(computer
					.getName());
			if (gestionComputerService.insertOrUpdate(computer)) {
				sb.append(" has been ");
			} else {
				sb.append(" has not been ");
			}
			sb.append("updated");
			redirectAttributes.addFlashAttribute("info", sb.toString());
			view = "redirect:affichageComputers.html";
		}
		return view;
	}

	public void setGestionComputerService(
			GestionComputerService gestionComputerService) {
		this.gestionComputerService = gestionComputerService;
	}

	@InitBinder
	public void initBinderUser(WebDataBinder binder) {
		binder.registerCustomEditor(Company.class, new CompanyConverter(
				gestionComputerService));
	}

}
