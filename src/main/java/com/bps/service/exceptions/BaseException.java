package com.bps.service.exceptions;

public class BaseException extends Exception {

	private static final long serialVersionUID = 1L;
	private int httpResponseCode;
	private String errorCode;
	private String errorTitle;
	private String errorMessage;
	
	public BaseException(int httpResponseCode, String errorCode) {
		super();
		this.httpResponseCode = httpResponseCode;
		this.errorCode = errorCode;
		this.errorTitle = ErrorMapper.getErrorDetails(errorCode)[0];
		this.errorMessage = ErrorMapper.getErrorDetails(errorCode)[1];
	}

	public int getHttpResponseCode() {
		return httpResponseCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorTitle() {
		return errorTitle;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
