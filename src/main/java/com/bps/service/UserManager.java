package com.bps.service;

import com.bps.persistence.tables.User;

public class UserManager {
	public static final User getUser(String email) {
		User user = new User();
		return user;
	}
}
