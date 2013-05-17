package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.projet.computerdatabase.model.PageEdition;
import com.excilys.projet.computerdatabase.service.GestionComputerServiceImpl;

@SuppressWarnings("serial")
@WebServlet("/editionComputer")
public class EditionComputerServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		int id=0;
		
		try{
			id = Integer.parseInt(req.getParameter("id"));
			PageEdition pageEdition = GestionComputerServiceImpl.getInstance().createPageEdition(id);
			req.setAttribute("computer", pageEdition.getComputer());
			req.setAttribute("companies", pageEdition.getCompanies());
			
			getServletContext().getRequestDispatcher("/WEB-INF/editionComputer.jsp").forward(req, resp);
		}catch(NumberFormatException e){
			resp.sendRedirect("affichageComputers");
		} catch (SQLException e) {
			req.setAttribute("error", "Erreur technique.");
			getServletContext().getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(req, resp);
		}
	}

}
