package com.svs.hztb.common.exception;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.svs.hztb.common.model.StatusCode;

/**
 * exception class for System Errors
 * 
 * @author skairamkonda
 *
 */
public class SystemError extends BaseException {

	private static final long serialVersionUID = -677418850964124526L;

	/**
	 * SystemError constructor
	 * 
	 * @param message
	 * @param exception
	 * @param statusCode
	 * 
	 */
	public SystemError(String message, Throwable exception, StatusCode statusCode) {
		super(message, exception, statusCode);
	}

	/**
	 * SystemError constructor
	 * 
	 * @param statusCode
	 * 
	 */
	public SystemError(StatusCode statusCode) {
		super(statusCode);
	}

	/**
	 * SystemError constructor
	 * 
	 * @param message
	 * @param statusCodes
	 * 
	 */
	public SystemError(String message, List<Pair<StatusCode, String>> statusCodes) {
		super(message, statusCodes);
	}

	/**
	 * SystemError constructor
	 * 
	 * @param message
	 * @param statusCode
	 * 
	 */
	public SystemError(String message, StatusCode statusCode) {
		super(message, statusCode);
	}

	/**
	 * SystemError constructor
	 * 
	 * @param message
	 * @param cause
	 * 
	 */
	public SystemError(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * SystemError constructor
	 * 
	 * @param message
	 * 
	 */
	public SystemError(String message) {
		super(message);
	}

}
