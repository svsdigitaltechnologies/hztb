package com.svs.hztb.common.exception;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.svs.hztb.common.model.PlatformStatusCode;
import com.svs.hztb.common.model.StatusCode;

public abstract class BaseException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1141502736836346370L;
	protected final List<Pair<StatusCode, String>> statusCodes = new ArrayList<>();
	
	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable cause) {
		this(message, cause, PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);	}
	
	public BaseException(String message, StatusCode statusCode) {
		this(message, null, statusCode);
	}

	public BaseException(String message, Throwable cause, StatusCode statusCode) {
		super(message,cause);
		this.statusCodes.add(Pair.of(statusCode, null));
	}
	
	public BaseException(StatusCode statusCode) {
		this(null, null, statusCode);
	}
	
	public BaseException(String message, final List<Pair<StatusCode, String>> statusCodes) {
		super(message);
		if(statusCodes != null) {
			this.statusCodes.addAll(statusCodes);
		}
	}
	
	public List<Pair<StatusCode, String>> getStatusCodes() {
		return statusCodes;
	}
	
	public StatusCode getFirstStatusCode() {
		if(!statusCodes.isEmpty()) {
			return statusCodes.get(0).getLeft();
		}
		return null;
	}
	
	public String getFirstBackendMessage() {
		if(!statusCodes.isEmpty()) {
			return statusCodes.get(0).getRight();
		}
		return null;
	}
}
