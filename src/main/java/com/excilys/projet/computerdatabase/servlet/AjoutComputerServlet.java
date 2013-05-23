package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;

import com.excilys.projet.computerdatabase.service.GestionComputerService;


@SuppressWarnings("serial")
@WebServlet("/ajoutComputer")
public class AjoutComputerServlet extends HttpServlet{

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("springConfig.xml");
		GestionComputerService gestionComputerService = context.getBean(GestionComputerService.class);
		context.close();
		
		try {
			req.setAttribute("companies", gestionComputerService.getCompanies());
			getServletContext().getRequestDispatcher("/WEB-INF/ajoutComputer.jsp").forward(req, resp);
		} catch (DataAccessException e) {
			req.setAttribute("error", "Erreur technique.");
			getServletContext().getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(req, resp);
		}
	}
}
