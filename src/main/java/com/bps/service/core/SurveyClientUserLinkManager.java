package com.bps.service.core;

import java.util.Arrays;
import java.util.List;

import com.bps.dao.SurveyClientUserLinkDAO;
import com.bps.persistence.tables.SurveyClientUserLink;
import com.bps.service.exceptions.BaseException;

public class SurveyClientUserLinkManager {
	private SurveyClientUserLinkDAO surveyClientUserLinkDAO;
	public SurveyClientUserLinkManager() {
		surveyClientUserLinkDAO = new SurveyClientUserLinkDAO();
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
}
