package com.svs.hztb.api.sm.model.notification;

public class WelcomeNotificationRequest extends NotificationRequest {
	private String userName;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
