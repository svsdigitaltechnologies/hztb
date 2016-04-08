package com.svs.hztb.restfulclient;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.svs.hztb.common.model.RequestData;
import com.svs.hztb.restfulclient.model.RestfulRequest;

public class JSONClientRequestHandler extends AbstractClientRequestHandler {

	private final ObjectMapper jsonMapper;

	public JSONClientRequestHandler(String baseURL, Map<String, String> headers, HttpContext httpContext,
			CloseableHttpClient httpClient) {
		super(baseURL, headers, httpContext, httpClient);
		this.jsonMapper = new ObjectMapper();
		this.jsonMapper.setSerializationInclusion(Include.NON_NULL);
	}

	@Override
	public HttpEntity marshall(RequestData requestData, Object object) {
		try {
			return new StringEntity(jsonMapper.writeValueAsString(object), Charsets.UTF_8);
		} catch (Exception e) {
			throw new MarshallingException(e);
		}
	}

	@Override
	public <S> S unmarshall(RequestData requestData, String content, Class<S> clazz) {
		try {
			S response = jsonMapper.readValue(content, clazz);
			return response;
		} catch (IOException ioException) {
			throw new MarshallingException(ioException);
		}
	}

	@Override
	protected <T, S> void addHeaders(HttpRequestBase requestBase, RestfulRequest<T, S> request) {
		headers.keySet().stream().forEach(h -> Optional.ofNullable(headers.get(h))
				.ifPresent(value -> requestBase.addHeader(h, value)));
		super.addHeaders(requestBase, request);
	}

}
