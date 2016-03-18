package com.svs.speakthrough.api.model.user;


public class UserRequest {

	private String userName;
	
	private String userSfid;

	public String getUserSfid() {
		return userSfid;
	}

	public void setUserSfid(String userSfid) {
		this.userSfid = userSfid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
