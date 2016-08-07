package com.svs.hztb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the opinion_response database table.
 * 
 */
@Entity
@Table(name = "opinion_response", schema = "ebdb")
@NamedQuery(name = "OpinionResponse.findAll", query = "SELECT o FROM OpinionResponseEntity o")
public class OpinionResponseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "response_id")
	private int responseId;

	@Column(name = "responder_user_id")
	private Long responderUserId;

	@Column(name = "response_txt")
	private String responseTxt;

	@Column(name = "opinion_id")
	private int opinionId;

	@Column(name = "response_type")
	private String opinionResponseType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "response_time", insertable = false)
	private Date responseTime;

	public OpinionResponseEntity() {
	}

	public int getResponseId() {
		return this.responseId;
	}

	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}

	public Long getResponderUserId() {
		return this.responderUserId;
	}

	public void setResponderUserId(Long responderUserId) {
		this.responderUserId = responderUserId;
	}

	public String getResponseTxt() {
		return this.responseTxt;
	}

	public void setResponseTxt(String responseTxt) {
		this.responseTxt = responseTxt;
	}

	public int getOpinionId() {
		return opinionId;
	}

	public void setOpinionId(int opinionId) {
		this.opinionId = opinionId;
	}

	public String getOpinionResponseType() {
		return opinionResponseType;
	}

	public void setOpinionResponseType(String opinionResponseType) {
		this.opinionResponseType = opinionResponseType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}

}