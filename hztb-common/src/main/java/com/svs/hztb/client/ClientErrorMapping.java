package com.svs.hztb.client;

import java.util.Map;

import com.svs.hztb.common.model.StatusCode;

public interface ClientErrorMapping {
	
	Map<Integer, StatusCode> getMappings();
	
	StatusCode getStatusCode(Integer clientCode);

}
