package com.svs.hztb.api.sm.model.opinion;

public class OpinionResponseInput {
	private Long userId;
	private int opinionReqId;
	private String responseCode;
	private String responseTxt;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getOpinionReqId() {
		return opinionReqId;
	}

	public void setOpinionReqId(int opinionReqId) {
		this.opinionReqId = opinionReqId;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseTxt() {
		return responseTxt;
	}

	public void setResponseTxt(String responseTxt) {
		this.responseTxt = responseTxt;
	}

}
