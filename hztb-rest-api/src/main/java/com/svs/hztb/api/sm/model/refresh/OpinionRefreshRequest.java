package com.svs.hztb.api.sm.model.refresh;

/**
 * Request class for Opinion Refresh
 * 
 * @author skairamk
 *
 */
public class OpinionRefreshRequest {
	private int userId;
	private int responderUserId;
	private int opinionId;
	private String lastUpdatedTime;
	private byte[] selfiePic;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
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

	public int getResponderUserId() {
		return responderUserId;
	}

	public void setResponderUserId(int responderUserId) {
		this.responderUserId = responderUserId;
	}

	public byte[] getSelfiePic() {
		return selfiePic;
	}

	public void setSelfiePic(byte[] selfiePic) {
		this.selfiePic = selfiePic;
	}

}
