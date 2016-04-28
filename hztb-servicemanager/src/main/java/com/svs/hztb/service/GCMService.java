package com.svs.hztb.service;

import com.svs.hztb.api.sm.model.opinion.RequestOpinionInput;
import com.svs.hztb.common.model.RequestData;
import com.svs.hztb.common.model.business.User;

public interface GCMService {

	void sendWelcomeNotification(RequestData requestData, User user);

	void sendRequestOpinionNotification(RequestData requestData, RequestOpinionInput requestOpinionInput);

}
