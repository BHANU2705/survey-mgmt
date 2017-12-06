package com.bps.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OwnerHomeController
 */
@WebServlet("/home")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public HomeController() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if((boolean) request.getAttribute("isLoggedIn")) {
			String name = request.getAttribute("name").toString();
			String userType = request.getAttribute("userType").toString();
			String page = null;
			if (userType.equals("owner")) {
				page = "/views/ownerHome.jsp";
			} else if (userType.equals("admin")) {
				page = "/views/adminHome.jsp";
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(page);
			request.setAttribute("name", name);
			request.setAttribute("isLoggedIn", true);
			dispatcher.forward(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if((boolean) request.getAttribute("isLoggedIn")) {
			String name = request.getAttribute("name").toString();
			String userType = request.getAttribute("userType").toString();
			String page = null;
			if (userType.equals("owner")) {
				page = "/views/ownerHome.jsp";
			} else if (userType.equals("admin")) {
				page = "/views/adminHome.jsp";
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(page);
			request.setAttribute("name", name);
			request.setAttribute("isLoggedIn", true);
			dispatcher.forward(request, response);
		}
	}
}
