package com.svs.hztb.orchestration.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import com.svs.hztb.common.exception.BaseException;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.DownstreamError;
import com.svs.hztb.common.model.PlatformStatusCode;
import com.svs.hztb.common.model.StatusCode;
import com.svs.hztb.restfulclient.ClientType;
import com.svs.hztb.restfulclient.RestfulEndPoint;
import com.svs.hztb.restfulclient.RestfulEndpointErrorMapping;

public class BusinessException extends BaseException {

	/**
	 * 
	 */

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(BusinessException.class);

	private static final long serialVersionUID = -677418850964124526L;

	public BusinessException(StatusCode statusCode) {
		super("Business error occured", statusCode);
	}

	public BusinessException(String message, List<Pair<StatusCode, String>> statusCodes) {
		super(message, statusCodes);
	}

	public BusinessException(String message, StatusCode statusCode) {
		super(message, statusCode);
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

		for (Pair<StatusCode, String> pair : statusCodes) {
			if (result.length() > 0) {
				result.append(",");
			}
			Optional.ofNullable(pair.getRight()).ifPresent(m -> result.append(m));
		}
		return result.toString();
	}

	public static BusinessException build(ClientType clientType, String message, String errorCode) {
		RestfulEndpointErrorMapping errorMapping = clientType.getErrorMapping();

		List<Pair<StatusCode, String>> statusCodes = new ArrayList<>();

		if (errorMapping != null) {
			StatusCode statusCode = errorMapping.getStatusCode(Integer.parseInt(errorCode));
			if (statusCode == null) {
				statusCodes.add(Pair.of(PlatformStatusCode.BACKEND_SYSTEM_ERROR, message));
			} else {
				statusCodes.add(Pair.of(statusCode, message));
			}
		}
		return new BusinessException(listMessages(statusCodes), statusCodes);
	}

	public static BusinessException build(RestfulEndPoint endpoint, List<DownstreamError> errors) {
		RestfulEndpointErrorMapping errorMapping = endpoint.getErrorMapping();

		if (errorMapping != null) {
			errorMapping = endpoint.getClientType().getErrorMapping();
		}
		List<Pair<StatusCode, String>> statusCodes = new ArrayList<>();
		for (DownstreamError downstreamError : errors) {

			if (errorMapping != null) {
				StatusCode statusCode = errorMapping.getStatusCode(downstreamError.getCode());

				if (statusCode == null) {
					statusCodes.add(Pair.of(PlatformStatusCode.BACKEND_SYSTEM_ERROR, downstreamError.getMessage()));
				} else {
					statusCodes.add(Pair.of(statusCode, downstreamError.getMessage()));
				}
			}
		}
		return new BusinessException(listMessages(statusCodes), statusCodes);
	}
}
