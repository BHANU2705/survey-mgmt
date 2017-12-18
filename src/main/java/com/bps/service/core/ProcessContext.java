package com.bps.service.core;

import org.hibernate.SessionFactory;

import com.bps.persistence.tables.User;

public class ProcessContext {
	private User user;
	private SessionFactory sessionFactory;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}
