package com.bps.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.bps.persistence.tables.IBaseEntity;
import com.bps.persistence.tables.LifeCycle;
import com.bps.persistence.tables.Question;
import com.bps.persistence.tables.Survey;
import com.bps.service.exceptions.BaseException;
import com.bps.util.SurveyStatus;

public class SurveyDAO implements IBaseDAO {

	@Override
	public IBaseEntity create(IBaseEntity entity) throws BaseException {
		long startTime = System.currentTimeMillis();
		Survey survey = (Survey) entity;
		Session session = SessionManager.getSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(survey);
		tx.commit();
		SessionManager.closeSession(session);
		long endTime = System.currentTimeMillis();
		long d = endTime - startTime;
		System.out.println("DB Time: " + d);
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
		List<Survey> surveys = new ArrayList<>();
		Session session = SessionManager.getSession();
		session.setDefaultReadOnly(true);
		String queryString = "SELECT s.id, s.name, s.status, s.lifeCycle FROM Survey s";
		Query<?> query = session.createQuery(queryString);
		query.setReadOnly(true);
		List<?> data = query.getResultList();
		Iterator<?> it = data.iterator();
		while (it.hasNext()) {
			Object[] list = (Object[]) it.next();
			String id = (String) list[0];
			String name = (String) list[1];
			SurveyStatus status = (SurveyStatus) list[2];
			LifeCycle lifeCycle = (LifeCycle) list[3];
			Survey survey = new Survey();
			survey.setId(id);
			survey.setName(name);
			survey.setStatus(status);
			survey.setLifeCycle(lifeCycle);
			surveys.add(survey);
		}
		SessionManager.closeSession(session);
		return surveys.toArray(new Survey[surveys.size()]);
	}

	@Override
	public IBaseEntity read(IBaseEntity entity) throws BaseException {
		Survey survey = (Survey) entity;
		if(survey != null && survey.getId() != null && !survey.getId().isEmpty()) {
			Session session = SessionManager.getSession();
			session.setDefaultReadOnly(true);
			Survey dbSurvey = session.get(Survey.class, survey.getId());
			SessionManager.closeSession(session);
			if (dbSurvey.getQuestions() != null && !dbSurvey.getQuestions().isEmpty()) {
				List<Question> dbQuestions = dbSurvey.getQuestions();
				dbSurvey.setQuestions(null);
				List<Question> questionSet = new ArrayList<>();
				Set<String> set = new HashSet<>();
				for (Question question : dbQuestions) {
					if (question != null) {
						if(set.add(question.getId())) {
							questionSet.add(question);
						}
					}
				}
				dbSurvey.setQuestions(questionSet);
			}
			return dbSurvey;
		}
		return null;
	}
}
