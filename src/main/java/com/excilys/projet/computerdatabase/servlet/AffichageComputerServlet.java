package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.projet.computerdatabase.model.Page;
import com.excilys.projet.computerdatabase.service.GestionComputerServiceImpl;
import com.excilys.projet.computerdatabase.utils.SqlRequestOptions;
import com.mysql.jdbc.StringUtils;

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
		int sort=1;
		try{
			sort = Integer.parseInt(req.getParameter("s"));
		} catch (NumberFormatException e){
			sort = 2;
		}

		try {
			page = GestionComputerServiceImpl.getInstance().createPage(pageNumber, MAX_AFFICHAGE, new SqlRequestOptions(req.getParameter("f"), sort));
			String info = (String) req.getSession().getAttribute("info");

			if(!StringUtils.isNullOrEmpty(info)) {
				req.setAttribute("info", info);
				req.getSession().removeAttribute("info");
			}
			req.setAttribute("page", page);
			req.setAttribute("tri", req.getParameter("s"));
			req.setAttribute("filter", req.getParameter("f"));

			getServletContext().getRequestDispatcher("/WEB-INF/affichageComputers.jsp").forward(req, resp);
		} catch (SQLException e) {
			req.setAttribute("error", "Erreur technique");
			getServletContext().getRequestDispatcher("/WEB-INF/erroPage.jsp").forward(req, resp);
		}
		
		
		
	}
}
