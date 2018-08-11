package com.bps.service.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bps.dao.SurveyResponseDAO;
import com.bps.persistence.tables.SurveyResponse;
import com.bps.service.exceptions.BaseException;
import com.bps.util.CommonConstants;
import com.bps.util.SurveyResponseEntity;

public class SurveyResponseManager {
	private SurveyResponseDAO surveyResponseDAO;

	public SurveyResponseManager(String email) {
		surveyResponseDAO = new SurveyResponseDAO();
		surveyResponseDAO.setUserEmail(email);
	}

	public SurveyResponseEntity getResponse(final String surveyId) throws BaseException {
		SurveyResponseEntity entity = null;
		SurveyResponse response = new SurveyResponse();
		response.setSurveyId(surveyId);
		SurveyResponse dbResponse = (SurveyResponse) surveyResponseDAO.read(response);
		if (dbResponse != null) {
			entity = SurveyResponseEntity.build(dbResponse);
		}
		return entity;
	}

	public void submitResponse(final SurveyResponseEntity responseEntity, final HashMap<String, Part> imageMap) throws BaseException {
		if (responseEntity != null) {
			SurveyResponse surveyResponse = SurveyResponse.build(responseEntity);
			if (imageMap != null && imageMap.size() > 0) {
				Iterator<Entry<String, Part>> it = imageMap.entrySet().iterator();
				while(it.hasNext()) {
					Entry<String, Part> entry = it.next();
					String questionId = entry.getKey();
					Part filePart = entry.getValue();
					saveImage(responseEntity, questionId, filePart);
				}
			}
			surveyResponseDAO.create(surveyResponse);
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

	private void saveImage(SurveyResponseEntity responseEntity, String questionId, Part filePart) throws BaseException {
		if (filePart != null) {
			OutputStream out = null;
			InputStream fileContent = null;
			try {
				fileContent = filePart.getInputStream();
				String relativePath = File.separator + responseEntity.getSurveyId() + File.separator + questionId + File.separator + responseEntity.getUserId();
				String folderPath = CommonConstants.ABSOLUTE_PATH + relativePath;
				File folder = new File(folderPath);
				if(!folder.exists()) {
					folder.mkdirs();
				}
				out = new FileOutputStream(new File(folderPath + File.separator + getFileName(filePart)));

				int read = 0;
				final byte[] bytes = new byte[1024];

				while ((read = fileContent.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
			} catch (FileNotFoundException fne) {
				BaseException baseException = new BaseException(HttpServletResponse.SC_BAD_REQUEST, "IMAGE_NOT_FOUND",
						fne);
				throw baseException;
			} catch (IOException e) {
				BaseException baseException = new BaseException(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"IMAGE_IO_EXCEPTION", e);
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
