package com.bps.controller;

import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.bps.service.core.SessionFactoryManager;
import com.bps.service.exceptions.BaseException;
import com.bps.service.exceptions.ErrorMapper;

@WebServlet(urlPatterns =  "/bls", loadOnStartup = 1)
public final class BootLoaderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		try {
    		System.out.println("Loading from BootLoaderServlet.");
    		new ErrorMapper();
    		long startTime = Calendar.getInstance().getTimeInMillis();
			SessionFactoryManager.getSessionFactory();
			long endTime = Calendar.getInstance().getTimeInMillis();
			System.out.println("Initial setup done successfully.");
			System.out.println("Time taken to load session factory = " + (endTime - startTime) + " ms");
		} catch (BaseException e) {
			System.out.println("Boot loading failed.");
			e.printStackTrace();
		}
	}
}
