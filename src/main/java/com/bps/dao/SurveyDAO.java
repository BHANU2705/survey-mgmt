package com.bps.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.bps.persistence.tables.IBaseEntity;
import com.bps.persistence.tables.LifeCycle;
import com.bps.persistence.tables.Question;
import com.bps.persistence.tables.Survey;
import com.bps.service.exceptions.BaseException;
import com.bps.util.SurveyStatus;

public class SurveyDAO extends DAO implements IBaseDAO {
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
		List<Survey> surveys = new ArrayList<>();
		Session session = SessionManager.getSession();
		session.setDefaultReadOnly(true);
		String queryString = "SELECT s.id, s.name, s.status, s.lifeCycle FROM Survey s WHERE s.lifeCycle.createdBy = '" + super.getUserEmail() +"'";
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
		setAssignedUsersCount(session, surveys);
		SessionManager.closeSession(session);
		return surveys.toArray(new Survey[surveys.size()]);
	}
	
	private void setAssignedUsersCount(Session session, List<Survey> surveys) {
		String queryString = "SELECT surveyId, count(*) FROM SurveyClientUserLink group by surveyId";
		Query<?> q1 = session.createQuery(queryString);
		q1.setReadOnly(true);
		List<?> data = q1.getResultList();
		Iterator<?> it = data.iterator();
		Map<String, Integer> map = new HashMap<String, Integer>();
		while (it.hasNext()) {
			Object[] list = (Object[]) it.next();
			String surveyId = (String) list[0];
			Long assignedUsersCount = (Long) list[1];
			map.put(surveyId, assignedUsersCount.intValue());
		}
		if (data != null && surveys != null && !data.isEmpty() && !surveys.isEmpty()) {
			for (Survey surevy : surveys) {
				if (map.containsKey(surevy.getId())) {
					surevy.setAssignedUsersCount(map.get(surevy.getId()));
				}
			}
		}
	}

	@Override
	public IBaseEntity read(IBaseEntity entity) throws BaseException {
		Survey survey = (Survey) entity;
		if(survey != null && survey.getId() != null && !survey.getId().isEmpty()) {
			Session session = SessionManager.getSession();
			session.setDefaultReadOnly(true);
			Survey dbSurvey = session.get(Survey.class, survey.getId());
			setAssignedUsersCount(session, Arrays.asList(dbSurvey));
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
	
	public boolean isSurveyExists(String surveyId) throws BaseException {
		boolean isExist = false;
		Session session = SessionManager.getSession();
		session.setDefaultReadOnly(true);
		String queryString = "SELECT s.id FROM Survey s WHERE s.id = '" + surveyId +"'";
		Query<?> query = session.createQuery(queryString);
		query.setReadOnly(true);
		try {
			Object x = query.getSingleResult();
			if(x != null) {
				isExist = true;
			}
		} catch (NoResultException e) {
			isExist = false;
		} finally {
			SessionManager.closeSession(session);
		}
		return isExist;
	}
}
