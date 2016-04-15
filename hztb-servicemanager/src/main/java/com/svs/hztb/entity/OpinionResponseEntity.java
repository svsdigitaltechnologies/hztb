package com.svs.hztb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 @NamedQuery(name="OpinionResponse.findAll", query="SELECT o FROM OpinionResponseEntity o")
public class OpinionResponseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "response_id")
	private int responseId;

	@Column(name = "responder_user_id")
	private int responderUserId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "response_time")
	private Date responseTime;

	@Column(name = "response_txt")
	private String responseTxt;

	// bi-directional many-to-one association to Opinion
	@ManyToOne
	@JoinColumn(name = "opinion_id")
	private OpinionEntity opinion;

	// bi-directional many-to-one association to OpinionResponseType
	@ManyToOne
	@JoinColumn(name = "response_type")
	private OpinionResponseTypeEntity opinionResponseType;

	public OpinionResponseEntity() {
	}

	public int getResponseId() {
		return this.responseId;
	}

	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}

	public int getResponderUserId() {
		return this.responderUserId;
	}

	public void setResponderUserId(int responderUserId) {
		this.responderUserId = responderUserId;
	}

	public Date getResponseTime() {
		return this.responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}

	public String getResponseTxt() {
		return this.responseTxt;
	}

	public void setResponseTxt(String responseTxt) {
		this.responseTxt = responseTxt;
	}

	public OpinionEntity getOpinion() {
		return this.opinion;
	}

	public void setOpinion(OpinionEntity opinion) {
		this.opinion = opinion;
	}

	public OpinionResponseTypeEntity getOpinionResponseType() {
		return this.opinionResponseType;
	}

	public void setOpinionResponseType(OpinionResponseTypeEntity opinionResponseType) {
		this.opinionResponseType = opinionResponseType;
	}

}