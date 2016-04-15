package com.svs.hztb.common.model.business;

import com.svs.hztb.api.sm.model.ping.PingRequest;
import com.svs.hztb.api.sm.model.registration.RegistrationRequest;
import com.svs.hztb.api.sm.model.user.UserProfileRequest;
import com.svs.hztb.api.sm.model.validateotp.ValidateOTPRequest;

public class User {

	private String mobileNumber;
	private String name;
	private String imei;
	private String otpCode;
	private String otpCreationDateTime;
	private String deviceRegId;
	private String dataPushed;
	private String emailAddress;
	private String userId;
	private String invalidOtpCount;

	public User() {

	}

	public User(RegistrationRequest registrationRequest) {
		this.mobileNumber = registrationRequest.getMobileNumber();
	}

	public User(PingRequest pingRequest) {
		this.mobileNumber = pingRequest.getMobileNumber();
		this.setImei(pingRequest.getImei());
	}

	public User(ValidateOTPRequest validateOTPRequest) {
		this.mobileNumber = validateOTPRequest.getMobileNumber();
		this.otpCode = validateOTPRequest.getOtpCode();
		this.imei = validateOTPRequest.getImei();
		this.deviceRegId = validateOTPRequest.getDeviceRegId();
	}

	public User(UserProfileRequest userProfileRequest) {
		this.mobileNumber = userProfileRequest.getMobileNumber();
		this.name = userProfileRequest.getName();
		this.emailAddress = userProfileRequest.getEmailAddress();
	}

	public String getInvalidOtpCount() {
		return invalidOtpCount;
	}

	public void setInvalidOtpCount(String invalidOtpCount) {
		this.invalidOtpCount = invalidOtpCount;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
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

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
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

}
