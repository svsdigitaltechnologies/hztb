package com.svs.hztb.api.sm.model.user;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Request class for registered users service api.
 * 
 * @author skairamk
 *
 */
public class RegisteredProfilesRequest {
	@NotNull
	@Size(min = 1)
	@Valid
	private List<RegisteredProfileRequest> registeredProfileRequests;

	public List<RegisteredProfileRequest> getRegisteredProfileRequests() {
		return registeredProfileRequests;
	}

	public void setRegisteredProfileRequests(List<RegisteredProfileRequest> registeredProfileRequests) {
		this.registeredProfileRequests = registeredProfileRequests;
	}
}
