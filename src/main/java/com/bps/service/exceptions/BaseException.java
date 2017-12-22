package com.bps.service.exceptions;

public class BaseException extends Exception {

	private static final long serialVersionUID = 1L;
	private int httpResponseCode;
	private String errorCode;
	private String errorTitle;
	private String errorMessage;
	private Exception coreException;
	
	public BaseException(int httpResponseCode, String errorCode) {
		super();
		this.httpResponseCode = httpResponseCode;
		this.errorCode = errorCode;
		this.errorTitle = ErrorMapper.getErrorDetails(errorCode)[0];
		this.errorMessage = ErrorMapper.getErrorDetails(errorCode)[1];
	}
	
	public BaseException(int httpResponseCode, String errorCode, Exception coreException) {
		this(httpResponseCode, errorCode);
		this.setCoreException(coreException);
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

	public Exception getCoreException() {
		return coreException;
	}

	public void setCoreException(Exception coreException) {
		this.coreException = coreException;
	}
}
