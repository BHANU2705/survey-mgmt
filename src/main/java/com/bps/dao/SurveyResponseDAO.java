package com.bps.dao;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.bps.persistence.tables.IBaseEntity;
import com.bps.persistence.tables.SurveyResponse;
import com.bps.service.exceptions.BaseException;

public class SurveyResponseDAO extends DAO implements IBaseDAO {

	@Override
	public IBaseEntity create(IBaseEntity entity) throws BaseException {
		SurveyResponse surveyResponse = (SurveyResponse) entity;
		Session session = SessionManager.getSession();
		Transaction tx = session.beginTransaction();
		session.save(surveyResponse);
		tx.commit();
		SessionManager.closeSession(session);
		return surveyResponse;
	}

	@Override
	public boolean update(IBaseEntity entity) throws BaseException {
		return false;
	}

	@Override
	public boolean delete(IBaseEntity entity) throws BaseException {
		return false;
	}

	@Override
	public IBaseEntity[] read() throws BaseException {
		return null;
	}

	@Override
	public IBaseEntity read(IBaseEntity entity) throws BaseException {
		SurveyResponse sr = (SurveyResponse) entity;
		SurveyResponse dbResponse = null;
		if (sr != null && sr.getSurveyId() != null && !sr.getSurveyId().isEmpty()) {
			Session session = SessionManager.getSession();
			session.setDefaultReadOnly(true);
			String queryString = "SELECT s.responseId, s.surveyId, s.userId, s.answer, s.answeredOn "
					+ "FROM SurveyResponse s WHERE s.userId = '" + super.getUserEmail() + "' and s.surveyId = '" + sr.getSurveyId() + "'";
			Query<?> query = session.createQuery(queryString);
			query.setReadOnly(true);
			List<?> data = query.getResultList();
			SessionManager.closeSession(session);
			if (data != null && !data.isEmpty()) {
				Iterator<?> it = data.iterator();
				while (it.hasNext()) {
					Object[] list = (Object[]) it.next();
					String responseId = (String) list[0];
					String surveyId = (String) list[1];
					String userId = (String) list[2];
					String answer = (String) list[3];
					Calendar answeredOn = (Calendar) list[4];

					dbResponse = new SurveyResponse();
					dbResponse.setAnswer(answer);
					dbResponse.setAnsweredOn(answeredOn);
					dbResponse.setSurveyId(surveyId);
					dbResponse.setUserId(userId);
					dbResponse.setResponseId(responseId);
				}
			}
		}
		return dbResponse;
	}
}
