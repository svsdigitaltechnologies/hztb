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

public class BusinessError extends BaseException {

	/**
	 * 
	 */

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(BusinessError.class);

	private static final long serialVersionUID = -677418850964124526L;

	public BusinessError(StatusCode statusCode) {
		super("Business error occured", statusCode);
	}

	public BusinessError(String message, List<Pair<StatusCode, String>> statusCodes) {
		super(message, statusCodes);
	}

	public BusinessError(String message, StatusCode statusCode) {
		super(message, statusCode);
	}

	public BusinessError(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessError(String message, Throwable cause, StatusCode statusCode) {
		super(message, cause, statusCode);
	}

	public BusinessError(String message) {
		super(message);
	}

	private static String listMessages(List<Pair<StatusCode, String>> statusCodes) {
		StringBuilder result = new StringBuilder();

		for (Pair<StatusCode, String> pair : statusCodes) {
			if (result.length() > 0) {
				result.append(",");
			}
			Optional.ofNullable(pair.getRight()).ifPresent(result::append);
		}
		return result.toString();
	}

	public static BusinessError build(ClientType clientType, String message, String errorCode) {
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
		return new BusinessError(listMessages(statusCodes), statusCodes);
	}

	public static BusinessError build(RestfulEndPoint endpoint, List<DownstreamError> errors) {
		RestfulEndpointErrorMapping errorMapping = endpoint.getErrorMapping();

		if (errorMapping != null) {
			errorMapping = endpoint.getClientType().getErrorMapping();
		}
		List<Pair<StatusCode, String>> statusCodes = new ArrayList<>();
		for (DownstreamError downstreamError : errors) {
			LOGGER.debug("Downstream service error, code: {}, message: {}", downstreamError.getCode(),
					downstreamError.getMessage());
			if (errorMapping != null) {
				StatusCode statusCode = errorMapping.getStatusCode(downstreamError.getCode());

				if (statusCode == null) {
					statusCodes.add(Pair.of(PlatformStatusCode.BACKEND_SYSTEM_ERROR, downstreamError.getMessage()));
				} else {
					statusCodes.add(Pair.of(statusCode, downstreamError.getMessage()));
				}
			}
		}
		return new BusinessError(listMessages(statusCodes), statusCodes);
	}
}
