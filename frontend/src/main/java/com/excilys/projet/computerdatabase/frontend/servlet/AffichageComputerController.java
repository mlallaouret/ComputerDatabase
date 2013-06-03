package com.excilys.projet.computerdatabase.frontend.servlet;

import com.excilys.projet.computerdatabase.model.Page;
import com.excilys.projet.computerdatabase.serviceapi.GestionComputerService;
import com.excilys.projet.computerdatabase.utils.SqlRequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/affichageComputers")
public class AffichageComputerController {

	private final static int MAX_AFFICHAGE = 10;

	public void setGestionComputerService(
			GestionComputerService gestionComputerService) {
		this.gestionComputerService = gestionComputerService;
	}

	@Autowired
	private GestionComputerService gestionComputerService;

	@RequestMapping(method = RequestMethod.GET)
	public String doGet(
			Model model,
			@RequestParam(value = "page", defaultValue = "0") Integer pageParam,
			@RequestParam(value = "s", defaultValue = "2") Integer s,
			@RequestParam(value = "f", defaultValue = "") String f){
		Page page = gestionComputerService.createPage(pageParam, MAX_AFFICHAGE,
				new SqlRequestOptions(f, s));
		model.addAttribute("page", page);
		model.addAttribute("tri", s);
		model.addAttribute("filter", f);
		
		return "affichageComputers";
	}
}