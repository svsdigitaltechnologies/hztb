package com.svs.hztb.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import com.svs.hztb.adapter.impl.UserAdapterImpl;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.StatusCode;
import com.svs.hztb.restfulclient.RestfulEndpointErrorMapping;

public enum RestfulEndpointErrorMappingImpl implements RestfulEndpointErrorMapping {

	DS(entry(1, ServiceManagerStatusCode.DATA_SERVICES_ERROR), 
			entry(2, ServiceManagerStatusCode.USER_NOT_AVAILABLE),
			entry(3, ServiceManagerStatusCode.USER_ALREADY_REGISTERED),
			entry(4, ServiceManagerStatusCode.USER_NOT_AVAILABLE_MOBILE_IMEI)), 
	SM(entry(100, ServiceManagerStatusCode.INVALID_OTP),
			entry(101, ServiceManagerStatusCode.OTP_NOT_VALID)), 
	CT(entry(165, ServiceManagerStatusCode.INVALID_VERSION_NUMBER),
			(entry(301, ServiceManagerStatusCode.NO_CREDIT_LEFT))),
	GCM(entry(201, ServiceManagerStatusCode.INVALID_GCM_REGISTRATION_ID));

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(UserAdapterImpl.class);

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
			LOGGER.error("Failed to map error code for client code: {} " + clientCode);
			statusCode = null;
		}
		return statusCode;
	}

}
