package com.bps.controller;

import java.io.IOException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bps.persistence.tables.UserRole;
import com.bps.persistence.tables.UserRoleEnum;
import com.bps.service.core.ProcessContextPool;
import com.bps.util.CommonConstants;
import com.bps.util.CommonUtility;

@WebServlet(urlPatterns = CommonConstants.URL_MAIN_CONTROLLER, name = "MainController")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MainController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CommonUtility.logout(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = null;
		Set<UserRole> userRoles = ProcessContextPool.get().getUser().getUserRoles();
		boolean isAdmin = false, isUser = false;
		if (userRoles != null) {
			for (UserRole userRole : userRoles) {
				if(UserRoleEnum.Admin.toString().equals(userRole.getRole().getId())) {
					isAdmin = true;
				}
				if(UserRoleEnum.User.toString().equals(userRole.getRole().getId())) {
					isUser = true;
				}
			}
		}
		request.setAttribute("isAdmin", isAdmin);
		request.setAttribute("isUser", isUser);
		page = "/views/adminHome.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}
}
