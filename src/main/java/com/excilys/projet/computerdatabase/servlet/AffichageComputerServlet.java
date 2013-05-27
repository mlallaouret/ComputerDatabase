package com.excilys.projet.computerdatabase.servlet;

import com.excilys.projet.computerdatabase.model.Page;
import com.excilys.projet.computerdatabase.service.GestionComputerService;
import com.excilys.projet.computerdatabase.utils.SqlRequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AffichageComputerServlet {

	private final static int MAX_AFFICHAGE = 10;

    public void setGestionComputerService(GestionComputerService gestionComputerService) {
        this.gestionComputerService = gestionComputerService;
    }

    @Autowired
    private GestionComputerService gestionComputerService;

	@RequestMapping(value="/affichageComputers",  method= RequestMethod.GET)
    public String doGet(Model model, @RequestParam(value="page", defaultValue = "0") Integer pageParam, @RequestParam(value="s", defaultValue = "2") Integer s,
                        @RequestParam(value="f", defaultValue = "") String f){

		Integer pageNumber=pageParam;
		Page page = null;

		int sort=s;

		try {
			page = gestionComputerService.createPage(pageNumber, MAX_AFFICHAGE, new SqlRequestOptions(f, sort));
			//String info = (String) req.getSession().getAttribute("info");

			/*if(!StringUtils.isNullOrEmpty(info)) {
				model.addAttribute("info", info);
				req.getSession().removeAttribute("info");
			}*/
			model.addAttribute("page", page);
			model.addAttribute("tri", sort);
			model.addAttribute("filter", f);

			return "affichageComputers";
		} catch (DataAccessException e) {
			model.addAttribute("error", "Erreur technique");
			return "errorPage";
		}
		
		
		
	}
}