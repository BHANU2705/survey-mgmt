package com.bps.persistence.tables;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class SurveyClientUserLink implements IBaseEntity, Serializable {
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientUserEmail == null) ? 0 : clientUserEmail.hashCode());
		result = prime * result + ((surveyId == null) ? 0 : surveyId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SurveyClientUserLink)) {
			return false;
		}
		SurveyClientUserLink other = (SurveyClientUserLink) obj;
		if (clientUserEmail == null) {
			if (other.clientUserEmail != null) {
				return false;
			}
		} else if (!clientUserEmail.equals(other.clientUserEmail)) {
			return false;
		}
		if (surveyId == null) {
			if (other.surveyId != null) {
				return false;
			}
		} else if (!surveyId.equals(other.surveyId)) {
			return false;
		}
		return true;
	}
}
