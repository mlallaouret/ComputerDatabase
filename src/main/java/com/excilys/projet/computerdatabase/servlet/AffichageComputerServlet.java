package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.projet.computerdatabase.utils.Page;

@SuppressWarnings("serial")
@WebServlet("/affichageComputers")
public class AffichageComputerServlet extends HttpServlet {

	private final static int MAX_AFFICHAGE = 10;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Integer pageNumber=0;
		Page page = null;
		if(req.getParameter("page")!=null) {
			try{
				pageNumber=Integer.parseInt(req.getParameter("page"));
			}catch(NumberFormatException e){
				pageNumber=0;
			}
		}
		page = new Page(req.getParameter("f"), req.getParameter("s"), pageNumber, MAX_AFFICHAGE);
		req.setAttribute("page", page);
		req.setAttribute("tri", req.getParameter("s"));

		getServletContext().getRequestDispatcher("/WEB-INF/affichageComputers.jsp").forward(req, resp);
		
	}
}
