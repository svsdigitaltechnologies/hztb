package com.svs.hztb.sm.notification.transformer;

import org.springframework.stereotype.Component;

import com.svs.hztb.api.gcm.model.notification.MessageData;
import com.svs.hztb.api.gcm.model.notification.MessageRequest;
import com.svs.hztb.api.gcm.model.notification.MessageResponse;
import com.svs.hztb.common.exception.SystemException;
import com.svs.hztb.orchestration.component.model.FlowContext;
import com.svs.hztb.orchestration.exception.BusinessException;
import com.svs.hztb.sm.common.annotation.RestfulTransformer;
import com.svs.hztb.sm.common.enums.ServiceManagerRestfulEndpoint;
import com.svs.hztb.sm.common.transformer.GCMRestfulAbstractTransformer;

@RestfulTransformer(ServiceManagerRestfulEndpoint.GCM_SEND)
@Component
public class GCMSendNotificationTransformer extends GCMRestfulAbstractTransformer<MessageRequest, MessageResponse> {

	@Override
	public MessageRequest transformRequest(FlowContext flowContext) {
		MessageRequest messageRequest = new MessageRequest();
		messageRequest.addRegId("11111");
		messageRequest.addRegId("22222");
		messageRequest.addRegId("33333");
		MessageData messageData = new MessageData();
		messageData.setTitle("Hztb opinion request");
		messageData.setMessage("opinion request");
		messageRequest.addData(messageData);
		flowContext.setModelElement(messageRequest);
		return messageRequest;
	}

	@Override
	public void transformResponse(FlowContext flowContext, MessageResponse response) {
		System.out.println(response);
		MessageRequest messageRequest = flowContext.getModelElement(MessageRequest.class);
		if(response.getResults().size() != messageRequest.getRegistration_ids().size()) {
		}
		
	}

	@Override
	public Class<MessageResponse> getResponseClass() {
		return MessageResponse.class;
	}

}
