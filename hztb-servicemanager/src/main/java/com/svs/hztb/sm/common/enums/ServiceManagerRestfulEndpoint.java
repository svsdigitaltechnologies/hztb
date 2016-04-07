package com.svs.hztb.sm.common.enums;

import java.util.Optional;

import org.springframework.http.HttpMethod;

import com.svs.hztb.common.enums.RestfulEndpointErrorMappingImpl;
import com.svs.hztb.common.enums.ServiceManagerClientType;
import com.svs.hztb.restfulclient.ClientType;
import com.svs.hztb.restfulclient.RestfulEndPoint;
import com.svs.hztb.restfulclient.RestfulEndpointErrorMapping;

public enum ServiceManagerRestfulEndpoint implements RestfulEndPoint {
	
	CLICKATELL_GET(ServiceManagerClientType.CT,"/user/jsonGetResponse",HttpMethod.GET, RestfulEndpointErrorMappingImpl.CT),
	CLICKATELL_POST(ServiceManagerClientType.CT,"/user/jsonPostResponse",HttpMethod.POST, RestfulEndpointErrorMappingImpl.CT),
	CLICKATELL(ServiceManagerClientType.CT,"/rest/message",HttpMethod.POST, RestfulEndpointErrorMappingImpl.CT);

	
	
	private final transient ClientType clientType;
	private final String endpoint;
	private final HttpMethod httpMethod;
	private final transient Optional<Class<?>> errorClass;
	private RestfulEndpointErrorMappingImpl restfulEndpointErrorMappingImpl;
	
	private ServiceManagerRestfulEndpoint(ClientType clientType, String endpoint, HttpMethod httpMethod) {
		this(clientType, endpoint, httpMethod, null, null);
	}

	private ServiceManagerRestfulEndpoint(ClientType clientType, String endpoint, HttpMethod httpMethod, Class<?> errorClass) {
		this(clientType, endpoint, httpMethod, errorClass, null);
	}

	private ServiceManagerRestfulEndpoint(ClientType clientType, String endpoint, HttpMethod httpMethod, RestfulEndpointErrorMappingImpl restfulEndpointErrorMappingImpl) {
		this(clientType, endpoint, httpMethod, null, restfulEndpointErrorMappingImpl);
	}

	private ServiceManagerRestfulEndpoint(ClientType clientType, String endpoint, HttpMethod httpMethod, Class<?> errorClass, RestfulEndpointErrorMappingImpl restfulEndpointErrorMappingImpl) {
		this.clientType = clientType;
		this.endpoint = endpoint;
		this.httpMethod = httpMethod;
		this.errorClass = Optional.ofNullable(errorClass);
		this.restfulEndpointErrorMappingImpl = restfulEndpointErrorMappingImpl;
	}

	public RestfulEndpointErrorMapping getErrorMapping() {
		return clientType.getErrorMapping();
	}

	public void setRestfulEndpointErrorMappingImpl(RestfulEndpointErrorMappingImpl restfulEndpointErrorMappingImpl) {
		this.restfulEndpointErrorMappingImpl = restfulEndpointErrorMappingImpl;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public String getURI() {
		return endpoint;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public Optional<Class<?>> getErrorClass() {
		return errorClass;
	}

}
