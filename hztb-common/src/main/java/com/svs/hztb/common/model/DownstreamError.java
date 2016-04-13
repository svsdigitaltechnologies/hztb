package com.svs.hztb.common.model;

public class DownstreamError {
	private final Integer code;
	private final String message;

	public DownstreamError(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
