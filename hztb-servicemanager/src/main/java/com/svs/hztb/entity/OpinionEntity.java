package com.svs.hztb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the opinion database table.
 * 
 */
@Entity
@Table(name = "opinion", schema = "ebdb")
@NamedQuery(name = "Opinion.findAll", query = "SELECT o FROM OpinionEntity o")
public class OpinionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "opinion_id")
	private int opinionId;

	@Column(name = "photo")
	private String photo;

	@Column(name = "product_url")
	private String productUrl;

	@Column(name = "reported_action")
	private String reportedAction;

	@GeneratedValue
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "requested_time", insertable = false, updatable = false)
	private Date requestedTime;

	@Column(name = "channel_id")
	private String channelId;

	@Column(name = "requested_group_id")
	private int groupId;

	@Column(name = "requester_user_id")
	private Long userId;

	@Column(name = "store_id")
	private String storeId;

	// bi-directional many-to-one association to Product

	// @ManyToOne(cascade=CascadeType.MERGE)
	// @ManyToOne(cascade=CascadeType.ALL)
	// private ProductEntity product;
	@Column(name = "product_name")
	private String product;

	public OpinionEntity() {
	}

	public int getOpinionId() {
		return this.opinionId;
	}

	public void setOpinionId(int opinionId) {
		this.opinionId = opinionId;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getProductUrl() {
		return this.productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public String getReportedAction() {
		return this.reportedAction;
	}

	public void setReportedAction(String reportedAction) {
		this.reportedAction = reportedAction;
	}

	public Date getRequestedTime() {
		return this.requestedTime;
	}

	public void setRequestedTime(Date requestedTime) {
		this.requestedTime = requestedTime;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

}