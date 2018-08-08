package com.bps.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bps.util.CommonConstants;


@WebServlet(urlPatterns = CommonConstants.URL_SURVEY_REPONSE_CONTROLLER, name = "SurveyResponseController")
@MultipartConfig
public class SurveyResponseController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String other = request.getParameter("otherQ"); // Retrieves <input type="text" name="description">
		    Part filePart = request.getPart("image"); // Retrieves <input type="file" name="file">
		    String x = filePart.getSubmittedFileName();
		    Long size = filePart.getSize();
		    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
		    InputStream fileContent = filePart.getInputStream();
		    System.out.println("Hello");
		    response.getWriter().append("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
