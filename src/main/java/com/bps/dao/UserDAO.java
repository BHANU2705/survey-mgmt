package com.bps.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.bps.persistence.tables.IBaseEntity;
import com.bps.persistence.tables.User;
import com.bps.persistence.tables.UserRole;
import com.bps.persistence.tables.UserRoleEnum;
import com.bps.service.exceptions.BaseException;

public class UserDAO extends DAO implements IBaseDAO {

	@Override
	public User create(IBaseEntity entity) throws BaseException {
		User user = (User) entity;

		Session session = SessionManager.getSession();
		Transaction tx = session.beginTransaction();
		for (UserRole userRole : user.getUserRoles()) {
			session.saveOrUpdate(userRole);
		}
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
		Query<?> deleteSurveyQuery = session
				.createQuery("DELETE FROM Survey WHERE lifeCycle.createdBy = '" + user.getEmail() + "'");
		Query<?> deleteLinkQuery = session
				.createQuery("DELETE FROM SurveyClientUserLink WHERE clientUserEmail = '" + user.getEmail() + "'");
		Query<?> deleteUserRoleQuery = session
				.createQuery("DELETE FROM USER_ROLE ur WHERE ur.user.email = '" + user.getEmail() + "'");
		Query<?> deleteUserQuery = session
				.createQuery("DELETE FROM User WHERE email = '" + user.getEmail() + "'");
		Transaction tx = session.beginTransaction();
		deleteSurveyQuery.executeUpdate();
		deleteLinkQuery.executeUpdate();
		deleteUserRoleQuery.executeUpdate();
		deleteUserQuery.executeUpdate();
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
		if (user != null && user.getEmail() != null && !user.getEmail().isEmpty()) {
			Session session = SessionManager.getSession();
			session.setDefaultReadOnly(true);
			User dbUser = session.get(User.class, user.getEmail());
			SessionManager.closeSession(session);
			return dbUser;
		}
		return null;
	}

	public User[] getMyClientUsers() throws BaseException {
		/*
		 * String queryString =
		 * "SELECT u.email, u.name from User u where u.email in (SELECT userEmail from UserRole where roleId = 'User' and adminEmailId = '"
		 * + super.getUserEmail() + "')"; Session session = null; try { session =
		 * SessionManager.getSession(); Query<?> q1 = session.createQuery(queryString);
		 * q1.setReadOnly(true); List<?> data = q1.getResultList(); if (data.size() > 0)
		 * { Iterator<?> it = data.iterator(); List<User> users = new ArrayList<User>();
		 * User user = null; while (it.hasNext()) { Object[] list = (Object[])
		 * it.next(); String clientUserEmail = (String) list[0]; String clientUserName =
		 * (String) list[1]; user = new User(); user.setName(clientUserName);
		 * user.setEmail(clientUserEmail); users.add(user); }
		 * setAssignedSurveyCount(session, users); SessionManager.closeSession(session);
		 * return users.toArray(new User[users.size()]); } } finally {
		 * SessionManager.closeSession(session); }
		 */
		Session session = null;
		List<User> filteredUsers = null;
		try {
			session = SessionManager.getSession();
			session.setDefaultReadOnly(true);
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<User> criteria = builder.createQuery(User.class);
			criteria.from(User.class);
			List<User> users = session.createQuery(criteria).getResultList();

			String myEmailId = super.getUserEmail();
			if (users != null && !users.isEmpty() && myEmailId != null) {
				filteredUsers = new ArrayList<User>();
				Set<UserRole> userRoles = null;
				for (User user : users) {
					if (user != null) {
						user.setPassword(null);
						userRoles = user.getUserRoles();
						if (userRoles != null && !userRoles.isEmpty()) {
							for (UserRole userRole : userRoles) {
								if (userRole != null && UserRoleEnum.User.toString().equals(userRole.getRole().getId())
										&& userRole.getAdminEmailId().equalsIgnoreCase(myEmailId)) {
									filteredUsers.add(user);
								}
							}
						}
					}
				}
				setAssignedSurveyCount(session, filteredUsers);
			}
		} finally {
			SessionManager.closeSession(session);
		}
		return filteredUsers.toArray(new User[filteredUsers.size()]);
	}

	private void setAssignedSurveyCount(Session session, List<User> users) throws BaseException {
		String queryString = "SELECT clientUserEmail, count(*) FROM SurveyClientUserLink group by clientUserEmail";
		session = SessionManager.getSession();
		Query<?> q1 = session.createQuery(queryString);
		q1.setReadOnly(true);
		List<?> data = q1.getResultList();
		Iterator<?> it = data.iterator();
		Map<String, Integer> map = new HashMap<String, Integer>();
		while (it.hasNext()) {
			Object[] list = (Object[]) it.next();
			String clientUserEmail = (String) list[0];
			Long assignedSurveyCount = (Long) list[1];
			map.put(clientUserEmail, assignedSurveyCount.intValue());
		}
		if (data != null && users != null && !data.isEmpty() && !users.isEmpty()) {
			for (User user : users) {
				if (map.containsKey(user.getEmail())) {
					user.setAssignedSurveyCount(map.get(user.getEmail()));
				}
			}
		}
	}
}
