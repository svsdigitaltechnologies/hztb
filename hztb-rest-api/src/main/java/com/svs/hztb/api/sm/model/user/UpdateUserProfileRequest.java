package com.svs.hztb.api.sm.model.user;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.svs.hztb.api.common.utils.HZTBRegularExpressions;

/**
 * Request class for update User Profile service api.
 * 
 * @author skairamk
 *
 */
public class UpdateUserProfileRequest extends UserProfileRequest {

	@Pattern(regexp = HZTBRegularExpressions.ALPHA_NUMERIC_SPACE_REGEX)
	@Size(max = 40)
	private String name;

	@Pattern(regexp = HZTBRegularExpressions.EMAIL_PATTERN)
	@Size(max = 50)
	private String emailAddress;

	private byte[] profilePic;

	private String deviceRegId;

	public byte[] getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}

	public UpdateUserProfileRequest() {
		// do nothing
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getDeviceRegId() {
		return deviceRegId;
	}

	public void setDeviceRegId(String deviceRegId) {
		this.deviceRegId = deviceRegId;
	}

}
