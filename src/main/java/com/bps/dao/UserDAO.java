package com.bps.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bps.persistence.tables.IBaseEntity;
import com.bps.persistence.tables.User;
import com.bps.service.exceptions.BaseException;

public class UserDAO implements IBaseDAO {

	@Override
	public User create(IBaseEntity entity) throws BaseException {
		User user = (User) entity;
		Session session = SessionManager.getSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(user);
		tx.commit();
		SessionManager.closeSession(session);
		return user;
	}

	@Override
	public boolean update(IBaseEntity entity) throws BaseException {
		User user = (User) entity;
		Session session = SessionManager.getSession();
		Transaction tx = session.beginTransaction();
		session.update(user);
		tx.commit();
		SessionManager.closeSession(session);
		return true;
	}

	@Override
	public boolean delete(IBaseEntity entity) throws BaseException {
		User user = (User) entity;
		Session session = SessionManager.getSession();
		Transaction tx = session.beginTransaction();
		session.delete(user.getEmail());
		tx.commit();
		SessionManager.closeSession(session);
		return true;
	}

	@Override
	public User[] read() throws BaseException {
		List<User> users = new ArrayList<User>();
		Session session = SessionManager.getSession();
		session.setDefaultReadOnly(true);
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
	    criteria.from(User.class);
	    users = session.createQuery(criteria).getResultList();
	    SessionManager.closeSession(session);
		return (User[]) users.toArray();
	}

	@Override
	public User read(IBaseEntity entity) throws BaseException {
		User user = (User) entity;
		if(user != null && user.getEmail() != null && !user.getEmail().isEmpty()) {
			Session session = SessionManager.getSession();
			session.setDefaultReadOnly(true);
			User dbUser = session.get(User.class, user.getEmail());
			SessionManager.closeSession(session);
			return dbUser;
		}
		return null;
	}
	
}
