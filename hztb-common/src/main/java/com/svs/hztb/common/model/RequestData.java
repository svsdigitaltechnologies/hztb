package com.svs.hztb.common.model;

import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;

public interface RequestData {
	public String getRequestId();
	
	default Optional<Map<String, String>> getHeaders(String targetId, String mediaType) {
		final String HTTP_TARGET_ID = "TARGET_ID";
		final String HTTP_ACCEPT = "ACCEPT";
		return Optional.ofNullable(ImmutableMap.of(HTTP_TARGET_ID, targetId, HTTP_ACCEPT, mediaType));
	}
}
