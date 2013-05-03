package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.projet.computerdatabase.service.AffichageComputerService;
import com.excilys.projet.computerdatabase.service.EditionComputerService;

@SuppressWarnings("serial")
@WebServlet("/ajoutComputer")
public class EditionComputerServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		int id=-1;
		
		if(req.getParameter("id")!=null) {
			id = Integer.parseInt(req.getParameter("id"));
		}
		
		req.setAttribute("computer", EditionComputerService.getInstance().getComputer(id));
		req.setAttribute("companies", EditionComputerService.getInstance().getCompanies());
		
		getServletContext().getRequestDispatcher("/WEB-INF/editionComputer.jsp").forward(req, resp);
		
	}

}
