package com.svs.hztb.common.model.business;

import com.svs.hztb.api.sm.model.registration.OneTimePasswordRequest;
import com.svs.hztb.api.sm.model.validateotp.ValidateOTPRequest;

/**
 * Business object for One Time Password
 * 
 * @author skairamk
 *
 */
public class OneTimePassword {

	private Long id;
	private String mobileNumber;
	private String identity;
	private String uniqueId;
	private String otpCode;
	private String otpCreationDateTime;
	private Integer invalidOtpCount;
	private Integer smsSentCount;
	private String smsWaitTime;
	private String voiceWaitTime;
	private String otpWaitTime;

	public OneTimePassword() {
		// do nothing
	}

	/**
	 * 
	 * @param oneTimePasswordRequest
	 */
	public OneTimePassword(OneTimePasswordRequest oneTimePasswordRequest) {
		this.mobileNumber = oneTimePasswordRequest.getMobileNumber();
		this.identity = oneTimePasswordRequest.getId();
		this.uniqueId = oneTimePasswordRequest.getUniqueId();
	}

	/**
	 * 
	 * @param oneTimePasswordRequest
	 */
	public OneTimePassword(ValidateOTPRequest validateOTPRequest) {
		this.mobileNumber = validateOTPRequest.getMobileNumber();
		this.identity = validateOTPRequest.getId();
		this.uniqueId = validateOTPRequest.getUniqueId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
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

	public Integer getInvalidOtpCount() {
		return invalidOtpCount;
	}

	public void setInvalidOtpCount(Integer invalidOtpCount) {
		this.invalidOtpCount = invalidOtpCount;
	}

	public Integer getSmsSentCount() {
		return smsSentCount;
	}

	public void setSmsSentCount(Integer smsSentCount) {
		this.smsSentCount = smsSentCount;
	}

	public String getSmsWaitTime() {
		return smsWaitTime;
	}

	public void setSmsWaitTime(String smsWaitTime) {
		this.smsWaitTime = smsWaitTime;
	}

	public String getVoiceWaitTime() {
		return voiceWaitTime;
	}

	public void setVoiceWaitTime(String voiceWaitTime) {
		this.voiceWaitTime = voiceWaitTime;
	}

	public String getOtpWaitTime() {
		return otpWaitTime;
	}

	public void setOtpWaitTime(String otpWaitTime) {
		this.otpWaitTime = otpWaitTime;
	}

}
