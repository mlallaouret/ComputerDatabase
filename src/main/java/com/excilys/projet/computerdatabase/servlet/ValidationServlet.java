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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;

import com.excilys.projet.computerdatabase.model.Computer;
import com.excilys.projet.computerdatabase.service.GestionComputerService; 
import com.mysql.jdbc.StringUtils;

@WebServlet("/validation")
@SuppressWarnings("serial")
public class ValidationServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(ValidationServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("springConfig.xml");
		GestionComputerService gestionComputerService = context.getBean(GestionComputerService.class);
		context.close();
		
		boolean error = false;
		Computer computer = new Computer();
		
		//Check de l'id
		if(req.getParameter("id")!=null){
			computer.setId(Integer.parseInt(req.getParameter("id")));
		}
		
		//Check du nom de l'ordinateur
		if(req.getParameter("name")==null || req.getParameter("name").trim().length()==0){
			error = true;
			req.setAttribute("nameError", "error");	
		} else {
			computer.setName(req.getParameter("name"));
		}
		
		//Check des dates
		SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
		df.applyPattern("yyyy-MM-dd");
		df.setLenient(false);
		
		if(req.getParameter("introduced").isEmpty()) {
			computer.setIntroduced(null);
		} else {
			try {
				computer.setIntroduced(df.parse(req.getParameter("introduced")));
			} catch (ParseException e) {
				error = true;
				req.setAttribute("introducedError", "error");
			}
		}
		
		if(req.getParameter("discontinued").isEmpty()) {
			computer.setDiscontinued(null);
		} else {	
			try {
				computer.setDiscontinued(df.parse(req.getParameter("discontinued")));
			} catch (ParseException e) {
				error = true;
				req.setAttribute("discontinuedError", "error");
			}	
		}
		
		try {
			//Check de la compagnie
			if(!StringUtils.isNullOrEmpty(req.getParameter("company"))){
				computer.setCompany(gestionComputerService.getCompany(Integer.parseInt(req.getParameter("company"))));
			}
			if(!error) {
				gestionComputerService.insertOrUpdate(computer);
				StringBuilder sb = new StringBuilder("Computer ").append(computer.getName()).append(" has been ");
				if(req.getParameter("id")!=null){
					sb.append("updated");
				} else {
					sb.append("created");
				}
				req.getSession().setAttribute("info", sb.toString());
				resp.sendRedirect("affichageComputers");
			} else { 
				req.setAttribute("computer", computer);
				req.setAttribute("companies", gestionComputerService.getCompanies());
				if(req.getParameter("id")!=null) {
					
					getServletContext().getRequestDispatcher("/WEB-INF/editionComputer.jsp").forward(req, resp);
				} else {
					getServletContext().getRequestDispatcher("/WEB-INF/ajoutComputer.jsp").forward(req, resp);
				}
				
			}
		} catch(DataAccessException e){
			req.setAttribute("error", "Erreur technique");
			getServletContext().getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(req, resp);
		} catch(IllegalArgumentException e) {
			logger.warn(e.getMessage());
			req.setAttribute("error", "L'ordinateur n'existe pas.");
			getServletContext().getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(req, resp);
		}
		
		
	}

}
