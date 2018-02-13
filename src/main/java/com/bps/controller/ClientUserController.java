package com.bps.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;

import com.bps.persistence.tables.Role;
import com.bps.persistence.tables.User;
import com.bps.persistence.tables.UserRole;
import com.bps.service.core.ProcessContext;
import com.bps.service.core.ProcessContextPool;
import com.bps.service.core.UserManager;
import com.bps.service.core.email.EmailManager;
import com.bps.service.exceptions.BaseException;
import com.bps.util.CommonConstants;

@WebServlet(urlPatterns = CommonConstants.URL_CUSER_CONTROLLER, name = "CUserController")
public class ClientUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProcessContext context = ProcessContextPool.get();
		if (context == null) {
			ProcessContext processContext = new ProcessContext();
			ProcessContextPool.set(processContext);
		}
		HttpSession session = request.getSession(false);
		String email = (String) session.getAttribute(CommonConstants.EMAIL);
		String action = request.getParameter(CommonConstants.ACTION);
		if (action != null) {
			if ("createClientUser".equalsIgnoreCase(action)) {
				createClientUser(request, response);
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	private void createClientUser(HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter(CommonConstants.EMAIL);
		String password = RandomStringUtils.randomAlphanumeric(10);
		String name = request.getParameter(CommonConstants.NAME);
		User user = new User(email, password);
		user.setName(name);
		UserManager userManager = new UserManager();
		Role role = new Role();
		role.setId(UserRole.User.toString());
		role.setName(UserRole.User.toString());
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		user.setRoles(roles);
		try {
			HttpSession session = request.getSession();
			String adminEmail = (String) session.getAttribute(CommonConstants.EMAIL);
			String adminName = (String) session.getAttribute(CommonConstants.NAME);
			/*String adminName = ProcessContextPool.get().getUser().getName();
			String adminEmail = ProcessContextPool.get().getUser().getEmail();*/
			userManager.createUser(user, adminEmail);
			EmailManager emailManager = new EmailManager();
			String admin = adminName + " <"	+ adminEmail + ">";
			emailManager.sendClientUserCreationEmail(email, name, password, admin);
			} catch (BaseException e) {
			e.printStackTrace();
		}
		response.setStatus(HttpServletResponse.SC_CREATED);
	}
}
