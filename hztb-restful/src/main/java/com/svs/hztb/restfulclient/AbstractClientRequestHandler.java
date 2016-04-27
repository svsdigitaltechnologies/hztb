package com.svs.hztb.restfulclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.http.HttpMethod;

import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.RequestData;
import com.svs.hztb.common.util.PerformanceTimer;
import com.svs.hztb.restfulclient.model.RestfulRequest;
import com.svs.hztb.restfulclient.model.RestfulResponse;

public abstract class AbstractClientRequestHandler implements ClientRequestHandler {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(AbstractClientRequestHandler.class);

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
		LOGGER.info("Running endpoint {}", request.getEndpoint().name());
		CloseableHttpResponse httpResponse = null;

		try {
			if (request.getEndpoint().getHttpMethod() == HttpMethod.GET) {
				httpResponse = doGet(request);
			} else {
				httpResponse = doPost(request);
			}
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (HttpStatus.SC_OK != statusCode) {
				LOGGER.callOutDownStream("{} returned from downstream call",
						request.getEndpoint().getClientType().getName(), request.getEndpoint().getURI(), statusCode);
			}
			String response = getResponse(httpResponse);

			try {
				PerformanceTimer timer = new PerformanceTimer();
				RestfulResponse<S> restfulResponse = RestfulResponse.buildResponse(statusCode,
						unmarshall(request.getRequestData(), response, request.getResponseClass()));
				timer.logPerformance("unmarshall." + request.getEndpoint().getClientType().getName() + "."
						+ request.getEndpoint().name());
				if (HttpStatus.SC_OK == statusCode) {
					timer = new PerformanceTimer();
					validate(request.getRequestData(), response);
					timer.logPerformance("validate.response." + request.getEndpoint().getClientType().getName() + "."
							+ request.getEndpoint().name());
				}
				return restfulResponse;
			} catch (MarshallingException e) {
				LOGGER.callOut("Error occured while marshalling response: {}", e);
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
				LOGGER.error("Failed to umarshall error response: {}", e);
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
		LOGGER.info("Get request uri {}", uri);
		HttpGet get = new HttpGet(uri);
		return makeCall(request, get);
	}

	private <T, S> CloseableHttpResponse doPost(RestfulRequest<T, S> request) throws IOException {
		String uri = baseURL + request.getUri();
		LOGGER.info("Post request uri {}", uri);

		HttpPost httpPost = new HttpPost(uri);
		PerformanceTimer timer = new PerformanceTimer();
		httpPost.setEntity(marshall(request.getRequestData(), request.getPayload()));
		timer.logPerformance(
				"marshall." + request.getEndpoint().getClientType().getName() + "." + request.getEndpoint().name());
		return makeCall(request, httpPost);
	}

	private <T, S> CloseableHttpResponse makeCall(RestfulRequest<T, S> request, HttpRequestBase requestBase)
			throws IOException, ClientProtocolException {
		PerformanceTimer timer = new PerformanceTimer();
		addHeaders(requestBase, request);
		CloseableHttpResponse response = httpClient.execute(requestBase, httpContext);
		timer.logPerformance(requestBase.getMethod() + "." + request.getEndpoint().getClientType().getName() + "."
				+ request.getEndpoint().name());
		return response;
	}

	protected <T, S> void addHeaders(HttpRequestBase requestBase, RestfulRequest<T, S> request) {
		ClientType clientType = request.getEndpoint().getClientType();

		/*Optional<Map<String, String>> headers = request.getRequestData().getHeaders(clientType.getTargetId(),
				clientType.getMediaType().toString());
		headers.ifPresent(map -> map.keySet()
				.forEach(h -> Optional.ofNullable(map.get(h)).ifPresent(value -> requestBase.addHeader(h, value))));*/
	}

	protected abstract <T> void validate(RequestData requestData, T response);

}
