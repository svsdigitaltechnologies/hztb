package com.svs.hztb.common.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ErrorStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9127867295214624680L;
	
	private static final String STATUS = "status";
	
	private final Map<String, String> statusCodes = new HashMap<String, String>();
	
	private final String message;
	
	public ErrorStatus(String statusCode, String message) {
		statusCodes.put(STATUS, statusCode);
		this.message = message;
	}
	
	public ErrorStatus(StatusCode statusCode) {
		this(statusCode.getStatusCode(), statusCode.getMessage());
	}
	
	public String getStatus() {
		return statusCodes.get(STATUS);
	}
	
	public String getMessage() {
		return message;
	}
}
