package com.svs.hztb.common.exception;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.svs.hztb.common.model.StatusCode;

public class SystemException extends BaseException {

	private static final long serialVersionUID = -677418850964124526L;

	public SystemException(String message, Throwable exception, StatusCode statusCode) {
		super(message, exception, statusCode);
	}

	public SystemException(StatusCode statusCode) {
		super(statusCode);
	}

	public SystemException(String message, List<Pair<StatusCode, String>> statusCodes) {
		super(message, statusCodes);
	}

	public SystemException(String message, StatusCode statusCode) {
		super(message, statusCode);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemException(String message) {
		super(message);
	}

}
