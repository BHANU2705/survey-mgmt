package com.bps.model.validators;

import com.bps.dao.UserDAO;
import com.bps.persistence.tables.IBaseEntity;
import com.bps.persistence.tables.User;

public class UserValidator implements IValidator {

	private UserDAO userDAO;

	public UserValidator() {
		userDAO = new UserDAO();
	}

	@Override
	public IBaseEntity validate(IBaseEntity entity) {
		User user = (User) entity;
		User dbUser = userDAO.read(user);
		if (dbUser != null) {
			if (dbUser.equals(user)) {
				dbUser.setPassword(null);
				return dbUser;
			}
		}
		return null;
	}
}
