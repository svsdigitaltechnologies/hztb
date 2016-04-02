package com.svs.hztb.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id")
	private int userId;

	@Column(name="data_pushed_ind")
	private String dataPushedInd;

	@Column(name="email_address")
	private String emailAddress;

	private String firstname;

	@Column(name="gcm_reg_id")
	private String gcmRegId;

	@Column(name="imei_code")
	private String imeiCode;

	private String lastname;

	@Column(name="mobile_number")
	private String mobileNumber;

	@Column(name="otp_code")
	private String otpCode;

	@Column(name="otp_create_time")
	private String otpCreateTime;

	//bi-directional many-to-many association to Group
	@ManyToMany
	@JoinTable(
		name="user_group"
		, joinColumns={
			@JoinColumn(name="user_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="group_id")
			}
		)
	private List<Group> groups;

	public User() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
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

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getGcmRegId() {
		return this.gcmRegId;
	}

	public void setGcmRegId(String gcmRegId) {
		this.gcmRegId = gcmRegId;
	}

	public String getImeiCode() {
		return this.imeiCode;
	}

	public void setImeiCode(String imeiCode) {
		this.imeiCode = imeiCode;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
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

	public List<Group> getGroups() {
		return this.groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

}