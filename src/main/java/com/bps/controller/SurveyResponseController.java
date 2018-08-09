package com.bps.controller;

import java.io.IOException;
import java.io.PrintWriter;

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
import com.bps.util.SurveyResponseEntity;
import com.google.gson.Gson;

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
			manager.submitResponse(surveyReponse, filePart);
			writer.append("success");
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
}
