package com.bps.persistence.tables;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.bps.util.CommonConstants;
import com.bps.util.CommonUtility;
import com.bps.util.SurveyAnswerEntity;
import com.bps.util.SurveyResponseEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Entity
public class SurveyResponse implements IBaseEntity {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String responseId;

	@Column(nullable = false)
	private String surveyId;

	@Column(nullable = false)
	private String userId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Calendar answeredOn;

	@Lob
	private String answer;

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

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

	public Calendar getAnsweredOn() {
		return answeredOn;
	}

	public void setAnsweredOn(Calendar answeredOn) {
		this.answeredOn = answeredOn;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public static SurveyResponse build(final SurveyResponseEntity entity) {
		SurveyResponse surveyResponse = null;
		if (entity != null) {
			surveyResponse = new SurveyResponse();

			Gson gson = CommonUtility.buildGson();
			Type listType = new TypeToken<List<SurveyAnswerEntity>>() {
			}.getType();
			String answerString = gson.toJson(entity.getAnswers(), listType);
			surveyResponse.setAnswer(answerString);

			surveyResponse
					.setAnsweredOn(Calendar.getInstance(TimeZone.getTimeZone(CommonConstants.UTC), Locale.ENGLISH));
			surveyResponse.setSurveyId(entity.getSurveyId());
			surveyResponse.setUserId(entity.getUserId());
		}
		return surveyResponse;
	}
}
