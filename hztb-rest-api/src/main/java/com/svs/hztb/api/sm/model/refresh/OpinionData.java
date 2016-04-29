package com.svs.hztb.api.sm.model.refresh;

public class OpinionData {
	private int opinionId;
	//private int requestedUserId;
	private int requestedGroupId;
	private String productName;
	public int getOpinionId() {
		return opinionId;
	}
	public void setOpinionId(int opinionId) {
		this.opinionId = opinionId;
	}
//	public int getRequestedUserId() {
//		return requestedUserId;
//	}
//	public void setRequestedUserId(int requestedUserId) {
//		this.requestedUserId = requestedUserId;
//	}
	public int getRequestedGroupId() {
		return requestedGroupId;
	}
	public void setRequestedGroupId(int requestedGroupId) {
		this.requestedGroupId = requestedGroupId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	

}
