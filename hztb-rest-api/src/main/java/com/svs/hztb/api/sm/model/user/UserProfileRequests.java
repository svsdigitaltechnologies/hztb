package com.svs.hztb.api.sm.model.user;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserProfileRequests {
	@NotNull
	@Size(min = 1)
	@Valid
	private List<UserProfileRequest> userProfileRequests;

	public List<UserProfileRequest> getUserProfileRequests() {
		return userProfileRequests;
	}

	public void setUserProfileRequests(List<UserProfileRequest> userProfileRequests) {
		this.userProfileRequests = userProfileRequests;
	}
}
