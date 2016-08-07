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
public class UpdateUserProfileResponse {

	@NotNull
	@Pattern(regexp = HZTBRegularExpressions.ONLY_DIGITS_REGEX)
	private Long userId;

	private String mobileNumber;
	private String name;
	private String deviceId;
	private String otpCode;
	private String otpCreationDateTime;
	private String gcmRegId;
	private String emailAddress;
	private String profilePictureURL;
	private Boolean isUserAlreadyRegistered;

	public Boolean getIsUserAlreadyRegistered() {
		return isUserAlreadyRegistered;
	}

	public void setIsUserAlreadyRegistered(Boolean isUserAlreadyRegistered) {
		this.isUserAlreadyRegistered = isUserAlreadyRegistered;
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

	public UpdateUserProfileResponse() {
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

	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}

	public String getOtpCreationDateTime() {
		return otpCreationDateTime;
	}

	public void setOtpCreationDateTime(String otpCreationDateTime) {
		this.otpCreationDateTime = otpCreationDateTime;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getGcmRegId() {
		return gcmRegId;
	}

	public void setGcmRegId(String gcmRegId) {
		this.gcmRegId = gcmRegId;
	}

}
