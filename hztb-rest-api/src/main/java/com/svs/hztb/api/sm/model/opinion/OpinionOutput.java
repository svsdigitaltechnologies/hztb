package com.svs.hztb.api.sm.model.opinion;

import com.svs.hztb.api.sm.model.BasicOutput;
import com.svs.hztb.api.sm.model.Error;

public class OpinionOutput extends BasicOutput {
	
	private OpinionResponseOutput opinionResponseOutput;
	private RequestOpinionOutput requestOpinionOutput;
	private Error errorOutput;

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

	public Error getErrorOutput() {
		return errorOutput;
	}

	public void setErrorOutput(Error errorOutput) {
		this.errorOutput = errorOutput;
	}

}
