package com.svs.hztb.api.sm.model.notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationRequest {
	private List<String> deviceRegIds;
	private String message;
	private String title;
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getDeviceRegIds() {
		return deviceRegIds;
	}

	public void setDeviceRegIds(List<String> deviceRegIds) {
		this.deviceRegIds = deviceRegIds;
	}
	
	public void addDeviceRegId(String deviceRegId) {
		if(deviceRegIds == null) {
			deviceRegIds = new ArrayList<String>();
		}
		deviceRegIds.add(deviceRegId);
	}
}
