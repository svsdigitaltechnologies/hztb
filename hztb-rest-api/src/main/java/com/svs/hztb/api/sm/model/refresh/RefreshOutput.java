package com.svs.hztb.api.sm.model.refresh;

import java.util.List;

import com.svs.hztb.api.sm.model.BasicOutput;
import com.svs.hztb.api.sm.model.ErrorOutput;

public class RefreshOutput extends BasicOutput {
	
	private ErrorOutput errorOutput;
	private List<OpinionData> opinionDataList;
	private List<OpinionResponseData> opinionResponseDataList;
	
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

	public List<OpinionResponseData> getOpinionResponseDataList() {
		return opinionResponseDataList;
	}

	public void setOpinionResponseDataList(List<OpinionResponseData> opinionResponseDataList) {
		this.opinionResponseDataList = opinionResponseDataList;
	}
	
	
	

}
