package com.bps.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.persistence.ManyToOne;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bps.persistence.tables.LifeCycle;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class CommonUtility {

	public static Gson buildGson() {
		return new GsonBuilder().addSerializationExclusionStrategy(getExclusionStrategy()).create();
	}

	private static ExclusionStrategy getExclusionStrategy() {
		ExclusionStrategy exlStrategy = new ExclusionStrategy() {
			@Override
			public boolean shouldSkipField(FieldAttributes fas) {
				return (null != fas.getAnnotation(ManyToOne.class));
			}

			@Override
			public boolean shouldSkipClass(Class<?> classO) {
				return (null != classO.getAnnotation(ManyToOne.class));
			}
		};
		return exlStrategy;
	}

	public static LifeCycle getLifeCycle(Operation operation, String user) {
		LifeCycle lifeCycle = new LifeCycle();
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone(CommonConstants.UTC), Locale.ENGLISH);
		switch (operation) {
		case CREATE:
			lifeCycle.setCreatedOn(c);
			lifeCycle.setUpdatedOn(c);
			lifeCycle.setCreatedBy(user);
			lifeCycle.setUpdatedBy(user);
			break;
		case UPDATE:
			lifeCycle.setUpdatedOn(c);
			lifeCycle.setUpdatedBy(user);
			break;
		default:
			break;
		}
		return lifeCycle;
	}

	public static void navigateToPage(String page, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}

	public static String getPageURL(CommonConstants.Page page) {
		String url = null;
		switch (page) {
		case Login:
			url = CommonConstants.URL_HOME_CONTROLLER;
			break;
		case Main:
			url = CommonConstants.URL_MAIN_CONTROLLER;
			break;
		default:
			break;
		}
		return url;
	}

	public static void logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.logout();
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute("email");
			session.removeAttribute("name");
			session.invalidate();
		}
		response.sendRedirect(request.getContextPath());
	}

	public static JsonElement getJSONData(BufferedReader reader) {
		try {
			String line = null;
			StringBuilder jsonBuff = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				jsonBuff.append(line);
			}
			String data = jsonBuff.toString();
			if (isValidJson(data)) {
				JsonParser parser = new JsonParser();
				return parser.parse(data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isValidJson(String str) {
		if (str != null && !str.isEmpty()) {
			final Gson gson = new Gson();
			try {
				gson.fromJson(str, Object.class);
				return true;
			} catch (JsonSyntaxException e) {
				return false;
			}
		}
		return false;
	}
}
