package com.svs.hztb.sm.common.transformer;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.DownstreamError;
import com.svs.hztb.restfulclient.model.RestfulResponse;

public abstract class ClickatellRestfulAbstractTransformer<T, S> extends RestfulServiceAbstractTransformer<T, S> {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(ClickatellRestfulAbstractTransformer.class);

	@Override
	public List<DownstreamError> getErrors(RestfulResponse<S> response) {
		return null;
	}

	@Override
	public boolean isSuccess(RestfulResponse<S> restfulResponse) {
		return (super.isSuccess(restfulResponse) || (restfulResponse.getStatusCode() == HttpStatus.ACCEPTED.value()
				&& restfulResponse.getResponse().isPresent()));
	}

}
