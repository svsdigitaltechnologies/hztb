package com.svs.hztb.exception;

public class DataServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2532741174331476019L;

	private String statusCode;

	public DataServiceException() {
		super();
	}

	public DataServiceException(String message) {
		super(message);
	}

	public DataServiceException(Throwable cause) {
		super(cause);
	}

	public DataServiceException(Throwable cause, String statusCode) {
		super(cause);
		this.statusCode = statusCode;
	}
	
	public DataServiceException(String message, String statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	public DataServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

}
