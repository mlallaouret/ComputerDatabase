package com.excilys.projet.computerdatabase.frontend.servlet;

import com.excilys.projet.computerdatabase.serviceapi.GestionComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/delete")
public class DeleteController {

	@Autowired
	private GestionComputerService gestionComputerService;

	public void setGestionComputerService(
			GestionComputerService gestionComputerService) {
		this.gestionComputerService = gestionComputerService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doPost(Model model, @RequestParam("id") Integer id,
			RedirectAttributes redirectAttributes) {
		if (gestionComputerService.deleteComputer(id)) {
			redirectAttributes.addFlashAttribute("info",
					"Computer has been deleted");
		} else {
			redirectAttributes.addFlashAttribute("info",
					"Computer has not been deleted");
		}
		return "redirect:affichageComputers.html";
	}
}
