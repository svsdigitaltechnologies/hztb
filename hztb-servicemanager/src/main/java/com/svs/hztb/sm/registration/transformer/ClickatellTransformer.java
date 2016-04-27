package com.svs.hztb.sm.registration.transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.svs.hztb.api.sm.model.clickatell.ClickatellRequest;
import com.svs.hztb.api.sm.model.clickatell.ClickatellResponse;
import com.svs.hztb.common.model.DownstreamError;
import com.svs.hztb.common.model.HztbResponse;
import com.svs.hztb.orchestration.component.model.FlowContext;
import com.svs.hztb.restfulclient.model.RestfulResponse;
import com.svs.hztb.sm.common.annotation.RestfulTransformer;
import com.svs.hztb.sm.common.enums.ServiceManagerRestfulEndpoint;
import com.svs.hztb.sm.common.transformer.RestfulServiceAbstractTransformer;

@RestfulTransformer(ServiceManagerRestfulEndpoint.CLICKATELL_POST)
@Component
public class ClickatellTransformer extends RestfulServiceAbstractTransformer<ClickatellRequest, ClickatellResponse> {

	@Override
	public ClickatellRequest transformRequest(FlowContext flowContext) {
		ClickatellRequest clickatellRequest = new ClickatellRequest();
		clickatellRequest.setTo("18479874489");
		clickatellRequest.setText("123456");
		return clickatellRequest;
	}

	@Override
	public void transformResponse(FlowContext flowContext, ClickatellResponse response) {
		System.out.println("In response");
		flowContext.setModelElement(response);
	}

	@Override
	public List<DownstreamError> getErrors(RestfulResponse<ClickatellResponse> response) {
		if (!response.getErrorPayload().isPresent()) {
			return Arrays.asList(new DownstreamError(response.getStatusCode(), response.getErrorResponse().get()));
		}
		HztbResponse errorResponse = (HztbResponse) response.getErrorPayload().get();
		List<DownstreamError> errors = new ArrayList<>();
		errorResponse.getHeader().getErrors().stream().forEach(e -> errors.add(new DownstreamError(Integer.parseInt(e.getStatus()), e.getMessage())));
		return errors;
	}

	@Override
	public Class<ClickatellResponse> getResponseClass() {
		return ClickatellResponse.class;
	}

}
