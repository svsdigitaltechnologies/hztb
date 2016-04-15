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
 * The persistent class for the channel database table.
 * 
 */
@Entity
@Table(name = "channel", schema = "ebdb")
 @NamedQuery(name="Channel.findAll", query="SELECT c FROM ChannelEntity c")
public class ChannelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "channel_id")
	private int channelId;

	@Column(name = "channel_desc")
	private String channelDesc;

	@Column(name = "channel_type")
	private String channelType;

	// bi-directional many-to-one association to Opinion

	/*@OneToMany(mappedBy = "channel")
	private List<OpinionEntity> opinions;
*/
	public ChannelEntity() {
	}

	public int getChannelId() {
		return this.channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getChannelDesc() {
		return this.channelDesc;
	}

	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}

	public String getChannelType() {
		return this.channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	/*
	 * public List<OpinionEntity> getOpinions() { return this.opinions; }
	 * 
	 * public void setOpinions(List<OpinionEntity> opinions) { this.opinions =
	 * opinions; }
	 */

}