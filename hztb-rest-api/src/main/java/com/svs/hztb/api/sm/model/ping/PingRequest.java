package com.svs.hztb.api.sm.model.ping;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.svs.hztb.api.common.utils.HZTBRegularExpressions;
import com.svs.hztb.api.sm.model.user.UserProfileRequest;

/**
 * 
 * Request class for Ping Service API
 * 
 * @author skairamk
 *
 */
public class PingRequest extends UserProfileRequest {

	@NotNull
	@Pattern(regexp = HZTBRegularExpressions.DEVICE_ID_REGEX)
	private String uniqueId;

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

}
