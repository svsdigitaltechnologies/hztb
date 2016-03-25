package com.svs.hztb.common.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.svs.hztb.client.ClientErrorMapping;
import com.svs.hztb.common.model.StatusCode;

public enum ClientErrorMappingImpl implements ClientErrorMapping {

	DS(entry(1, ServiceManagerStatusCode.DATA_SERVICES_ERROR),
		entry(2, ServiceManagerStatusCode.USER_NOT_AVAILABLE),
		entry(3, ServiceManagerStatusCode.USER_ALREADY_REGISTERED));

	private final static Logger logger = LoggerFactory.getLogger(ClientErrorMappingImpl.class);

	private final transient Map<Integer, StatusCode> mappings = new HashMap<>();

	private ClientErrorMappingImpl(Map.Entry<Integer, StatusCode>... entries) {
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
