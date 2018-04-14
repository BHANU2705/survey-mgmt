package com.bps.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.bps.persistence.tables.IBaseEntity;
import com.bps.persistence.tables.Question;
import com.bps.service.exceptions.BaseException;

public class QuestionDAO extends DAO implements IBaseDAO {

	@Override
	public IBaseEntity create(IBaseEntity entity) throws BaseException {
		return null;
	}

	@Override
	public boolean update(IBaseEntity entity) throws BaseException {
		Question question = (Question) entity;
		Session session = SessionManager.getSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(question);
		tx.commit();
		SessionManager.closeSession(session);
		return true;
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
