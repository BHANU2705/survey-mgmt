package com.bps.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bps.model.validators.UserValidator;
import com.bps.persistence.tables.User;
import com.bps.service.ProcessContextPool;

@WebServlet(urlPatterns = "/home", name = "Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Home() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/Test/");
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = null;
		String action = request.getParameter("action");
		if (action != null) {
			if (action.equalsIgnoreCase("signin")) {
				email = request.getParameter("loginEmail");
				String password = request.getParameter("loginPassword");
				User user = new User(email, password);
				UserValidator validator = new UserValidator();
				User dbUser = (User) validator.validate(user);
				if(dbUser != null) {
					ProcessContextPool.get().setUser(dbUser);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/maincontroller");
					request.setAttribute("email", email);
					request.setAttribute("name", dbUser.getName());
					ProcessContextPool.get().setUser(dbUser);
					dispatcher.forward(request, response);
				} else {
					//TODO: handle error case
				}
			} else if (action.equalsIgnoreCase("signup")){

			} else {
				//TODO: handle error case
			}
		}
	}
}
