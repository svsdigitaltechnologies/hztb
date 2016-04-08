package com.svs.hztb.api.sm.model.user;

import javax.validation.constraints.NotNull;

public class UserProfileRequest {
	@NotNull
	private String mobileNumber;
	
	private String name;
	private String emailAddress;

	public UserProfileRequest() {
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
