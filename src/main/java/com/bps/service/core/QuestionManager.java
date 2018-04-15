package com.bps.service.core;

import com.bps.dao.QuestionDAO;
import com.bps.dao.QuestionOptionDAO;
import com.bps.persistence.tables.Question;
import com.bps.persistence.tables.QuestionOption;
import com.bps.service.exceptions.BaseException;
import com.bps.util.QuestionType;

public class QuestionManager {
	QuestionDAO dao = null;

	public Question updateQuestion(Question question) throws BaseException {
		Question dbQuestion = readQuestion(question.getId());
		dbQuestion.setText(question.getText());
		if (null != question.getType()) {
			if (question.getType().equals(QuestionType.CheckBox)
					|| question.getType().equals(QuestionType.Dropdown) || question.getType().equals(QuestionType.Radio)) {
				if (dbQuestion.getType().equals(QuestionType.CheckBox)
						|| dbQuestion.getType().equals(QuestionType.Dropdown) || dbQuestion.getType().equals(QuestionType.Radio)) {
					for (int i = 0; i < dbQuestion.getOptions().size(); i++) {
						dbQuestion.getOptions().get(i).setText(question.getOptions().get(i).getText());
					}
				} else {
					QuestionOption option = null;
					for (int i = 0; i < question.getOptions().size(); i++) {
						option = new QuestionOption();
						option.setText(question.getOptions().get(i).getText());
						option.setQuestion(dbQuestion);
						dbQuestion.addOption(option);
					}
				}
			} else {
				if (dbQuestion.getOptions() != null && !dbQuestion.getOptions().isEmpty()) {
					QuestionOptionDAO questionOptionDAO = new QuestionOptionDAO();
					questionOptionDAO.updateAndDelete(dbQuestion.getOptions());
				}
				dbQuestion.setOptions(null);
			}
		}
		dbQuestion.setType(question.getType());
		dao = new QuestionDAO();
		dao.update(dbQuestion);
		return question;
	}

	public Question readQuestion(String questionId) throws BaseException {
		dao = new QuestionDAO();
		Question question = new Question();
		if (questionId != null && !questionId.isEmpty()) {
			question.setId(questionId);
			Question dbQuestion = (Question) dao.read(question);
			return dbQuestion;
		}
		return null;
	}
}
