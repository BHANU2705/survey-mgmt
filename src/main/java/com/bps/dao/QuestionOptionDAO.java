package com.bps.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bps.persistence.tables.IBaseEntity;
import com.bps.persistence.tables.QuestionOption;
import com.bps.service.exceptions.BaseException;

public class QuestionOptionDAO extends DAO implements IBaseDAO {

	@Override
	public IBaseEntity create(IBaseEntity entity) throws BaseException {
		return null;
	}

	public boolean updateAndDelete(List<QuestionOption> questionOptions) throws BaseException {
		Session session = SessionManager.getSession();
		Transaction tx = session.beginTransaction();
		for (QuestionOption questionOption : questionOptions) {
			questionOption.setQuestion(null);
			session.update(questionOption);
		}
		tx.commit();
		tx = session.beginTransaction();
		for (QuestionOption questionOption : questionOptions) {
			session.delete(questionOption);
		}
		tx.commit();
		SessionManager.closeSession(session);
		return true;
	}

	@Override
	public boolean delete(IBaseEntity entity) throws BaseException {
		QuestionOption questionOption = (QuestionOption) entity;
		Session session = SessionManager.getSession();
		Transaction tx = session.beginTransaction();
		session.delete(questionOption);
		tx.commit();
		SessionManager.closeSession(session);
		return true;
	}
	
	public boolean delete(List<QuestionOption> questionOptions) throws BaseException {
		Session session = SessionManager.getSession();
		Transaction tx = session.beginTransaction();
		for (QuestionOption questionOption : questionOptions) {
			session.delete(questionOption);
		}
		tx.commit();
		SessionManager.closeSession(session);
		return true;
	}

	@Override
	public IBaseEntity[] read() throws BaseException {
		return null;
	}

	@Override
	public IBaseEntity read(IBaseEntity entity) throws BaseException {
		QuestionOption question = (QuestionOption) entity;
		if (question != null && question.getId() != null) {
			Session session = SessionManager.getSession();
			session.setDefaultReadOnly(true);
			QuestionOption dbQuestionOption = session.get(QuestionOption.class, question.getId());
			SessionManager.closeSession(session);
			return dbQuestionOption;
		} else {
			return null;
		}
	}

	@Override
	public boolean update(IBaseEntity entity) throws BaseException {
		// TODO Auto-generated method stub
		return false;
	}

}
