package com.svs.hztb.api.sm.model.refresh;

/**
 * Request class for Opinion Refresh
 * 
 * @author skairamk
 *
 */
public class OpinionRefreshRequest {
	private Long userId;
	private Long responderUserId;
	private int opinionId;
	private String lastUpdatedTime;
	private byte[] selfiePic;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(String lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public int getOpinionId() {
		return opinionId;
	}

	public void setOpinionId(int opinionId) {
		this.opinionId = opinionId;
	}

	public Long getResponderUserId() {
		return responderUserId;
	}

	public void setResponderUserId(Long responderUserId) {
		this.responderUserId = responderUserId;
	}

	public byte[] getSelfiePic() {
		return selfiePic;
	}

	public void setSelfiePic(byte[] selfiePic) {
		this.selfiePic = selfiePic;
	}

}
