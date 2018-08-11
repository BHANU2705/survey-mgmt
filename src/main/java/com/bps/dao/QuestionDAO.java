package com.bps.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.bps.persistence.tables.IBaseEntity;
import com.bps.persistence.tables.Question;
import com.bps.persistence.tables.Survey;
import com.bps.service.exceptions.BaseException;
import com.bps.util.QuestionType;

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
		session.update(question);
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
		Question question = (Question) entity;
		if (question != null && question.getId() != null) {
			Session session = SessionManager.getSession();
			session.setDefaultReadOnly(true);
			Question dbQuestion = session.get(Question.class, question.getId());
			SessionManager.closeSession(session);
			return dbQuestion;
		} else {
			return null;
		}
	}

	public List<Question> readImageTypeQuestions() throws BaseException {
		List<Question> questions = null;
		Session session = SessionManager.getSession();
		session.setDefaultReadOnly(true);
		String queryString = "SELECT q.id, q.survey FROM Question q WHERE q.type = " + QuestionType.Image.ordinal() +"";
		Query<?> query = session.createQuery(queryString);
		query.setReadOnly(true);
		List<?> data = query.getResultList();
		SessionManager.closeSession(session);
		if(data != null && !data.isEmpty()) {
			questions = new ArrayList<>();
			Iterator<?> it = data.iterator();
			while (it.hasNext()) {
				Object[] list = (Object[]) it.next();
				String questionId = (String) list[0];
				Survey survey = (Survey) list[1];
				Question question = new Question();
				question.setId(questionId);
				question.setSurvey(survey);
				questions.add(question);
			}
		}
		return questions;
	}
}
