package com.svs.hztb.sm.common.model.business;

import com.svs.hztb.api.common.utils.HZTBRegularExpressions;
import com.svs.hztb.common.model.RequestData;
import static com.svs.hztb.sm.common.model.ServiceManagerConstants.CUSTOM_HTTP_HEADER_REQUEST_ID;
import static com.svs.hztb.sm.common.model.ServiceManagerConstants.CUSTOM_HTTP_HEADER_REQUESTOR_ID;
import static com.svs.hztb.sm.common.model.ServiceManagerConstants.CUSTOM_HTTP_HEADER_TIMESTAMP;
import static com.svs.hztb.sm.common.model.ServiceManagerConstants.HTTP_HEADER_ACCEPT;
import static com.svs.hztb.sm.common.model.ServiceManagerConstants.HTTP_HEADER_ACCEPT_LANGUAGE;
import static com.svs.hztb.sm.common.model.ServiceManagerConstants.HTTP_HEADER_CACHE_CONTROL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.HeaderParam;

public class RequestMetaData implements RequestData {
	
	public static final String DEFAULT_ACCEPT_LANGUAGE = "en-US";
	public static final String DEFAULT_CACHE_CONTROL = "no-store";

	@HeaderParam(HTTP_HEADER_ACCEPT)
	@Pattern(regexp = HZTBRegularExpressions.ACCEPT)
	private String accept;
	
	@HeaderParam(HTTP_HEADER_CACHE_CONTROL)
	@Pattern(regexp = HZTBRegularExpressions.CACHE_CONTROL)
	private String cacheControl;
	
	@HeaderParam(HTTP_HEADER_ACCEPT_LANGUAGE)
	@Pattern(regexp = HZTBRegularExpressions.ACCEPT_LANGUAGE)
	private String acceptLanguage;
	
	@HeaderParam(CUSTOM_HTTP_HEADER_REQUEST_ID)
	@Pattern(regexp = HZTBRegularExpressions.REQUEST_ID)
	@NotNull
	@Size(min = 1, max = 64)
	private String requestId;
	
	@HeaderParam(CUSTOM_HTTP_HEADER_REQUESTOR_ID)
	@Pattern(regexp = HZTBRegularExpressions.REQUESTOR_ID)
	private String requestorId;
	
	@HeaderParam(CUSTOM_HTTP_HEADER_TIMESTAMP)
	private String timestamp;
	
	@Override
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getCacheControl() {
		return cacheControl;
	}

	public void setCacheControl(String cacheControl) {
		this.cacheControl = cacheControl;
	}

	public String getAcceptLanguage() {
		return acceptLanguage;
	}

	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}

	public String getRequestorId() {
		return requestorId;
	}

	public void setRequestorId(String requestorId) {
		this.requestorId = requestorId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	
}
