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
import com.bps.service.core.SessionFactoryManager;
import com.bps.service.core.UserManager;
import com.bps.service.exceptions.BaseException;
import com.bps.util.CommonConstants;

@WebServlet(urlPatterns = CommonConstants.URL_HOME_CONTROLLER, name = CommonConstants.HOME)
public class HomeController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    public HomeController() {
    }
    
    static {
    	SessionFactoryManager.getSessionFactory();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/Test/");
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter(CommonConstants.LOGIN_ACTION);
		if (action != null) {
			if (action.equalsIgnoreCase(CommonConstants.LOGIN_ACTION_SIGNIN)) {
				String email = request.getParameter(CommonConstants.EMAIL);
				String password = request.getParameter(CommonConstants.PASSWORD);
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
				String email = request.getParameter(CommonConstants.EMAIL);
				String password = request.getParameter(CommonConstants.PASSWORD);
				String name = request.getParameter(CommonConstants.NAME);
				User user = new User(email, password);
				user.setName(name);
				UserManager userManager = new UserManager();
				try {
					userManager.createUser(user);
				} catch (BaseException e) {
					e.printStackTrace();
				}
			} else {
				//TODO: handle error case
			}
		}
	}
}
