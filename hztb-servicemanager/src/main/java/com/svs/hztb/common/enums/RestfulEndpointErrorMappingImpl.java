package com.svs.hztb.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.svs.hztb.common.model.StatusCode;
import com.svs.hztb.restfulclient.RestfulEndpointErrorMapping;

public enum RestfulEndpointErrorMappingImpl implements RestfulEndpointErrorMapping{

	DS(entry(1, ServiceManagerStatusCode.DATA_SERVICES_ERROR), entry(2, ServiceManagerStatusCode.USER_NOT_AVAILABLE),
			entry(3, ServiceManagerStatusCode.USER_ALREADY_REGISTERED),
			entry(4, ServiceManagerStatusCode.USER_NOT_AVAILABLE_MOBILE_IMEI)), 
	SM(
					entry(100, ServiceManagerStatusCode.INVALID_OTP), entry(101, ServiceManagerStatusCode.EXPIRED_OTP)),
	CT(
			entry(1, ServiceManagerStatusCode.NEED_PAYMENT), entry(101, ServiceManagerStatusCode.EXPIRED_OTP));

	private final static Logger logger = LoggerFactory.getLogger(RestfulEndpointErrorMappingImpl.class);

	private final transient Map<Integer, StatusCode> mappings = new HashMap<>();

	private RestfulEndpointErrorMappingImpl(Map.Entry<Integer, StatusCode>... entries) {
		for (Map.Entry<Integer, StatusCode> entry : entries) {
			mappings.put(entry.getKey(), entry.getValue());
		}
	}

	private static Pair<Integer, StatusCode> entry(Integer code, StatusCode statusCode) {
		return Pair.<Integer, StatusCode> of(code, statusCode);
	}

	@Override
	public Map<Integer, StatusCode> getMappings() {
		return mappings;
	}

	@Override
	public StatusCode getStatusCode(Integer clientCode) {
		if (clientCode == null) {
			return null;
		}
		StatusCode statusCode = mappings.get(clientCode);
		if (statusCode == null) {
			logger.error("Failed to map error code for client code: {} " + clientCode);
			statusCode = null;
		}
		return statusCode;
	}

}
