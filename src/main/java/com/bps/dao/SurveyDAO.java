package com.bps.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bps.persistence.tables.IBaseEntity;
import com.bps.persistence.tables.Survey;
import com.bps.service.exceptions.BaseException;

public class SurveyDAO implements IBaseDAO {

	@Override
	public IBaseEntity create(IBaseEntity entity) throws BaseException {
		Survey survey = (Survey) entity;
		Session session = SessionManager.getSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(survey);
		tx.commit();
		SessionManager.closeSession(session);
		return survey;
	}

	@Override
	public boolean update(IBaseEntity entity) throws BaseException {
		Survey survey = (Survey) entity;
		Session session = SessionManager.getSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(survey);
		tx.commit();
		SessionManager.closeSession(session);
		return true;
	}

	@Override
	public boolean delete(IBaseEntity entity) throws BaseException {
		Survey survey = (Survey) entity;
		Session session = SessionManager.getSession();
		Transaction tx = session.beginTransaction();
		session.delete(survey);
		tx.commit();
		SessionManager.closeSession(session);
		return true;
	}

	@Override
	public IBaseEntity[] read() throws BaseException {
		List<Survey> surveys = new ArrayList<Survey>();
		Session session = SessionManager.getSession();
		session.setDefaultReadOnly(true);
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Survey> criteria = builder.createQuery(Survey.class);
	    criteria.from(Survey.class);
	    surveys = session.createQuery(criteria).getResultList();
	    SessionManager.closeSession(session);
		return (Survey[]) surveys.toArray();
	}

	@Override
	public IBaseEntity read(IBaseEntity entity) throws BaseException {
		Survey survey = (Survey) entity;
		if(survey != null && survey.getId() != null && !survey.getId().isEmpty()) {
			Session session = SessionManager.getSession();
			session.setDefaultReadOnly(true);
			Survey dbSurvey = session.get(Survey.class, survey.getId());
			SessionManager.closeSession(session);
			return dbSurvey;
		}
		return null;
	}
}
