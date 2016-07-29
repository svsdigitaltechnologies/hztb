package com.svs.hztb.api.gcm.model.notification;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MessageRequest {
	private List<String> registration_ids;
	private Map<String, String> data;

	public List<String> getRegistration_ids() {
		return registration_ids;
	}

	public void setRegistration_ids(List<String> registration_ids) {
		this.registration_ids = registration_ids;
	}

	public void addRegId(String regId) {
		if (registration_ids == null) {
			registration_ids = new LinkedList<>();
		}
		registration_ids.add(regId);
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
