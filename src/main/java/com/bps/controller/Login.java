package com.bps.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/home", name = "Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Login() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/Test/");
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userType = null;
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		if (email.equals("owner") && password.equals("owner")) {
			userType = "owner";
		} else if (email.equals("admin") && password.equals("admin")) {
			userType = "admin";
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/maincontroller");
		request.setAttribute("name", email);
		request.setAttribute("isLoggedIn", true);
		request.setAttribute("userType", userType);
		dispatcher.forward(request, response);
	}
}
