package com.bps.service.core;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;

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
		setUserEmail(email);
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
		if(surveyDAO.delete(survey)) {
			deleteSurveyImageFolder(survey.getId());
		}
		return survey;
	}
	
	private boolean deleteSurveyImageFolder(String surveyId) {
		boolean isDeleted = false;
		String relativePath = File.separator + surveyId;
		String folderPath = CommonConstants.ABSOLUTE_PATH + relativePath;
		File folder = new File(folderPath);
		if(folder.exists()) {
			try {
				FileUtils.forceDelete(folder);
				isDeleted = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return isDeleted;
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
	
	public boolean isSurveyExist(String surveyId) throws BaseException {
		return surveyDAO.isSurveyExists(surveyId);
	}
}
