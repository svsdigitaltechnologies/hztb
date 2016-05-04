package com.svs.hztb.api.sm.model.clickatell;

public class ClickatellMessage {
	
	private String accepted;
	private String to;
	private String apiMessageId;
	private ClickatellError error;

	
	public ClickatellError getError() {
		return error;
	}
	public void setError(ClickatellError error) {
		this.error = error;
	}
	public String getAccepted() {
		return accepted;
	}
	public void setAccepted(String accepted) {
		this.accepted = accepted;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getApiMessageId() {
		return apiMessageId;
	}
	public void setApiMessageId(String apiMessageId) {
		this.apiMessageId = apiMessageId;
	}
}
