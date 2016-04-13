package com.svs.hztb.common.enums;

import org.springframework.http.MediaType;

import com.svs.hztb.restfulclient.ClientType;
import com.svs.hztb.restfulclient.RestfulEndpointErrorMapping;

public enum ServiceManagerClientType implements ClientType {
	DS(MediaType.ALL, "DS", RestfulEndpointErrorMappingImpl.DS), SM(MediaType.ALL, "SM",
			RestfulEndpointErrorMappingImpl.SM), CT(MediaType.APPLICATION_JSON, "CT",
					RestfulEndpointErrorMappingImpl.CT);

	private RestfulEndpointErrorMappingImpl errorMapping;
	private MediaType mediaType;
	private String targetId;

	private ServiceManagerClientType(MediaType mediaType, String targetId,
			RestfulEndpointErrorMappingImpl errorMapping) {
		this.mediaType = mediaType;
		this.errorMapping = errorMapping;
		this.targetId = targetId;
	}

	@Override
	public String getName() {
		return this.name();
	}

	@Override
	public RestfulEndpointErrorMapping getErrorMapping() {
		return errorMapping;
	}

	@Override
	public MediaType getMediaType() {
		return mediaType;
	}

	@Override
	public String getTargetId() {
		return targetId;
	}
}
