package com.svs.hztb.restfulclient;

import java.io.IOException;

import org.apache.http.HttpEntity;

import com.svs.hztb.common.model.RequestData;
import com.svs.hztb.restfulclient.model.RestfulRequest;
import com.svs.hztb.restfulclient.model.RestfulResponse;

public interface ClientRequestHandler {
	<T, S> RestfulResponse<S> execute(RestfulRequest<T, S> adapterRequest) throws IOException;

	<T> HttpEntity marshall(RequestData requestData, T object) throws IOException;

	<S> S unmarshall(RequestData requestData, String content, Class<S> clazz) throws IOException;
}
