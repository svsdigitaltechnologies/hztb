package com.svs.hztb.api.sm.model.opinion;

import java.util.List;

import com.svs.hztb.api.sm.model.product.Product;

public class RequestOpinionRequest {
	private int requesterUserId;
	private Product product;
	private int channelId;
	private String storeGeoCode;
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
	
	
	
	
}
