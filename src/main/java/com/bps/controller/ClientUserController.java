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
import com.bps.service.core.UserManager;
import com.bps.service.core.email.EmailManager;
import com.bps.service.exceptions.BaseException;
import com.bps.util.CommonConstants;
import com.bps.util.CommonUtility;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

@WebServlet(urlPatterns = CommonConstants.URL_CUSER_CONTROLLER, name = "CUserController")
public class ClientUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String str = "\name\":\"Bhanu\"";
		response.addHeader(CommonConstants.CONTENT_TYPE, CommonConstants.APPLICATION_JSON+";charset=UTF-8");
		response.getWriter().append(str);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		createClientUser(request, response);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	private void createClientUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (request.getContentType().equalsIgnoreCase("application/json")) {
				JsonElement payload = CommonUtility.getJSONData(request.getReader());
				Gson gson = new Gson();
				User user = gson.fromJson(payload, User.class);
				String password = RandomStringUtils.randomAlphanumeric(10);
				user.setPassword(password);
				UserManager userManager = new UserManager();
				Role role = new Role();
				role.setId(UserRole.User.toString());
				role.setName(UserRole.User.toString());
				List<Role> roles = new ArrayList<>();
				roles.add(role);
				user.setRoles(roles);

				HttpSession session = request.getSession();
				String adminEmail = (String) session.getAttribute(CommonConstants.EMAIL);
				String adminName = (String) session.getAttribute(CommonConstants.NAME);
				userManager.createUser(user, adminEmail);
				EmailManager emailManager = new EmailManager();
				String admin = adminName + " <"	+ adminEmail + ">";
				emailManager.sendClientUserCreationEmail(user.getEmail(), user.getName(), user.getPassword(), admin);
			}
		} catch (BaseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setStatus(HttpServletResponse.SC_CREATED);
	}
}
