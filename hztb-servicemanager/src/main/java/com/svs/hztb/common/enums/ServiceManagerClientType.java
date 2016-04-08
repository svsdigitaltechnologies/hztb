package com.svs.hztb.common.enums;

import org.springframework.http.MediaType;

import com.svs.hztb.restfulclient.ClientType;
import com.svs.hztb.restfulclient.RestfulEndpointErrorMapping;

public enum ServiceManagerClientType implements ClientType {
	DS(MediaType.ALL, RestfulEndpointErrorMappingImpl.DS),
	SM(MediaType.ALL, RestfulEndpointErrorMappingImpl.SM),
	CT(MediaType.APPLICATION_JSON, RestfulEndpointErrorMappingImpl.CT);
	
	private RestfulEndpointErrorMappingImpl errorMapping;
	private MediaType mediaType;

	private ServiceManagerClientType(MediaType mediaType, RestfulEndpointErrorMappingImpl errorMapping) {
		this.mediaType = mediaType;
		this.errorMapping = errorMapping;
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

}
