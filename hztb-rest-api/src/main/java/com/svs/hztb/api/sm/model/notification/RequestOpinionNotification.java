package com.svs.hztb.api.sm.model.notification;

import com.svs.hztb.api.sm.model.opinion.OpinionRequest;

public class RequestOpinionNotification extends NotificationRequest {
	
	OpinionRequest requestOpinionInput;
	
	public OpinionRequest getRequestOpinionInput() {
		return requestOpinionInput;
	}
	
	public void setRequestOpinionInput(OpinionRequest requestOpinionInput) {
		this.requestOpinionInput = requestOpinionInput;
	}
}
