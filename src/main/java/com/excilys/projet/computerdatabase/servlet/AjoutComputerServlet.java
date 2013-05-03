package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.projet.computerdatabase.service.EditionComputerService;

@SuppressWarnings("serial")
@WebServlet("/ajoutcomputer")
public class AjoutComputerServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setAttribute("companies", EditionComputerService.getInstance().getCompanies());
		
		getServletContext().getRequestDispatcher("/WEB-INF/ajoutComputer.jsp").forward(req, resp);
	}
}
