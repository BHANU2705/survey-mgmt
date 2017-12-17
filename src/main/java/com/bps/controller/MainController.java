package com.bps.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bps.persistence.tables.Role;
import com.bps.persistence.tables.UserRole;
import com.bps.service.ProcessContextPool;

@WebServlet(urlPatterns = "/maincontroller", name = "MainController")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MainController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
