package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.projet.computerdatabase.model.PageEdition;
import com.excilys.projet.computerdatabase.service.GestionComputerService;

@SuppressWarnings("serial")
@WebServlet("/editionComputer")
public class EditionComputerServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(EditionComputerServlet.class);
	private ApplicationContext context;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if (context == null){
            context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        }
		GestionComputerService gestionComputerService = context.getBean(GestionComputerService.class);
		
		int id=0;
		
		try{
			id = Integer.parseInt(req.getParameter("id"));
			PageEdition pageEdition = gestionComputerService.createPageEdition(id);
			req.setAttribute("computer", pageEdition.getComputer());
			req.setAttribute("companies", pageEdition.getCompanies());
			
			getServletContext().getRequestDispatcher("/WEB-INF/editionComputer.jsp").forward(req, resp);
		}catch(NumberFormatException e){
			resp.sendRedirect("affichageComputers");
		} catch (DataAccessException e) {
			req.setAttribute("error", "Erreur technique.");
			getServletContext().getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(req, resp);
		} catch (IllegalArgumentException e) {
			logger.warn(e.getMessage());
			req.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(req, resp);
		}
	}

}
