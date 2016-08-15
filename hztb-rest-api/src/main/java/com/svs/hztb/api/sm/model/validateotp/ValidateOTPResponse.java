package com.svs.hztb.api.sm.model.validateotp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.svs.hztb.api.sm.model.user.UserProfileResponse;

/**
 * Response class for Validate OTP service api.
 * 
 * @author skairamk
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidateOTPResponse extends UserProfileResponse {

	private Boolean isUserAlreadyRegistered;
	private String otpWaitTime;

	public Boolean getIsUserAlreadyRegistered() {
		return isUserAlreadyRegistered;
	}

	public void setIsUserAlreadyRegistered(Boolean isUserAlreadyRegistered) {
		this.isUserAlreadyRegistered = isUserAlreadyRegistered;
	}

	public String getOtpWaitTime() {
		return otpWaitTime;
	}

	public void setOtpWaitTime(String otpWaitTime) {
		this.otpWaitTime = otpWaitTime;
	}

}
