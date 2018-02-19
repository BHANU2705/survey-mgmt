package com.bps.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.bps.persistence.tables.IBaseEntity;
import com.bps.persistence.tables.User;
import com.bps.service.exceptions.BaseException;

public class UserDAO extends DAO implements IBaseDAO {

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
		User dbUser = session.get(User.class, user.getEmail());
		Query<?> deleteSurveyQuery = session.createQuery("DELETE FROM Survey WHERE lifeCycle.createdBy = '" + user.getEmail() + "'");
		Query<?> deleteLinkQuery = session.createQuery("DELETE FROM SurveyClientUserLink WHERE clientUserEmail = '" + user.getEmail() + "'");
		Transaction tx = session.beginTransaction();
		session.delete(dbUser);
		deleteSurveyQuery.executeUpdate();
		deleteLinkQuery.executeUpdate();
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
	
	public User[] getMyClientUsers() throws BaseException {
		Session session = SessionManager.getSession();
		session.setDefaultReadOnly(true);
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(User.class);
		query.select(root).where(builder.and(builder.equal(root.get("lifeCycle").get("createdBy"), super.getUserEmail()), builder.notEqual(root.get("lifeCycle").get("createdBy"), root.get("email"))));
        Query<User> q=session.createQuery(query);
        List<User> users = q.getResultList();
		setAssignedSurveyCount(session, users);
		SessionManager.closeSession(session);
		return users.toArray(new User[users.size()]);
	}

	private void setAssignedSurveyCount(Session session, List<User> users) {
		String queryString = "SELECT clientUserEmail, count(*) FROM SurveyClientUserLink group by clientUserEmail";
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
