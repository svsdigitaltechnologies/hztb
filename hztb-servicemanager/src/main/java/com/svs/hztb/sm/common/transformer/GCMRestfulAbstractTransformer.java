package com.svs.hztb.sm.common.transformer;

import java.util.List;

import com.svs.hztb.common.model.DownstreamError;
import com.svs.hztb.restfulclient.model.RestfulResponse;

public abstract class GCMRestfulAbstractTransformer<T, S> extends RestfulServiceAbstractTransformer<T, S> {

	@Override
	public List<DownstreamError> getErrors(RestfulResponse<S> response) {
		return null;
	}

	@Override
	public boolean isSuccess(RestfulResponse<S> restfulResponse) {
		return super.isSuccess(restfulResponse);
		
	}
}
