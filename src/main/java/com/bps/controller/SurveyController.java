package com.bps.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bps.persistence.tables.Question;
import com.bps.persistence.tables.QuestionOption;
import com.bps.persistence.tables.Survey;
import com.bps.service.core.SurveyManager;
import com.bps.service.exceptions.BaseException;
import com.bps.util.CommonConstants;
import com.bps.util.CommonUtility;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

@WebServlet(urlPatterns = CommonConstants.URL_SURVEY_CONTROLLER, name = "SurveyController")
public class SurveyController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String[]> map = request.getParameterMap();
		String[] surveyIds = null;
		String surveyString = null;
		Gson gson = CommonUtility.buildGson();
		Type listType = new TypeToken<List<Survey>>() {}.getType();
		try {
			if (map != null && !map.isEmpty()) {
				if (map.containsKey(CommonConstants.ID)) {
					surveyIds = map.get(CommonConstants.ID);
					SurveyManager surveyManager = new SurveyManager();
					Survey survey = surveyManager.readSurvey(surveyIds[0]);
					surveyString = gson.toJson(survey, Survey.class);
				}
			} else {
				String email = (String) request.getSession().getAttribute(CommonConstants.EMAIL);
				SurveyManager surveyManager = new SurveyManager(email);
				List<Survey> surveys = surveyManager.readSurveys();
				surveyString = gson.toJson(surveys, listType);
			}
			response.addHeader(CommonConstants.CONTENT_TYPE, CommonConstants.APPLICATION_JSON+";charset=UTF-8");
			response.getWriter().append(surveyString);
		} catch (BaseException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}  catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long startTime = System.currentTimeMillis();
		try {
			if (request.getContentType().equalsIgnoreCase("application/json")) {
				JsonElement payload = CommonUtility.getJSONData(request.getReader());
				Gson gson = new Gson();
				Survey survey = gson.fromJson(payload, Survey.class);
				List<Question> questions = survey.getQuestions();
				survey.setQuestions(null);
				for (Question question : questions) {
					survey.addQuestion(question);
					if (question.getOptions() != null && !question.getOptions().isEmpty()) {
						List<QuestionOption> options = question.getOptions();
						question.setOptions(null);
						for (QuestionOption option: options) {
							question.addOption(option);
						}
					}
				}
				String email = (String) request.getSession().getAttribute(CommonConstants.EMAIL);
				SurveyManager surveyManager = new SurveyManager(email);
				try {
					surveyManager.createSurvey(survey);
				} catch (BaseException e) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					e.printStackTrace();
				}
			}
			response.setStatus(HttpServletResponse.SC_CREATED);
			long endTime = System.currentTimeMillis();
			long d = endTime - startTime;
			System.out.println("Controller Time: " + d);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String surveyId = request.getParameter("id");
		SurveyManager manager = new SurveyManager();
		try {
			Survey survey =  manager.readSurvey(surveyId);
			manager.deleteSurvey(survey);
		} catch (BaseException e) {
			e.printStackTrace();
		}
		response.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
}
