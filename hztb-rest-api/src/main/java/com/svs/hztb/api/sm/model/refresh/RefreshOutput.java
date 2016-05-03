package com.svs.hztb.api.sm.model.refresh;

import java.util.List;

import com.svs.hztb.api.sm.model.BasicOutput;

public class RefreshOutput extends BasicOutput {
	
	private Error errorVO;
	private List<OpinionData> opinionDataList;
	private List<OpinionResponseData> opinionResponseDataList;
	

	public Error getErrorVO() {
		return errorVO;
	}

	public void setErrorVO(Error errorVO) {
		this.errorVO = errorVO;
	}

	public List<OpinionData> getOpinionDataList() {
		return opinionDataList;
	}

	public void setOpinionDataList(List<OpinionData> opinionDataList) {
		this.opinionDataList = opinionDataList;
	}

	public List<OpinionResponseData> getOpinionResponseDataList() {
		return opinionResponseDataList;
	}

	public void setOpinionResponseDataList(List<OpinionResponseData> opinionResponseDataList) {
		this.opinionResponseDataList = opinionResponseDataList;
	}
	
	
	

}
