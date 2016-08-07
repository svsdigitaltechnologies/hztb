package com.svs.hztb.api.sm.model.registration;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.svs.hztb.api.common.utils.HZTBRegularExpressions;

/**
 * Rest API Request class for Registration Process
 * 
 * @author skairamk
 *
 */
public class RegistrationRequest {

	@NotNull
	@Size(min = 1, max = 15)
	@Pattern(regexp = HZTBRegularExpressions.ONLY_DIGITS_REGEX)
	private String mobileNumber;

	@NotNull
	@Pattern(regexp = HZTBRegularExpressions.DEVICE_ID_REGEX)
	@Size(min = 16, max = 64)
	private String uniqueId;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

}
