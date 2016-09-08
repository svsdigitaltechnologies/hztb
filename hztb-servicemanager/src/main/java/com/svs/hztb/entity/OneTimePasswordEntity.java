package com.svs.hztb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the code register database table.
 * 
 */
@Entity
@Table(name = "code_register", schema = "ebdb")
@NamedQuery(name = "OneTimePasswordEntity.findAll", query = "SELECT u FROM OneTimePasswordEntity u")
public class OneTimePasswordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "pn")
	private String mobileNumber;

	@Column(name = "identity")
	private String identity;

	@Column(name = "code")
	private String code;

	@Column(name = "code_time")
	private String codeTime;

	@Column(name = "invalid_attempts")
	private Integer invalidAttempts;

	@Column(name = "sms_sent_count")
	private Integer smsSentCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeTime() {
		return codeTime;
	}

	public void setCodeTime(String codeTime) {
		this.codeTime = codeTime;
	}

	public Integer getInvalidAttempts() {
		return invalidAttempts;
	}

	public void setInvalidAttempts(Integer invalidAttempts) {
		this.invalidAttempts = invalidAttempts;
	}

	public Integer getSmsSentCount() {
		return smsSentCount;
	}

	public void setSmsSentCount(Integer smsSentCount) {
		this.smsSentCount = smsSentCount;
	}

}