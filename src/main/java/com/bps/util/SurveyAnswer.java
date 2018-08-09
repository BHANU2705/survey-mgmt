package com.bps.util;

import java.util.List;

public class SurveyAnswer {
	private String questionId;
	private QuestionType questionType;
	private List<ResponseData> responses;
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
	public List<ResponseData> getResponses() {
		return responses;
	}
	public void setResponses(List<ResponseData> responses) {
		this.responses = responses;
	}
}
