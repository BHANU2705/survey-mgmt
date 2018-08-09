package com.bps.util;

import java.util.List;

public class SurveyResponse {
	private String surveyId;
	private String userId;
	private List<SurveyAnswer> answers;
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
	public List<SurveyAnswer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<SurveyAnswer> answers) {
		this.answers = answers;
	}
}
