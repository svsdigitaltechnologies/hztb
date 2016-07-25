package com.svs.hztb.common.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HztbResponse {

	private ResponseHeader header;

	@JsonCreator
	public HztbResponse(@JsonProperty("header") ResponseHeader header) {
		this.header = header;
	}

	public ResponseHeader getHeader() {
		return header;
	}
}
