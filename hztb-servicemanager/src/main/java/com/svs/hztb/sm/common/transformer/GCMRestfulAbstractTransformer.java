package com.svs.hztb.sm.common.transformer;

import java.util.List;
import java.util.Optional;

import com.svs.hztb.api.gcm.model.notification.MessageRequest;
import com.svs.hztb.api.gcm.model.notification.MessageResponse;
import com.svs.hztb.api.gcm.model.notification.MessageResult;
import com.svs.hztb.common.logging.Logger;
import com.svs.hztb.common.logging.LoggerFactory;
import com.svs.hztb.common.model.DownstreamError;
import com.svs.hztb.orchestration.component.model.FlowContext;
import com.svs.hztb.restfulclient.model.RestfulResponse;

public abstract class GCMRestfulAbstractTransformer<T, S> extends RestfulServiceAbstractTransformer<T, S> {

	private static final Logger LOGGER = LoggerFactory.INSTANCE.getLogger(GCMRestfulAbstractTransformer.class);

	@Override
	public List<DownstreamError> getErrors(RestfulResponse<S> response) {
		return null;
	}

	@Override
	public boolean isSuccess(RestfulResponse<S> restfulResponse) {
		return super.isSuccess(restfulResponse);

	}

	@Override
	public void transformResponse(FlowContext flowContext, S response) {
		MessageResponse messageResponse = (MessageResponse) response;
		LOGGER.debug("GCM Response {}", response);
		MessageRequest messageRequest = flowContext.getModelElement(MessageRequest.class);
		// print summary
		LOGGER.logGCMActivity("Success {}, Failure {}, Canonical Ids {}, Mutilcast id {}", messageResponse.getSuccess(),
				messageResponse.getFailure(), messageResponse.getCanonicalIds(), messageResponse.getMulticastId());

		for (int i = 0; i < messageRequest.getRegistration_ids().size(); i++) {
			String regId = messageRequest.getRegistration_ids().get(i);
			printGCMResponse(messageResponse.getResults().get(i), regId);
		}
	}

	private void printGCMResponse(MessageResult messageResult, String regId) {

		Optional.ofNullable(messageResult.getMessageId())
				.ifPresent(p -> LOGGER.logGCMActivity("Message Succesfully sent to Reg Id {} ", regId));
		Optional.ofNullable(messageResult.getError())
				.ifPresent(p -> LOGGER.logGCMActivity("Message not sent for {}, error is {}", regId, p));

	}

}
