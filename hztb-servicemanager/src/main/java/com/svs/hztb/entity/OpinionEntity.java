package com.svs.hztb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
 @NamedQuery(name="Opinion.findAll", query="SELECT o FROM OpinionEntity o")
public class OpinionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "opinion_id")
	private int opinionId;

	private String photo;

	@Column(name = "product_url")
	private String productUrl;

	@Column(name = "reported_action")
	private String reportedAction;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "requested_time")
	private Date requestedTime;

	@Column(name = "channel_id")
	private String channelId;

	@Column(name = "requested_group_id")
	private int groupId;

	@Column(name = "requester_user_id")
	private int userId;

	@Column(name = "store_id")
	private String storeId;
	
	//bi-directional many-to-one association to Product
	
	
	
	@ManyToOne(cascade=CascadeType.MERGE)
	private ProductEntity product;

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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}
	
	

}