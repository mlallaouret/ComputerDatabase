package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.projet.computerdatabase.service.GestionComputerService;

@SuppressWarnings("serial")
@WebServlet("/editionComputer")
public class EditionComputerServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		int id=0;
		
		if(req.getParameter("id")!=null) {
			try{
				id = Integer.parseInt(req.getParameter("id"));
				if(!GestionComputerService.getInstance().isComputerExists(id)) {
					getServletContext().getRequestDispatcher("/index").forward(req, resp);
				} else {
					
					req.setAttribute("computer", GestionComputerService.getInstance().getComputer(id));
					req.setAttribute("companies", GestionComputerService.getInstance().getCompanies());
					
					getServletContext().getRequestDispatcher("/WEB-INF/editionComputer.jsp").forward(req, resp);
				}
			}catch(NumberFormatException e){
				resp.sendRedirect("affichageComputers");
			}
		}
		
		
		
		
	}

}
