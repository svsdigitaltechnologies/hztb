package com.svs.hztb.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.svs.hztb.client.ClientErrorMapping;
import com.svs.hztb.client.ClientType;
import com.svs.hztb.common.exception.BaseException;
import com.svs.hztb.common.model.PlatformStatusCode;
import com.svs.hztb.common.model.StatusCode;

public class BusinessException extends BaseException {

	/**
	 * 
	 */

	private final static Logger logger = LoggerFactory.getLogger(BusinessException.class);

	private static final long serialVersionUID = -677418850964124526L;

	public BusinessException(StatusCode statusCode) {
		super("Business error occured", statusCode);
	}

	public BusinessException(String message, List<Pair<StatusCode, String>> statusCodes) {
		super(message, statusCodes);
	}
	
	public BusinessException(String message, StatusCode statusCode) {
		super(message, statusCode);
		logger.error("Business error occured - statusCode: " + statusCode + ". message: " + message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message, Throwable cause, StatusCode statusCode) {
		super(message, cause, statusCode);
	}

	public BusinessException(String message) {
		super(message);
	}

	private static String listMessages(List<Pair<StatusCode, String>> statusCodes) {
		StringBuilder result = new StringBuilder();
		for (Pair<StatusCode, String> pair: statusCodes) {
			if(result.length() > 0 ) {
				result.append(",");
			}
			Optional.ofNullable(pair.getRight()).ifPresent(m -> result.append(m));
		}
		return result.toString();
	}
	
	public static BusinessException build(ClientType clientType, String message, String errorCode) {
		ClientErrorMapping errorMapping = clientType.getErrorMapping();
		List<Pair<StatusCode, String>> statusCodes = new ArrayList<>();

		if(errorMapping != null) {
			StatusCode statusCode = errorMapping.getStatusCode(Integer.parseInt(errorCode));
			if (statusCode == null) {
				statusCodes.add(Pair.of(PlatformStatusCode.BACKEND_SYSTEM_ERROR, message));
			} else {
				statusCodes.add(Pair.of(statusCode, message));
			}
		}
		return new BusinessException(listMessages(statusCodes), statusCodes);
	}

}
