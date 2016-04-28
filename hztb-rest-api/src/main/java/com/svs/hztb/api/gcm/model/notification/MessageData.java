package com.svs.hztb.api.gcm.model.notification;

import java.util.HashMap;
import java.util.Map;

public class MessageData {
	
	private Map<String, String> data;
	private String title;
	private String message;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}
	
	public void addData(String key, String value) {
		if (data == null) {
			this.data = new HashMap<String, String>();
		}
		this.data.put(key, value);
	}
}
