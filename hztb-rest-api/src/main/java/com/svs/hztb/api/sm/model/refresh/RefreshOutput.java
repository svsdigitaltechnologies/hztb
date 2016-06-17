package com.svs.hztb.api.sm.model.refresh;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.svs.hztb.api.sm.model.BasicOutput;
import com.svs.hztb.api.sm.model.ErrorOutput;

public class RefreshOutput extends BasicOutput {
	
	private ErrorOutput errorOutput;
	private List<OpinionData> opinionDataList;
	private OpinionResponseInfo opinionResponseInfo = new OpinionResponseInfo();
	private ResponseGivenPendingInfo responseGivenPendingInfo = new ResponseGivenPendingInfo();
	
	private Collection<OpinionCountData> opinionCountsList;
	//private List<OpinionResponseData> opinionResponseDataList;
	
	
	public ErrorOutput getErrorOutput() {
		return errorOutput;
	}

	public void setErrorOutput(ErrorOutput errorOutput) {
		this.errorOutput = errorOutput;
	}

	public List<OpinionData> getOpinionDataList() {
		return opinionDataList;
	}

	public void setOpinionDataList(List<OpinionData> opinionDataList) {
		this.opinionDataList = opinionDataList;
	}
//
//	public List<OpinionResponseData> getOpinionResponseDataList() {
//		return opinionResponseDataList;
//	}
//
//	public void setOpinionResponseDataList(List<OpinionResponseData> opinionResponseDataList) {
//		this.opinionResponseDataList = opinionResponseDataList;
//	}

	public OpinionResponseInfo getOpinionResponseInfo() {
		return opinionResponseInfo;
	}

	public void setOpinionResponseInfo(OpinionResponseInfo opinionResponseInfo) {
		this.opinionResponseInfo = opinionResponseInfo;
	}

	public Collection<OpinionCountData> getOpinionCountsList() {
		return opinionCountsList;
	}

	public void setOpinionCountsList(Collection<OpinionCountData> opinionCountsList) {
		this.opinionCountsList = opinionCountsList;
	}

	public ResponseGivenPendingInfo getResponseGivenPendingInfo() {
		return responseGivenPendingInfo;
	}

	public void setResponseGivenPendingInfo(ResponseGivenPendingInfo responseGivenPendingInfo) {
		this.responseGivenPendingInfo = responseGivenPendingInfo;
	}

	


	
	
	

}
