package com.bps.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bps.persistence.tables.Question;
import com.bps.service.core.QuestionManager;
import com.bps.service.exceptions.BaseException;
import com.bps.util.CommonUtility;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

@WebServlet(urlPatterns = "/question", name = "QuestionController")
public class QuestionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonElement payload = CommonUtility.getJSONData(request.getReader());
		Gson gson = new Gson();
		Question question = gson.fromJson(payload, Question.class);
		QuestionManager manager = new QuestionManager();
		try {
			manager.updateQuestion(question);
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		} catch (BaseException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
