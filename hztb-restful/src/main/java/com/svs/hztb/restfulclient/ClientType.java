package com.svs.hztb.restfulclient;

import org.springframework.http.MediaType;

public interface ClientType {

		String getName();
		
		RestfulEndpointErrorMapping getErrorMapping();
		
		MediaType getMediaType();
		
		String getTargetId();
		
}
