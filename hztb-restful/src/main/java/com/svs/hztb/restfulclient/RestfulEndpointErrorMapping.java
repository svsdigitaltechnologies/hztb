package com.svs.hztb.restfulclient;

import java.util.Map;

import com.svs.hztb.common.model.StatusCode;

public interface RestfulEndpointErrorMapping {
	
	Map<Integer, StatusCode> getMappings();
	
	StatusCode getStatusCode(Integer clientCode);

}
