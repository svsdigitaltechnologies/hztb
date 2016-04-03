package com.svs.hztb.api.sm.model.user;

import javax.validation.constraints.NotNull;

public class UserProfileRequest {
	@NotNull
	private String phoneNumber;
	private byte[] profilePic;
	private String profilePicS3Url;
	private long pictureVersion;

	public UserProfileRequest() {
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public byte[] getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(byte[] profilePic) {
		this.profilePic = profilePic;
	}

	public String getProfilePicS3Url() {
		return profilePicS3Url;
	}

	public void setProfilePicS3Url(String profilePicS3Url) {
		this.profilePicS3Url = profilePicS3Url;
	}

	public long getPictureVersion() {
		return pictureVersion;
	}

	public void setPictureVersion(long pictureVersion) {
		this.pictureVersion = pictureVersion;
	}

}
