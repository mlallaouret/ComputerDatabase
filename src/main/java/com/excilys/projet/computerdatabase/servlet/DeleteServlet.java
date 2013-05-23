package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.projet.computerdatabase.service.GestionComputerService;
import com.excilys.projet.computerdatabase.service.GestionComputerServiceImpl;

@WebServlet("/delete")
@SuppressWarnings("serial")
public class DeleteServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(DeleteServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("springConfig.xml");
		GestionComputerService gestionComputerService = context.getBean("gestionComputerServiceImpl", GestionComputerServiceImpl.class);
		try {
			gestionComputerService.deleteComputer(Integer.parseInt(req.getParameter("id")));
			req.getSession().setAttribute("info", "Computer has been deleted");
			resp.sendRedirect("affichageComputers");
		} catch (SQLException e) {
			req.setAttribute("error", "Erreur technique.");
			getServletContext().getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(req, resp);
		} catch(NumberFormatException e){
			req.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(req, resp);
		}catch (IllegalArgumentException e){
			logger.warn(e.getMessage());
			req.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher("/WEB-INF/errorPage.jsp").forward(req, resp);
		}
		
	}

}
