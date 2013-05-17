package com.excilys.projet.computerdatabase.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.projet.computerdatabase.service.GestionComputerServiceImpl;

@WebServlet("/delete")
@SuppressWarnings("serial")
public class DeleteServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			GestionComputerServiceImpl.getInstance().deleteComputer(Integer.parseInt(req.getParameter("id")));
			req.getSession().setAttribute("info", "Computer has been deleted");
			resp.sendRedirect("affichageComputers");
		} catch (SQLException e) {
			req.setAttribute("error", "Erreur technique.");
			getServletContext().getRequestDispatcher("/WEB-INF/erroPage.jsp").forward(req, resp);
		} catch(NumberFormatException e){
			req.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher("/WEB-INF/erroPage.jsp").forward(req, resp);
		}catch (IllegalArgumentException e){
			req.setAttribute("error", e.getMessage());
			getServletContext().getRequestDispatcher("/WEB-INF/erroPage.jsp").forward(req, resp);
		}
		
	}

}
