package com.bps.util;

import java.util.List;

public class SurveyResponseEntity {
	private String surveyId;
	private String userId;
	private List<SurveyAnswerEntity> answers;
	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<SurveyAnswerEntity> getAnswers() {
		return answers;
	}
	public void setAnswers(List<SurveyAnswerEntity> answers) {
		this.answers = answers;
	}
	
}
