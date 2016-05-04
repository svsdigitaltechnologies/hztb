package com.svs.hztb.sm.registration.transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.svs.hztb.api.sm.model.clickatell.ClickatellErrorResponse;
import com.svs.hztb.api.sm.model.clickatell.ClickatellRequest;
import com.svs.hztb.api.sm.model.clickatell.ClickatellResponse;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.DownstreamError;
import com.svs.hztb.common.model.HztbResponse;
import com.svs.hztb.common.model.business.User;
import com.svs.hztb.orchestration.component.model.FlowContext;
import com.svs.hztb.restfulclient.model.RestfulResponse;
import com.svs.hztb.sm.common.annotation.RestfulTransformer;
import com.svs.hztb.sm.common.enums.ServiceManagerRestfulEndpoint;
import com.svs.hztb.sm.common.transformer.ClickatellRestfulAbstractTransformer;
import com.svs.hztb.sm.common.transformer.RestfulServiceAbstractTransformer;

@RestfulTransformer(ServiceManagerRestfulEndpoint.CLICKATELL)
@Component
public class ClickatellTransformer
		extends ClickatellRestfulAbstractTransformer<ClickatellRequest, ClickatellResponse> {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(ClickatellPosttTransformer.class);

	@Override
	public ClickatellRequest transformRequest(FlowContext flowContext) {
		User user = flowContext.getModelElement(User.class);
		ClickatellRequest clickatellRequest = new ClickatellRequest();
		clickatellRequest.setTo(user.getMobileNumber());
		clickatellRequest.setText(user.getOtpCode());
		return clickatellRequest;
	}

	@Override
	public void transformResponse(FlowContext flowContext, ClickatellResponse response) {
		LOGGER.debug("Clickatell Response {}", response);
		flowContext.setModelElement(response);
		LOGGER.debug("OTP sent to mobile number {} and apiMessageId is {}",
				response.getData().getMessage().get(0).getTo(),
				response.getData().getMessage().get(0).getApiMessageId());
	}

	@Override
	public List<DownstreamError> getErrors(RestfulResponse<ClickatellResponse> response) {
		if (!response.getErrorPayload().isPresent()) {
			return Arrays.asList(new DownstreamError(response.getStatusCode(), response.getErrorResponse().get()));
		}
		ClickatellErrorResponse errorResponse = (ClickatellErrorResponse) response.getErrorPayload().get();
		List<DownstreamError> errors = new ArrayList<>();
		errors.add(new DownstreamError(Integer.parseInt(errorResponse.getError().getCode()), errorResponse.getError().getDescription()));
		return errors;
	}

	@Override
	public Class<ClickatellResponse> getResponseClass() {
		return ClickatellResponse.class;
	}

}
