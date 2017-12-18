package com.bps.service.core;

import com.bps.dao.UserDAO;
import com.bps.persistence.tables.IBaseEntity;
import com.bps.persistence.tables.User;
import com.bps.service.api.IValidator;

public class UserManager implements IValidator {
	private UserDAO userDAO;
	
	public UserManager() {
		userDAO = new UserDAO();
	}
	
	public User getUser(String email) {
		if (email != null && !email.isEmpty()) {
			User user = new User();
			user.setEmail(email);
			return this.getUser(user);
		}
		return null;
	}
	
	public User getUser(User user) {
		if(user != null && user.getEmail() != null && !user.getEmail().isEmpty()) {
			return userDAO.read(user);
		}
		return null;
	}

	@Override
	public IBaseEntity validate(IBaseEntity entity) {
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
