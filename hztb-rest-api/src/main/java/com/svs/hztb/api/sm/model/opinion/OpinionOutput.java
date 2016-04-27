package com.svs.hztb.api.sm.model.opinion;

import com.svs.hztb.api.sm.model.BasicOutput;

public class OpinionOutput extends BasicOutput {
	private OpinionResponseOutput opinionResponseOutput;
	private RequestOpinionOutput requestOpinionOutput;
	private Error errorVO;

	public RequestOpinionOutput getRequestOpinionOutput() {
		return requestOpinionOutput;
	}

	public void setRequestOpinionOutput(RequestOpinionOutput requestOpinionOutput) {
		this.requestOpinionOutput = requestOpinionOutput;
	}

	public OpinionResponseOutput getOpinionResponseOutput() {
		return opinionResponseOutput;
	}

	public void setOpinionResponseOutput(OpinionResponseOutput opinionResponseOutput) {
		this.opinionResponseOutput = opinionResponseOutput;
	}

	public Error getErrorVO() {
		return errorVO;
	}

	public void setErrorVO(Error errorVO) {
		this.errorVO = errorVO;
	}

	
	
	
	
	
}
