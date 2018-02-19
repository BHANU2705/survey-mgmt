package com.bps.service.core;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
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
	private String userEmail;
	
	public UserManager() {
		userDAO = new UserDAO();
	}
	
	public UserManager(String email) {
		userDAO = new UserDAO();
		userDAO.setUserEmail(email);
		setUserEmail(email);
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public User createUser(User user, String createdByEmail) throws BaseException {
		if (user == null || user.getEmail() == null || user.getEmail().isEmpty()
				|| user.getPassword() == null || user.getPassword().isEmpty()
				|| user.getName() == null || user.getName().isEmpty()) {
			BaseException e = new BaseException(HttpServletResponse.SC_BAD_REQUEST, ErrorCode.MISSING_MANDATORY_ITEMS);
			throw e;
		}
		String creatorEmail = (createdByEmail == null) ? user.getEmail() : createdByEmail;
		user.setLifeCycle(CommonUtility.getLifeCycle(Operation.CREATE, creatorEmail));
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
			User dbUser = userDAO.read(user);
			if (dbUser != null) {
				dbUser.setPassword(null);
			}
			return dbUser;
		}
		return null;
	}
	
	@Override
	public IBaseEntity validate(IBaseEntity entity) throws BaseException {
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
			String updatorEmail = (userEmail == null) ? user.getEmail() : userEmail;
			u.getLifeCycle().setUpdatedBy(updatorEmail);
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
	
	public List<User> getMyClientUsers() throws BaseException {
		User[] entities = (User[]) userDAO.getMyClientUsers();
		return Arrays.asList(entities);
	}
}
