package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.service.ValidationService;

@WebServlet("/validation")
@SuppressWarnings("serial")
public class ValidationServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		boolean error = false;
		
		Computer computer = new Computer();
		
		if(req.getParameter("name")==null) {
			error = true;
			req.setAttribute("nameError", "Le nom de l'ordinateur doit être précisé");
			
		} else {
			computer.setName(req.getParameter("name"));
		}
		
		if(req.getParameter("company")==""){
			error=true;
			req.setAttribute("companyError", "Le nom de l'ordinateur doit être précisé");
		} else {
			computer.setCompany(ValidationService.getInstance().getCompany(Integer.parseInt(req.getParameter("company"))));
		}
		
		if(!error) {
			ValidationService.getInstance().insertOrUpdate(computer);
			getServletContext().getRequestDispatcher("/index.html").forward(req, resp);
		}	
		
	}
	
}
