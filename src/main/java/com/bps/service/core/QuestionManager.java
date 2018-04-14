package com.bps.service.core;

import com.bps.dao.QuestionDAO;
import com.bps.persistence.tables.Question;
import com.bps.service.exceptions.BaseException;

public class QuestionManager {
	QuestionDAO dao = null;
	
	public Question updateQuestion(Question question) throws BaseException {
		dao = new QuestionDAO();
		dao.update(question);
		return question;
	}
}
