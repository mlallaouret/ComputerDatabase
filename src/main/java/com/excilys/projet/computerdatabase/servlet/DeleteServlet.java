package com.excilys.projet.computerdatabase.servlet;

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

import javax.servlet.http.HttpServlet;


@Controller
public class DeleteServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(DeleteServlet.class);
    @Autowired
    private GestionComputerService gestionComputerService;

    public void setGestionComputerService(GestionComputerService gestionComputerService) {
        this.gestionComputerService = gestionComputerService;
    }

    @RequestMapping(value="/delete",  method= RequestMethod.POST)
    public String doPost(Model model, @RequestParam("id") String id){

		try {
			gestionComputerService.deleteComputer(Integer.parseInt(id));
			//req.getSession().setAttribute("info", "Computer has been deleted");
			return "redirect:affichageComputers.html";
		} catch (DataAccessException e) {
			model.addAttribute("error", "Erreur technique.");
			return "errorPage";
		} catch(NumberFormatException e){
			model.addAttribute("error", e.getMessage());
			return "errorPage.jsp";
		}catch (IllegalArgumentException e){
			logger.warn(e.getMessage());
			model.addAttribute("error", e.getMessage());
			return "errorPage";
		}
		
	}

}
