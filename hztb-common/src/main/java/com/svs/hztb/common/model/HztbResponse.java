package com.svs.hztb.common.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HztbResponse {
	
	@Valid
	@NotNull
	private ResponseHeader header;

	@JsonCreator
	public HztbResponse(@JsonProperty("header") ResponseHeader header) {
		this.header = header;
	}

	public ResponseHeader getHeader() {
		return header;
	}
}
