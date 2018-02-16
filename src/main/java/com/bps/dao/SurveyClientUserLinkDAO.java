package com.bps.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bps.persistence.tables.IBaseEntity;
import com.bps.persistence.tables.SurveyClientUserLink;
import com.bps.service.exceptions.BaseException;

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
}
