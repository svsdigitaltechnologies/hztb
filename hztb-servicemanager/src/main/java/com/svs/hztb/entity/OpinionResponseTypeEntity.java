package com.svs.hztb.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the opinion_response_type database table.
 * 
 */
@Entity
@Table(name = "opinion_response_type", schema = "ebdb")
 @NamedQuery(name="OpinionResponseType.findAll", query="SELECT o FROM OpinionResponseTypeEntity o")
public class OpinionResponseTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "response_type")
	private String responseType;

	@Column(name = "response_description")
	private String responseDescription;

	// bi-directional many-to-one association to OpinionResponse
	@OneToMany(mappedBy = "opinionResponseType")
	private List<OpinionResponseEntity> opinionResponses;

	public OpinionResponseTypeEntity() {
	}

	public String getResponseType() {
		return this.responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getResponseDescription() {
		return this.responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public List<OpinionResponseEntity> getOpinionResponses() {
		return this.opinionResponses;
	}

	public void setOpinionResponses(List<OpinionResponseEntity> opinionResponses) {
		this.opinionResponses = opinionResponses;
	}

	

}