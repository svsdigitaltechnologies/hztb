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

	@Column(name = "email_address")
	private String emailAddress;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "gcm_reg_id")
	private String gcmRegId;

	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "pic_url")
	private String picUrl;

	@Column(name = "pic_version", insertable = false)
	private String picVersion;

	@Column(name = "registered")
	private String registered;

	@Column(name = "identity")
	private String identity;

	@Column(name = "pw")
	private String pw;

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

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public List<GroupEntity> getGroups() {
		return this.groups;
	}

	public void setGroups(List<GroupEntity> groups) {
		this.groups = groups;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

}