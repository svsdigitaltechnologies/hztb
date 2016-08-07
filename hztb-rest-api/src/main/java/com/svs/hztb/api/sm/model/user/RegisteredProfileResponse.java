package com.svs.hztb.api.sm.model.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.svs.hztb.api.common.utils.HZTBRegularExpressions;

/**
 * Base class for User Profile Request
 * 
 * @author skairamk
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisteredProfileResponse {

	@NotNull
	@Size(min = 1, max = 15)
	@Pattern(regexp = HZTBRegularExpressions.ONLY_DIGITS_REGEX)
	private String mobileNumber;

	@Pattern(regexp = HZTBRegularExpressions.ALPHA_NUMERIC_REGEX)
	private String name;

	@NotNull
	@Pattern(regexp = HZTBRegularExpressions.ONLY_DIGITS_REGEX)
	private Long userId;

	private String profilePictureURL;

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

}
