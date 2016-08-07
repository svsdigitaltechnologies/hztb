package com.svs.hztb.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "user", schema = "ebdb")
@NamedQuery(name = "UserEntity.findAll", query = "SELECT u FROM UserEntity u")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(name = "data_pushed_ind")
	private String dataPushedInd;

	@Column(name = "email_address")
	private String emailAddress;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "gcm_reg_id")
	private String gcmRegId;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "otp_code")
	private String otpCode;

	@Column(name = "otp_create_time")
	private String otpCreateTime;

	@Column(name = "pic_url")
	private String picUrl;

	@Column(name = "pic_version", insertable = false)
	private String picVersion;

	@Column(name = "invalid_otp_retries")
	private String invalidOtpRetries;

	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "registered", insertable = false)
	private String registered;

	@Column(name = "registered_already", insertable = false)
	private String registeredAlready;

	public String getRegisteredAlready() {
		return registeredAlready;
	}

	public void setRegisteredAlready(String registeredAlready) {
		this.registeredAlready = registeredAlready;
	}

	public String getRegistered() {
		return registered;
	}

	public void setRegistered(String registered) {
		this.registered = registered;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPicVersion() {
		return picVersion;
	}

	public void setPicVersion(String picVersion) {
		this.picVersion = picVersion;
	}

	// bi-directional many-to-many association to Group
	@ManyToMany
	@JoinTable(name = "user_group", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "group_id") })
	private List<GroupEntity> groups;

	public UserEntity() {
		// do nothing
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Long getUserId() {
		return this.userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDataPushedInd() {
		return this.dataPushedInd;
	}

	public void setDataPushedInd(String dataPushedInd) {
		this.dataPushedInd = dataPushedInd;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getGcmRegId() {
		return this.gcmRegId;
	}

	public void setGcmRegId(String gcmRegId) {
		this.gcmRegId = gcmRegId;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getOtpCode() {
		return this.otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}

	public String getOtpCreateTime() {
		return this.otpCreateTime;
	}

	public void setOtpCreateTime(String otpCreateTime) {
		this.otpCreateTime = otpCreateTime;
	}

	public List<GroupEntity> getGroups() {
		return this.groups;
	}

	public void setGroups(List<GroupEntity> groups) {
		this.groups = groups;
	}

	public String getInvalidOtpRetries() {
		return invalidOtpRetries;
	}

	public void setInvalidOtpRetries(String invalidOtpRetries) {
		this.invalidOtpRetries = invalidOtpRetries;
	}

}