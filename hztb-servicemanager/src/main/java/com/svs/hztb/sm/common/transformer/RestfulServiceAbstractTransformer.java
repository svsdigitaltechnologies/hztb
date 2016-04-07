package com.svs.hztb.sm.common.transformer;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.svs.hztb.common.model.DownstreamError;
import com.svs.hztb.common.model.PlatformStatusCode;
import com.svs.hztb.orchestration.component.model.FlowContext;
import com.svs.hztb.orchestration.component.transformer.RestfulServiceTransformer;
import com.svs.hztb.orchestration.exception.BusinessException;
import com.svs.hztb.restfulclient.URIParameterName;
import com.svs.hztb.restfulclient.model.RestfulRequest;
import com.svs.hztb.restfulclient.model.RestfulResponse;

public abstract class RestfulServiceAbstractTransformer<T, S> implements RestfulServiceTransformer<T, S> {

	@Override
	public Map<URIParameterName, String> buildURIParameterReplacementMap(FlowContext flowContext) {
		return Collections.emptyMap();
	}

	@Override
	public void transformError(FlowContext flowContext, RestfulRequest<T, S> request,
			RestfulServiceTransformer<T, S> transformer, RestfulResponse<S> response) {
		if (!response.getResponse().isPresent() && !response.getErrorPayload().isPresent()) {
			throw new BusinessException(PlatformStatusCode.BACKEND_SYSTEM_ERROR);
		} else {
			throw BusinessException.build(request.getEndpoint(), transformer.getErrors(response));
		}
	}

	@Override
	public boolean isSuccess(RestfulResponse<S> restfulResponse) {
		return restfulResponse.getStatusCode() == HttpStatus.OK.value() && restfulResponse.getResponse().isPresent();
	}

	@Override
	public abstract Class<S> getResponseClass();

	@Override
	public DownstreamError getFirstError(RestfulResponse<S> response) {
		return getErrors(response).stream().findFirst().orElse(null);
	}

}
