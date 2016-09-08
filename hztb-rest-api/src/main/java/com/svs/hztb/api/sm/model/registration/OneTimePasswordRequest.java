package com.svs.hztb.api.sm.model.registration;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.svs.hztb.api.common.utils.HZTBRegularExpressions;
import com.svs.hztb.common.model.HztbRequest;

/**
 * Rest API Request class for Code Registration Process
 * 
 * @author skairamk
 *
 */
public class OneTimePasswordRequest extends HztbRequest {

	@NotNull
	@Size(min = 1, max = 15)
	@Pattern(regexp = HZTBRegularExpressions.ONLY_DIGITS_REGEX)
	private String mobileNumber;

	@NotNull
	@Pattern(regexp = HZTBRegularExpressions.DEVICE_ID_REGEX)
	@Size(min = 16, max = 64)
	private String id;

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
