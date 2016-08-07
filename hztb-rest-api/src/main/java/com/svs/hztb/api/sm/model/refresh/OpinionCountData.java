package com.svs.hztb.api.sm.model.refresh;

public class OpinionCountData {
	private Long userId;
	private int givenCount;
	private int pendingCount;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getGivenCount() {
		return givenCount;
	}

	public void setGivenCount(int givenCount) {
		this.givenCount = givenCount;
	}

	public int getPendingCount() {
		return pendingCount;
	}

	public void setPendingCount(int pendingCount) {
		this.pendingCount = pendingCount;
	}

}
