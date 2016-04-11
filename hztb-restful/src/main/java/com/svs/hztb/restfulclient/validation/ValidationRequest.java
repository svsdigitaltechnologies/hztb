package com.svs.hztb.restfulclient.validation;

import com.svs.hztb.common.model.RequestData;

public class ValidationRequest<T> {
	private final RequestData requestData;
	private final T payload;
	
	public ValidationRequest(RequestData requestData, T payload) {
		super();
		this.requestData = requestData;
		this.payload = payload;
	}

	public RequestData getRequestData() {
		return requestData;
	}

	public T getPayload() {
		return payload;
	}
	
	
}
