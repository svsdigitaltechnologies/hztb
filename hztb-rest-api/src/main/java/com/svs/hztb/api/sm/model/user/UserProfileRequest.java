package com.svs.hztb.api.sm.model.user;

public class UserProfileRequest {
	private long phoneNumber;
	private byte[] profilePic;
	private String profilePicS3Url;
	private long pictureVersion;

	public UserProfileRequest() {
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
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
