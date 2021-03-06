package com.svs.hztb.api.sm.model.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.svs.hztb.api.common.utils.HZTBRegularExpressions;

/**
 * Base class for User Profile Request
 * 
 * @author skairamk
 *
 */
public class UserProfileRequest {

	@NotNull
	@Pattern(regexp = HZTBRegularExpressions.ONLY_DIGITS_REGEX)
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
