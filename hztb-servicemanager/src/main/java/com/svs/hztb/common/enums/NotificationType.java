package com.svs.hztb.common.enums;

/**
 * Enum - defines all the enum types for notifications
 */
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
