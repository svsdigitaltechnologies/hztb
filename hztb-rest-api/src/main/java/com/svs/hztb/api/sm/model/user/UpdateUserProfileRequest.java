package com.svs.hztb.api.sm.model.user;

/**
 * Request class for update User Profile service api.
 * 
 * @author skairamk
 *
 */
public class UpdateUserProfileRequest extends UserProfileRequest {

	private String name;
	private String emailAddress;
	private byte[] profilePic;

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

}
