package com.bps.service.core;

import java.util.List;

import com.bps.dao.RolesDAO;
import com.bps.persistence.tables.Role;
import com.bps.service.exceptions.BaseException;
import com.google.gson.Gson;

public class RolesManager {
	private RolesDAO rolesDAO = null;
	public RolesManager(String email) {
		rolesDAO = new RolesDAO();
		rolesDAO.setUserEmail(email);
	}
	
	public String getRoles() throws BaseException {
		List<Role> roles = rolesDAO.fetchAssignedRoles();
		Gson gson = new Gson();
		return gson.toJson(roles);
	}
}
