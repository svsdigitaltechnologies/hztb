package com.svs.hztb.common.logging;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.svs.hztb.common.util.StringUtil;

public class FailureLogMessage {
	static final int REASON_FAILURE_TYPE_MAX = 30;
	static final int REASON_FAILURE_TEXT_MAX = 30;

	@NotNull
	@Size(min = 1, max = 26)
	private String timestamp;

	@NotNull
	@Size(min = 1, max = 20)
	@Pattern(regexp = "^([a-zA-Z0-9_]*)$")
	private String deliveryChannel;

	@NotNull
	@Size(min = 1, max = 50)
	@Pattern(regexp = "^([a-zA-Z0-9_ -]*)$")
	private String messageType;

	@NotNull
	@Size(min = 1, max = 30)
	private String reasonFailureType;

	@NotNull
	@Size(min = 1, max = 10240)
	private String reasonFailureText;

	@NotNull
	private Object payload;

	public FailureLogMessage(String timestamp, String deliveryChannel, String messageType, String reasonFailureType,
			String reasonFailureText, Object payload) {
		this.timestamp = timestamp;
		this.deliveryChannel = deliveryChannel;
		this.messageType = messageType;
		this.reasonFailureType = reasonFailureType;
		if (this.reasonFailureType != null && this.reasonFailureType.length() > REASON_FAILURE_TYPE_MAX) {
			this.reasonFailureType = this.reasonFailureType.substring(0, REASON_FAILURE_TYPE_MAX);
		}

		this.reasonFailureText = reasonFailureText;
		if (this.reasonFailureText != null && this.reasonFailureText.length() > REASON_FAILURE_TEXT_MAX) {
			this.reasonFailureText = this.reasonFailureText.substring(0, REASON_FAILURE_TEXT_MAX);
		}

		this.payload = payload;
	}

	public FailureLogMessage() {
		// nothing
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getDeliveryChannel() {
		return deliveryChannel;
	}

	public void setDeliveryChannel(String deliveryChannel) {
		this.deliveryChannel = deliveryChannel;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getReasonFailureType() {
		return reasonFailureType;
	}

	public void setReasonFailureType(String reasonFailureType) {
		this.reasonFailureType = reasonFailureType;
	}

	public String getReasonFailureText() {
		return reasonFailureText;
	}

	public void setReasonFailureText(String reasonFailureText) {
		this.reasonFailureText = reasonFailureText;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return StringUtil.convertObjectToJSON(this);
	}
}
