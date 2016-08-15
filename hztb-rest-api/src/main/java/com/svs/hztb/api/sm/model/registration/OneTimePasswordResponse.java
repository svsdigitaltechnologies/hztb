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
public class OneTimePasswordResponse extends HztbResponse {

	private String smsWaitTime;
	private String voiceWaitTime;
	private String otpWaitTime;
	private String status;

	public String getSmsWaitTime() {
		return smsWaitTime;
	}

	public void setSmsWaitTime(String smsWaitTime) {
		this.smsWaitTime = smsWaitTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
