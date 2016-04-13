package com.svs.hztb.common.enums;

import org.springframework.http.HttpStatus;

import com.svs.hztb.common.model.PlatformStatusCode;
import com.svs.hztb.common.model.StatusCode;

public enum ServiceManagerStatusCode implements StatusCode {
	
	DATA_SERVICES_ERROR("10000",String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), "Data Services Error"),
	USER_NOT_AVAILABLE("10001",String.valueOf(HttpStatus.BAD_REQUEST.value()), "User not available"),
	USER_ALREADY_REGISTERED("10002",String.valueOf(HttpStatus.BAD_REQUEST.value()), "User already registered"),
	USER_NOT_AVAILABLE_MOBILE_IMEI("10003",String.valueOf(HttpStatus.BAD_REQUEST.value()), "User not available with mobile number and imei"),
	INVALID_OTP("20000", String.valueOf(HttpStatus.BAD_REQUEST.value()), "Invalid OTP"),
	OTP_NOT_VALID("20001", String.valueOf(HttpStatus.BAD_REQUEST.value()), "OTP is either expired or invalid OTP count exceeded"),
	
	NEED_PAYMENT("30000",String.valueOf(HttpStatus.BAD_REQUEST.value()), "Need Payment");
	


	private String statusCode;
	private String httpStatusCode;
	private String message;
	
	private ServiceManagerStatusCode(String statusCode, String httpStatusCode, String message) {
		this.statusCode = statusCode;
		this.httpStatusCode = httpStatusCode;
		this.message = message;
	}
	
	
	public static StatusCode fromStatusCode(String statusCode) {
		for (ServiceManagerStatusCode code : ServiceManagerStatusCode.values()) {
			if(code.statusCode.equalsIgnoreCase(statusCode)) {
				return code;
			}
		}
		return PlatformStatusCode.ERROR_OCCURED_DURING_BUSINESS_PROCESSING;
	}
	
	public static StatusCode fromHttpStatusCode(String httpStatusCode) {
		for (ServiceManagerStatusCode code : ServiceManagerStatusCode.values()) {
			if(code.httpStatusCode.equalsIgnoreCase(httpStatusCode)) {
				return code;
			}
		}
		throw new IllegalArgumentException("Unable to resolve the HTTP Status Code for the StatusCode enum: " + httpStatusCode);
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
