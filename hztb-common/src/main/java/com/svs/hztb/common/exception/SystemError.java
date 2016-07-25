package com.svs.hztb.common.exception;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.svs.hztb.common.model.StatusCode;

public class SystemError extends BaseException {

	private static final long serialVersionUID = -677418850964124526L;

	public SystemError(String message, Throwable exception, StatusCode statusCode) {
		super(message, exception, statusCode);
	}

	public SystemError(StatusCode statusCode) {
		super(statusCode);
	}

	public SystemError(String message, List<Pair<StatusCode, String>> statusCodes) {
		super(message, statusCodes);
	}

	public SystemError(String message, StatusCode statusCode) {
		super(message, statusCode);
	}

	public SystemError(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemError(String message) {
		super(message);
	}

}
