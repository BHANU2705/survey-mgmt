package com.bps.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bps.persistence.tables.Role;
import com.bps.persistence.tables.User;
import com.bps.persistence.tables.UserRole;
import com.bps.service.core.ProcessContextPool;
import com.bps.service.core.UserManager;
import com.bps.service.core.email.EmailManager;
import com.bps.service.exceptions.BaseException;
import com.bps.util.CommonConstants;

@WebServlet(urlPatterns = CommonConstants.URL_HOME_CONTROLLER, name = CommonConstants.HOME)
public class HomeController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public HomeController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session != null) {
			session.invalidate();
		}
		response.sendRedirect("/Test/");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter(CommonConstants.ACTION);
		if (action != null) {
			if (action.equalsIgnoreCase(CommonConstants.LOGIN_ACTION_SIGNIN)) {
				String email = request.getParameter(CommonConstants.EMAIL);
				String password = request.getParameter(CommonConstants.PASSWORD);
				User user = new User(email, password);
				UserManager validator = new UserManager();
				User dbUser = null;
				try {
					dbUser = (User) validator.validate(user);
					if (dbUser != null) {
						ProcessContextPool.get().setUser(dbUser);
						RequestDispatcher dispatcher = request
								.getRequestDispatcher(CommonConstants.URL_MAIN_CONTROLLER);
						request.setAttribute(CommonConstants.EMAIL, email);
						request.setAttribute(CommonConstants.NAME, dbUser.getName());
						HttpSession session = request.getSession();
						session.setAttribute(CommonConstants.EMAIL, email);
						session.setAttribute(CommonConstants.NAME, dbUser.getName());
						dispatcher.forward(request, response);
					} else { // User validation failed
						request.setAttribute(CommonConstants.IS_LOGIN_FAILED, true);
						RequestDispatcher dispatcher = request
								.getRequestDispatcher("/");
						dispatcher.forward(request, response);
					}
				} catch (BaseException e) {
					e.printStackTrace();
				}
			} else if (action.equalsIgnoreCase(CommonConstants.LOGIN_ACTION_SIGNUP)) {
				String email = request.getParameter(CommonConstants.EMAIL);
				String password = request.getParameter(CommonConstants.PASSWORD);
				String name = request.getParameter(CommonConstants.NAME);
				User user = new User(email, password);
				user.setName(name);
				UserManager userManager = new UserManager();
				Role role = new Role();
				role.setId(UserRole.Admin.toString());
				List<Role> roles = new ArrayList<>();
				roles.add(role);
				user.setRoles(roles);
				try {
					userManager.createUser(user);
					EmailManager emailManager = new EmailManager();
					emailManager.sendSignUpEmail(email);
					ProcessContextPool.get().setUser(user);
					request.setAttribute(CommonConstants.EMAIL, user.getEmail());
					request.setAttribute(CommonConstants.NAME, user.getName());
					MainController mainController = new MainController();
					mainController.doPost(request, response);
				} catch (BaseException e) {
					request.setAttribute(CommonConstants.IS_SIGNUP_FAILED, true);
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("/");
					dispatcher.forward(request, response);
				}

			} else {
				// TODO: handle error case
			}
		}
	}
}
