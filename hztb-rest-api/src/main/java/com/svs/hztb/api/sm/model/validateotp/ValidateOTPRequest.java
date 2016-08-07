package com.svs.hztb.api.sm.model.validateotp;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.svs.hztb.api.common.utils.HZTBRegularExpressions;

/**
 * 
 * Request class for Validate OTP Service API
 * 
 * @author skairamk
 *
 */
public class ValidateOTPRequest {

	@NotNull
	@Size(min = 1, max = 15)
	@Pattern(regexp = HZTBRegularExpressions.ONLY_DIGITS_REGEX)
	private String mobileNumber;

	@NotNull
	@Size(min = 1, max = 6)
	@Pattern(regexp = HZTBRegularExpressions.ONLY_DIGITS_REGEX)
	private String otpCode;

	@NotNull
	@Pattern(regexp = HZTBRegularExpressions.DEVICE_ID_REGEX)
	@Size(min = 16, max = 64)
	private String uniqueId;

	@NotNull
	private String deviceRegId;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getDeviceRegId() {
		return deviceRegId;
	}

	public void setDeviceRegId(String deviceRegId) {
		this.deviceRegId = deviceRegId;
	}

}
