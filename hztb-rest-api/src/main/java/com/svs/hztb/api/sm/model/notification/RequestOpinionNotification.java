package com.svs.hztb.api.sm.model.notification;

import com.svs.hztb.api.sm.model.opinion.RequestOpinionInput;

public class RequestOpinionNotification extends NotificationRequest {
	
	RequestOpinionInput requestOpinionInput;
	
	public RequestOpinionInput getRequestOpinionInput() {
		return requestOpinionInput;
	}
	
	public void setRequestOpinionInput(RequestOpinionInput requestOpinionInput) {
		this.requestOpinionInput = requestOpinionInput;
	}
}
