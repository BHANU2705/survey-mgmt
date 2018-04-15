package com.bps.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bps.service.core.RolesManager;
import com.bps.service.exceptions.BaseException;
import com.bps.util.CommonConstants;

@WebServlet(urlPatterns = "/roles", name = "RoleController")
public class RoleController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RoleController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = (String) request.getSession().getAttribute(CommonConstants.EMAIL);
		RolesManager rolesManager = new RolesManager(email);
		try {
			String roles = rolesManager.getRoles();
			response.addHeader(CommonConstants.CONTENT_TYPE, CommonConstants.APPLICATION_JSON);
			response.getWriter().append(roles);
		} catch (BaseException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
