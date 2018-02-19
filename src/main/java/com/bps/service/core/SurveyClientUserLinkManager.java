package com.bps.service.core;

import java.util.Arrays;
import java.util.List;

import com.bps.dao.SurveyClientUserLinkDAO;
import com.bps.persistence.tables.SurveyClientUserLink;
import com.bps.service.exceptions.BaseException;
import com.bps.util.SurveyUserUtility;

public class SurveyClientUserLinkManager {
	private SurveyClientUserLinkDAO surveyClientUserLinkDAO;
	private String userEmail = null;;
		
	public SurveyClientUserLinkManager(String email) {
		surveyClientUserLinkDAO = new SurveyClientUserLinkDAO();
		surveyClientUserLinkDAO.setUserEmail(email);
		setUserEmail(email);
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public void createLink(SurveyClientUserLink link) throws BaseException {
		surveyClientUserLinkDAO.create(link);
	}
	
	public void createLinks(List<SurveyClientUserLink> links) throws BaseException {
		for (SurveyClientUserLink link : links) {
			surveyClientUserLinkDAO.create(link);
		}
	}
	
	public SurveyClientUserLink deleteLink(SurveyClientUserLink link) throws BaseException {
		surveyClientUserLinkDAO.delete(link);
		return link;
	}
	
	public SurveyClientUserLink readLink(String surveyId, String clientUserEmail) throws BaseException {
		SurveyClientUserLink link = new SurveyClientUserLink();
		link.setSurveyId(surveyId);
		link.setClientUserEmail(clientUserEmail);
		return (SurveyClientUserLink) surveyClientUserLinkDAO.read(link);
	}
	
	public List<SurveyClientUserLink> readLinks() throws BaseException {
		SurveyClientUserLink[] entities = (SurveyClientUserLink[]) surveyClientUserLinkDAO.read();
		return Arrays.asList(entities);
	}
	
	public List<SurveyUserUtility> getMyUnassignedSurveys(String clientUserEmail) throws BaseException {
		List<SurveyUserUtility> utilities = surveyClientUserLinkDAO.getMyUnassignedSurveys(clientUserEmail, userEmail);
		return utilities;
	}
	
	public List<SurveyUserUtility> getMyUnassignedUsers(String surveyId) throws BaseException {
		List<SurveyUserUtility> utilities = surveyClientUserLinkDAO.getMyUnassignedUsers(surveyId, userEmail);
		return utilities;
	}
}
