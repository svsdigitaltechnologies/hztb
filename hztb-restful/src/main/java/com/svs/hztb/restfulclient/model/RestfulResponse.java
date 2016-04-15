package com.svs.hztb.restfulclient.model;

import java.util.Optional;

public class RestfulResponse<S> {
	private final int statusCode;
	private final Optional<S> response;
	private final Optional<Object> errorPayload;
	private final Optional<String> errorResponse;

	private RestfulResponse(int statusCode, Optional<S> response, Optional<String> errorResponse,
			Optional<Object> errorPayload) {
		this.statusCode = statusCode;
		this.response = response;
		this.errorResponse = errorResponse;
		this.errorPayload = errorPayload;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public Optional<S> getResponse() {
		return response;
	}

	public Optional<Object> getErrorPayload() {
		return errorPayload;
	}

	public Optional<String> getErrorResponse() {
		return errorResponse;
	}

	public static <S> RestfulResponse<S> buildResponse(int statusCode, S response) {
		return new RestfulResponse<>(statusCode, Optional.ofNullable(response), Optional.empty(), Optional.empty());
	}

	public static <S> RestfulResponse<S> buildErrorResponse(int statusCode, String errorResponse) {
		return new RestfulResponse<>(statusCode, Optional.empty(), Optional.ofNullable(errorResponse),
				Optional.empty());
	}

	public static <S> RestfulResponse<S> buildErrprResponse(int statusCode, Object errorPayload) {
		return new RestfulResponse<S>(statusCode, Optional.empty(), Optional.empty(),
				Optional.ofNullable(errorPayload));
	}
}
