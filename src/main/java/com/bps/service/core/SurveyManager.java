package com.bps.service.core;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.bps.dao.SurveyDAO;
import com.bps.persistence.tables.Survey;
import com.bps.service.exceptions.BaseException;
import com.bps.util.CommonConstants;
import com.bps.util.CommonUtility;
import com.bps.util.Operation;

public class SurveyManager {
	private SurveyDAO surveyDAO;
	private String userEmail;
	public SurveyManager() {
		surveyDAO = new SurveyDAO();
	}
	public SurveyManager(String email) {
		surveyDAO = new SurveyDAO();
		surveyDAO.setUserEmail(email);
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Survey createSurvey(Survey survey) throws BaseException {
		survey.setLifeCycle(CommonUtility.getLifeCycle(Operation.CREATE, userEmail));
		surveyDAO.create(survey);
		return survey;
	}
	
	public Survey updateSurvey(Survey survey) throws BaseException {
		survey.getLifeCycle().setUpdatedOn(Calendar.getInstance(TimeZone.getTimeZone(CommonConstants.UTC), Locale.ENGLISH));
		survey.getLifeCycle().setUpdatedBy(userEmail);
		surveyDAO.update(survey);
		return survey;
	}
	
	public Survey deleteSurvey(Survey survey) throws BaseException {
		surveyDAO.delete(survey);
		return survey;
	}
	
	public Survey readSurvey(String surveyId) throws BaseException {
		Survey survey = new Survey();
		survey.setId(surveyId);
		return (Survey) surveyDAO.read(survey);
	}
	
	public List<Survey> readSurveys() throws BaseException {
		Survey[] entities = (Survey[]) surveyDAO.read();
		return Arrays.asList(entities);
	}
}
