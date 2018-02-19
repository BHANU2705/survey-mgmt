package com.bps.util;

public class SurveyUserUtility {
	private String surveyId;
	private String surveyName;
	private String clientUserEmail;
	private String userName;

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	public String getClientUserEmail() {
		return clientUserEmail;
	}

	public void setClientUserEmail(String userEmail) {
		this.clientUserEmail = userEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
