package com.svs.hztb.restfulclient.model;

import com.svs.hztb.common.model.RequestData;
import com.svs.hztb.restfulclient.RestfulEndPoint;

public class RestfulRequest<T, S> {
	private final Class<S> responseClass;
	private final T payload;
	private final String uri;
	private final RequestData requestData;
	private final RestfulEndPoint endpoint;

	private RestfulRequest(RequestData requestData, String uri, RestfulEndPoint endpoint, T payload,
			Class<S> responseClass) {
		this.requestData = requestData;
		this.uri = uri;
		this.endpoint = endpoint;
		this.payload = payload;
		this.responseClass = responseClass;
	}

	public static <T, S> RestfulRequest<T, S> build(RequestData requestData, RestfulEndPoint endPoint, T payload,
			Class<S> responseClass) {
		return build(requestData, endPoint.getURI(), endPoint, payload, responseClass);
	}

	public static <T, S> RestfulRequest<T, S> build(RequestData requestData, String uri, RestfulEndPoint endPoint,
			T payload, Class<S> responseClass) {
		return new RestfulRequest<>(requestData, uri, endPoint, payload, responseClass);
	}

	public Class<S> getResponseClass() {
		return responseClass;
	}

	public T getPayload() {
		return payload;
	}

	public String getUri() {
		return uri;
	}

	public RequestData getRequestData() {
		return requestData;
	}

	public RestfulEndPoint getEndpoint() {
		return endpoint;
	}

}
