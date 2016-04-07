package com.svs.hztb.common.model;

import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;

public interface RequestData {
	public String getRequestId();
	default Optional<Map<String, String>> getHeaders(String mediaType) {
		final String HTTP_ACCEPT = "Accept";
		return Optional.ofNullable(ImmutableMap.of(HTTP_ACCEPT,mediaType));
	}
}
