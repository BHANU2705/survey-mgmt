package com.bps.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bps.persistence.tables.Role;
import com.bps.persistence.tables.UserRole;
import com.bps.service.core.ProcessContextPool;
import com.bps.util.CommonConstants;

@WebServlet(urlPatterns = CommonConstants.URL_MAIN_CONTROLLER, name = "MainController")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MainController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
	    if (session != null && session.getAttribute("name") != null) {
	    	RequestDispatcher rd=request.getRequestDispatcher("/");
	        rd.forward(request, response);
		}
		response.sendRedirect("/Test/");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = null;
		List<Role> roles = ProcessContextPool.get().getUser().getRoles();
		boolean isAdmin = false, isOwner = false, isUser = false;
		for (Role role : roles) {
			if(UserRole.Admin.toString().equals(role.getId())) {
				isAdmin = true;
			}
			if(UserRole.Owner.toString().equals(role.getId())) {
				isOwner = true;
			}
			if(UserRole.User.toString().equals(role.getId())) {
				isUser = true;
			}
		}
		request.setAttribute("isAdmin", isAdmin);
		request.setAttribute("isOwner", isOwner);
		request.setAttribute("isUser", isUser);
		page = "/views/adminHome.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}
}
