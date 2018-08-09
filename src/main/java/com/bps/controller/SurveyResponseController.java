package com.bps.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bps.service.core.SurveyResponseManager;
import com.bps.service.exceptions.BaseException;
import com.bps.util.CommonConstants;
import com.bps.util.CommonUtility;
import com.bps.util.SurveyResponseEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@WebServlet(urlPatterns = CommonConstants.URL_SURVEY_REPONSE_CONTROLLER, name = "SurveyResponseController")
@MultipartConfig
public class SurveyResponseController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final PrintWriter writer = response.getWriter();
		try {
			String answersJsonString = request.getParameter("answers");
			
			Gson gson = new Gson();
			SurveyResponseEntity surveyReponse = gson.fromJson(answersJsonString, SurveyResponseEntity.class);
			Part filePart = request.getPart("image");
			
			String email = (String) request.getSession().getAttribute(CommonConstants.EMAIL);
			SurveyResponseManager manager = new SurveyResponseManager(email);
			SurveyResponseEntity existingResponse = manager.getResponse(surveyReponse.getSurveyId());
			if (existingResponse == null) {
				manager.submitResponse(surveyReponse, filePart);
				response.setStatus(HttpServletResponse.SC_CREATED);
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().append("You have already submitted response for this survey.");
				response.setContentType("text/plain");
			}
		} catch (BaseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String surveyId = request.getParameter("surveyId");
		String email = (String) request.getSession().getAttribute(CommonConstants.EMAIL);
		SurveyResponseManager manager = new SurveyResponseManager(email);
		try {
			SurveyResponseEntity surveyResponse = manager.getResponse(surveyId);
			if (surveyResponse != null) {
				Gson gson = CommonUtility.buildGson();
				Type listType = new TypeToken<SurveyResponseEntity>() {}.getType();
				String surveyResponseString = gson.toJson(surveyResponse, listType);
				
				response.setContentType(CommonConstants.APPLICATION_JSON);
				response.getWriter().append(surveyResponseString);
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
}
