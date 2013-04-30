package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.service.AffichageComputerService;

@SuppressWarnings("serial")
@WebServlet("/index.html")
public class AffichageComputerServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//super.doGet(req, resp);
		
		List<Computer> liste = AffichageComputerService.getInstance().getComputers();
		
		req.setAttribute("computers", liste);
		req.setAttribute("total", liste.size());
		
		getServletContext().getRequestDispatcher("/WEB-INF/affichageComputers.jsp").forward(req, resp);
		
	}
}
