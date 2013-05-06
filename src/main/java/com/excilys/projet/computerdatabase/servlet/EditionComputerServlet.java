package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.service.GestionComputerService;

@SuppressWarnings("serial")
@WebServlet("/editionComputer")
public class EditionComputerServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/WEB-INF/editionComputer.jsp").forward(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		int id=0;
		
		if(req.getParameter("id")!=null) {
			id = Integer.parseInt(req.getParameter("id"));
		}
		if(!GestionComputerService.getInstance().isIdExists(id)) {
			getServletContext().getRequestDispatcher("/index").forward(req, resp);
		} else {
			Computer computer = GestionComputerService.getInstance().getComputer(id);
			
			req.setAttribute("computer", GestionComputerService.getInstance().getComputer(id));
			req.setAttribute("companies", GestionComputerService.getInstance().getCompanies());
			
			getServletContext().getRequestDispatcher("/WEB-INF/editionComputer.jsp").forward(req, resp);
		}
		
		
		
	}

}
