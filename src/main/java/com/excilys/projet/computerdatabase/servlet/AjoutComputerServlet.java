package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.projet.computerdatabase.service.GestionComputerService;


@SuppressWarnings("serial")
@WebServlet("/ajoutComputer")
public class AjoutComputerServlet extends HttpServlet{

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("springConfig.xml");
		GestionComputerService gestionComputerService = context.getBean(GestionComputerService.class);
		
		try {
			req.setAttribute("companies", gestionComputerService.getCompanies());
			getServletContext().getRequestDispatcher("/WEB-INF/ajoutComputer.jsp").forward(req, resp);
		} catch (SQLException e) {
			req.setAttribute("error", "Erreur technique.");
			getServletContext().getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(req, resp);
		}
	}
}
