package com.svs.hztb.common.model.business;

import com.svs.hztb.api.sm.model.ping.PingRequest;
import com.svs.hztb.api.sm.model.registration.RegistrationRequest;
import com.svs.hztb.api.sm.model.user.UpdateUserProfileRequest;
import com.svs.hztb.api.sm.model.user.UserProfileRequest;
import com.svs.hztb.api.sm.model.validateotp.ValidateOTPRequest;

/**
 * This class is an business object for user data
 */
public class User {

	private String mobileNumber;
	private String name;
	private String otpCode;
	private String otpCreationDateTime;
	private String deviceRegId;
	private String dataPushed;
	private String emailAddress;
	private Long userId;
	private String invalidOtpCount;
	private byte[] profilePic;
	private String profilePicUrl;
	private String profilePicVersion;
	private String deviceId;
	private String registered;
	private String registeredAlready;

	public User() {
		// Intentionally left blank.
	}

	/**
	 * Constructor for registration request.
	 * 
	 * @param registrationRequest
	 */
	public User(RegistrationRequest registrationRequest) {
		this.mobileNumber = registrationRequest.getMobileNumber();
		this.deviceId = registrationRequest.getUniqueId();
	}

	/**
	 * Constructor for ping request.
	 * 
	 * @param pingRequest
	 */
	public User(PingRequest pingRequest) {
		this.userId = Long.parseLong(pingRequest.getUserId());
		this.deviceId = pingRequest.getUniqueId();
	}

	/**
	 * Constructor for validateOTP request.
	 * 
	 * @param validateOTPRequest
	 */
	public User(ValidateOTPRequest validateOTPRequest) {
		this.mobileNumber = validateOTPRequest.getMobileNumber();
		this.otpCode = validateOTPRequest.getOtpCode();
		this.deviceRegId = validateOTPRequest.getDeviceRegId();
		this.deviceId = validateOTPRequest.getUniqueId();
	}

	/**
	 * Constructor for updateUserProfile request.
	 * 
	 * @param userProfileRequest
	 */
	public User(UpdateUserProfileRequest userProfileRequest) {
		this.userId = Long.parseLong(userProfileRequest.getUserId());
		this.name = userProfileRequest.getName();
		this.emailAddress = userProfileRequest.getEmailAddress();
		this.profilePic = userProfileRequest.getProfilePic();
	}

	/**
	 * Constructor for getUserProfile request.
	 * 
	 * @param userProfileRequest
	 */
	public User(UserProfileRequest userProfileRequest) {
		this.userId = Long.parseLong(userProfileRequest.getUserId());
	}

	public String getProfilePicVersion() {
		return profilePicVersion;
	}

	public void setProfilePicVersion(String profilePicVersion) {
		this.profilePicVersion = profilePicVersion;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public byte[] getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}

	public String getProfilePicUrl() {
		return profilePicUrl;
	}

	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
	}

	public String getInvalidOtpCount() {
		return invalidOtpCount;
	}

	public void setInvalidOtpCount(String invalidOtpCount) {
		this.invalidOtpCount = invalidOtpCount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getDataPushed() {
		return dataPushed;
	}

	public void setDataPushed(String dataPushed) {
		this.dataPushed = dataPushed;
	}

	public String getDeviceRegId() {
		return deviceRegId;
	}

	public void setDeviceRegId(String deviceRegId) {
		this.deviceRegId = deviceRegId;
	}

	public String getOtpCreationDateTime() {
		return otpCreationDateTime;
	}

	public void setOtpCreationDateTime(String otpCreationDateTime) {
		this.otpCreationDateTime = otpCreationDateTime;
	}

	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
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

	public String getRegistered() {
		return registered;
	}

	public void setRegistered(String registered) {
		this.registered = registered;
	}

	public String getRegisteredAlready() {
		return registeredAlready;
	}

	public void setRegisteredAlready(String registeredAlready) {
		this.registeredAlready = registeredAlready;
	}

}
