package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.excilys.projet.computerdatabase.service.GestionComputerService;


@SuppressWarnings("serial")
@WebServlet("/ajoutComputer")
public class AjoutComputerServlet extends HttpServlet{

	private ApplicationContext context;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		if (context == null){
            context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        }
		GestionComputerService gestionComputerService = context.getBean(GestionComputerService.class);
		
		try {
			req.setAttribute("companies", gestionComputerService.getCompanies());
			getServletContext().getRequestDispatcher("/WEB-INF/ajoutComputer.jsp").forward(req, resp);
		} catch (DataAccessException e) {
			req.setAttribute("error", "Erreur technique.");
			getServletContext().getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(req, resp);
		}
	}
}
