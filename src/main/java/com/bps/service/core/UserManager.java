package com.bps.service.core;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import com.bps.dao.UserDAO;
import com.bps.persistence.tables.IBaseEntity;
import com.bps.persistence.tables.User;
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
		user.setLifeCycle(CommonUtility.getLifeCycle(Operation.CREATE, user.getEmail()));
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
	
	public User updateUser(User user, boolean isNameUpdate, boolean isPasswordUpdate) throws BaseException {
		if(user != null && user.getEmail() != null && !user.getEmail().isEmpty()) {
			User u = getUser(user);
			if (isNameUpdate) {
				u.setName(user.getName());
			}
			if (isPasswordUpdate) {
				u.setPassword(user.getPassword());
			}
			u.getLifeCycle().setUpdatedOn(Calendar.getInstance(TimeZone.getTimeZone(CommonConstants.UTC), Locale.ENGLISH));
			u.getLifeCycle().setUpdatedBy(user.getEmail());
			if(userDAO.update(u)) {
				return u;
			}
		}
		return null;
	}
	public User deleteUser(User user) throws BaseException {
		if(user != null) {
			if(userDAO.delete(user)) {
				return user;
			}
		}
		return null;
	}
}
