package com.bps.util;

import java.util.List;

public class SurveyAnswerEntity {
	private String questionId;
	private QuestionType questionType;
	private List<ResponseDataEntity> responses;
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public QuestionType getQuestionType() {
		return questionType;
	}
	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}
	public List<ResponseDataEntity> getResponses() {
		return responses;
	}
	public void setResponses(List<ResponseDataEntity> responses) {
		this.responses = responses;
	}
}
