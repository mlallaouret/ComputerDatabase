package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.projet.computerdatabase.service.EditionComputerService;

@WebServlet("/delete")
@SuppressWarnings("serial")
public class DeleteServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		EditionComputerService.getInstance().deleteComputer(Integer.parseInt(req.getParameter("id")));
		
		getServletContext().getRequestDispatcher("/inde.html").forward(req, resp);
	}

}
