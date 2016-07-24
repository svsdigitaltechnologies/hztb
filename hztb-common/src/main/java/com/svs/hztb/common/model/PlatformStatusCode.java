package com.svs.hztb.common.model;

import com.svs.hztb.common.exception.SystemError;

public enum PlatformStatusCode implements StatusCode {

	ERROR_OCCURED_DURING_BUSINESS_PROCESSING("50000", "500",
			"Error Occured during Business Processing"), BACKEND_SYSTEM_ERROR("50001", "500",
					"Backend System Error"), SUCCESS("00000", "200", "Success"), PAYLOAD_COULD_NOT_BE_PARSED("90000",
							"400", "Payload could not be parsed"), MANDATORY_DOCUMENT_FIELD_MISSING("90001", "400",
									"Mandatory document/field missing"), INVALID_FIELD_LENGTH("90002", "400",
											"Invalid field length"), INVALID_FIELD_TYPE("90003", "400",
													"Invalid field type"), INVALID_FIELD_VALUE("90004", "400",
															"Invalid field value"), INVALID_HTTP_HEADER("90005", "400",
																	"Invalid HTTP Header"), NO_CONTENT_TYPE_IN_THE_HEADER(
																			"90006", "400",
																			"No Content-Type in HTTP header");

	private String statusCode;
	private String httpStatusCode;
	private String message;

	private PlatformStatusCode(String statusCode, String httpStatusCode, String message) {
		this.statusCode = statusCode;
		this.httpStatusCode = httpStatusCode;
		this.message = message;
	}

	public static StatusCode fromStatusCode(String statusCode) {
		for (PlatformStatusCode code : PlatformStatusCode.values()) {
			if (code.statusCode.equalsIgnoreCase(statusCode)) {
				return code;
			}
		}
		return ERROR_OCCURED_DURING_BUSINESS_PROCESSING;
	}

	public static StatusCode fromHttpStatusCode(String httpStatusCode) {
		for (PlatformStatusCode code : PlatformStatusCode.values()) {
			if (code.httpStatusCode.equalsIgnoreCase(httpStatusCode)) {
				return code;
			}
		}
		throw new SystemError("Unable to resolve the HTTP Status Code for the StatusCode enum: " + httpStatusCode);
	}

	@Override
	public String getStatusCode() {
		return statusCode;
	}

	@Override
	public String getHttpStatusCode() {
		return httpStatusCode;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
