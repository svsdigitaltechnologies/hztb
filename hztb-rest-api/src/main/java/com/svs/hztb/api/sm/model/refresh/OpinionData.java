package com.svs.hztb.api.sm.model.refresh;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.svs.hztb.api.sm.model.product.Product;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpinionData {
	private int opinionId;
	// private int requestedUserId;
	private int requestedGroupId;
	private String productName;
	private Product product;
	private String selfieUrl;
	private Map<String, Integer> responseCounts = new HashMap<String, Integer>();

	public int getOpinionId() {
		return opinionId;
	}

	public void setOpinionId(int opinionId) {
		this.opinionId = opinionId;
	}

	public String getSelfieUrl() {
		return selfieUrl;
	}

	public void setSelfieUrl(String selfieUrl) {
		this.selfieUrl = selfieUrl;
	}

	// public int getRequestedUserId() {
	// return requestedUserId;
	// }
	// public void setRequestedUserId(int requestedUserId) {
	// this.requestedUserId = requestedUserId;
	// }
	public int getRequestedGroupId() {
		return requestedGroupId;
	}

	public void setRequestedGroupId(int requestedGroupId) {
		this.requestedGroupId = requestedGroupId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Map<String, Integer> getResponseCounts() {
		return responseCounts;
	}

	public void setResponseCounts(Map<String, Integer> responseCounts) {
		this.responseCounts = responseCounts;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
