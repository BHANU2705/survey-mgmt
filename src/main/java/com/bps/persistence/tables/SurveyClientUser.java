package com.bps.persistence.tables;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class SurveyClientUser implements IBaseEntity, Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String surveyId;
	@Id
	private String clientUserEmail;
	public String getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}
	public String getClientUserEmail() {
		return clientUserEmail;
	}
	public void setClientUserEmail(String clientUserEmail) {
		this.clientUserEmail = clientUserEmail;
	}
}
