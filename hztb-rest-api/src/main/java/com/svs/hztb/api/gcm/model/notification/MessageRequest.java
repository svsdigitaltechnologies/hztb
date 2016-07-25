package com.svs.hztb.api.gcm.model.notification;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MessageRequest {
	private List<String> registrationIds;
	private Map<String, String> data;

	public List<String> getRegistrationIds() {
		return registrationIds;
	}

	public void setRegistrationIds(List<String> registrationIds) {
		this.registrationIds = registrationIds;
	}

	public void addRegId(String regId) {
		if (registrationIds == null) {
			registrationIds = new LinkedList<>();
		}
		registrationIds.add(regId);
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	public void addData(MessageData messageData) {
		if (data == null)
			data = new HashMap<>();
		data = messageData.getData();
	}

}
