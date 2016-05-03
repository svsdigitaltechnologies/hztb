package com.svs.hztb.api.sm.model.opinion;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.svs.hztb.api.sm.model.product.Product;

public class RequestOpinionInput {
	private int requesterUserId;
	private Product product;
	private int channelId;
	private String storeGeoCode;
	private String productUrl;
	//Added for sample, delete this
	private Date date;
	
	private int requestedGroupId;
	private List<Integer> requestedUserIds;

	public int getRequesterUserId() {
		return requesterUserId;
	}

	public void setRequesterUserId(int requesterUserId) {
		this.requesterUserId = requesterUserId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public String getStoreGeoCode() {
		return storeGeoCode;
	}

	public void setStoreGeoCode(String storeGeoCode) {
		this.storeGeoCode = storeGeoCode;
	}

	public int getRequestedGroupId() {
		return requestedGroupId;
	}

	public void setRequestedGroupId(int requestedGroupId) {
		this.requestedGroupId = requestedGroupId;
	}

	public List<Integer> getRequestedUserIds() {
		return requestedUserIds;
	}

	public void setRequestedUserIds(List<Integer> requestedUserIds) {
		this.requestedUserIds = requestedUserIds;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	
	

}
