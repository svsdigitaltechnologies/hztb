package com.svs.speakthrough.controller;

import javax.validation.constraints.NotNull;

public class UserRequest {

	@NotNull
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
