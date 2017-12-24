package com.bps.util;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bps.persistence.tables.LifeCycle;

public class CommonUtility {

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

	public static void navigateToPage(String page, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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
}
