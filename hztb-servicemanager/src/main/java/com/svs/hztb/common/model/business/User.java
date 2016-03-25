package com.svs.hztb.common.model.business;

import com.svs.hztb.api.sm.model.registration.RegistrationRequest;

public class User {

	private String phoneNumber;
	private String name;

	public User() {

	}

	public User(RegistrationRequest registrationRequest) {
		this.phoneNumber = registrationRequest.getPhoneNumber();
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
