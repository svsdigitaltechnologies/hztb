package com.svs.hztb.api.sm.model.refresh;

public class OpinionResponseData {
	private int opinionId;
	private String responseText;
	private String responseType;
	// private Date responseTime;
	private Long responderUserId;

	public int getOpinionId() {
		return opinionId;
	}

	public void setOpinionId(int opinionId) {
		this.opinionId = opinionId;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	// public Date getResponseTime() {
	// return responseTime;
	// }
	// public void setResponseTime(Date responseTime) {
	// this.responseTime = responseTime;
	// }
	public Long getResponderUserId() {
		return responderUserId;
	}

	public void setResponderUserId(Long responderUserId) {
		this.responderUserId = responderUserId;
	}

}
