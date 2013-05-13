package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.service.GestionComputerService;
import com.mysql.jdbc.StringUtils;

@WebServlet("/validation")
@SuppressWarnings("serial")
public class ValidationServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		boolean error = false;
		
		Computer computer = new Computer();
		
		//Check de l'id
		if(req.getParameter("id")!=null){
			computer.setId(Integer.parseInt(req.getParameter("id")));
		}
		
		//Check du nom de l'ordinateur
		if(StringUtils.isNullOrEmpty(req.getParameter("name"))) {
			error = true;
			req.setAttribute("nameError", "error");
			
		} else {
			computer.setName(req.getParameter("name"));
		}
		
		//Check de la compagnie
		if(!StringUtils.isNullOrEmpty(req.getParameter("company"))){
			computer.setCompany(GestionComputerService.getInstance().getCompany(Integer.parseInt(req.getParameter("company"))));
		}
		
		//Check des dates
		SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
		df.applyPattern("yyyy-MM-dd");
		df.setLenient(false);
		
		if(req.getParameter("introduced").equals("")) {
			computer.setIntroduced(null);
		} else {
			try {
				computer.setIntroduced(df.parse(req.getParameter("introduced")));
			} catch (ParseException e) {
				error = true;
				req.setAttribute("introducedError", "error");
			}
		}
		
		if(req.getParameter("discontinued").equals("")) {
			computer.setDiscontinued(null);
		} else {	
			try {
				computer.setDiscontinued(df.parse(req.getParameter("discontinued")));
			} catch (ParseException e) {
				error = true;
				req.setAttribute("discontinuedError", "error");
			}	
		}
		
		if(!error) {
			GestionComputerService.getInstance().insertOrUpdate(computer);
			resp.sendRedirect("index.jsp");
		} else { 
			req.setAttribute("computer", computer);
			if(req.getParameter("id")!=null) {
				
				getServletContext().getRequestDispatcher("/WEB-INF/editionComputer.jsp").forward(req, resp);
			} else {
				getServletContext().getRequestDispatcher("/WEB-INF/ajoutComputer.jsp").forward(req, resp);
			}
			
		}
		
	}

}
