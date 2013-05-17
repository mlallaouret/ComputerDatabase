package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.projet.computerdatabase.service.GestionComputerServiceImpl;


@SuppressWarnings("serial")
@WebServlet("/ajoutComputer")
public class AjoutComputerServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		try {
			req.setAttribute("companies", GestionComputerServiceImpl.getInstance().getCompanies());
			getServletContext().getRequestDispatcher("/WEB-INF/ajoutComputer.jsp").forward(req, resp);
		} catch (SQLException e) {
			req.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher("/WEB-INF/erroPage.jsp").forward(req, resp);
		}
	}
}
