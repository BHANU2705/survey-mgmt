package com.bps.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

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
		return null;
	}

}
