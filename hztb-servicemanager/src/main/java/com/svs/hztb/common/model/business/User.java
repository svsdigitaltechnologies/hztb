package com.svs.hztb.common.model.business;

import java.util.Optional;

import com.svs.hztb.api.sm.model.ping.PingRequest;
import com.svs.hztb.api.sm.model.registration.OneTimePasswordRequest;
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
	private String deviceRegId;
	private String dataPushed;
	private String emailAddress;
	private Long userId;
	private byte[] profilePic;
	private String profilePicUrl;
	private String profilePicVersion;
	private String registered;
	private String registeredAlready;
	private String identity;
	private String pw;

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
	}

	/**
	 * Constructor for code registration request.
	 * 
	 * @param codeRegistrationRequest
	 */
	public User(OneTimePasswordRequest codeRegistrationRequest) {
		this.mobileNumber = codeRegistrationRequest.getMobileNumber();
		this.identity = codeRegistrationRequest.getId();
	}

	/**
	 * Constructor for ping request.
	 * 
	 * @param pingRequest
	 */
	public User(PingRequest pingRequest) {
		this.userId = Long.parseLong(pingRequest.getUserId());
	}

	/**
	 * Constructor for validateOTP request.
	 * 
	 * @param validateOTPRequest
	 */
	public User(ValidateOTPRequest validateOTPRequest) {
		this.mobileNumber = validateOTPRequest.getMobileNumber();
		this.identity = validateOTPRequest.getId();
	}

	/**
	 * Constructor for updateUserProfile request.
	 * 
	 * @param userProfileRequest
	 */
	public User(UpdateUserProfileRequest userProfileRequest) {
		this.userId = Long.parseLong(userProfileRequest.getUserId());
		Optional.ofNullable(userProfileRequest.getName()).ifPresent(p -> this.setName(p.trim()));
		this.emailAddress = userProfileRequest.getEmailAddress();
		this.profilePic = userProfileRequest.getProfilePic();
		this.deviceRegId = userProfileRequest.getDeviceRegId();
	}

	/**
	 * Constructor for getUserProfile request.
	 * 
	 * @param userProfileRequest
	 */
	public User(UserProfileRequest userProfileRequest) {
		this.userId = Long.parseLong(userProfileRequest.getUserId());
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getProfilePicVersion() {
		return profilePicVersion;
	}

	public void setProfilePicVersion(String profilePicVersion) {
		this.profilePicVersion = profilePicVersion;
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

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

}
