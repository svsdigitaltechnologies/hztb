package com.svs.hztb.restfulclient;

import java.util.Optional;

import org.springframework.http.HttpMethod;

public interface RestfulEndPoint {
	
	String name();
	
	ClientType getClientType();
	
	String getURI();
	
	HttpMethod getHttpMethod();
	
	Optional<Class<?>> getErrorClass();
	
	RestfulEndpointErrorMapping getErrorMapping();

}
