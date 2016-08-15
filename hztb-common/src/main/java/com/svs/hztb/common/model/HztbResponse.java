package com.svs.hztb.common.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HztbResponse {

	private Boolean isError;
	private ResponseHeader header;

	public void setHeader(ResponseHeader header) {
		this.header = header;
	}

	public HztbResponse() {
	}

	@JsonCreator
	public HztbResponse(@JsonProperty("header") ResponseHeader header) {
		this.header = header;
	}

	public ResponseHeader getHeader() {
		return header;
	}

	public Boolean getIsError() {
		return isError;
	}

	public void setIsError(Boolean isError) {
		this.isError = isError;
	}

}
