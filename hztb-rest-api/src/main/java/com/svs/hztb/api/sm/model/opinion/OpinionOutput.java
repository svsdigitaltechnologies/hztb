package com.svs.hztb.api.sm.model.opinion;

import com.svs.hztb.api.sm.model.BasicOutput;
import com.svs.hztb.api.sm.model.ErrorOutput;

public class OpinionOutput extends BasicOutput {
	
	private OpinionResponseOutput opinionResponseOutput;
	private RequestOpinionOutput requestOpinionOutput;
	private ErrorOutput errorOutput;

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

	public ErrorOutput getErrorOutput() {
		return errorOutput;
	}

	public void setErrorOutput(ErrorOutput errorOutput) {
		this.errorOutput = errorOutput;
	}

}
