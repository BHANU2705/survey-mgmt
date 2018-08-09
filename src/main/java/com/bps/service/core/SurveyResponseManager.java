package com.bps.service.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bps.dao.SurveyResponseDAO;
import com.bps.persistence.tables.SurveyResponse;
import com.bps.service.exceptions.BaseException;
import com.bps.util.SurveyResponseEntity;

public class SurveyResponseManager {
	private SurveyResponseDAO surveyResponseDAO;
//	private String userEmail;
	private final static String PATH = "C:\\Users\\i305297\\Documents\\Personal\\Project\\Uploaded";
	public SurveyResponseManager(String email) {
		surveyResponseDAO = new SurveyResponseDAO();
		surveyResponseDAO.setUserEmail(email);
//		setUserEmail(email);
	}

	/*public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}*/

	public void submitResponse(final SurveyResponseEntity responseEntity, final Part filePart) throws BaseException {
		if (responseEntity != null) {
			SurveyResponse surveyResponse = SurveyResponse.build(responseEntity);
			if (filePart != null && filePart.getSize() > 0) {
				saveImage(filePart);
				surveyResponse.setImageFileName(getFileName(filePart));
				surveyResponse.setImageSize(filePart.getSize());
				surveyResponse.setImageFilePath(PATH);
				surveyResponseDAO.create(surveyResponse);
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

	private void saveImage(Part filePart) throws BaseException {
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
				BaseException baseException = new BaseException(HttpServletResponse.SC_BAD_REQUEST, "IMAGE_NOT_FOUND", fne);
				throw baseException;
			} catch (IOException e) {
				BaseException baseException = new BaseException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "IMAGE_IO_EXCEPTION", e);
				throw baseException;
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
