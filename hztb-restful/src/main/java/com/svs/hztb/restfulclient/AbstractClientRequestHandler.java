package com.svs.hztb.restfulclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import com.svs.hztb.restfulclient.model.RestfulRequest;
import com.svs.hztb.restfulclient.model.RestfulResponse;

public abstract class AbstractClientRequestHandler implements ClientRequestHandler {

	private final String baseURL;
	protected Map<String, String> headers = new HashMap<>();
	private final CloseableHttpClient httpClient;
	private final HttpContext httpContext;

	public AbstractClientRequestHandler(String baseURL, Map<String, String> headers, HttpContext httpContext,
			CloseableHttpClient closeableHttpClient) {
		this.baseURL = baseURL;
		this.headers = headers;
		this.httpClient = closeableHttpClient;
		this.httpContext = httpContext;
	}

	@Override
	public <T, S> RestfulResponse<S> execute(RestfulRequest<T, S> adapterRequest) throws IOException {
		return call(adapterRequest);
	}

	@SuppressWarnings("unchecked")
	private <T, S> RestfulResponse<S> call(RestfulRequest<T, S> request) throws IOException {
		CloseableHttpResponse httpResponse = null;

		try {
			if (request.getEndpoint().getHttpMethod() == HttpMethod.GET) {
				httpResponse = doGet(request);
			} else {
				httpResponse = doPost(request);
			}
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK != statusCode) {
				System.out.println("Downstream call error ");
			}
			String response = getResponse(httpResponse);

			try {
				RestfulResponse<S> restfulResponse = RestfulResponse.buildResponse(statusCode,
						unmarshall(request.getRequestData(), response, request.getResponseClass()));
				if (HttpStatus.SC_OK == statusCode) {
					System.out.println("200 Ok response received ");
				}
				return restfulResponse;
			} catch (MarshallingException e) {
				return handleError(statusCode, response, request);
			}
		} finally {
			if (httpResponse != null) {
				httpResponse.close();
			}

		}
	}

	@SuppressWarnings("unchecked")
	private <T, S> RestfulResponse<S> handleError(int statusCode, String response, RestfulRequest<T, S> request)
			throws IOException {
		if (request.getEndpoint().getErrorClass().isPresent()) {
			try {

				return (RestfulResponse<S>) RestfulResponse.buildErrprResponse(statusCode,
						unmarshall(request.getRequestData(), response, request.getEndpoint().getErrorClass().get()));

			} catch (MarshallingException e) {
				return RestfulResponse.buildErrorResponse(statusCode, response);
			}
		} else {
			return RestfulResponse.buildErrorResponse(statusCode, response);
		}
	}

	private static String getResponse(HttpResponse httpResponse) throws IOException {
		InputStream inputStream = httpResponse.getEntity().getContent();
		return IOUtils.toString(inputStream, Charsets.UTF_8);
	}

	private <T, S> CloseableHttpResponse doGet(RestfulRequest<T, S> request) throws IOException {
		String uri = baseURL + request.getUri();

		HttpGet get = new HttpGet(uri);
		return makeCall(request, get);
	}

	private <T, S> CloseableHttpResponse doPost(RestfulRequest<T, S> request) throws IOException {
		String uri = baseURL + request.getUri();
		HttpPost httpPost = new HttpPost(uri);
		httpPost.setEntity(marshall(request.getRequestData(), request.getPayload()));
		return makeCall(request, httpPost);
	}

	private <T, S> CloseableHttpResponse makeCall(RestfulRequest<T, S> request, HttpRequestBase requestBase)
			throws IOException, ClientProtocolException {
		addHeaders(requestBase, request);
		CloseableHttpResponse response = httpClient.execute(requestBase, httpContext);
		return response;
	}

	protected <T, S> void addHeaders(HttpRequestBase requestBase, RestfulRequest<T, S> request) {
		requestBase.addHeader("Content-Type", request.getEndpoint().getClientType().getMediaType().toString());
		requestBase.addHeader("Accept", request.getEndpoint().getClientType().getMediaType().toString());

	}
}
