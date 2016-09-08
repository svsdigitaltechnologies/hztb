package com.svs.hztb.api.sm.model.registration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.svs.hztb.common.model.HztbResponse;

/**
 * Response class for requestcode service API
 * 
 * @author skairamk
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetOTPResponse extends HztbResponse {

	private String otpCode;

	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}

}
