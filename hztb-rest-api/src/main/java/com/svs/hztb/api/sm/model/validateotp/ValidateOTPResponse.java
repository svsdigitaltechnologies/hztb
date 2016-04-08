package com.svs.hztb.api.sm.model.validateotp;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.svs.hztb.api.common.utils.HZTBRegularExpressions;

public class ValidateOTPResponse {
	
	@NotNull
	@Size(min = 1, max = 15)
	@Pattern(regexp = HZTBRegularExpressions.ONLY_DIGITS_REGEX)
	private String mobileNumber;
	
	private Boolean isValidateOTPSuccesful = false;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Boolean getIsValidateOTPSuccesful() {
		return isValidateOTPSuccesful;
	}

	public void setIsValidateOTPSuccesful(Boolean isValidateOTPSuccesful) {
		this.isValidateOTPSuccesful = isValidateOTPSuccesful;
	}

}
