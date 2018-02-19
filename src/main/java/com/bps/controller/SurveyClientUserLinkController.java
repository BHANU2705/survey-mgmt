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

import com.bps.persistence.tables.SurveyClientUserLink;
import com.bps.service.core.SurveyClientUserLinkManager;
import com.bps.service.core.SurveyManager;
import com.bps.service.core.UserManager;
import com.bps.service.exceptions.BaseException;
import com.bps.util.CommonConstants;
import com.bps.util.CommonUtility;
import com.bps.util.SurveyUserUtility;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

@WebServlet(urlPatterns = CommonConstants.URL_SURVEY_CLIENTUSER_CONTROLLER, name = "SurveyClientUserLinkController")
public class SurveyClientUserLinkController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String[]> map = request.getParameterMap();
		String responseString = null;
		Gson gson = CommonUtility.buildGson();
		Type listType = new TypeToken<List<SurveyClientUserLink>>() {}.getType();
		Type utilityListType = new TypeToken<List<SurveyUserUtility>>() {}.getType();
		String email = (String) request.getSession().getAttribute(CommonConstants.EMAIL);
		SurveyClientUserLinkManager manager = new SurveyClientUserLinkManager(email);
		try {
			if (map != null && !map.isEmpty()) {
				String surveyId = null;
				String clientUserEmail = null;
				if (map.containsKey(CommonConstants.SURVEY_ID)) {
					surveyId = map.get(CommonConstants.SURVEY_ID)[0];
				}
				if (map.containsKey(CommonConstants.CLIENT_USER_EMAIL)) {
					clientUserEmail = map.get(CommonConstants.CLIENT_USER_EMAIL)[0];
				}
				if (map.containsKey(CommonConstants.QUERY_TYPE)) {
					String qType = map.get(CommonConstants.QUERY_TYPE)[0];
					if (CommonConstants.GET_MY_UNASSIGNED_SURVEYS.equalsIgnoreCase(qType)) {
						if (clientUserEmail != null && !clientUserEmail.isEmpty()) {
							List<SurveyUserUtility> utilities = manager.getMyUnassignedSurveys(clientUserEmail);
							responseString = gson.toJson(utilities, utilityListType);
						} else {
							response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
							response.getWriter().append("Missing Client User Email.");
						}
					} else if (CommonConstants.GET_MY_UNASSIGNED_USERS.equalsIgnoreCase(qType)) {
						if (surveyId != null && !surveyId.isEmpty()) {
							List<SurveyUserUtility> utilities = manager.getMyUnassignedUsers(surveyId);
							responseString = gson.toJson(utilities, utilityListType);
						} else {
							response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
							response.getWriter().append("Missing Survey Id.");
						}
					}
				} else {
					SurveyClientUserLink link = manager.readLink(surveyId, clientUserEmail);
					responseString = gson.toJson(link, SurveyClientUserLink.class);
				}
			} else {
				List<SurveyClientUserLink> links = manager.readLinks();
				responseString = gson.toJson(links, listType);
			}
			response.addHeader(CommonConstants.CONTENT_TYPE, CommonConstants.APPLICATION_JSON + ";charset=UTF-8");
			response.getWriter().append(responseString);
		} catch (BaseException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (request.getContentType().equalsIgnoreCase("application/json")) {
				JsonElement payload = CommonUtility.getJSONData(request.getReader());
				Gson gson = new Gson();
				Type listType = new TypeToken<List<SurveyClientUserLink>>() {
				}.getType();
				List<SurveyClientUserLink> links = gson.fromJson(payload, listType);
				if (validateLinks(links)) {
					String email = (String) request.getSession().getAttribute(CommonConstants.EMAIL);
					SurveyClientUserLinkManager manager = new SurveyClientUserLinkManager(email);
					try {
						manager.createLinks(links);
					} catch (BaseException e) {
						response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						e.printStackTrace();
					}
					response.setStatus(HttpServletResponse.SC_CREATED);
				} else {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response.getWriter().append("Either the survey or user email is not valid.");
				}
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private boolean validateLinks(List<SurveyClientUserLink> links) throws BaseException {
		boolean isValid = true;
		if (links != null && !links.isEmpty()) {
			SurveyManager surveyManager = new SurveyManager();
			UserManager userManager = new UserManager();
			for (SurveyClientUserLink link : links) {
				if (userManager.getUser(link.getClientUserEmail()) == null
						|| !surveyManager.isSurveyExist(link.getSurveyId())) {
					isValid = false;
					break;
				}
			}
		}
		return isValid;
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String[]> map = request.getParameterMap();
		if (map != null && !map.isEmpty()) {
			if (map.containsKey(CommonConstants.SURVEY_ID) && map.containsKey(CommonConstants.CLIENT_USER_EMAIL)) {
				String surveyId = map.get(CommonConstants.ID)[0];
				String clientUserEmail = map.get(CommonConstants.CLIENT_USER_EMAIL)[0];
				SurveyClientUserLink link = new SurveyClientUserLink();
				link.setClientUserEmail(clientUserEmail);
				link.setSurveyId(surveyId);
				String email = (String) request.getSession().getAttribute(CommonConstants.EMAIL);
				SurveyClientUserLinkManager manager = new SurveyClientUserLinkManager(email);
				try {
					manager.deleteLink(link);
				} catch (BaseException e) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					e.printStackTrace();
				}
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
