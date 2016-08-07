package com.svs.hztb.api.sm.model.validateotp;

import com.svs.hztb.api.sm.model.user.UserProfileResponse;

/**
 * Response class for Validate OTP service api.
 * 
 * @author skairamk
 *
 */
public class ValidateOTPResponse extends UserProfileResponse {

	private Boolean isValidateOTPSuccesful;
	private Boolean isUserAlreadyRegistered;

	public Boolean getIsUserAlreadyRegistered() {
		return isUserAlreadyRegistered;
	}

	public void setIsUserAlreadyRegistered(Boolean isUserAlreadyRegistered) {
		this.isUserAlreadyRegistered = isUserAlreadyRegistered;
	}

	public Boolean getIsValidateOTPSuccesful() {
		return isValidateOTPSuccesful;
	}

	public void setIsValidateOTPSuccesful(Boolean isValidateOTPSuccesful) {
		this.isValidateOTPSuccesful = isValidateOTPSuccesful;
	}

}
