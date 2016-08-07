package com.svs.hztb.api.sm.model.user;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Request class for registered users service api.
 * 
 * @author skairamk
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisteredProfilesResponse {
	private List<RegisteredProfileResponse> registeredProfileResponses;

	public List<RegisteredProfileResponse> getRegisteredProfileResponses() {
		return registeredProfileResponses;
	}

	public void setRegisteredProfileResponses(List<RegisteredProfileResponse> registeredProfileResponses) {
		this.registeredProfileResponses = registeredProfileResponses;
	}

	public void addRegisteredProfileResponse(RegisteredProfileResponse registeredProfileResponse) {
		if (registeredProfileResponses == null) {
			registeredProfileResponses = new ArrayList<>();
		}
		registeredProfileResponses.add(registeredProfileResponse);
	}

}
