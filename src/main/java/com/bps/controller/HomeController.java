package com.bps.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bps.persistence.tables.User;
import com.bps.service.core.ProcessContextPool;
import com.bps.service.core.UserManager;
import com.bps.util.CommonConstants;

@WebServlet(urlPatterns = CommonConstants.URL_HOME_CONTROLLER, name = CommonConstants.HOME)
public class HomeController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    public HomeController() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/Test/");
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = null;
		String action = request.getParameter(CommonConstants.LOGIN_ACTION);
		if (action != null) {
			if (action.equalsIgnoreCase(CommonConstants.LOGIN_ACTION_SIGNIN)) {
				email = request.getParameter(CommonConstants.LOGIN_EMAIL);
				String password = request.getParameter(CommonConstants.LOGIN_PASSWORD);
				User user = new User(email, password);
				UserManager validator = new UserManager();
				User dbUser = (User) validator.validate(user);
				if(dbUser != null) {
					ProcessContextPool.get().setUser(dbUser);
					RequestDispatcher dispatcher = request.getRequestDispatcher(CommonConstants.URL_MAIN_CONTROLLER);
					request.setAttribute(CommonConstants.EMAIL, email);
					request.setAttribute(CommonConstants.NAME, dbUser.getName());
					ProcessContextPool.get().setUser(dbUser);
					dispatcher.forward(request, response);
				} else {
					//TODO: handle error case
				}
			} else if (action.equalsIgnoreCase(CommonConstants.LOGIN_ACTION_SIGNUP)) {
				

			} else {
				//TODO: handle error case
			}
		}
	}
}
