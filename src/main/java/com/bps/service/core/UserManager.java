package com.bps.service.core;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.bps.dao.UserDAO;
import com.bps.persistence.tables.IBaseEntity;
import com.bps.persistence.tables.Role;
import com.bps.persistence.tables.User;
import com.bps.persistence.tables.UserRole;
import com.bps.service.api.IValidator;
import com.bps.service.exceptions.BaseException;
import com.bps.service.exceptions.ErrorCode;
import com.bps.util.CommonConstants;
import com.bps.util.CommonUtility;
import com.bps.util.Operation;

public class UserManager implements IValidator {
	private UserDAO userDAO;
	
	public UserManager() {
		userDAO = new UserDAO();
	}
	
	public User createUser(User user) throws BaseException {
		if (user == null || user.getEmail() == null || user.getEmail().isEmpty()
				|| user.getPassword() == null || user.getPassword().isEmpty()
				|| user.getName() == null || user.getName().isEmpty()) {
			BaseException e = new BaseException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.MISSING_MANDATORY_ITEMS);
			throw e;
		}
		user.setLifeCycle(CommonUtility.getLifeCycle(Operation.CREATE, CommonConstants.SELF));
		Role role = new Role();
		role.setId(UserRole.Admin.toString());
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		user.setRoles(roles);
		userDAO.create(user);
		return user;
	}
	
	public User getUser(String email) throws BaseException {
		if (email != null && !email.isEmpty()) {
			User user = new User();
			user.setEmail(email);
			return this.getUser(user);
		}
		return null;
	}
	
	public User getUser(User user) throws BaseException {
		if(user != null && user.getEmail() != null && !user.getEmail().isEmpty()) {
			return userDAO.read(user);
		}
		return null;
	}

	@Override
	public IBaseEntity validate(IBaseEntity entity) throws BaseException {
		User user = (User) entity;
		User dbUser = this.getUser(user);
		if (dbUser != null) {
			if (dbUser.equals(user)) {
				dbUser.setPassword(null);
				return dbUser;
			}
		}
		return null;
	}
}
