package com.bps.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bps.util.CommonConstants;
import com.bps.util.CommonUtility;
import com.bps.util.SurveyResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@WebServlet(urlPatterns = CommonConstants.URL_SURVEY_REPONSE_CONTROLLER, name = "SurveyResponseController")
@MultipartConfig
public class SurveyResponseController extends HttpServlet {
	private final static String PATH = "C:\\Users\\i305297\\Documents\\Personal\\Project\\Uploaded";
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final PrintWriter writer = response.getWriter();
		try {
			String answersJsonString = request.getParameter("answers");
			
			Gson gson = new Gson();
			SurveyResponse surveyReponse = gson.fromJson(answersJsonString, SurveyResponse.class);
			Part filePart = request.getPart("image");
			saveImage(filePart);
			writer.append("success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private String getFileName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	private void saveImage(Part filePart) {
		if (filePart != null) {
			OutputStream out = null;
			InputStream fileContent = null;
			try {
				fileContent = filePart.getInputStream();
				out = new FileOutputStream(new File(PATH + File.separator + getFileName(filePart)));

				int read = 0;
				final byte[] bytes = new byte[1024];

				while ((read = fileContent.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
			} catch (FileNotFoundException fne) {
				fne.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
					if (fileContent != null) {
						fileContent.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
