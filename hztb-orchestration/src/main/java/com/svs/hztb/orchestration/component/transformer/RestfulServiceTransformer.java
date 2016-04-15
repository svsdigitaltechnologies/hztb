package com.svs.hztb.orchestration.component.transformer;

import java.util.List;
import java.util.Map;

import com.svs.hztb.common.model.DownstreamError;
import com.svs.hztb.orchestration.component.model.FlowContext;
import com.svs.hztb.restfulclient.URIParameterName;
import com.svs.hztb.restfulclient.model.RestfulRequest;
import com.svs.hztb.restfulclient.model.RestfulResponse;

public interface RestfulServiceTransformer<T, S> extends Transformer {
	Map<URIParameterName, String> buildURIParameterReplacementMap(FlowContext flowContext);

	T transformRequest(FlowContext flowContext);

	void transformResponse(FlowContext flowContext, S response);

	void transformError(FlowContext flowContext, RestfulRequest<T, S> request,
			RestfulServiceTransformer<T, S> transformer, RestfulResponse<S> response);

	List<DownstreamError> getErrors(RestfulResponse<S> response);

	boolean isSuccess(RestfulResponse<S> restfulResponse);

	Class<S> getResponseClass();

	DownstreamError getFirstError(RestfulResponse<S> response);

}
