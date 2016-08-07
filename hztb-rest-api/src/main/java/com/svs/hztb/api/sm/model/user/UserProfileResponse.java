package com.svs.hztb.api.sm.model.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.svs.hztb.api.common.utils.HZTBRegularExpressions;

/**
 * 
 * Base class for User Profile Response
 * 
 * @author skairamk
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileResponse {

	@NotNull
	@Pattern(regexp = HZTBRegularExpressions.ONLY_DIGITS_REGEX)
	private Long userId;

	private String name;
	private String emailAddress;
	private String profilePictureURL;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getProfilePictureURL() {
		return profilePictureURL;
	}

	public void setProfilePictureURL(String profilePictureURL) {
		this.profilePictureURL = profilePictureURL;
	}

	public UserProfileResponse() {
		// do nothing
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
