package com.svs.hztb.api.sm.model.refresh;

import java.util.ArrayList;
import java.util.List;

public class OpinionResponseInfo {
	private List<OpinionResponseData> opinionResponseDataList = new ArrayList<OpinionResponseData>();
	private List<Long> unResponsiveUsers = new ArrayList<>();

	public List<OpinionResponseData> getOpinionResponseDataList() {
		return opinionResponseDataList;
	}

	public void setOpinionResponseDataList(List<OpinionResponseData> opinionResponseDataList) {
		this.opinionResponseDataList = opinionResponseDataList;
	}

	public List<Long> getUnResponsiveUsers() {
		return unResponsiveUsers;
	}

	public void setUnResponsiveUsers(List<Long> unResponsiveUsers) {
		this.unResponsiveUsers = unResponsiveUsers;
	}

}
