package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.service.GestionComputerService;

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
		if(req.getParameter("name")==null) {
			error = true;
			req.setAttribute("nameError", "Le nom de l'ordinateur doit être précisé");
			
		} else {
			computer.setName(req.getParameter("name"));
		}
		
		//Check de la compagnie
		if(req.getParameter("company")==""){
			error=true;
			req.setAttribute("companyError", "Le nom de l'ordinateur doit être précisé");
		} else {
			computer.setCompany(GestionComputerService.getInstance().getCompany(Integer.parseInt(req.getParameter("company"))));
		}
		
		//Check des dates
		Pattern p = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d");
		if(req.getParameter("introduced")==null) {
			computer.setIntroduced(null);
		} else {
			
			Matcher m = p.matcher(req.getParameter("introduced"));
			if(m.find()){
				try {
					computer.setIntroduced(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("introduced")));
				} catch (ParseException e) {
					error = true;
					req.setAttribute("introducedError", "La date n'est pas au bon format");
				}
				
			}else{
				error = true;
			}
		}
		
		if(req.getParameter("discontinued")==null) {
			computer.setDiscontinued(null);
		} else {
			
			Matcher m = p.matcher(req.getParameter("discontinued"));
			if(m.find()){
				try {
					computer.setDiscontinued(new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("discontinued")));
				} catch (ParseException e) {
					error = true;
					req.setAttribute("discontinuedError", "La date n'est pas au bon format");
				}
			}else{
				error = true;
			}
			
		}
		
		if(!error) {
			GestionComputerService.getInstance().insertOrUpdate(computer);
			resp.sendRedirect("/ComputerDatabase/index");
		} else {
			if(req.getParameter("id")!=null){
				
				getServletContext().getRequestDispatcher("/editionComputer").forward(req, resp);
			}else {
				getServletContext().getRequestDispatcher("/ajoutComputer").forward(req, resp);
			}
			
		}
		
	}

}
