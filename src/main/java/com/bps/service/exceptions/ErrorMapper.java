package com.bps.service.exceptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class ErrorMapper {
	private static Map<String, String> map;
	static {
		loadErrorMap();
	}

	public static String[] getErrorDetails(String errorCode) {
		String[] errDetails = new String[2];
		if (map == null) {
			loadErrorMap();
		}
		if (map != null && !map.isEmpty()) {
			errDetails[0] = map.get(errorCode + "_TITLE");
			errDetails[1] = map.get(errorCode + "_MSG");
		}
		return errDetails;
	}

	private static void loadErrorMap() {
		System.out.println("Loading Error map for first time");
		FileInputStream fileInput = null;
		try {
			File file = new File(ErrorMapper.class.getClassLoader().getResource("error.properties").getFile());
			fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();
			Enumeration<Object> enuKeys = properties.keys();
			if (properties != null && !properties.isEmpty()) {
				map = new HashMap<String, String>();
				while (enuKeys.hasMoreElements()) {
					String key = (String) enuKeys.nextElement();
					String value = properties.getProperty(key);
					map.put(key, value);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInput != null) {
				try {
					fileInput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
