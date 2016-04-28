package com.svs.hztb.api.sm.model.notification;

import java.util.ArrayList;
import java.util.List;

import com.svs.hztb.api.sm.model.product.Product;

public class NotificationRequest {
	private List<String> deviceRegIds;
	private String message;
	private String title;
	private Product product;
	
	private String notificationType;
	
	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

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
