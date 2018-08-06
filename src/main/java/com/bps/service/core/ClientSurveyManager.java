package com.bps.service.core;

import java.util.Arrays;
import java.util.List;

import com.bps.dao.ClientSurveyDAO;
import com.bps.service.exceptions.BaseException;
import com.bps.util.ClientSurvey;

public class ClientSurveyManager {

	private ClientSurveyDAO clientSurveyDAO;

	public ClientSurveyManager(String loggedInUserEmail) {
		clientSurveyDAO = new ClientSurveyDAO();
		clientSurveyDAO.setUserEmail(loggedInUserEmail);
	}
	
	public List<ClientSurvey> readSurveys() throws BaseException {
		ClientSurvey[] entities = (ClientSurvey[]) clientSurveyDAO.read();
		return Arrays.asList(entities);
	}
}
