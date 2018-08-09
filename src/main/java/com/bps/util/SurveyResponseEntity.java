package com.bps.util;

import java.lang.reflect.Type;
import java.util.List;

import com.bps.persistence.tables.SurveyResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
	public static SurveyResponseEntity build(final SurveyResponse dbResponse) {
		SurveyResponseEntity entity = null;
		if(dbResponse != null) {
			entity = new SurveyResponseEntity();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<SurveyAnswerEntity>>() {}.getType();
			List<SurveyAnswerEntity> answers = gson.fromJson(dbResponse.getAnswer(), listType);
			entity.setAnswers(answers);
			entity.setSurveyId(dbResponse.getSurveyId());
			entity.setUserId(dbResponse.getUserId());
		}
		return entity;
	}
}
