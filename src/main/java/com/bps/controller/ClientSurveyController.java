package com.bps.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bps.service.core.ClientSurveyManager;
import com.bps.service.exceptions.BaseException;
import com.bps.util.ClientSurvey;
import com.bps.util.CommonConstants;
import com.bps.util.CommonUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@WebServlet(urlPatterns = CommonConstants.URL_CLIENT_SURVEY_CONTROLLER, name = "ClientSurveyController")
public class ClientSurveyController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUserEmail = (String) request.getSession().getAttribute(CommonConstants.EMAIL);
		ClientSurveyManager clientSurveyManager = new ClientSurveyManager(loggedInUserEmail);
		try {
			List<ClientSurvey> surveys = clientSurveyManager.readSurveys();
			Gson gson = CommonUtility.buildGson();
			Type listType = new TypeToken<List<ClientSurvey>>() {}.getType();
			String surveyString = gson.toJson(surveys, listType);
			response.addHeader(CommonConstants.CONTENT_TYPE, CommonConstants.APPLICATION_JSON+";charset=UTF-8");
			response.getWriter().append(surveyString);
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
}
