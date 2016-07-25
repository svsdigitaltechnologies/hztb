package com.svs.hztb.sm.notification.transformer;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.svs.hztb.api.gcm.model.notification.MessageData;
import com.svs.hztb.api.gcm.model.notification.MessageRequest;
import com.svs.hztb.api.gcm.model.notification.MessageResponse;
import com.svs.hztb.api.sm.model.notification.NotificationRequest;
import com.svs.hztb.orchestration.component.model.FlowContext;
import com.svs.hztb.sm.common.annotation.RestfulTransformer;
import com.svs.hztb.sm.common.enums.ServiceManagerRestfulEndpoint;
import com.svs.hztb.sm.common.transformer.GCMRestfulAbstractTransformer;

@RestfulTransformer(ServiceManagerRestfulEndpoint.GCM_SEND_NOTIFICATION)
@Component
public class GCMNotificationTransformer extends GCMRestfulAbstractTransformer<MessageRequest, MessageResponse> {

	@Override
	public MessageRequest transformRequest(FlowContext flowContext) {
		MessageRequest messageRequest = new MessageRequest();
		NotificationRequest notificationRequest = flowContext.getModelElement(NotificationRequest.class);
		List<String> deviceRegIds = notificationRequest.getDeviceRegIds();
		deviceRegIds.stream().forEach(messageRequest::addRegId);

		MessageData messageData = new MessageData();
		messageData.addData("title", notificationRequest.getTitle());
		messageData.addData("message", notificationRequest.getMessage());
		Optional.ofNullable(notificationRequest.getProduct())
				.ifPresent(p -> messageData.addData("productName", p.getName()));
		messageData.addData("notificationType", notificationRequest.getNotificationType());
		messageRequest.addData(messageData);
		flowContext.setModelElement(messageRequest);
		return messageRequest;
	}

	@Override
	public Class<MessageResponse> getResponseClass() {
		return MessageResponse.class;
	}

}
