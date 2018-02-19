package com.bps.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.bps.persistence.tables.IBaseEntity;
import com.bps.persistence.tables.SurveyClientUserLink;
import com.bps.service.exceptions.BaseException;
import com.bps.util.SurveyUserUtility;

public class SurveyClientUserLinkDAO extends DAO implements IBaseDAO {

	@Override
	public IBaseEntity create(IBaseEntity entity) throws BaseException {
		SurveyClientUserLink link = (SurveyClientUserLink) entity;
		Session session = SessionManager.getSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(link);
		tx.commit();
		SessionManager.closeSession(session);
		return link;
	}

	@Override
	public boolean update(IBaseEntity entity) throws BaseException {
		return false;
	}

	@Override
	public boolean delete(IBaseEntity entity) throws BaseException {
		SurveyClientUserLink link = (SurveyClientUserLink) entity;
		Session session = SessionManager.getSession();
		Transaction tx = session.beginTransaction();
		session.delete(link);
		tx.commit();
		SessionManager.closeSession(session);
		return true;
	}

	@Override
	public IBaseEntity[] read() throws BaseException {
		List<SurveyClientUserLink> links = new ArrayList<SurveyClientUserLink>();
		Session session = SessionManager.getSession();
		session.setDefaultReadOnly(true);
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<SurveyClientUserLink> criteria = builder.createQuery(SurveyClientUserLink.class);
	    criteria.from(SurveyClientUserLink.class);
	    links = session.createQuery(criteria).getResultList();
	    SessionManager.closeSession(session);
	    return links.toArray(new SurveyClientUserLink[links.size()]);
	}

	@Override
	public IBaseEntity read(IBaseEntity entity) throws BaseException {
		SurveyClientUserLink link = (SurveyClientUserLink) entity;
		Session session = SessionManager.getSession();
		session.setDefaultReadOnly(true);
		SurveyClientUserLink dbLink = session.get(SurveyClientUserLink.class, link);
		SessionManager.closeSession(session);
		return dbLink;
	}
	
	public List<SurveyUserUtility> getMyUnassignedSurveys(String clientUserEmail, String admin) throws BaseException {
		List<SurveyUserUtility> utilities = new ArrayList<>();
		Session session = SessionManager.getSession();
		session.setDefaultReadOnly(true);
		String queryString = "SELECT s.id, s.name FROM Survey s WHERE s.lifeCycle.createdBy = '" + admin +"' AND s.id NOT IN (select l.surveyId from SurveyClientUserLink l where l.clientUserEmail = '" + clientUserEmail +"')";
		Query<?> query = session.createQuery(queryString);
		query.setReadOnly(true);
		List<?> data = query.getResultList();
		SessionManager.closeSession(session);
		Iterator<?> it = data.iterator();
		SurveyUserUtility utility = null;
		while (it.hasNext()) {
			Object[] list = (Object[]) it.next();
			String surveyId = (String) list[0];
			String surveyName = (String) list[1];
			utility = new SurveyUserUtility();
			utility.setSurveyId(surveyId);
			utility.setSurveyName(surveyName);
			utility.setClientUserEmail(clientUserEmail);
			utilities.add(utility);
		}
		return utilities;
	}
	
	public List<SurveyUserUtility> getMyUnassignedUsers(String surveyId, String admin) throws BaseException {
		List<SurveyUserUtility> utilities = new ArrayList<>();
		Session session = SessionManager.getSession();
		session.setDefaultReadOnly(true);
		String queryString = "SELECT u.name, u.email FROM User u WHERE u.lifeCycle.createdBy = '" + admin +"' AND u.EMAIL NOT IN (select l.clientUserEmail from SurveyClientUserLink l where l.surveyId = '" + surveyId +"')";
		Query<?> query = session.createQuery(queryString);
		query.setReadOnly(true);
		List<?> data = query.getResultList();
		SessionManager.closeSession(session);
		Iterator<?> it = data.iterator();
		SurveyUserUtility utility = null;
		while (it.hasNext()) {
			Object[] list = (Object[]) it.next();
			String userName = (String) list[0];
			String userEmail = (String) list[1];
			utility = new SurveyUserUtility();
			utility.setUserName(userName);
			utility.setClientUserEmail(userEmail);
			utilities.add(utility);
		}
		return utilities;
	}
}
