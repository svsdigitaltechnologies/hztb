package com.svs.hztb.common.enums;

public enum NotificationType {
	WELCOME("Welcome"), REQUEST("Request"), RESPONSE("Response"), PRODUCT("Product"), PROFILE("Profile");
	
	private String notificationId;
	private NotificationType(String name) {
		this.notificationId = name;
	}
	public String getNotificationId() {
		return notificationId;
	}
}

