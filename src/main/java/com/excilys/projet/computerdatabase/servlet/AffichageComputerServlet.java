package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.service.GestionComputerService;

@SuppressWarnings("serial")
@WebServlet("/index")
public class AffichageComputerServlet extends HttpServlet {

	private final static int MAX_AFFICHAGE = 10;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Integer page=0;
		Integer total = GestionComputerService.getInstance().getComputerCount();
		if(req.getParameter("page")!=null) {
			page=Integer.parseInt(req.getParameter("page"));
			req.setAttribute("displayTo", (page +1)*10);
			if(page<0){
				page=0;
			} else if(page>0 && (total - (page)*10<0)){
				page=0;
			}
		}
		
		if((total - (page+1)*10)<1) {
			req.setAttribute("displayTo", total);
		} else {
			req.setAttribute("displayTo", (page +1)*10);
		}
		int displayFrom = page * MAX_AFFICHAGE +1;
		req.setAttribute("displayFrom", displayFrom);
		List<Computer> liste = GestionComputerService.getInstance().getComputers(page, MAX_AFFICHAGE);
		
		req.setAttribute("computers", liste);
		req.setAttribute("last", total - (page+1)*10);
		req.setAttribute("total", total);
		req.setAttribute("page", page);
		getServletContext().getRequestDispatcher("/WEB-INF/affichageComputers.jsp").forward(req, resp);
		
	}
}
