package com.svs.hztb.api.sm.model.registration;

import javax.validation.constraints.NotNull;

public class RegistrationRequest {
	
	@NotNull
	private String phoneNumber;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
