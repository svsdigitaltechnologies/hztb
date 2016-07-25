package com.svs.hztb.orchestration.component.step;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.stream.Collectors;

import com.svs.hztb.common.exception.SystemError;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.PlatformStatusCode;
import com.svs.hztb.common.util.PerformanceTimer;
import com.svs.hztb.common.util.StringUtil;
import com.svs.hztb.orchestration.component.model.FlowContext;
import com.svs.hztb.orchestration.component.transformer.RestfulServiceTransformer;
import com.svs.hztb.restfulclient.RestfulClientProcessor;
import com.svs.hztb.restfulclient.RestfulEndPoint;
import com.svs.hztb.restfulclient.URIParameterName;
import com.svs.hztb.restfulclient.model.RestfulRequest;
import com.svs.hztb.restfulclient.model.RestfulResponse;

public class RestfulStepDefinition<T, S> extends AbstractStepDefinition {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(RestfulStepDefinition.class);

	protected RestfulClientProcessor restfulClientProcessor;
	protected RestfulServiceTransformer<T, S> transformer;
	private final RestfulEndPoint endpoint;

	public RestfulStepDefinition(final RestfulClientProcessor restfulClientProcessor,
			final RestfulServiceTransformer<T, S> transformer, final RestfulEndPoint endpoint) {
		this.restfulClientProcessor = restfulClientProcessor;
		this.transformer = transformer;
		this.endpoint = endpoint;
	}

	@Override
	public void process(FlowContext flowContext) throws Exception {
		LOGGER.debug("{} started processing ... ", endpoint.name());
		if (!transformer.proceed(flowContext)) {
			return;
		}
		String uri = null;
		RestfulRequest<T, S> restfulRequest = null;
		Map<URIParameterName, String> uriParameterNames = transformer.buildURIParameterReplacementMap(flowContext);
		Map<String, Object> parameterReplacements = uriParameterNames.keySet().stream()
				.collect(Collectors.toMap(URIParameterName::getValue, uriParameterNames::get));

		T request = transformer.transformRequest(flowContext);
		try {
			uri = StringUtil.spelReplacePayload(endpoint.getURI(), parameterReplacements);
			restfulRequest = RestfulRequest.build(flowContext.getRequestData(), uri, endpoint, request,
					transformer.getResponseClass());
			LOGGER.debug("Restful Request: {}", restfulRequest);

		} catch (Exception exception) {
			throw new SystemError(String.format("unable to convert URI %s using spel", endpoint.getURI()), exception,
					PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING);
		}
		final PerformanceTimer timer = new PerformanceTimer();
		RestfulResponse<S> restfulResponse = null;
		boolean status = false;
		try {
			restfulResponse = call(restfulRequest, flowContext);
			LOGGER.debug("Restful Response: {}", restfulResponse);
			status = transformer.isSuccess(restfulResponse);
			if (status) {
				transformer.transformResponse(flowContext, restfulResponse.getResponse().get());
			} else {
				transformer.transformError(flowContext, restfulRequest, transformer, restfulResponse);
			}
		} catch (IOException ex) {
			LOGGER.error("Downstream call exception: {}", ex);
			LOGGER.callOutDownStream("Error occured while making HTTP {} with exception {}",
					restfulRequest.getEndpoint().getClientType().getName(), restfulRequest.getEndpoint().getURI(),
					restfulRequest.getEndpoint().getHttpMethod(), ex);
			throw new SystemError(
					MessageFormat.format("Error occured while making http {0} with exception {1}",
							restfulRequest.getEndpoint().getHttpMethod(), ex.getMessage()),
					ex, PlatformStatusCode.BACKEND_SYSTEM_ERROR);
		} finally {
			timer.logPerformance("downstream." + endpoint.getClientType().getName() + "." + endpoint.name()
					+ (status ? ".success" : "fail"));
			LOGGER.debug("{} finished processing", endpoint.name());
		}
	}

	public RestfulClientProcessor getResfulClientAdapter() {
		return restfulClientProcessor;
	}

	protected RestfulResponse<S> call(RestfulRequest<T, S> adapterRequest, FlowContext flowContext) throws IOException {
		return restfulClientProcessor.execute(adapterRequest);
	}

	@Override
	public String toString() {
		return "TR [transformer =" + transformer + ", route=" + endpoint + "]";
	}

	@Override
	protected String getIdentifier() {
		return "restful-step::" + transformer.getClass().getSimpleName();
	}
}
