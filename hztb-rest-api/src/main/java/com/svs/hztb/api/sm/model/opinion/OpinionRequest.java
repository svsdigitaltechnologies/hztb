package com.svs.hztb.api.sm.model.opinion;

import java.util.Date;
import java.util.List;

import com.svs.hztb.api.sm.model.product.Product;

/**
 * 
 * Request class for Opinion Requests.
 * 
 * @author skairamk
 *
 */
public class OpinionRequest {
	private Long requesterUserId;
	private Product product;
	private int channelId;
	private String storeGeoCode;
	private byte[] selfiePic;

	// Added for sample, delete this
	private Date date;

	// private int requestedGroupId;
	private String groupName;

	private List<Long> requestedUserIds;
	private List<Integer> requestedGroupIds;

	public Long getRequesterUserId() {
		return requesterUserId;
	}

	public void setRequesterUserId(Long requesterUserId) {
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

	public List<Long> getRequestedUserIds() {
		return requestedUserIds;
	}

	public void setRequestedUserIds(List<Long> requestedUserIds) {
		this.requestedUserIds = requestedUserIds;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<Integer> getRequestedGroupIds() {
		return requestedGroupIds;
	}

	public void setRequestedGroupIds(List<Integer> requestedGroupIds) {
		this.requestedGroupIds = requestedGroupIds;
	}

	public byte[] getSelfiePic() {
		return selfiePic;
	}

	public void setSelfiePic(byte[] selfiePic) {
		this.selfiePic = selfiePic;
	}

}
