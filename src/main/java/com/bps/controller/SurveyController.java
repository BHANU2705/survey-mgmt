package com.bps.controller;

import java.io.IOException;
import java.util.List;

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
import com.bps.util.CommonUtility;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

@WebServlet("/survey")
public class SurveyController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long startTime = System.currentTimeMillis();
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
			SurveyManager manager = new SurveyManager();
			try {
				manager.createSurvey(survey);
			} catch (BaseException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				e.printStackTrace();
			}
		}
		response.setStatus(HttpServletResponse.SC_CREATED);
		long endTime = System.currentTimeMillis();
		long d = endTime - startTime;
		System.out.println("Controller Time: " + d);
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
