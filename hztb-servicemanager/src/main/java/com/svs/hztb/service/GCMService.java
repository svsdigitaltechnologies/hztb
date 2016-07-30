package com.svs.hztb.service;

import java.util.List;

import com.svs.hztb.api.sm.model.opinion.OpinionResponseInput;
import com.svs.hztb.api.sm.model.opinion.OpinionRequest;
import com.svs.hztb.common.model.RequestData;
import com.svs.hztb.common.model.business.User;

public interface GCMService {

	void sendWelcomeNotification(RequestData requestData, User user);


	void sendRequestOpinionNotification(RequestData requestData, List<Integer> toUserIds, Integer fromUserId);
	void sendResponseOpinionNotification(RequestData requestData, OpinionResponseInput opinionResponseInput);

}
